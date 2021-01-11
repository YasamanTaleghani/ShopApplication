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
import android.widget.TextView;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.HorizontalRecyclerAdapter;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.List;


public class SearchFragment extends Fragment {

    public static final String ARG_SEARCH_QUERY = "arg_search_query";
    private String mSearchQuery;
    private ProductionViewModel mViewModel;

    private TextView mTextViewSearch;
    private HorizontalRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String search) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_QUERY, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            mSearchQuery = getArguments().getString(ARG_SEARCH_QUERY, "");
        }
        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mViewModel.fetchSearchItems(mSearchQuery);
        mViewModel.getSearchItemsLiveData().observe(this, new Observer<List<ProductsItem>>() {
            @Override
            public void onChanged(List<ProductsItem> items) {
                setUpAdapter(items);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        mTextViewSearch = view.findViewById(R.id.search_text_view);
        mRecyclerView = view.findViewById(R.id.search_recycler_view);
    }

    private void setUpAdapter(List<ProductsItem> items) {
        mTextViewSearch.setText(mSearchQuery);
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