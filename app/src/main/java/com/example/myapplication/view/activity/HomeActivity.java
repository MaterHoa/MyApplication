package com.example.myapplication.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerAdapter;
import com.example.myapplication.view.fragment.HomeFragment;
import com.example.myapplication.view.fragment.LocationFragment;
import com.example.myapplication.view.fragment.NotificationFragment;
import com.example.myapplication.view.fragment.UserFragment;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";
    FragmentManager fragmentManager;
    ViewPagerAdapter adapter;
    HomeFragment homeFragment;
    private int[] tabIcons = {
            R.drawable.tab_home_active,
            R.drawable.tab_noti_active,
            R.drawable.tab_location_active,
            R.drawable.tab_user_active,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        toolbar = findViewById(R.id.toolBarHome);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new HomeFragment(), "Home", tabIcons[0]);
        adapter.addFragment(new NotificationFragment(), "Notification", tabIcons[1]);
        adapter.addFragment(new LocationFragment(), "Store", tabIcons[2]);
        adapter.addFragment(new UserFragment(), "Account", tabIcons[3]);
        viewPager.setAdapter(adapter);

        highLightCurrentTab(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                highLightCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }

}
