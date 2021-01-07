package com.example.shopapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.shopapplication.R;

public abstract class SingelFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate layout for activity
        setContentView(R.layout.activity_singel_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //check if fragment exists in container (configuration changes save the fragments)
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        //create an add fragment transaction for CrimeDetailFragment
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
        }
    }
}