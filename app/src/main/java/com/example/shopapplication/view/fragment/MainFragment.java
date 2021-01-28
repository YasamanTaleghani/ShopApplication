package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.InitialLoadingAdapter;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.retrofit.Products.ImagesItem;
import com.example.shopapplication.retrofit.Products.LoadingProduct;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.utilities.SettingPreferenses;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {

    public static final String NOTIFICATION_TAG = "notificationTag";
    private RecyclerViewAdapter mNewestItemsAdapter;
    private RecyclerViewAdapter mHighestRankedAdapter;
    private InitialLoadingAdapter mAdapterLoadingItems;
    private RecyclerView mRecyclerViewNewestItems;
    private RecyclerView mRecyclerViewHighestRanked;

    private ProductionViewModel mProductionViewModel;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationWorkManager();
        fetchItems();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        findViews(view);
        //initView();

        return view;
    }

    private void notificationWorkManager(){

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .build();

        int timeInterval = SettingPreferenses.getTimeIntervalPreferences
                (getActivity(), SettingPreferenses.TIME_INTERVAL);

        PeriodicWorkRequest periodicWorkRequest=
                new PeriodicWorkRequest.Builder(
                        ProductionViewModel.MyWorker.class, timeInterval, TimeUnit.HOURS)
                        .addTag(NOTIFICATION_TAG)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(getContext()).enqueue(periodicWorkRequest);

    }

    private void initView(){
        List<LoadingProduct> productsItemsInitial = new ArrayList<>();
        LoadingProduct item = new LoadingProduct(
                "در حال بارگذاری",
                "در حال بارگذاری",
                R.drawable.ic_loading);

        for (int i = 0; i < 10; i++) {
            productsItemsInitial.add(item);
        }

        setInitialRecyclerView(productsItemsInitial, mRecyclerViewNewestItems);
        setInitialRecyclerView(productsItemsInitial, mRecyclerViewHighestRanked);
    }

    private void fetchItems() {
        mProductionViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);

        mProductionViewModel.fetchHighestRankedAndNewestItemsAsync();
        mProductionViewModel.getHighestRankedItemsLiveData().observe(this,
                new Observer<List<ProductsItem>>() {
            @Override
            public void onChanged(List<ProductsItem> items) {
                setHighestRankedItemsAdapter(items);
            }
        });

        mProductionViewModel.getNewestItemsLiveData().observe(this,
                new Observer<List<ProductsItem>>() {
            @Override
            public void onChanged(List<ProductsItem> items) {
                setNewestItemsAdapter(items);
            }
        });
    }



    private void findViews(View view) {
        mRecyclerViewNewestItems = view.findViewById(R.id.recyclerViewNewestItems);
        mRecyclerViewHighestRanked = view.findViewById(R.id.recyclerViewHighestRanked);
    }

    private void setNewestItemsAdapter(List<ProductsItem> items) {

        int newestItemsId = items.get(0).getId();
        SettingPreferenses.putNewestAppItemPreferences(getActivity(), newestItemsId);

        if (items.size()>0){
            mNewestItemsAdapter = new RecyclerViewAdapter(getActivity(), items);
            mRecyclerViewNewestItems.setAdapter(mNewestItemsAdapter);
            mRecyclerViewNewestItems.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.HORIZONTAL,
                            true));
        }
    }

    private void setHighestRankedItemsAdapter(List<ProductsItem> items){
        if (items.size()>0){
            mHighestRankedAdapter = new RecyclerViewAdapter(getActivity(), items);
            mRecyclerViewHighestRanked.setAdapter(mHighestRankedAdapter);
            mRecyclerViewHighestRanked.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.HORIZONTAL,
                            true));
        }
    }

    private void setInitialRecyclerView(List<LoadingProduct> items, RecyclerView recyclerView){
        if (items.size()>0){
            mAdapterLoadingItems = new InitialLoadingAdapter(getContext(), items);
            recyclerView.setAdapter(mAdapterLoadingItems);
            recyclerView.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.HORIZONTAL,
                            true));
        }
    }
}