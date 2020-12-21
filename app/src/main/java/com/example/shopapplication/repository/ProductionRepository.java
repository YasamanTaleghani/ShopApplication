package com.example.shopapplication.repository;

import android.util.Log;

import com.example.shopapplication.model.ProductionItem;
import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductionRepository {

    public static final String TAG = "ProductionRepository";
    private List<ProductionItem> mItems= new ArrayList<>();
    private ShopService mShopService;
    private Retrofit mRetrofit;

    //Getter & Setter
    public List<ProductionItem> getItems() {
        return mItems;
    }
    public void setItems(List<ProductionItem> items) {
        mItems = items;
    }

    //Constructor
    public ProductionRepository() {
        mRetrofit =  RetrofitInstance.getInstance();
        mShopService = mRetrofit.create(ShopService.class);
    }

    //Methods
    public List<ProductionItem> fetchItems(){

        Call<List<ProductionItem>> call =
                mShopService.listItems(NetworkParams.PRODUCTS_LIST);
        try {
            Response<List<ProductionItem>> response = call.execute();
            return response.body();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //TODO
    private void fetchItemsAsync(){

    }
}
