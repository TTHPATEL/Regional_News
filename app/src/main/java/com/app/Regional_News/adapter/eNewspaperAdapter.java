package com.app.Regional_News.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.Regional_News.EnewspaperShowActivity;
import com.app.Regional_News.R;
import com.app.Regional_News.data.Enewspaper_list_listdata;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class eNewspaperAdapter extends RecyclerView.Adapter<eNewspaperAdapter.ViewHolder> {

    private final ArrayList<Enewspaper_list_listdata> clist;
    private final Context mContext;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_enewspaper_title,tv_enewspaper_date;
        public LinearLayout enewspaper_list_layout;

        public ViewHolder(View v) {
            super(v);
            tv_enewspaper_title = v.findViewById(R.id.tv_enewspaper_title);
            enewspaper_list_layout = v.findViewById(R.id.enewspaper_list_layout);
            tv_enewspaper_date = v.findViewById(R.id.tv_enewspaper_date);
        }
    }

    // Provide a suitable constructor
    public eNewspaperAdapter(Context context, ArrayList<Enewspaper_list_listdata> data) {
        this.clist = data;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public eNewspaperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_enewspaper, parent, false);

        eNewspaperAdapter.ViewHolder vh = new eNewspaperAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(eNewspaperAdapter.ViewHolder holder, int position) {
        final Enewspaper_list_listdata data = clist.get(position);
        Log.e("Adapter", "Binding item at position: " + position); // Log item binding
        holder.tv_enewspaper_title.setText(data.getEnewspaper_title());
        holder.tv_enewspaper_date.setText(data.getEnews_date());

      




        holder.enewspaper_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EnewspaperShowActivity.class);
//                i.putExtra("getEnews_pdf_data", data.getEnews_pdf_data());
                i.putExtra("getEnewspaper_title", data.getEnewspaper_title());
                i.putExtra("getEnewspaper_id",data.getEnewspaper_id());
                mContext.startActivity(i);
            }
        });

        
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
