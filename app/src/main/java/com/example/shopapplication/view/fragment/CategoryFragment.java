package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopapplication.R;


public class CategoryFragment extends Fragment {

    public static final String CATEGORY_NAME = "category_name";
    private String getCategoryName;
    private TextView mTextView;

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
        mTextView = view.findViewById(R.id.fragment_category_textview);
        mTextView.setText(getCategoryName);

        return view;
    }
}