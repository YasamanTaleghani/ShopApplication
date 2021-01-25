package com.example.shopapplication.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.HorizontalRecyclerAdapter;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SearchFragment extends Fragment {

    public static final String ARG_SEARCH_QUERY = "arg_search_query";

    private String mSearchQuery;
    private ProductionViewModel mViewModel;

    private TextView mTextViewSearch;
    private HorizontalRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Button mButtonFilter, mButtonSearch;
    private int checkedItem =0;
    private List<ProductsItem> mProductsItemsUnchanged = new ArrayList<>();
    private List<ProductsItem> mProductsItemschanged = new ArrayList<>();

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
                mProductsItemsUnchanged.addAll(items);
                mProductsItemschanged.addAll(items);
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
        setListeners();

        return view;
    }

    private void findViews(View view) {
        mTextViewSearch = view.findViewById(R.id.search_text_view);
        mRecyclerView = view.findViewById(R.id.search_recycler_view);
        mButtonFilter = view.findViewById(R.id.btn_filter);
        mButtonSearch = view.findViewById(R.id.btn_search);
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

    private void setListeners() {
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpAlertDialog();
            }
        });
    }

    private void setUpAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("مرتب سازی براساس");

        //add a radio button  list
        String[] names = new String[]{"مرتبط ترین","گران ترین","ارزان ترین","جدیدترین"};

        builder.setSingleChoiceItems(names, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        checkedItem =0;
                        setUpAdapter(mProductsItemsUnchanged);
                        dialogInterface.dismiss();
                        break;
                    case 1:
                        checkedItem =1;
                        Collections.sort(mProductsItemschanged, new Comparator<ProductsItem>() {
                            @Override
                            public int compare(ProductsItem p1, ProductsItem p2) {
                                return Integer.parseInt(p2.getPrice())-Integer.parseInt(p1.getPrice());
                            }
                        });
                        setUpAdapter(mProductsItemschanged);
                        dialogInterface.dismiss();
                        break;
                    case 2:
                        checkedItem =2;
                        Collections.sort(mProductsItemschanged, new Comparator<ProductsItem>() {
                            @Override
                            public int compare(ProductsItem p1, ProductsItem p2) {
                                return Integer.parseInt(p1.getPrice())-Integer.parseInt(p2.getPrice());
                            }
                        });
                        setUpAdapter(mProductsItemschanged);
                        dialogInterface.dismiss();
                        break;
                    case 3:
                        checkedItem =3;
                        Collections.sort(mProductsItemschanged, new Comparator<ProductsItem>() {
                            @Override
                            public int compare(ProductsItem p1, ProductsItem p2) {
                                return (p1.getDateCreated()).compareTo(p2.getDateCreated());
                            }
                        });
                        setUpAdapter(mProductsItemschanged);
                        dialogInterface.dismiss();
                        break;
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }
}