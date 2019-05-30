package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.News;
import com.example.myapplication.network.ItemClickListener;

import java.util.List;



public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolderNews> {

    private Context context;
    private LayoutInflater inflater;
    private List<News> list;
    ItemClickListener itemClickListener;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.list = newsList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderNews onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_custom_news_home, viewGroup, false);

        return new ViewHolderNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderNews viewholderNews, int i) {
        Uri image = Uri.parse(list.get(i).getImage());
        Glide.with(context).load(image).into(viewholderNews.imgNews);
        viewholderNews.txtTitle.setText(list.get(i).getTitle());
        viewholderNews.txtDescription.setText(list.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderNews extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgNews;
        TextView txtTitle, txtDescription;

        public ViewHolderNews(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
