package com.example.shopapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.model.CategoriesItem;
import com.example.shopapplication.retrofit.model.ProductsItem;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductionRepository {

    public static final String TAG = "ProductionRepository";
    private ShopService mShopService;
    private static ProductionRepository mRepository = null;
    private int pageHighestRankedOrder = 1;
    private int pageNewestItems = 1;
    public static final int PAGE_ITEMS = 1;

    private List<ProductsItem> mItemsRanked = new ArrayList<>();
    private List<ProductsItem> mItemsNewest = new ArrayList<>();
    private boolean request = true;

    private MutableLiveData<List<ProductsItem>> mHighestRankedItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mNewestItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductsItem> mItemLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryResponse>> mCategoryItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mItemsLiveData = new MutableLiveData<>();
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
    public static ProductionRepository getInstance() {
        if (mRepository == null) {
            mRepository = new ProductionRepository();
            return mRepository;
        } else {
            return mRepository;
        }
    }

    public void fetchItemsAsyncHighestRateAndNewest() {


            Call<List<ProductsItem>> call =
                    mShopService.listItems(
                            NetworkParams.getHighestRateOptions(), pageHighestRankedOrder);

            call.enqueue(new Callback<List<ProductsItem>>() {

                @Override
                public void onResponse
                        (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {

                    if (response.isSuccessful()){
                        List<ProductsItem> productsItems = response.body();

                        for (int i = 0; i <productsItems.size() ; i++) {
                            Log.d(TAG, "product:" + (i+1) + productsItems.get(i).getName());
                            mItemsRanked.add(productsItems.get(i));
                            mItemsNewest.add(productsItems.get(i));
                        }
                        if (productsItems.size()==10){
                            pageHighestRankedOrder++;
                            fetchItemsAsyncHighestRateAndNewest();
                        } else {
                            Collections.sort(mItemsRanked, new Comparator<ProductsItem>() {
                                @Override
                                public int compare(ProductsItem p1, ProductsItem p2) {
                                    return p1.getAverageRating().compareTo(p2.getAverageRating());
                                }
                            });
                            Collections.reverse(mItemsRanked);
                            mHighestRankedItemsLiveData.setValue(mItemsRanked);

                            Collections.sort(mItemsNewest, new Comparator<ProductsItem>() {
                                @Override
                                public int compare(ProductsItem p1, ProductsItem p2) {
                                    return p1.getDateCreated().compareTo(p2.getDateCreated());
                                }
                            });
                            mNewestItemsLiveData.setValue(mItemsNewest);
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }

            });


        /*Collections.sort(mItemsRanked, new Comparator<ProductsItem>() {
            @Override
            public int compare(ProductsItem p1, ProductsItem p2) {
                return p1.getAverageRating().compareTo(p2.getAverageRating());
            }
        });
        mHighestRankedItemsLiveData.setValue(mItemsRanked);

        Collections.sort(mItemsRanked, new Comparator<ProductsItem>() {
            @Override
            public int compare(ProductsItem p1, ProductsItem p2) {
                return p1.getDateCreated().compareTo(p2.getDateCreated());
            }
        });

        mNewestItemsLiveData.setValue(mItemsNewest);*/

    }


    public void fetchItem(int id) {
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

    public void fetchCategoriesAsync() {
        Call<List<CategoryResponse>> call =
                mShopService.listCategories(NetworkParams.getBaseOptions());

        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call,
                                   Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponses = response.body();

                /*for (int i = 0; i < categoryResponses.size() ; i++) {
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                }*/

                mCategoryItemsLiveData.setValue(categoryResponses);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductionItemsAsync() {
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getBaseOptions(),1);

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

    public void fetchSearchItemsAsync(String query) {
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
