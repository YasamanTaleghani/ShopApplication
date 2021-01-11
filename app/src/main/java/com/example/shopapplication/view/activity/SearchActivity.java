package com.example.shopapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.view.fragment.SearchFragment;

public class SearchActivity extends SingelFragmentActivity {

    private String searchQuery;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public Fragment createFragment() {
        Bundle bundle = getIntent().getExtras();
        searchQuery = bundle.getString(MainActivity.EXTRA_SEARCH_QUERY);
        SearchFragment searchFragment = SearchFragment.newInstance(searchQuery);
        return searchFragment;
    }
}