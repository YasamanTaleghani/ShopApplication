package com.example.shopapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.shopapplication.R;
import com.example.shopapplication.fragment.HighestRankFragment;
import com.example.shopapplication.fragment.MostVisitedFragment;
import com.example.shopapplication.fragment.NewestItemsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private PageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initView();
    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
    }

    private void initView() {
        mPageAdapter = new PageAdapter(MainActivity.this);
        mViewPager.setAdapter(mPageAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText(R.string.newestItems);
                                break;
                            }
                            case 1: {
                                tab.setText(R.string.mostVisitedItems);
                                break;
                            }
                            case 2: {
                                tab.setText(R.string.highestRankedItems);
                                break;
                            }
                        }
                    }
                });

        tabLayoutMediator.attach();
    }

    private class PageAdapter extends FragmentStateAdapter {

        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    NewestItemsFragment newestItemsFragment = NewestItemsFragment.newInstance();
                    return newestItemsFragment;
                case 1:
                    MostVisitedFragment mostVisitedFragment = MostVisitedFragment.newInstance();
                    return mostVisitedFragment;
                case 2:
                    HighestRankFragment highestRankFragment = HighestRankFragment.newInstance();
                    return highestRankFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }
}