package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolderNotification> {

    private LayoutInflater inflater;
    private List<Notification> list;

    public NotificationAdapter(Context context, List<Notification> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderNotification onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_custom_notification, viewGroup, false);
        return new ViewHolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotification viewHolderNotification, int i) {
        viewHolderNotification.txtTitle.setText(list.get(i).getTitle());
        viewHolderNotification.txtDescription.setText(list.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderNotification extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription;

        public ViewHolderNotification(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleNoti);
            txtDescription = itemView.findViewById(R.id.txtDescriptionNoti);
        }
    }
}
