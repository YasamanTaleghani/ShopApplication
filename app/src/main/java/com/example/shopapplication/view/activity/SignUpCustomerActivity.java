package com.example.shopapplication.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.shopapplication.view.fragment.SignUpCustomerFragment;

public class SignUpCustomerActivity extends SingelFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpCustomerActivity.class);
    }

    @Override
    public Fragment createFragment() {
        Fragment profileFragment = SignUpCustomerFragment.newInstance();
        return profileFragment;
    }
}