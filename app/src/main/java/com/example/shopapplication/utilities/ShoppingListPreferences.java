package com.example.shopapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class ShoppingListPreferences {
    private static final String PREF_SHOP_LIST = "shopList";

    public static String getPrefShopList(Context context){

        return getSharedPreferences(context).getString(PREF_SHOP_LIST, null);
    }

    public static void setPrefShopList(Context context, String item){

        getSharedPreferences(context)
                .edit()
                .putString(PREF_SHOP_LIST, item)
                .apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
