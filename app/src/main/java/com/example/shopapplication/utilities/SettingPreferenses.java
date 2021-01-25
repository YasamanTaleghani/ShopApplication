package com.example.shopapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreferenses {

    public static final String SETTING_ON_OFF = "settingOnOff";
    public static final String TIME_INTERVAL = "timeInterval";
    public static final String NEWEST_APP_ITEM = "neweseCurrenApptItem";
    public static final String NEWEST_SERVER_ITEM = "newestServerItem";

    private static SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences("com.example.shopapplication", Context.MODE_PRIVATE);
    }

    public static boolean putSettingOnOffPreferences(Activity activity,int onOffSituation){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SETTING_ON_OFF , onOffSituation);
        editor.commit();
        return true;
    }

    public static int getSettingOnOffPreferences(Activity activity, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        int temp = sharedPreferences.getInt(key, 1);
        return temp;
    }

    public static boolean putTimeIntervalPreferences(Activity activity, int timeInterval){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIME_INTERVAL , timeInterval);
        editor.commit();
        return true;
    }

    public static int getTimeIntervalPreferences(Activity activity, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        int temp = sharedPreferences.getInt(key, 4);
        return temp;
    }

    public static boolean putNewestAppItemPreferences(Activity activity, int itemId){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NEWEST_APP_ITEM , itemId);
        editor.commit();
        return true;
    }

    public static int getNewestItemPreferences(Activity activity, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        int temp = sharedPreferences.getInt(key, 0);
        return temp;
    }

    public static boolean putNewestServerItemPreferences(Activity activity, int itemId){
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NEWEST_SERVER_ITEM , itemId);
        editor.commit();
        return true;
    }

}
