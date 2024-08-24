package com.app.Regional_News;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.Regional_News.adapter.NotificationAdapter;
import com.app.Regional_News.adapter.NotificationsAdapter;
import com.app.Regional_News.adapter.NotificationsAdapter;
import com.app.Regional_News.adapter.NotificationsAdapter;
import com.app.Regional_News.data.Notification_data;
import com.app.Regional_News.data.Notification_listdata;
import com.app.Regional_News.data.Notification_data;
import com.app.Regional_News.data.Notification_listdata;
import com.app.Regional_News.data.Notification_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private NotificationsAdapter adapter;
    private ArrayList<Notification_listdata> notificationList;
    private BaseApiService mApiService;
    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the toolbar title
        getSupportActionBar().setTitle(R.string.notification);
        mApiService = UtilsApi.getAPIService();
        

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

        // THIS CODE FOR SHOW VERTICAL LINE IN BELOW OF EACH ITEM IN RECYCLEVIEW
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });

        if (NetworkUtils.isConnected(this)) {
            showProgress(true);
            getdata();
        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }

    }

private void getdata() {
    mApiService.rnsNotificationRequest("notification_list")
            .enqueue(new Callback<Notification_data>() {
                @Override
                public void onResponse(Call<Notification_data> call, Response<Notification_data> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()){
                        Log.e("msg",""+response.code());
                        showProgress(false);
                        Notification_data degdata=response.body();
                        Log.e("msg2",degdata.getMsg());
                        if (degdata.getStatus().equals("1")){
                            String error_message = degdata.getMsg();
                            Toast.makeText(NotificationActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            displayData(degdata.getNotification_listdata());
                        } else {
                            String error_message = degdata.getMsg();
                            Toast.makeText(NotificationActivity.this, error_message, Toast.LENGTH_SHORT).show();
                        }   
                    } else {
                        Log.e("msg1",""+response.code());
                        Log.e("msg5",""+call.request().url());
                        showProgress(false);
                    }
                }

                @Override
                public void onFailure(Call<Notification_data> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("debug", "onFailure: ERROR > " + t.toString());
                    showProgress(false);
                }
            });
}
    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    private void displayData(ArrayList<Notification_listdata> degree_list) {
        adapter = new NotificationsAdapter(NotificationActivity.this, degree_list);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }
    }


    // IMPORTANT FOR BACK NAVIGATION BY ARROW
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // IMPORTANT FOR BACK NAVIGATION BY ARROW
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}