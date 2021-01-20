package com.example.shopapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ShoppingListPreferences {
    private static final String PREF_SHOP_LIST = "shopList";

    public static boolean addFavoriteItem(Activity activity, String favoriteItem){
        //Get previous favorite items
        String favoriteList = getStringFromPreferences(activity,null,PREF_SHOP_LIST);
        // Append new Favorite item
        if(favoriteList!=null){
            favoriteList = favoriteList+","+favoriteItem;
        }else{
            favoriteList = favoriteItem;
        }
        // Save in Shared Preferences
        return putStringInPreferences(activity,favoriteList);
    }

    public static String[] getFavoriteList(Activity activity){
        String favoriteList = getStringFromPreferences(activity,null,PREF_SHOP_LIST);
        return convertStringToArray(favoriteList);
    }
    private static boolean putStringInPreferences(Activity activity,String nick){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SHOP_LIST, nick);
        editor.commit();
        return true;
    }
    private static String getStringFromPreferences(Activity activity,String defaultValue,String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }

    private static String[] convertStringToArray(String str){
        String[] arr = str.split(",");
        return arr;
    }

}
