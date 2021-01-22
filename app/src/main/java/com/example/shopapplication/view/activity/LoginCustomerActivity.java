package com.example.shopapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.view.fragment.LoginCustomerFragment;

public class LoginCustomerActivity extends SingelFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginCustomerActivity.class);
    }

    @Override
    public Fragment createFragment() {
        Fragment fragment = LoginCustomerFragment.newInstance();
        return fragment;
    }

}