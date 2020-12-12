package com.example.shopapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.ProductionAdapter;
import com.example.shopapplication.model.ProductionItem;
import com.example.shopapplication.repository.ProductionRepository;

import java.util.List;


public class NewestItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProductionAdapter mAdapter;
    private ProductionRepository mRepository;

    public NewestItemsFragment() {
        // Required empty public constructor
    }

    public static NewestItemsFragment newInstance() {
        NewestItemsFragment fragment = new NewestItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newest_items, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    private void initViews() {
        List<ProductionItem> items = mRepository.getItems();
        mRecyclerView.setHasFixedSize(true);
        if (items.size()>0){
            mAdapter = new ProductionAdapter(getContext(), items);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }
}