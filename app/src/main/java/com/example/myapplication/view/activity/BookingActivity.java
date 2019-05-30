package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.StoresAdapter;
import com.example.myapplication.data.DataStores;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ApiUtils;
import com.example.myapplication.network.ItemClickListener;
import com.example.myapplication.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerView;
    Toolbar toolbar;
    StoresAdapter adapter;
    List<Store> storeList;
    UserService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking);

        toolbar = findViewById(R.id.toolBarBooking);
        recyclerView = findViewById(R.id.rvSalon);
        service = ApiUtils.getUsernameService();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Call<DataStores> call = service.getStores();
        call.enqueue(new Callback<DataStores>() {
            @Override
            public void onResponse(Call<DataStores> call, Response<DataStores> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        storeList = response.body().getData();
                        adapter = new StoresAdapter(BookingActivity.this, storeList);
                        adapter.setClickListener(BookingActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataStores> call, Throwable t) {
                Toast.makeText(BookingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent iBook = new Intent(BookingActivity.this, BookingDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("address", storeList.get(position).getAddress());
        iBook.putExtras(bundle);
        startActivity(iBook);
    }
}
