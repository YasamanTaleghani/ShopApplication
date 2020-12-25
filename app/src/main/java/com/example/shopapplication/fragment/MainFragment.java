package com.example.shopapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.RecyclerViewAdapter;
import com.example.shopapplication.model.ProductionItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private PageAdapter mPageAdapter;
    private ArrayList<ProductionItem> mNewestItems = new ArrayList<>();
    private ArrayList<ProductionItem> mHighestRankedItems = new ArrayList<>();
    private RecyclerViewAdapter mNewestItemsAdapter;
    private RecyclerViewAdapter mHighestRankedAdapter;
    private RecyclerView mRecyclerViewNewestItems;
    private RecyclerView mRecyclerViewHighestRanked;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        findViews(view);
        //fetchItems
        initView();
        setAdapter();

        return view;
    }

    private void findViews(View view) {
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);
    }

    private void initView() {
        mPageAdapter = new PageAdapter(getActivity());
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

    private void setAdapter() {

        if (mNewestItems.size()>0){
            mNewestItemsAdapter = new RecyclerViewAdapter(getContext(), mNewestItems);
            mRecyclerViewNewestItems.setAdapter(mNewestItemsAdapter);
            mRecyclerViewNewestItems.setLayoutManager
                    (new LinearLayoutManager(
                            getContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false));
        }

        if (mHighestRankedItems.size()>0){
            mHighestRankedAdapter = new RecyclerViewAdapter(getContext(), mHighestRankedItems);
            mRecyclerViewHighestRanked.setAdapter(mHighestRankedAdapter);
            mRecyclerViewHighestRanked.setLayoutManager
                    (new LinearLayoutManager(
                            getContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false));
        }
    }
}