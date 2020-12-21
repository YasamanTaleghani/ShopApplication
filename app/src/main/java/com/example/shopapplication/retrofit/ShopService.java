package com.example.shopapplication.retrofit;

import com.example.shopapplication.model.ProductionItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ShopService {

    @GET(NetworkParams.PATH_PRODUCTS)
    Call<List<ProductionItem>> listItems(@QueryMap Map<String, String> options);
}
