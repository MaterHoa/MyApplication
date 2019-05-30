package com.example.myapplication.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ItemClickListener;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment{
    private int position;
    private Store store;
    TextView txtName, txtAddress;
    Button btnDirection;
    LocationFragment locationFragment;
    ItemClickListener clickListener;

    public MapFragment() {

    }

    @SuppressLint("ValidFragment")
    public MapFragment(int position, Store store) {
        this.position = position;
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_stores, container, false);

        txtName = view.findViewById(R.id.txtNameStore);
        txtAddress = view.findViewById(R.id.txtAddressStore);
        btnDirection = view.findViewById(R.id.btnDirection);

        txtName.setText(getStore().getName());
        txtAddress.setText(getStore().getAddress());

        locationFragment = new LocationFragment();

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null){
                    double latStart = location.getLatitude();
                    double lngStart = location.getLongitude();

                    Toast.makeText(getActivity(), "Latitude " + latStart + "Longitude " + lngStart, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }


}
