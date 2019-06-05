package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.myapplication.R;

import java.util.List;

public class TimeAdapter extends BaseAdapter {
    Context context;
    List<String> times;
    LayoutInflater inflater;
    int positionSelect = -1;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    public TimeAdapter(Context context, List<String> times){
        this.context = context;
        this.times = times;
        inflater = LayoutInflater.from(context);

        mPreferences = context.getSharedPreferences(sharePrefFile, Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_custom_select_time, parent, false);
        }

        Button button = convertView.findViewById(R.id.btnTime);

        button.setText(times.get(position));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionSelect = position;
                notifyDataSetChanged();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("time", button.getText().toString());
                editor.apply();
            }

        });

        if (position == positionSelect){
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorButton));
        }

        return convertView;
    }

}
