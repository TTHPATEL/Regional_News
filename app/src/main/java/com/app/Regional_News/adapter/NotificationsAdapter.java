package com.app.Regional_News.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.app.Regional_News.R;
import com.app.Regional_News.data.Notification_listdata;

import java.util.ArrayList;

public class NotificationsAdapter extends ArrayAdapter<Notification_listdata> {

    public NotificationsAdapter(Context context, ArrayList<Notification_listdata> notifications) {
        super(context, 0, notifications);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_notification_item, parent, false);
        }

        Notification_listdata notification = getItem(position);

        TextView title = convertView.findViewById(R.id.noti_title);
        TextView description = convertView.findViewById(R.id.noti_desc);
        TextView time = convertView.findViewById(R.id.noti_time);

        title.setText(notification.getNoti_title());
        description.setText(notification.getNoti_desc());
        time.setText(notification.getNoti_time());

        // Customize the appearance based on the notification status if needed
        if ("unread".equals(notification.getNoti_status())) {
            title.setTextColor(ContextCompat.getColor(getContext(), R.color.pink));
        } else {
            title.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        }

        return convertView;
    }
}
