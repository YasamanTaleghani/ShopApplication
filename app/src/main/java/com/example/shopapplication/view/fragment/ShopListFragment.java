package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.HorizontalRecyclerAdapter;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.utilities.ShoppingListPreferences;
import com.example.shopapplication.viewmodel.ProductionViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ShopListFragment extends Fragment {

    private ProductionViewModel mProductionViewModel;
    private List<ProductsItem> mProductsItems;

    private RecyclerView mRecyclerView;
    private HorizontalRecyclerAdapter mAdapter;

    public ShopListFragment() {
        // Required empty public constructor
    }

    public static ShopListFragment newInstance() {
        ShopListFragment fragment = new ShopListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductionViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);

        String[] mIds = ShoppingListPreferences.getFavoriteList(getActivity());
        int[] mIdItems = new int[mIds.length];
        for (int i = 0; i < mIds.length ; i++) {
            mIdItems[i] = Integer.parseInt(mIds[i]);
        }
        for (int i = 0; i < mIdItems.length; i++) {
            mProductionViewModel.fetchItem(mIdItems[i]);
            mProductionViewModel.getItemLiveData().observe(this, new Observer<ProductsItem>() {
                @Override
                public void onChanged(ProductsItem productsItem) {
                    mProductsItems.add(productsItem);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        findView(view);
        setUpAdapter(mProductsItems);

        return view;
    }

    private void findView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_shop_list);
    }

    private void setUpAdapter(List<ProductsItem> items) {
        if (items.size()>0){
            mAdapter = new HorizontalRecyclerAdapter(getActivity(), items);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.VERTICAL, false));
        }
    }


}