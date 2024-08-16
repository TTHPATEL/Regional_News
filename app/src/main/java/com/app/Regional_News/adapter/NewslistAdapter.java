package com.app.Regional_News.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback; // Import for Picasso


import com.app.Regional_News.R;
import com.app.Regional_News.data.News_listfetch_listdata;
import com.app.Regional_News.ui.NewsShowActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewslistAdapter extends RecyclerView.Adapter<NewslistAdapter.ViewHolder> {

    private final ArrayList<News_listfetch_listdata> clist;
    private final Context mContext;
    private SharedPreferences sharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_m_name, keywordtext;
        public CardView cardview;
        public ImageView news_images;
        public LinearLayout news_list_layout;
        public CheckBox save_check;

        public ViewHolder(View v) {
            super(v);
            tv_m_name = v.findViewById(R.id.news_headline);
            cardview = v.findViewById(R.id.cardview);
            news_images = v.findViewById(R.id.news_images);
            news_list_layout = v.findViewById(R.id.news_list_layout);
            keywordtext = v.findViewById(R.id.keywordtext);
            save_check = v.findViewById(R.id.save_check);
        }
    }

    public NewslistAdapter(Context context, ArrayList<News_listfetch_listdata> data) {
        this.clist = data;
        this.mContext = context;
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
    }

    @Override
    public NewslistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_newslist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewslistAdapter.ViewHolder holder, int position) {
        final News_listfetch_listdata data = clist.get(position);
        holder.tv_m_name.setText(data.getNews_headline());
        holder.keywordtext.setText(data.getKeyword());

        // Load image using Picasso
        Picasso.get()
                .load(data.getNews_imgurl())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.news_images);

        // Check if this item is already saved
        boolean isChecked = sharedPreferences.getBoolean(data.getNews_id(), false);
        holder.save_check.setChecked(isChecked);

        holder.save_check.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data.getNews_id(), isChecked1);
            editor.apply();
        });

        holder.news_list_layout.setOnClickListener(v -> {
            Intent i = new Intent(mContext, NewsShowActivity.class);
            i.putExtra("getNews_id", data.getNews_id());
            i.putExtra("news_imgurl", data.getNews_imgurl());
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
