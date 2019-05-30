package com.example.myapplication.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Store;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

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
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    double latStart = location.getLatitude();
                    double lngStart = location.getLongitude();
                    double latEnd = storeList.get(position).getLat();
                    double lngEnd = storeList.get(position).getLongt();

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+latStart+","+lngStart+"&daddr="+latEnd+","+lngEnd));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER );
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    context.startActivity(intent);
                }
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
