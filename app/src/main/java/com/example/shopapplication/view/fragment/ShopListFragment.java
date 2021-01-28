package com.example.shopapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.ShopListAdapter;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.retrofit.orders.LineItemsItem;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.LoginCustomerActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopListFragment extends Fragment {

    private ProductionViewModel mProductionViewModel;
    private List<ProductsItem> mProductsItems;

    private RecyclerView mRecyclerView;
    private ShopListAdapter mAdapter;
    private Button mButtonSendOrder;
    private TextView mTextViewTotalPrice;

    private ArrayList<ProductionModel> orderItems= new ArrayList<>();
    int totalPrice = 0;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        findView(view);

        String customerEmail = CustomerPreferences.getCustomerIfPreferences
                (getActivity(),CustomerPreferences.PREF_SHOP_LIST);

        mProductionViewModel.returnAllProductionModels().observe(
                getActivity(), new Observer<List<ProductionModel>>() {
                    @Override
                    public void onChanged(@Nullable List<ProductionModel> productionModels) {
                        getProductionIds(productionModels);
                    }
                });

        setListeners();

        return view;
    }

    private void getProductionIds(List<ProductionModel> productionModels) {
        List<ProductionModel> shopList = new ArrayList<>();
        for (int i = 0; i < productionModels.size() ; i++) {
            shopList.add(productionModels.get(i));
        }
        setUpAdapter(shopList);
        initView(shopList);
    }

    private void initView(List<ProductionModel> productionModels) {

        if (productionModels.size()>0) {
            for (int i = 0; i < productionModels.size() ; i++) {
                totalPrice += Integer.parseInt(productionModels.get(i).ProductionPrice);
            }
            mTextViewTotalPrice.setText(totalPrice + " هزار تومان");
        } else {
            mTextViewTotalPrice.setText("0" + "هزار تومان");
        }
    }


    private void findView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_shop_list);
        mButtonSendOrder =  view.findViewById(R.id.btn_send_order);
        mTextViewTotalPrice = view.findViewById(R.id.text_view_totalprice);
    }

    private void setUpAdapter(List<ProductionModel> items) {
        if (items.size()>0){
            mAdapter = new ShopListAdapter(getActivity(), items);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager
                    (new LinearLayoutManager(
                            getActivity(),
                            LinearLayoutManager.VERTICAL, false));

            for (int i = 0; i < items.size() ; i++) {
                orderItems.add(items.get(i));
            }
        }

    }

    private void setListeners() {
        mButtonSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerEmail = CustomerPreferences.getCustomerIfPreferences(
                        getActivity(), CustomerPreferences.PREF_SHOP_LIST);


                if (customerEmail==null){
                    Intent intent = LoginCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                } else {

                    String totalPriceOrder = String.valueOf(totalPrice);
                    int customerId = CustomerPreferences.getCustomerId(
                            getActivity(), CustomerPreferences.CUSTOMER_ID);

                    List<LineItemsItem> items = new ArrayList<>();
                    for (int i = 0; i < orderItems.size() ; i++) {
                        LineItemsItem item = new LineItemsItem(
                                orderItems.get(i).ProductionPrice,
                                orderItems.get(i).id,
                                orderItems.get(i).productionName);

                        items.add(item);
                    }

                    mProductionViewModel.postOrder(
                            totalPriceOrder,
                            customerId,
                            "0");

                    Toast.makeText(getActivity(), "سفارش شما با موفقیت به ثبت رسید", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    //TODO: delete all products
                    mProductionViewModel.deleteAllProductionOrder();
                }
            }
        });
    }
}