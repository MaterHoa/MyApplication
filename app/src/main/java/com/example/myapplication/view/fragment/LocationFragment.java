package com.example.myapplication.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LocationAdapter;
import com.example.myapplication.data.DataStores;
import com.example.myapplication.helper.DirectionJSONParser;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    ViewPager viewPager;
    UserService userService;
    RelativeLayout layout;
    LocationAdapter adapter;
    SupportMapFragment mapFragment;
    List<Store> stores;
    MarkerOptions markerStart, markerEnd;
    double lat, longt;
    Polyline polyline;

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
                        layout = viewPager.findViewById(R.id.rlStore);
                        layout.findViewById(R.id.btnDirection).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                                Criteria criteria = new Criteria();
                                String provider = locationManager.getBestProvider(criteria, true);
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                    double latEnd = stores.get(0).getLat();
                                    double lngEnd = stores.get(0).getLongt();

                                    LatLng start = new LatLng(latStart, lngStart);
                                    LatLng end = new LatLng(latEnd, lngEnd);

                                    getDirectionsUrl(start, end);
                                    drawPath(start, end);
                                }
                            }
                        });
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
                lat = stores.get(i).getLat();
                longt = stores.get(i).getLongt();
                LatLng newLatlng = new LatLng(lat, longt);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(newLatlng).title(stores.get(i).getName());
                map.addMarker(markerOptions);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatlng, 16));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(16);
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
        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latStart = location.getLatitude();
            double lngStart = location.getLongitude();
            LatLng myLocation = new LatLng(latStart, lngStart);
            map.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    private String getDirectionsUrl(LatLng start, LatLng end) {
        String origin = "origin=" + start.latitude + "," + start.longitude;
        String dest = "destination=" + end.latitude + "," + end.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String parameters = origin + "&" + dest + "&" + sensor + "&" + mode + "&key=" + R.string.directionKey;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private void drawPath(LatLng start, LatLng end) {

        String origin = new StringBuilder(String.valueOf(start.latitude)).append(",").append(start.longitude).toString();
        String destination = new StringBuilder(String.valueOf(end.latitude)).append(",").append(end.longitude).toString();

        userService = ApiUtils.getUsernameServiceScalars();
        userService.getDirections(origin, destination).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                new ParserTask().execute(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionJSONParser parser = new DirectionJSONParser();
                routes = parser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);

            ArrayList points;

            for (int i = 0; i < lists.size(); i++){
                points = new ArrayList();
                PolylineOptions polylineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = lists.get(i);
                for (int j = 0; j < lists.size(); j++){
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                polylineOptions.addAll(points);
                polylineOptions.width(12);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

                map.addPolyline(polylineOptions);
            }
        }
    }
}
