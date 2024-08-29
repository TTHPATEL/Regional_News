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
import com.app.Regional_News.data.Saved_news_datalist;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavednewsAdapater  extends RecyclerView.Adapter<SavednewsAdapater.ViewHolder>{

    private final ArrayList<Saved_news_datalist> clist;
    private final Context mContext;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_m_name, tv_m_amount, keywordtext;
        public CardView cardview;
        public ImageView news_images;
        public LinearLayout news_list_layout;
        public CheckBox news_checkbox; // Add CheckBox
        public LinearLayout share_lay;


        public ViewHolder(View v) {
            super(v);
            tv_m_name = v.findViewById(R.id.news_headline);
            cardview = v.findViewById(R.id.cardview);
            news_images = v.findViewById(R.id.news_images); // This is where news_images ImageView is initialized
            news_list_layout = v.findViewById(R.id.news_list_layout);
            keywordtext = v.findViewById(R.id.keywordtext);
            news_checkbox = v.findViewById(R.id.save_check); // Initialize CheckBox
            share_lay = v.findViewById(R.id.share_lay);


        }
    }

    // Provide a suitable constructor
    public SavednewsAdapater(Context context, ArrayList<Saved_news_datalist> data) {
        this.clist = data;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SavednewsAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_newslist, parent, false);
        return new SavednewsAdapater.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SavednewsAdapater.ViewHolder holder, int position) {
        // - get element from arraylist at this position
        // - replace the contents of the view with that element
        final Saved_news_datalist data = clist.get(position);
        holder.tv_m_name.setText(data.getNews_headline());
        holder.keywordtext.setText(data.getKeyword());

        holder.news_checkbox.setChecked(true); // Set the checkbox to checked
        holder.news_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                // If the checkbox is unchecked, remove the item from SharedPreferences and the list
                SharedPreferences.Editor editor = mContext.getSharedPreferences("favorites", Context.MODE_PRIVATE).edit();
                editor.remove(data.getNews_id());
                editor.apply();
                clist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, clist.size());
            }
        });

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

        holder.news_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NewsShowActivity.class);
                i.putExtra("getNews_id", data.getNews_id());
                i.putExtra("news_imgurl", data.getNews_imgurl());
                i.putExtra("isChecked", holder.news_checkbox.isChecked()); // Pass the checkbox state
                mContext.startActivity(i);
            }
        });

        // Handle sharing functionality
        holder.share_lay.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareText = "Headline: " + data.getNews_headline() + "\n" + data.getNews_des_1() + "\n" + "News by : Regional News " +"\n";
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
