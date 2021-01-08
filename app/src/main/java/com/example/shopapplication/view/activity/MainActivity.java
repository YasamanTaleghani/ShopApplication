package com.example.shopapplication.view.activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopapplication.R;
import com.example.shopapplication.adapter.CustomExpandableListAdapter;
import com.example.shopapplication.helper.FragmentNavigaionManager;
import com.example.shopapplication.helper.NavigationManager;
import com.example.shopapplication.view.fragment.MainFragment;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.viewmodel.ProductionViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "categories";
    public static final String EXTRA_CATEGORY_NAME =
            "com.example.shopapplication.view.activity.categoryName";

    private ProductionViewModel mViewModel;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    //
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mListTitle;
    private Map<String, List<String>> mListChild;
    private NavigationManager mNavigationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        addingFragment();
        findView();
        initItems();
        fetchCategories();
    }

    private void addingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //check if fragment exists in container (configuration changes save the fragments)
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        //create an add fragment transaction for CrimeDetailFragment
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }

    private void fetchCategories() {
        mViewModel =  new ViewModelProvider(this).get(ProductionViewModel.class);
        mViewModel.fetchCategoriesAsync();
        mViewModel.getCategoryItemsLiveData().observe(this, new Observer<List<CategoryResponse>>() {
            @Override
            public void onChanged(List<CategoryResponse> responses) {
                for (int i = 0; i < responses.size() ; i++) {
                    Log.d(TAG, "category" + i + "is: " + responses.get(i).getName());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void findView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mExpandableListView = findViewById(R.id.nav_list);
    }

    private void initItems() {
        mNavigationManager = FragmentNavigaionManager.getInstance(this);


        View ListHeaderView = getLayoutInflater().inflate
                (R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(ListHeaderView);
        getData();
        addDrawerItem();
        setUpDrawer();
        setUpListeners();
    }

    private void setUpListeners() {
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(
                    ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Log.d(TAG, "on child click");
                String selectedItem = mListChild.get(mListTitle.get(i)).get(i1);
                //getSupportActionBar().setTitle(selectedItem);

                Intent intent = new Intent(CategoryActivity.newIntent(getBaseContext()));
                intent.putExtra(EXTRA_CATEGORY_NAME, selectedItem);
                startActivity(intent);

                mDrawerLayout.closeDrawer(GravityCompat.END);

                return false;
            }
        });
    }

    private void getData() {
        ArrayList<String> title = new ArrayList<>();
        title.add(getResources().getString(R.string.digital));
        title.add(getResources().getString(R.string.fashion));
        title.add(getResources().getString(R.string.bookArt));
        title.add(getResources().getString(R.string.food));
        title.add(getResources().getString(R.string.beauty));
        title.add(getResources().getString(R.string.sale));
        title.add(getResources().getString(R.string.home));

        List<String> child1 = Arrays.asList("دیجیتال", "گوشی موبایل", "ساعت و مچ بند هوشمند");
        List<String> child2 = Arrays.asList
                ("پوشاک زنانه", "پوشاک مردانه", "کفش", "کیف و کوله", "مد و پوشاک");
        List<String> child3 = Arrays.asList("فیلم", "کتاب و مجلات", "کتاب و هنر");
        List<String> child4 = Arrays.asList("سوپرمارکت", "لبنیات", "مواد پروتئینی", "نوشیدنی ها");
        List<String> child5 = Arrays.asList( "بهداشت");
        List<String> child6 = Arrays.asList( "فروش ویژه");
        List<String> child7 = Arrays.asList( "لوازم منزل");

        mListChild = new TreeMap<>();
        mListChild.put(title.get(0), child1);
        mListChild.put(title.get(1), child2);
        mListChild.put(title.get(2), child3);
        mListChild.put(title.get(3), child4);
        mListChild.put(title.get(4), child5);
        mListChild.put(title.get(5), child6);
        mListChild.put(title.get(6), child7);

        mListTitle = new ArrayList<>(mListChild.keySet());
    }

    private void addDrawerItem() {
        mExpandableListAdapter =
                new CustomExpandableListAdapter
                        (this, mListTitle, mListChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
    }

    private void setUpDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, (R.string.open), (R.string.close)){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

}