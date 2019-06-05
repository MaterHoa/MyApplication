package com.example.myapplication.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.data.DataNotification;
import com.example.myapplication.model.Notification;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    UserService userService;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    String id, name, avatar, accessToken;
    boolean isVip;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_custom_noti, container, false);
        recyclerView = view.findViewById(R.id.rvNotification);
        linearLayout = view.findViewById(R.id.lnNoti);
        userService = ApiUtils.getUsernameService();

        mPreferences = getActivity().getSharedPreferences(sharePrefFile, Context.MODE_PRIVATE);
        if (mPreferences.contains("accessToken")){
            String token = mPreferences.getString("accessToken", "");
            Call<DataNotification> call = userService.getNotification(token);
            call.enqueue(new Callback<DataNotification>() {
                @Override
                public void onResponse(Call<DataNotification> call, Response<DataNotification> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            List<Notification> list = response.body().getData();
                            NotificationAdapter adapter = new NotificationAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            adapter.notifyDataSetChanged();
                        } else {
                            linearLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.empty));
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataNotification> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
