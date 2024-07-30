package com.app.Regional_News;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
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

import com.app.Regional_News.adapter.NewslistAdapter;
import com.app.Regional_News.data.News_listfetch_data;
import com.app.Regional_News.data.News_listfetch_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_newslistActivity extends AppCompatActivity {



    private TextView keywordDetail;
    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    NewslistAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_newslist);

        mApiService = UtilsApi.getAPIService();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView



//        keywordDetail = findViewById(R.id.keywordDetail);

        Intent intent = getIntent();
        if (intent != null) {
            String keyword = intent.getStringExtra("keyword");
//            keywordDetail.setText(keyword);
        }

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getmm();
            }
        });


        if (NetworkUtils.isConnected(this)) {
            showProgress(true);
            getmm();
        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
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

    private void getmm() {

        mApiService.rnNewslistRequest("news_list_show")
                .enqueue(new Callback<News_listfetch_data>() {
                    @Override
                    public void onResponse(Call<News_listfetch_data> call, Response<News_listfetch_data> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful()){
                            Log.e("msg",""+response.code());
                            showProgress(false);
                            News_listfetch_data degdata=response.body();
                            Log.e("msg2",degdata.getMsg());
                            if (degdata.getStatus().equals("1")){
                                String error_message = degdata.getMsg();
                                Toast.makeText(Search_newslistActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                displayData(degdata.getNews_list_show());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(Search_newslistActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("msg1",""+response.code());
                            Log.e("msg5",""+call.request().url());
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<News_listfetch_data> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        showProgress(false);
                    }
                });
        
    }

    private void displayData(ArrayList<News_listfetch_listdata> degree_list) {
        adapter = new NewslistAdapter(this, degree_list);
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
            // your code
            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
