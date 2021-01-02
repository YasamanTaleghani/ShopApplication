package com.example.shopapplication.retrofit;


import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.model.ProductsItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ShopService {

    @GET(NetworkParams.PATH_PRODUCTS)
    Call<List<ProductsItem>> listItems(@QueryMap Map<String, String> options);

    @GET(NetworkParams.PRODUCTION_ID)
    Call<ProductsItem> item(@Path("id") int id,  @QueryMap Map<String, String> options);

    @GET(NetworkParams.PRODUCTION_CATEGORIES)
    Call<List<CategoryResponse>> listCategories(@QueryMap Map<String, String> options);

}
