package com.app.Regional_News;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.Regional_News.adapter.SearchNewslistAdapter;
import com.app.Regional_News.data.Search_News_listfetch_data;
import com.app.Regional_News.data.Search_News_listfetch_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_newslistActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    SearchNewslistAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String new_keyword = null;
    ImageView noConnectionImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_newslist);

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // INTENT GETTING KEYWORD
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        String Image_direct_keyword = intent.getStringExtra("Image_direct_keyword");

        // Determine which value to use

        if (keyword != null && !keyword.isEmpty()) {
            new_keyword = keyword;
        } else if (Image_direct_keyword != null && !Image_direct_keyword.isEmpty()) {
            new_keyword = Image_direct_keyword;
        }

        // Set the toolbar title
        getSupportActionBar().setTitle(new_keyword);

        mApiService = UtilsApi.getAPIService();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
        noConnectionImage = findViewById(R.id.no_connection_image);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata(new_keyword);
            }
        });

        if (NetworkUtils.isConnected(this)) {
            showProgress(true);
            getdata(new_keyword);
            noConnectionImage.setVisibility(View.GONE);  // Hide the image when there is a connection

        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
            noConnectionImage.setVisibility(View.VISIBLE);  // Show the image when there is no connectio

        }
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

    private void getdata(String keyword) {
        mApiService.rnSearchNewslistRequest("search_news_list_show", keyword)
                .enqueue(new Callback<Search_News_listfetch_data>() {
                    @Override
                    public void onResponse(Call<Search_News_listfetch_data> call, Response<Search_News_listfetch_data> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful()) {
                            Log.e("msg", "" + response.code());
                            showProgress(false);
                            Search_News_listfetch_data degdata = response.body();
                            Log.e("msg2", degdata.getMsg());
                            if (degdata.getStatus().equals("1")) {
                                String error_message = degdata.getMsg();
                                Log.e("error_message :",error_message);
//                                Toast.makeText(Search_newslistActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                displayData(degdata.getSearch_news_list_show());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(Search_newslistActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("msg1", "" + response.code());
                            Log.e("msg5", "" + call.request().url());
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Search_News_listfetch_data> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        showProgress(false);
                    }
                });
    }

    private void displayData(ArrayList<Search_News_listfetch_listdata> degree_list) {
        adapter = new SearchNewslistAdapter(this, degree_list);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata(new_keyword);// Refresh data when the fragment is visible again
    }
}
