package com.example.myapplication.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class FinishBookingActivity extends AppCompatActivity {

    TextView txtFinish;
    Button btnBackHome;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_finish_booking);

        txtFinish = findViewById(R.id.txtBooking);
        btnBackHome = findViewById(R.id.btnBackHome);

        mPreferences = getSharedPreferences(sharePrefFile, MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String address = bundle.getString("address1");
        String date = bundle.getString("date");
        String time = mPreferences.getString("time","");

        txtFinish.setText("You booking is in : " + time + "\nAt: " + date + "\nAddress: " + address);

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishBookingActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
