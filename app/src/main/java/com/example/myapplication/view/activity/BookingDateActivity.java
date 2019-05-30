package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BookingDateActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout lnToday, lnTomorrow, lnAfter, lnDate;
    TextView txtToday, txtTomorrow, txtAfter;
    TableLayout tableLayout;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23;
    Button btnNext;
    String today, tomorrow, after;
    String date;
    String time;
    String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_date);

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

        lnToday.setOnClickListener(this);
        lnTomorrow.setOnClickListener(this);
        lnAfter.setOnClickListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);

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
        tableLayout = findViewById(R.id.tbTime);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
        btn21 = findViewById(R.id.btn21);
        btn22 = findViewById(R.id.btn22);
        btn23 = findViewById(R.id.btn23);

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
            case R.id.btn1:
                time = "9:00";
                break;
            case R.id.btn2:
                time = "9:30";
                break;
            case R.id.btn3:
                time = "10:00";
                break;
            case R.id.btn4:
                time = "10:30";
                break;
            case R.id.btn5:
                time = "11:00";
                break;
            case R.id.btn6:
                time = "11:30";
                break;
            case R.id.btn7:
                time = "12:00";
                break;
            case R.id.btn8:
                time = "12:30";
                break;
            case R.id.btn9:
                time = "13:00";
                break;
            case R.id.btn10:
                time = "13:30";
                break;
            case R.id.btn11:
                time = "14:00";
                break;
            case R.id.btn12:
                time = "14:30";
                break;
            case R.id.btn13:
                time = "15:00";
                break;
            case R.id.btn14:
                time = "15:30";
                break;
            case R.id.btn15:
                time = "16:00";
                break;
            case R.id.btn16:
                time = "16:30";
                break;
            case R.id.btn17:
                time = "17:00";
                break;
            case R.id.btn18:
                time = "17:30";
                break;
            case R.id.btn19:
                time = "18:00";
                break;
            case R.id.btn20:
                time = "18:30";
                break;
            case R.id.btn21:
                time = "19:00";
                break;
            case R.id.btn22:
                time = "19:30";
                break;
            case R.id.btn23:
                time = "20:00";
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
