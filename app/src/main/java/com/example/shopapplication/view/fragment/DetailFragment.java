package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.SliderAdapter;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.model.ImagesItem;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.viewmodel.ProductionViewModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class DetailFragment extends Fragment {

    public static final String ARG_PRODUCTION_ID = "production_id";

    private int mId;
    private ProductionViewModel mViewModel;


    private TextView mTextViewName;
    private TextView mTextViewPrice;
    private TextView mTextViewRate;
    private TextView mTextViewdesc;
    private List<ImagesItem> mImagesItems;
    private SliderView mSliderView;
    private Button mButtonBuy;

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
        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mViewModel.fetchItem(mId);
        mViewModel.getItemLiveData().observe(this, new Observer<ProductsItem>() {
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
        setListeners();

        return view;
    }

    private void findView(View view) {
        mTextViewName = view.findViewById(R.id.text_view_production_name);
        mTextViewPrice = view.findViewById(R.id.text_view_production_price);
        mTextViewRate = view.findViewById(R.id.text_view_production_rate);
        mTextViewdesc = view.findViewById(R.id.text_view_production_description);
        mSliderView =  view.findViewById(R.id.image_view_slider);
        mButtonBuy = view.findViewById(R.id.btn_add_to_buy_list);
    }

    private void initView(ProductsItem item) {
        mImagesItems = item.getImages();
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), mImagesItems);
        mSliderView.setSliderAdapter(sliderAdapter);

        mTextViewName.setText(item.getName());
        mTextViewPrice.setText(item.getPrice() + " تومان ");
        mTextViewRate.setText(item.getAverageRating());
        mTextViewdesc.setText(item.getDescription());
    }

    private void setListeners(){
        mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int customerId = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(),CustomerPreferences.PREF_SHOP_LIST);

                mViewModel.fetchItem(mId);
                mViewModel.getItemLiveData().observe(getViewLifecycleOwner(), new Observer<ProductsItem>() {
                    @Override
                    public void onChanged(ProductsItem productsItem) {
                        ProductionModel production = new ProductionModel(
                                customerId,
                                mId,
                                productsItem.getName(),
                                productsItem.getPrice());
                        mViewModel.insertProductionOrder(production);
                    }
                });


            }
        });
    }
}