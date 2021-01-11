package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerViewAdapter mNewestItemsAdapter;
    private RecyclerViewAdapter mHighestRankedAdapter;
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

        fetchItems();

    }

    private void fetchItems() {
        mProductionViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mProductionViewModel.fetchHighestRankedItemsAsync();
        mProductionViewModel.fetchNewestItemsAsync();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        mRecyclerViewNewestItems = view.findViewById(R.id.recyclerViewNewestItems);
        mRecyclerViewHighestRanked = view.findViewById(R.id.recyclerViewHighestRanked);
    }

    private void setNewestItemsAdapter(List<ProductsItem> items) {

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

}