package com.example.myapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.data.DataNews;
import com.example.myapplication.model.News;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.ItemClickListener;
import com.example.myapplication.network.UserService;
import com.example.myapplication.view.activity.BookingActivity;
import com.example.myapplication.view.activity.CouponActivity;
import com.example.myapplication.view.activity.MembershipActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener, ItemClickListener {
    public static final String TAG = "sate null";
    ImageView imgAvatar;
    TextView txtName, txtMember;
    RecyclerView recyclerView;
    LinearLayout lnBooking, lnMembership, lnCoupon;
    UserService userService;
    boolean vip;
    List<News> dataNews;
    String name, image, idd;
    NewsAdapter adapter;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    public HomeFragment(){

    }

    public static HomeFragment newInstance(int page, String title) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_home, container, false);

        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtName = view.findViewById(R.id.txtName);
        txtMember = view.findViewById(R.id.txtText);
        lnBooking = view.findViewById(R.id.lnBooking);
        lnCoupon = view.findViewById(R.id.lnCoupon);
        lnMembership = view.findViewById(R.id.lnMemership);
        recyclerView = view.findViewById(R.id.recyclerView);

        lnBooking.setOnClickListener(this);
        lnMembership.setOnClickListener(this);
        lnCoupon.setOnClickListener(this);

        mPreferences = getActivity().getSharedPreferences(sharePrefFile, Context.MODE_PRIVATE);
        if (mPreferences.contains("avatar")){
            Uri uri = Uri.parse(mPreferences.getString("avatar", ""));
            Glide.with(getActivity()).load(uri).into(imgAvatar);
        }
        if (mPreferences.contains("name")){
            txtName.setText(mPreferences.getString("name", ""));
        }
        if (mPreferences.contains("isVip")){
            if (mPreferences.getBoolean("isVip", true)){
                txtMember.setText("Vip member");
            } else {
                txtMember.setText("Basic member");
            }
        }

        userService = ApiUtils.getUsernameService();

        Call<DataNews> call = userService.getNews();
        call.enqueue(new Callback<DataNews>() {
            @Override
            public void onResponse(Call<DataNews> call, Response<DataNews> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        dataNews = response.body().getData();
                        adapter = new NewsAdapter(getActivity(), dataNews);
                        adapter.setItemClickListener(HomeFragment.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Data Empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataNews> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lnBooking:
                Intent iBook = new Intent(getActivity(), BookingActivity.class);
//                Bundle bBook = new Bundle();
//                bBook.putString("idd", idd);
//                bBook.putString("names", name);
//                iBook.putExtras(bBook);
                startActivity(iBook);
                break;
            case R.id.lnMemership:
                Intent iMember = new Intent(getActivity(), MembershipActivity.class);
                Bundle bMember = new Bundle();
                bMember.putString("idd", idd);
                bMember.putString("names", name);
                bMember.putBoolean("vip", vip);
                iMember.putExtras(bMember);
                startActivity(iMember);
                break;
            case R.id.lnCoupon:
                startActivity(new Intent(getActivity(), CouponActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Uri uri = Uri.parse(dataNews.get(position).getUrl());
        Intent changeBrower = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(changeBrower);
    }
}
