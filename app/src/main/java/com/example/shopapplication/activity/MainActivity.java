package com.example.shopapplication.activity;


import androidx.fragment.app.Fragment;
import com.example.shopapplication.fragment.MainFragment;
public class MainActivity extends SingelFragmentActivity {


    @Override
    public Fragment createFragment() {
        MainFragment mainFragment = MainFragment.newInstance();
        return mainFragment;
    }


}