package com.example.shopapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.view.fragment.CategoryFragment;
import com.example.shopapplication.view.fragment.DetailFragment;

public class CategoryActivity extends SingelFragmentActivity {

    String categoryName;

    public static Intent newIntent(Context context) {
        return new Intent(context, CategoryActivity.class);
    }

    @Override
    public Fragment createFragment() {
        Bundle bundle = getIntent().getExtras();
        categoryName = bundle.getString(MainActivity.EXTRA_CATEGORY_NAME);
        CategoryFragment categoryFragment = CategoryFragment.newInstance(categoryName);
        return categoryFragment;
    }
}