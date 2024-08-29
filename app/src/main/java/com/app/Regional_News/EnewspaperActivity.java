package com.app.Regional_News;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.app.Regional_News.adapter.VPAdapter;
import com.app.Regional_News.adapter.eNewspaperAdapter;
import com.app.Regional_News.data.Enewspaper_list_data;
import com.app.Regional_News.data.Enewspaper_list_listdata;
import com.app.Regional_News.data.Keywords_fetch_data;
import com.app.Regional_News.data.Keywords_fetch_listdata;
import com.app.Regional_News.data.News_showdata;
import com.app.Regional_News.data.News_showlistdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnewspaperActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    eNewspaperAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageView noConnectionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enewspaper);

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the toolbar title
        getSupportActionBar().setTitle(R.string.Enewstitle);

        mApiService = UtilsApi.getAPIService();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
        noConnectionImage = findViewById(R.id.no_connection_image);

        // THIS CODE FOR SHOW VERTICAL LINE IN BELOW OF EACH ITEM IN RECYCLEVIEW
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgress(true); // Show the progress bar when refreshing
                getdata();
            }
        });

        if (NetworkUtils.isConnected(this)) {
            showProgress(true); // Show the progress bar when loading the page
            getdata();
            noConnectionImage.setVisibility(View.GONE);  // Hide the image when there is a connection

        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
            noConnectionImage.setVisibility(View.VISIBLE);  // Show the image when there is no connectio
        }
    }

    private void getdata() {
        mApiService.rnEnewspaperlistRequest("Enewspaper_list")
                .enqueue(new Callback<Enewspaper_list_data>() {
                    @Override
                    public void onResponse(Call<Enewspaper_list_data> call, Response<Enewspaper_list_data> response) {
                        swipeRefreshLayout.setRefreshing(false);

                        if (response.isSuccessful()) {
                            showProgress(false); // Hide the progress bar after getting the response
                            Enewspaper_list_data degdata = response.body();
                            Log.e("msg2", degdata.getMsg());
                            Log.e("size", "List size: " + degdata.getEnewspaper_list_listdata().size());

                            if (degdata.getStatus().equals("1")) {
                                String error_message = degdata.getMsg();
                                Log.e("error_message :",error_message);
                                Toast.makeText(EnewspaperActivity.this, error_message, Toast.LENGTH_SHORT).show();

                                displayData(degdata.getEnewspaper_list_listdata());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(EnewspaperActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Hide the progress bar even if the response is not successful
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Enewspaper_list_data> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        showProgress(false); // Hide the progress bar in case of failure
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

    private void displayData(ArrayList<Enewspaper_list_listdata> degree_list) {
        adapter = new eNewspaperAdapter(EnewspaperActivity.this, degree_list);
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
