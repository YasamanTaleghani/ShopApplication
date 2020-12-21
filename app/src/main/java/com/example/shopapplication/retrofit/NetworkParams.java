package com.example.shopapplication.retrofit;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String PATH_PRODUCTS = "products";
    public static final String CONSUMER_KEY = "ck_f025265e3479f7bee8e93bffe5685517b93ec27d";
    public static final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";
    public static final String ORDER_BY_RATING = "rating";
    public static final String ORDER_BY_DATE = "date";
    public static final String ASC = "asc";

    public static final Map<String,String> PRODUCTS_LIST = new HashMap<String,String>(){{
        put("consumerKey", CONSUMER_KEY);
        put("consumerSecret", CONSUMER_SECRET);
    }};


    public static final Map<String,String> HIGHEST_RATE_PRODUCTS_LIST = new HashMap<String,String>(){{
        put("order_by", ORDER_BY_RATING);
        put("order", ASC);
        put("consumerKey", CONSUMER_KEY);
        put("consumerSecret", CONSUMER_SECRET);
    }};

    public static final Map<String,String> NEWEST_PRODUCTS_LIST = new HashMap<String,String>(){{
        put("order_by", ORDER_BY_RATING);
        put("consumerKey", CONSUMER_KEY);
        put("consumerSecret", CONSUMER_SECRET);
    }};


}
