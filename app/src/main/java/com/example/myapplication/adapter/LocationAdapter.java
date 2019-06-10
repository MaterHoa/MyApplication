package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Store;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public class LocationAdapter extends PagerAdapter implements View.OnClickListener {
    Context context;
    List<Store> storeList;
    LayoutInflater inflater;
    FragmentManager manager;
    GoogleMap map;
    onClickCallBack callBack;
    public LocationAdapter(Context context, List<Store> list, FragmentManager manager) {
        this.context = context;
        this.storeList = list;
        this.manager = manager;
        inflater = LayoutInflater.from(context);
    }

    public interface onClickCallBack{
        void onClickDirection(int i);
    }

    public void setCallBack(onClickCallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.layout_list_stores, null);
        TextView txtName = view.findViewById(R.id.txtNameStore);
        TextView txtAddress = view.findViewById(R.id.txtAddressStore);
        Button btnDirection = view.findViewById(R.id.btnDirection);

        txtName.setText(storeList.get(position).getName());
        txtAddress.setText(storeList.get(position).getAddress());

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClickDirection(position);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void onClick(View v) {

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
