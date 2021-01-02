package com.example.shopapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {


    private List<ProductsItem> mNewestItems = new ArrayList<>();
    private List<ProductsItem> mHighestRankedItems = new ArrayList<>();
    private RecyclerViewAdapter mNewestItemsAdapter;
    private RecyclerViewAdapter mHighestRankedAdapter;
    private RecyclerView mRecyclerViewNewestItems;
    private RecyclerView mRecyclerViewHighestRanked;
    private ProductionRepository mRepository;

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
        mRepository = ProductionRepository.getInstance();
        mRepository.fetchItemsAsyncNewestProducts(new ProductionRepository.CallBacksNewestItems() {
            @Override
            public void onNewestItemsResponse(List<ProductsItem> items) {
                setNewestItemsAdapter(items);
            }
        });

        mRepository.fetchItemsAsyncHighestRate(new ProductionRepository.CallBacksHighestRankedItems() {
            @Override
            public void onHighestRankedItemsResponse(List<ProductsItem> items) {
                setHighestRankedItemsAdapter(items);
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