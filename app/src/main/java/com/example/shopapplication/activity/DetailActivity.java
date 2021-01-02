package com.example.shopapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.fragment.DetailFragment;

public class DetailActivity extends SingelFragmentActivity {

    private int mId;

    public static Intent newIntent(Context context) {
        return new Intent(context, DetailActivity.class);
    }

    @Override
    public Fragment createFragment() {
        Bundle bundle = getIntent().getExtras();
        mId = bundle.getInt(RecyclerViewAdapter.EXTRA_PRODUCTION_ID);
        DetailFragment detailFragment = DetailFragment.newInstance(mId);
        return detailFragment;
    }
}