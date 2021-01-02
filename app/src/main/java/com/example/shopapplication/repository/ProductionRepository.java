package com.example.shopapplication.repository;

import android.util.Log;

import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;
import com.example.shopapplication.retrofit.model.ProductsItem;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductionRepository {

    public static final String TAG = "ProductionRepository";
    private List<ProductsItem> mNewestItems = new ArrayList<>();
    private ShopService mShopService;
    private static ProductionRepository mRepository = null;

    //Constructor
    private ProductionRepository() {
        mShopService = RetrofitInstance.getInstance().create(ShopService.class);
    }

    //Methods
    public static ProductionRepository getInstance(){
        if (mRepository == null){
            mRepository = new ProductionRepository();
            return mRepository;
        }
        else {
            return mRepository;
        }
    }

    public List<ProductsItem> fetchItemsHighestRate(){

        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getHighestRateOptions());

        try {
            List<ProductsItem> response = call.execute().body();
            return response;

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void fetchItemsAsyncHighestRate(CallBacksHighestRankedItems callBacks){
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getHighestRateOptions());

        call.enqueue(new Callback<List<ProductsItem>>() {

            @Override
            public void onResponse
            (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> productsItems = response.body();

                for (int i = 0; i < productsItems.size() ; i++) {
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                }

                //Update RecyclerView
                callBacks.onHighestRankedItemsResponse(productsItems);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }

        });
    }

    public List<ProductsItem> fetchItemsNewestProducts(){

        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getNewestOptions());
        try {
            List<ProductsItem> response = call.execute().body();
            return response;

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void fetchItemsAsyncNewestProducts(CallBacksNewestItems callBacks){
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getNewestOptions());

        call.enqueue(new Callback<List<ProductsItem>>() {

            //this run on main thread
            @Override
            public void onResponse
            (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> productsItems = response.body();

                //Update RecyclerView
                callBacks.onNewestItemsResponse(productsItems);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }

    public void fetchItem(int id, CallBackItem callBackItem){
        Call<ProductsItem> call = mShopService.item(id, NetworkParams.getBaseOptions());

        call.enqueue(new Callback<ProductsItem>() {
            @Override
            public void onResponse(Call<ProductsItem> call, Response<ProductsItem> response) {
                ProductsItem item = response.body();

                Log.d(TAG, "Get item: " + response.body().getName());

                callBackItem.onItemResponse(item);
            }

            @Override
            public void onFailure(Call<ProductsItem> call, Throwable t) {

            }
        });
    }

    public interface CallBacksNewestItems{
        void onNewestItemsResponse(List<ProductsItem> items);
    }

    public interface CallBacksHighestRankedItems{
        void onHighestRankedItemsResponse(List<ProductsItem> items);
    }

    public interface CallBackItem{
        void onItemResponse(ProductsItem item);
    }
}
