package com.example.shopapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CustomerPreferences {
    public static final String PREF_SHOP_LIST = "customerEmail";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String CUSTOMER_MAIL = "customerName";

    public static boolean putCustomerInPreferences(Activity activity,String customerEmail){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SHOP_LIST, customerEmail);
        editor.commit();
        return true;
    }

    private static SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences("com.example.shopapplication", Context.MODE_PRIVATE);
    }

    public static String getCustomerIfPreferences(Activity activity, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        String temp = sharedPreferences.getString(key, null);
        return temp;
    }

    public static boolean putCustomerId(Activity activity, int customerId){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CUSTOMER_ID, customerId);
        editor.commit();
        return true;
    }

    public static int getCustomerId(Activity activity, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        int id = sharedPreferences.getInt(key, 0);
        return id;
    }

    public static boolean putCustomerNamePreferences(Activity activity,String customerEmail){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUSTOMER_NAME, customerEmail);
        editor.commit();
        return true;
    }

    public static boolean putCustomerEmailPreferences(Activity activity,String customerEmail){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUSTOMER_MAIL, customerEmail);
        editor.commit();
        return true;
    }
}
