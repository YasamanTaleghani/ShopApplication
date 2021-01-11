package com.example.shopapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.model.CategoriesItem;
import com.example.shopapplication.retrofit.model.ProductsItem;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductionRepository {

    public static final String TAG = "ProductionRepository";
    private ShopService mShopService;
    private static ProductionRepository mRepository = null;
    public static final int PAGE_HIGHEST_RANKED_ITEMS = 1;
    public static final int PAGE_NEWEST_ITEMS = 1;
    public static final int PAGE_ITEMS = 1;

    private MutableLiveData<List<ProductsItem>> mHighestRankedItemsLiveData=new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mNewestItemsLiveData= new MutableLiveData<>();
    private MutableLiveData<ProductsItem> mItemLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryResponse>> mCategoryItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mItemsLiveData= new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mSearchItemsLiveData = new MutableLiveData<>();

    //Constructor
    private ProductionRepository() {
        mShopService = RetrofitInstance.getInstance().create(ShopService.class);
    }

    //Getter
    public MutableLiveData<List<ProductsItem>> getHighestRankedItemsLiveData() {
        return mHighestRankedItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getNewestItemsLiveData() {
        return mNewestItemsLiveData;
    }

    public MutableLiveData<ProductsItem> getItemLiveData() {
        return mItemLiveData;
    }

    public MutableLiveData<List<CategoryResponse>> getCategoryItemsLiveData() {
        return mCategoryItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getItemsLiveData() {
        return mItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
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

    public void fetchItemsAsyncHighestRate(){
        Call<List<ProductsItem>> call =
                mShopService.listItems(
                        NetworkParams.getHighestRateOptions(), PAGE_HIGHEST_RANKED_ITEMS);

        call.enqueue(new Callback<List<ProductsItem>>() {

            @Override
            public void onResponse
            (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> productsItems = response.body();

                //Update RecyclerView
                mHighestRankedItemsLiveData.setValue(productsItems);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }

        });
    }

    public void fetchItemsAsyncNewestProducts(){
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getNewestOptions(),PAGE_NEWEST_ITEMS);

        call.enqueue(new Callback<List<ProductsItem>>() {

            //this run on main thread
            @Override
            public void onResponse
            (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> productsItems = response.body();

                //Update RecyclerView
                mNewestItemsLiveData.setValue(productsItems);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }

    public void fetchItem(int id){
        Call<ProductsItem> call = mShopService.item(id, NetworkParams.getBaseOptions());

        call.enqueue(new Callback<ProductsItem>() {
            @Override
            public void onResponse(Call<ProductsItem> call, Response<ProductsItem> response) {
                ProductsItem item = response.body();

                //Log.d(TAG, "Get item: " + response.body().getName());

                mItemLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ProductsItem> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoriesAsync(){
        Call<List<CategoryResponse>> call =
                mShopService.listCategories(NetworkParams.getBaseOptions());

        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call,
                                   Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponses = response.body();

                for (int i = 0; i < categoryResponses.size() ; i++) {
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                }

                mCategoryItemsLiveData.setValue(categoryResponses);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductionItemsAsync(){
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getBaseOptions(), PAGE_ITEMS);

        call.enqueue(new Callback<List<ProductsItem>>() {
            @Override
            public void onResponse(Call<List<ProductsItem>> call,
                                   Response<List<ProductsItem>> response) {
                List<ProductsItem> items = response.body();

                /*for (int i = 0; i < items.size() ; i++) {
                    List<CategoriesItem> categories = response.body().get(i).getCategories();
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                    if(categories.size()>0){
                        for (int j = 0; j < categories.size() ; j++) {
                            Log.d(TAG, "Get Response: " + categories.get(j).getName());
                        }
                    }
                }*/

                mItemsLiveData.setValue(items);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSearchItemsAsync(String query){
        Call<List<ProductsItem>> call =
                mShopService.listSearchItems(NetworkParams.getSearchOptions(query), 1);

        call.enqueue(new Callback<List<ProductsItem>>() {
            @Override
            public void onResponse(Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> searchItems = response.body();

                mSearchItemsLiveData.setValue(searchItems);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}
