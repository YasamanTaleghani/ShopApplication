package com.example.shopapplication.retrofit;


import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.customer.Billing;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.retrofit.orders.LineItemsItem;
import com.example.shopapplication.retrofit.orders.OrdersResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ShopService {

    @GET(NetworkParams.PATH_PRODUCTS)
    Call<List<ProductsItem>> listItems
            (@QueryMap Map<String, String> options, @Query("page") int page);

    @GET(NetworkParams.PRODUCTION_ID)
    Call<ProductsItem> item(@Path("id") int id,  @QueryMap Map<String, String> options);

    @GET(NetworkParams.PRODUCTION_CATEGORIES)
    Call<List<CategoryResponse>> listCategories (@QueryMap Map<String, String> options);

    @GET(NetworkParams.PATH_PRODUCTS)
    Call<List<ProductsItem>> listSearchItems
            (@QueryMap Map<String, String> options,
             @Query("page") int pageSearch);

    @FormUrlEncoded
    @POST(NetworkParams.POST_CUSTOMERS)
    Call<CustomerResponse> customerResponse(
            @QueryMap Map<String, String> options,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("billing")Billing billing);

    @GET(NetworkParams.POST_CUSTOMERS)
    Call<List<CustomerResponse>> listCustomers
            (@QueryMap Map<String, String> options,
             @Query("page") int pageCustomers);

    @FormUrlEncoded
    @POST(NetworkParams.POST_ORDERS)
    Call<OrdersResponse> orderResponse(
            @QueryMap Map<String, String> options,
            @Field("total") String total,
            @Field("customer_id") int customerId,
            @Field("discountTotal") String discountTotal,
            @Field("billing") Billing billing,
            @Field("line_items")List<LineItemsItem> items);
}
