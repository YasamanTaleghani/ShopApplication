package com.example.shopapplication.retrofit;

import com.example.shopapplication.retrofit.model.ProductsItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {


    public static Retrofit getInstance(){

        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
