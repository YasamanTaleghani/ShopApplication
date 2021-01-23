package com.example.shopapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CustomerPreferences {
    public static final String PREF_SHOP_LIST = "customerId";

    public static boolean putCustomerInPreferences(Activity activity,int customerId){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_SHOP_LIST, customerId);
        editor.commit();
        return true;
    }

    public static int getCustomerIfPreferences(Activity activity, String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        int temp = sharedPreferences.getInt(key, 0);
        return temp;
    }

}
