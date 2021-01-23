package com.example.shopapplication.view.fragment;

import android.content.Intent;
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

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.HorizontalRecyclerAdapter;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.customer.Billing;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.retrofit.orders.LineItemsItem;
import com.example.shopapplication.retrofit.orders.OrdersResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.LoginCustomerActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopListFragment extends Fragment {

    private ProductionViewModel mProductionViewModel;
    private List<ProductsItem> mProductsItems;

    private RecyclerView mRecyclerView;
    private HorizontalRecyclerAdapter mAdapter;
    private Button mButtonSendOrder;

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

        int customerId = CustomerPreferences.getCustomerIfPreferences
                (getActivity(),CustomerPreferences.PREF_SHOP_LIST);
        List<ProductionModel> mIdItems = mProductionViewModel.returnAllProductionModels();
        List<ProductionModel> productionModelList = new ArrayList<>();
        for (int i = 0; i < mIdItems.size() ; i++) {
            if (mIdItems.get(i).customerId==customerId)
                productionModelList.add(mIdItems.get(i));
        }

        for (int i = 0; i < productionModelList.size(); i++) {
            mProductionViewModel.fetchItem(productionModelList.get(i).productionId);
            mProductionViewModel.getItemLiveData().observe(this, new Observer<ProductsItem>() {
                @Override
                public void onChanged(ProductsItem productsItem) {
                    mProductsItems.add(productsItem);
                    setUpAdapter(mProductsItems);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        findView(view);
        //setUpAdapter(mProductsItems);
        setListeners();

        return view;
    }

    private void findView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_shop_list);
        mButtonSendOrder =  view.findViewById(R.id.btn_send_order);
    }

    private void setUpAdapter(List<ProductsItem> items) {
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
        mButtonSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int customerId = CustomerPreferences.getCustomerIfPreferences(
                        getActivity(), CustomerPreferences.PREF_SHOP_LIST);

                int totalPrice = 0;

                if (customerId==0){
                    Intent intent = LoginCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                } else {
                    List<ProductionModel> allProducts = mProductionViewModel.returnAllProductionModels();
                    List<LineItemsItem> items = new ArrayList<>();
                    for (int i = 0; i < allProducts.size() ; i++) {
                        if (allProducts.get(i).customerId==customerId || allProducts.get(i).customerId==0){
                            ProductionModel production = allProducts.get(i);
                            LineItemsItem item = new LineItemsItem(
                                    production.getProductionPrice(),
                                    production.productionId,
                                    production.productionName);

                            totalPrice += Integer.parseInt(production.ProductionPrice);

                            items.add(item);
                        }
                    }

                    CustomerModel customerModel = mProductionViewModel.getCustomer(customerId);
                    Billing billing = new Billing(
                            customerModel.country,
                            customerModel.city,
                            customerModel.phone,
                            customerModel.address1,
                            customerModel.address2,
                            null,
                            customerModel.lastName,
                            customerModel.state,
                            customerModel.firstName,
                            customerModel.mail);

                    mProductionViewModel.postOrder(
                            String.valueOf(totalPrice),
                            customerId,
                            null,
                            billing,
                            items);
                }
            }
        });
    }

}