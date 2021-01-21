package com.example.shopapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.view.fragment.ProfileFragment;

public class ProfileActivity extends SingelFragmentActivity {


    @Override
    public Fragment createFragment() {
        Fragment profileFragment = ProfileFragment.newInstance();
        return profileFragment;
    }
}