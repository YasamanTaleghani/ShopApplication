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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductionRepository {

    public static final String TAG = "ProductionRepository";
    private List<ProductionItem> mNewestItems = new ArrayList<>();
    private ShopService mShopService;
    private Retrofit mRetrofit;

    //Getter & Setter
    public List<ProductionItem> getNewestItems() {
        return mNewestItems;
    }

    //Constructor
    public ProductionRepository() {
        mRetrofit =  RetrofitInstance.getInstance();
        mShopService = mRetrofit.create(ShopService.class);
    }

    //Methods
    public List<ProductionItem> fetchItems(){

        Call<List<ProductionItem>> call =
                mShopService.listItems(NetworkParams.getHighestRateOptions());
        try {
            Response<List<ProductionItem>> response = call.execute();
            return response.body();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<ProductionItem> fetchItemsAsync(){
        List<ProductionItem> productionItems = new ArrayList<>();


        Call<List<ProductionItem>> call =
                mShopService.listItems(NetworkParams.getHighestRateOptions());

        call.enqueue(new Callback<List<ProductionItem>>() {

            //this run on main thread
            @Override
            public void onResponse
            (Call<List<ProductionItem>> call, Response<List<ProductionItem>> response) {
                List<ProductionItem> items = response.body();
                productionItems.addAll(items);

            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductionItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

        return productionItems;
    }
}
