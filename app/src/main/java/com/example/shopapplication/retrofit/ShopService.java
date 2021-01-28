package com.example.shopapplication.retrofit;


import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.retrofit.orders.OrdersResponse;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET(NetworkParams.CUSTOMERS)
    Call<CustomerResponse> getCustomer
            (@Path("id") int customerId,
             @QueryMap Map<String, String> options);

    @GET(NetworkParams.CUSTOMERS)
    Call<List<CustomerResponse>> listCustomers
            (@QueryMap Map<String, String> options,
             @Query("page") int pageCustomers);

    @GET(NetworkParams.REVIEWS)
    Call<List<ReviewsResponse>> listReviews
            (@QueryMap Map<String, String> options,
             @Query("page") int pageReviews,
             @Query("product") int productId);

    //POST
    @FormUrlEncoded
    @POST(NetworkParams.CUSTOMERS)
    Call<CustomerResponse> customerResponse(
            @QueryMap Map<String, String> options,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email);

    @FormUrlEncoded
    @POST(NetworkParams.POST_ORDERS)
    Call<OrdersResponse> orderResponse(
            @QueryMap Map<String, String> options,
            @Field("total") String total,
            @Field("customer_id") int customerId,
            @Field("discountTotal") String discountTotal);
            //@Field("line_items")List<LineItemsItem> items);

    @FormUrlEncoded
    @POST(NetworkParams.REVIEWS)
    Call<ReviewsResponse> postReview(
            @QueryMap Map<String, String> options,
            @Field("product_id") int productId,
            @Field("review") String review,
            @Field("reviewer") String reviewer,
            @Field("reviewer_email") String reviewerEmail,
            @Field("rating") int rating);

    @FormUrlEncoded
    @PUT(NetworkParams.REVIEWS)
    Call<ReviewsResponse> updateReview(
            @Path("id") int reviewId,
            @QueryMap Map<String, String> options,
            @Field("review") String review,
            @Field("reviewer") String reviewer,
            @Field("reviewer_email") String reviewerEmail,
            @Field("rating") int rating);

    @FormUrlEncoded
    @DELETE(NetworkParams.REVIEWS)
    Call<ReviewsResponse> deleteReview(
            @Path("id") int reviewId,
            @QueryMap Map<String, String> options);
}
