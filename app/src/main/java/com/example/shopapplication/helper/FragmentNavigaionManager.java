package com.example.shopapplication.helper;

import androidx.fragment.app.FragmentManager;

import com.example.shopapplication.view.activity.MainActivity;

public class FragmentNavigaionManager implements NavigationManager {

    private static FragmentNavigaionManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mMainActivity;

    public static FragmentNavigaionManager getInstance(MainActivity mainActivity) {
        if (mInstance == null) {
            mInstance = new FragmentNavigaionManager();
        }
        mInstance.configure(mainActivity);
        return mInstance;
    }

    @Override
    public void showFragment (String string) {

    }

    private void configure(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        mFragmentManager = mMainActivity.getSupportFragmentManager();
    }
}
