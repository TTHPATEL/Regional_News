package com.app.Regional_News.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.Regional_News.R;
import com.app.Regional_News.data.Notification_listdata;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private final ArrayList<Notification_listdata> clist;
    private final Context mContext;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noti_title,noti_desc,noti_time;
        public LinearLayout noti_list_layout;

        public ViewHolder(View v) {
            super(v);
            noti_title = v.findViewById(R.id.noti_title);
            noti_list_layout = v.findViewById(R.id.noti_list_layout);
            noti_desc = v.findViewById(R.id.noti_desc);
            noti_time = v.findViewById(R.id.noti_time);
        }
    }

    // Provide a suitable constructor
    public NotificationsAdapter(Context context, ArrayList<Notification_listdata> data) {
        this.clist = data;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification, parent, false);

        NotificationsAdapter.ViewHolder vh = new NotificationsAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        // Get element from arraylist at this position and replace the contents of the view with that element
        final Notification_listdata data = clist.get(position);
        holder.noti_title.setText(data.getNoti_title());
        holder.noti_desc.setText(data.getNoti_desc());
        holder.noti_time.setText(data.getNoti_time());
        holder.noti_title.setTextColor(ContextCompat.getColor(mContext, R.color.teal_700)); // Replace with your normal color

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}
