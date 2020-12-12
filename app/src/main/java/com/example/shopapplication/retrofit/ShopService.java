package com.example.shopapplication.retrofit;

import com.example.shopapplication.retrofitModel.ProductsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ShopService {

    @GET(NetworkParams.PATH_PRODUCTS)
    Call<ProductsResponse> listItems(@QueryMap Map<String, String> options);
}
