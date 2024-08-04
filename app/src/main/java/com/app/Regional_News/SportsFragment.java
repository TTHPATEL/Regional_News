package com.app.Regional_News;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.Regional_News.adapter.NewslistAdapter;
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


public class SportsFragment extends Fragment {

    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    SearchNewslistAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String new_keyword = "Cricket";


    public SportsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sports, container, false);



        mApiService = UtilsApi.getAPIService();
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = rootView.findViewById(R.id.lyt_not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView



        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata(new_keyword);
            }
        });

        if (NetworkUtils.isConnected(getActivity())) {
            showProgress(true);
            getdata(new_keyword);
        } else {
            Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }








        return rootView;
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
                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
                                displayData(degdata.getSearch_news_list_show());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
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
        adapter = new SearchNewslistAdapter(getActivity(), degree_list);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}