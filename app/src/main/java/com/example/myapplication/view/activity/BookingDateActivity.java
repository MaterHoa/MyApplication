package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TimeAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;


public class BookingDateActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    LinearLayout lnToday, lnTomorrow, lnAfter, lnDate;
    TextView txtToday, txtTomorrow, txtAfter;
    GridView gridView;
    List<String> times;
    Button btnNext;
    String today, tomorrow, after;
    String date;
    String time;
    String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_date);
        ButterKnife.bind(this);


        init();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        address = bundle.getString("address");

        Calendar calendar = Calendar.getInstance();
        Date todayDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date afterDate = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        today = dateFormat.format(todayDate);
        tomorrow = dateFormat.format(tomorrowDate);
        after = dateFormat.format(afterDate);

        txtToday.setText(today);
        txtTomorrow.setText(tomorrow);
        txtAfter.setText(after);

        times = new ArrayList<>();
        times.add("9:00");
        times.add("9:30");
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");
        times.add("13:00");
        times.add("13:30");
        times.add("14:00");
        times.add("14:30");
        times.add("15:00");
        times.add("15:30");
        times.add("16:00");
        times.add("16:30");
        times.add("17:00");
        times.add("17:30");
        times.add("18:00");
        times.add("18:30");
        times.add("19:00");
        times.add("19:30");
        times.add("20:00");
        times.add("20:30");
        times.add("21:00");
        times.add("21:30");
        times.add("22:00");
        TimeAdapter timeAdapter = new TimeAdapter(this, times);
        gridView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();

        lnToday.setOnClickListener(this);
        lnTomorrow.setOnClickListener(this);
        lnAfter.setOnClickListener(this);

        btnNext.setOnClickListener(this);
    }

    private void init() {
        toolbar = findViewById(R.id.toolBarPickDate);
        lnAfter = findViewById(R.id.lnAfter);
        lnToday = findViewById(R.id.lnToday);
        lnDate = findViewById(R.id.lnDate);
        lnTomorrow = findViewById(R.id.lnTomorrow);
        txtToday = findViewById(R.id.txtToday);
        txtTomorrow = findViewById(R.id.txtTomorrow);
        txtAfter = findViewById(R.id.txtAfter);
        gridView = findViewById(R.id.gvTime);

        btnNext = findViewById(R.id.btnNextBook);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnToday:
                date = today;
                break;
            case R.id.lnTomorrow:
                date = tomorrow;
                break;
            case R.id.lnAfter:
                date = after;
                break;

            case R.id.btnNextBook:
                Intent iFinish = new Intent(BookingDateActivity.this, FinishBookingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                bundle.putString("time", time);
                bundle.putString("address1", address);
                iFinish.putExtras(bundle);
                startActivity(iFinish);
                break;
        }
    }
}
