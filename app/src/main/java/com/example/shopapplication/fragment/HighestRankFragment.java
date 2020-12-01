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

import static com.example.shopapplication.activity.MainActivity.mProductionItems;

public class HighestRankFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProductionAdapter mAdapter;

    public HighestRankFragment() {
        // Required empty public constructor
    }

    public static HighestRankFragment newInstance() {
        HighestRankFragment fragment = new HighestRankFragment();
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
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_highest_rank, container, false);

       findViews(view);
       initViews();

       return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    private void initViews() {
        mRecyclerView.setHasFixedSize(true);
        if (mProductionItems.size()>0){
            mAdapter = new ProductionAdapter(getContext(), mProductionItems);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }
}