package com.example.myapplication.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.myapplication.R;
import com.example.myapplication.adapter.LocationAdapter;
import com.example.myapplication.data.DataStores;
import com.example.myapplication.direction.DirectionJSONParser;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback, LocationAdapter.onClickCallBack {
    GoogleMap map;
    ViewPager viewPager;
    UserService userService;
    LocationAdapter adapter;
    SupportMapFragment mapFragment;
    List<Store> stores;
    double latEnd, lngEnd;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    public LocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_location_item, container, false);

        mPreferences = getActivity().getSharedPreferences(sharePrefFile, Context.MODE_PRIVATE);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewPager = view.findViewById(R.id.viewPagerStore);

        viewPager.setPageMargin(30);

        userService = ApiUtils.getUsernameService();
        Call<DataStores> call = userService.getStores();
        call.enqueue(new Callback<DataStores>() {
            @Override
            public void onResponse(Call<DataStores> call, Response<DataStores> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        stores = response.body().getData();
                        adapter = new LocationAdapter(getActivity(), stores, getActivity().getSupportFragmentManager());
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(adapter.getCount());
                        adapter.setCallBack(LocationFragment.this);
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
            public void onPageSelected(int i) {
                latEnd = stores.get(i).getLat();
                lngEnd = stores.get(i).getLongt();
                LatLng newLatlng = new LatLng(latEnd, lngEnd);
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

    @Override
    public void onClickDirection(int i) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latStart = location.getLatitude();
            double lngStart = location.getLongitude();
            LatLng myLocation = new LatLng(latStart, lngStart);
            double latEnd = stores.get(i).getLat();
            double lngEnd = stores.get(i).getLongt();
            LatLng end = new LatLng(latEnd, lngEnd);

            String url = getDirectionsUrl(myLocation, end);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
    }

    private String getDirectionsUrl(LatLng start, LatLng end) {
        String origin = "origin=" + start.latitude + "," + start.longitude;
        String dest = "destination=" + end.latitude + "," + end.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String parameters = origin + "&" + dest + "&" + sensor + "&" + mode + "&key=" + getActivity().getString(R.string.directionKey);
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }

    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionJSONParser parser = new DirectionJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            String directionMode = "driving";

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 2; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat").trim());
                    double lng = Double.parseDouble(point.get("lng").trim());
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

                lineOptions.addAll(points);
                if (directionMode.equalsIgnoreCase("walking")) {
                    lineOptions.width(10);
                    lineOptions.color(Color.MAGENTA);
                } else {
                    lineOptions.width(15);
                    lineOptions.color(Color.BLUE);
                }

            }
            map.addPolyline(lineOptions);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
