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

import com.app.Regional_News.EnewspaperActivity;
import com.app.Regional_News.NewsShowActivity;
import com.app.Regional_News.R;
import com.app.Regional_News.data.Search_News_listfetch_listdata;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchNewslistAdapter extends RecyclerView.Adapter<SearchNewslistAdapter.ViewHolder> {

    private final ArrayList<Search_News_listfetch_listdata> clist;
    private final Context mContext;
    private SharedPreferences sharedPreferences;


    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_m_name, tv_m_amount, keywordtext;
        public CardView cardview;
        public ImageView news_images;
        public LinearLayout news_list_layout;
        public CheckBox save_check;
        public LinearLayout share_lay;





        public ViewHolder(View v) {
            super(v);
            tv_m_name = v.findViewById(R.id.news_headline);
            cardview = v.findViewById(R.id.cardview);
            news_images = v.findViewById(R.id.news_images); // This is where news_images ImageView is initialized
            news_list_layout = v.findViewById(R.id.news_list_layout);
            keywordtext = v.findViewById(R.id.keywordtext);
            save_check = v.findViewById(R.id.save_check);
            share_lay = v.findViewById(R.id.share_lay);


        }
    }

    // Provide a suitable constructor
    public SearchNewslistAdapter(Context context, ArrayList<Search_News_listfetch_listdata> data) {
        this.clist = data;
        this.mContext = context;
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchNewslistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_newslist, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchNewslistAdapter.ViewHolder holder, int position) {
        // - get element from arraylist at this position
        // - replace the contents of the view with that element
        final Search_News_listfetch_listdata data = clist.get(position);
        holder.tv_m_name.setText(data.getNews_headline());
        holder.keywordtext.setText(data.getKeyword());

        // Load image using Picasso
        Picasso.get()
                .load(data.getNews_imgurl())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.news_images, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                        e.printStackTrace();
                    }
                });


            // Temporarily remove the listener to prevent it from being triggered when recycling views
        holder.save_check.setOnCheckedChangeListener(null);

        // Check if this item is already saved
        boolean isChecked = sharedPreferences.getBoolean(data.getNews_id(), false);
        holder.save_check.setChecked(isChecked);

        holder.save_check.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data.getNews_id(), isChecked1);
            editor.apply();
        });

        holder.news_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NewsShowActivity.class);
                i.putExtra("mm_id", data.getNews_id());
                i.putExtra("mm_m_year", data.getNews_headline());
                i.putExtra("getNews_id", data.getNews_id());
                i.putExtra("news_imgurl", data.getNews_imgurl());
                i.putExtra("isChecked", sharedPreferences.getBoolean(data.getNews_id(), false));

                mContext.startActivity(i);
            }
        });


        // Handle sharing functionality
        holder.share_lay.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareText = "Headline: " + data.getNews_headline() + "\n" + data.getNews_des_1() + "\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

            // Start the share intent
            mContext.startActivity(Intent.createChooser(shareIntent, "Share news via"));
        });
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
