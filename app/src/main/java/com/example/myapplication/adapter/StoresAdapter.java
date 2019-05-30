package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Store;
import com.example.myapplication.network.ItemClickListener;

import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolderStores> {

    private LayoutInflater inflater;
    private List<Store> list;
    private ItemClickListener clickListener;

    public StoresAdapter(Context context, List<Store> newsList) {
        inflater = LayoutInflater.from(context);
        this.list = newsList;
    }

    @NonNull
    @Override
    public ViewHolderStores onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_custom_booking, viewGroup, false);
        return new ViewHolderStores(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStores viewHolderStores, int i) {
        viewHolderStores.txtAddres.setText(list.get(i).getAddress());
        viewHolderStores.txtStoreName.setText(list.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderStores extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtAddres, txtStoreName;

        public ViewHolderStores(@NonNull View itemView) {
            super(itemView);
            txtAddres = itemView.findViewById(R.id.txtAddress);
            txtStoreName = itemView.findViewById(R.id.txtShopName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
