package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentIconList = new ArrayList<>();
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    public void addFragment(Fragment fragment, String title, int tabIcon){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(tabIcon);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab, null);
        ImageView imageView = view.findViewById(R.id.tabImageView);
        imageView.setImageResource(mFragmentIconList.get(position));
        LinearLayout linearLayout = view.findViewById(R.id.lnTabs);
        linearLayout.setGravity(Gravity.CENTER);
        return view;
    }

    public View getSelectedTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab, null);
        TextView textView = view.findViewById(R.id.tabTextView);
        textView.setText(mFragmentTitleList.get(position));

        ImageView imageView = view.findViewById(R.id.tabImageView);
        imageView.setImageResource(mFragmentIconList.get(position));
        imageView.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);

        LinearLayout linearLayout = view.findViewById(R.id.lnTabs);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setWeightSum(4);
        linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_tab));
        return view;
    }
}
