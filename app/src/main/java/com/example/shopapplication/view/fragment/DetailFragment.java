package com.example.shopapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.ReviewRecyclerViewAdapter;
import com.example.shopapplication.adapter.SliderAdapter;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.Products.ImagesItem;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.LoginCustomerActivity;
import com.example.shopapplication.view.activity.ShopListActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
    private RecyclerView mRecyclerViewReview;
    private ReviewRecyclerViewAdapter mAdapter;
    private EditText mEditTextReview, mEditTextRating;
    private Button mButtonAddReview;

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
            Log.d("p","production id is: " + mId);
        }

        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
        mViewModel.fetchItem(mId);
        mViewModel.getItemLiveData().observe(this, new Observer<ProductsItem>() {
            @Override
            public void onChanged(ProductsItem productsItem) {
                initView(productsItem);
            }
        });

        mViewModel.fetchReveiws(mId);
        mViewModel.getReviewsLiveData().observe(this, new Observer<List<ReviewsResponse>>() {
            @Override
            public void onChanged(List<ReviewsResponse> reviewsResponses) {
                setUpReviewListeners(reviewsResponses);
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
        mRecyclerViewReview = view.findViewById(R.id.review_recycler_view);
        mEditTextReview = view.findViewById(R.id.review_editText);
        mEditTextRating = view.findViewById(R.id.rating_editText);
        mButtonAddReview = view.findViewById(R.id.btn_review);
    }

    private void initView(ProductsItem item) {
        mImagesItems = item.getImages();
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), mImagesItems);
        mSliderView.setSliderAdapter(sliderAdapter);

        mTextViewName.setText(item.getName());
        mTextViewPrice.setText(item.getPrice() + " تومان ");
        mTextViewRate.setText(item.getAverageRating());

        Document document = Jsoup.parse(item.getDescription());
        Elements paragraphs = document.getElementsByTag("p");
        mTextViewdesc.setText(paragraphs.text());
    }

    private void setListeners(){
        mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerEmail = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(),CustomerPreferences.PREF_SHOP_LIST);

                mViewModel.fetchItem(mId);
                mViewModel.getItemLiveData().observe(getViewLifecycleOwner(), new Observer<ProductsItem>() {
                    @Override
                    public void onChanged(ProductsItem productsItem) {
                        ProductionModel production = new ProductionModel(
                                mId,
                                customerEmail,
                                productsItem.getName(),
                                productsItem.getPrice()
                                ,productsItem.getImages().get(0).getSrc());
                        /*Log.d("tag", production.customerId + production.productionId +
                                production.productionName + production.ProductionPrice);*/
                        mViewModel.insertProductionOrder(production);
                    }
                });

                Intent intent = ShopListActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        mButtonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewText = mEditTextReview.getText().toString();
                int reviewRating = Integer.parseInt(mEditTextRating.getText().toString());
                if (reviewText==null || reviewText.length()==0){
                    Toast.makeText
                            (getActivity(),
                                    "نظر خود را وارد کنید.", Toast.LENGTH_SHORT).show();
                } else if((mEditTextRating.getText().toString()==null ||
                        (mEditTextRating.getText().toString().length()==0))){
                    Toast.makeText
                            (getActivity(),
                                    "امتیاز خود را وارد کنید.", Toast.LENGTH_SHORT).show();
                } else if
                (CustomerPreferences.getCustomerId(getActivity(), CustomerPreferences.CUSTOMER_ID)==0){
                    Intent intent = LoginCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                } else {
                    String customerName = CustomerPreferences.getCustomerIfPreferences
                            (getActivity(), CustomerPreferences.CUSTOMER_NAME);
                    String customerEmail = CustomerPreferences.getCustomerIfPreferences
                            (getActivity(), CustomerPreferences.CUSTOMER_MAIL);
                    String review = mEditTextReview.getText().toString();
                    mViewModel.postReview(
                            mId,
                            review,
                            customerName,
                            customerEmail,
                            reviewRating,
                            getActivity());

                    mEditTextRating.setText("");
                    mEditTextReview.setText("");

                }
            }
        });
    }

    private void setUpReviewListeners(List<ReviewsResponse> reviewsResponses) {
        //Log.d("review", "review recycler size is: " + reviewsResponses.size());
        if (reviewsResponses.size()>0){
            mAdapter = new ReviewRecyclerViewAdapter(getContext(), reviewsResponses, getActivity());
            mRecyclerViewReview.setAdapter(mAdapter);
            mRecyclerViewReview.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.VERTICAL,
                            false));
        }
    }
}