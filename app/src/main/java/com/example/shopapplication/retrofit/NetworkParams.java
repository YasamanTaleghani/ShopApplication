package com.example.shopapplication.retrofit;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String PATH_PRODUCTS = "products/";
    public static final String CONSUMER_KEY = "ck_f025265e3479f7bee8e93bffe5685517b93ec27d";
    public static final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";
    public static final String PRODUCTION_ID = "products/{id}";
    public static final String PRODUCTION_CATEGORIES = "products/categories?per_page=20";
    public static final String ORDER_BY_RATING = "rating";
    public static final String ORDER_BY_DATE = "date";
    public static final String ASC = "asc";
    public static final String SEARCH = "search";
    public static final int PER_PAGE = 10;

    public static final Map<String,String> BASE_OPTIONS = new HashMap<String,String>(){{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static final Map<String,String> HIGHEST_RATE_PRODUCTS_LIST = new HashMap<String,String>(){{
        put("order_by", ORDER_BY_RATING);
        put("order", ASC);
    }};

    public static final Map<String,String> NEWEST_PRODUCTS_LIST = new HashMap<String,String>(){{
        put("order_by", ORDER_BY_DATE);
    }};

    public static Map<String, String> getBaseOptions() {
        Map<String, String> baseOptions = new HashMap<>();
        baseOptions.putAll(BASE_OPTIONS);

        return baseOptions;
    }

    public static Map<String, String> getHighestRateOptions() {
        Map<String, String> highestRateOptions = new HashMap<>();
        highestRateOptions.putAll(BASE_OPTIONS);
        highestRateOptions.putAll(HIGHEST_RATE_PRODUCTS_LIST);

        return highestRateOptions;
    }

    public static Map<String, String> getNewestOptions() {
        Map<String, String> newestOptions = new HashMap<>();
        newestOptions.putAll(BASE_OPTIONS);
        newestOptions.putAll(NEWEST_PRODUCTS_LIST);

        return newestOptions;
    }

    public static Map<String, String> getSearchOptions(String query){
        Map<String, String> searchOptions = new HashMap<>();
        searchOptions.putAll(BASE_OPTIONS);
        searchOptions.put(SEARCH, query);

        return searchOptions;
    }
}
