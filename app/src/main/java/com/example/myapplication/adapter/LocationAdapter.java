package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Store;

import java.util.List;

public class LocationAdapter extends PagerAdapter {
    Context context;
    List<Store> storeList;
    LayoutInflater inflater;

    public LocationAdapter(Context context, List<Store> list) {
//        super(fm);
        this.context = context;
        this.storeList = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.layout_list_stores, null);
        TextView txtName = view.findViewById(R.id.txtNameStore);
        TextView txtAddress = view.findViewById(R.id.txtAddressStore);
        Button btnDirection = view.findViewById(R.id.btnDirection);

        txtName.setText(storeList.get(position).getName());
        txtAddress.setText(storeList.get(position).getAddress());

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.92f;
    }

}
