package com.example.myapplication.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LocationAdapter;
import com.example.myapplication.data.DataStores;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    ViewPager viewPager;
    UserService userService;
    LocationAdapter adapter;
    SupportMapFragment mapFragment;
    List<Store> stores;
    MarkerOptions markerStart, markerEnd;

    public LocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_custom_location_item, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userService = ApiUtils.getUsernameService();

        viewPager = view.findViewById(R.id.viewPagerStore);
        viewPager.setPageMargin(30);

        Call<DataStores> call = userService.getStores();
        call.enqueue(new Callback<DataStores>() {
            @Override
            public void onResponse(Call<DataStores> call, Response<DataStores> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        stores = response.body().getData();
                        adapter = new LocationAdapter(getActivity(), stores);
                        viewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataStores> call, Throwable t) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int i) {
                double lat = stores.get(i).getLat();
                double longt = stores.get(i).getLongt();
                LatLng newLatlng = new LatLng(lat, longt);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(newLatlng).title(stores.get(i).getName());
                map.addMarker(markerOptions);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatlng, 17));

//                getLocation(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    public void getLocation(int i){
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
            double latEnd = stores.get(i).getLat();
            double lngEnd = stores.get(i).getLongt();

            Toast.makeText(getActivity(), "Latitude " + latStart + " Longitude " + lngStart, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Latitude " + latEnd + " Longitude " + lngEnd, Toast.LENGTH_SHORT).show();

            LatLng myLocation = new LatLng(latStart, lngStart);
            LatLng latLngEnd = new LatLng(latEnd, lngEnd);

            markerStart = new MarkerOptions().position(myLocation).title("Start");
            markerEnd = new MarkerOptions().position(latLngEnd).title("End");

            map.addMarker(markerStart);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(17);
//        map.getUiSettings().setCompassEnabled(true);
//        map.getUiSettings().setZoomControlsEnabled(true);
//        map.getUiSettings().setZoomGesturesEnabled(true);
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setTrafficEnabled(true);
//        map.setBuildingsEnabled(true);
//        map.setIndoorEnabled(true);

//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            map.setMyLocationEnabled(true);
//        } else {
//            //            Common.checkAndRequestPermissionsGPS(getActivity());
//        }



        LatLng latLng = new LatLng(21.019531, 105.831279);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}
