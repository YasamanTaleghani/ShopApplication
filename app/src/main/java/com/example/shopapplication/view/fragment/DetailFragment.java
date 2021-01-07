package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopapplication.R;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.model.ImagesItem;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.List;

public class DetailFragment extends Fragment {

    public static final String ARG_PRODUCTION_ID = "production_id";

    private int mId;
    private ProductionViewModel mProductionViewModel;


    private ImageView mImageView;
    private TextView mTextViewName;
    private TextView mTextViewPrice;
    private TextView mTextViewRate;
    private TextView mTextViewdesc;
    private List<ImagesItem> mImagesItems;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int id) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCTION_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            mId = getArguments().getInt(ARG_PRODUCTION_ID,-100);
        }
        mProductionViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mProductionViewModel.fetchItem(mId);
        mProductionViewModel.getItemLiveData().observe(this, new Observer<ProductsItem>() {
            @Override
            public void onChanged(ProductsItem productsItem) {
                initView(productsItem);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        findView(view);

        return view;
    }

    private void findView(View view) {
        mImageView = view.findViewById(R.id.image_view_detail);
        mTextViewName = view.findViewById(R.id.text_view_production_name);
        mTextViewPrice = view.findViewById(R.id.text_view_production_price);
        mTextViewRate = view.findViewById(R.id.text_view_production_rate);
        mTextViewdesc = view.findViewById(R.id.text_view_production_description);
    }

    private void initView(ProductsItem item) {
        mImagesItems = item.getImages();
        Glide.with(this)
                .load(mImagesItems.get(0).getSrc())
                .placeholder(R.drawable.ic_loading)
                .into(mImageView);

        mTextViewName.setText(item.getName());
        mTextViewPrice.setText(item.getPrice() + " تومان ");
        mTextViewRate.setText(item.getAverageRating());
        mTextViewdesc.setText(item.getDescription());
    }
}