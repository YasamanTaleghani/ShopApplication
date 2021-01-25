package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.Products.CategoriesItem;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    public static final String CATEGORY_NAME = "category_name";
    private String getCategoryName;
    private int mIdCategory;
    private List<ProductsItem> sCategoryProductsList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;

    private TextView mTextView;
    private RecyclerView mRecyclerView;

    private ProductionViewModel mViewModel;

    public CategoryFragment() {
        // Required empty public constructor
    }
    
    public static CategoryFragment newInstance(String categoryName) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCategoryName = getArguments().getString(CATEGORY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        findViews(view);
        initViewModel();
        getProductions();

        return view;
    }

    private void findViews(View view) {
        mTextView = view.findViewById(R.id.categoryTextView);
        mRecyclerView = view.findViewById(R.id.categoryRecyclerView);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mViewModel.fetchCategoriesAsync();
        mViewModel.getCategoryItemsLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<CategoryResponse>>() {
            @Override
            public void onChanged(List<CategoryResponse> responses) {
                getIdOfCategory(responses);
            }
        });
    }

    private void getIdOfCategory(List<CategoryResponse> responses) {
        for (int i = 0; i < responses.size(); i++) {
            CategoryResponse category = responses.get(i);
            if (category.getName().equals(getCategoryName))
                mIdCategory = category.getId();
        }
    }

    private void getProductions() {
        mViewModel.fetchItems();
        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<ProductsItem>>() {
            @Override
            public void onChanged(List<ProductsItem> items) {
                checkItems(items);
                setUpRecyclerView();
            }
        });
    }

    private void checkItems(List<ProductsItem> items) {
        for (int i = 0; i < items.size(); i++) {
            ProductsItem item = items.get(i);
            List<CategoriesItem> categoriesItems = item.getCategories();
            if (categoriesItems.size()>0){
                for (int j = 0; j < categoriesItems.size() ; j++) {
                    if (categoriesItems.get(j).getName().equals(getCategoryName)){
                        sCategoryProductsList.add(item);
                        break;
                    }
                }
            }

        }
    }

    private void setUpRecyclerView() {
        mTextView.setText(getCategoryName);
        if (sCategoryProductsList.size()>0){
            mAdapter = new RecyclerViewAdapter(getActivity(), sCategoryProductsList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager
                    (new GridLayoutManager(getActivity(), 2));

        }
    }
}