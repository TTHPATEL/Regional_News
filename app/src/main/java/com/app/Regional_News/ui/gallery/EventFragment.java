package com.app.Regional_News.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.Regional_News.R;
import com.app.Regional_News.adapter.EventAdapter;
import com.app.Regional_News.data.Eventdata;
import com.app.Regional_News.data.Eventlistdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {
//    public RecyclerView recyclerView;
//    private ProgressBar progressBar;
//    private LinearLayout lyt_not_found;
    BaseApiService mApiService;
    EventAdapter adapter;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] name = {"Stock Market", "Cricket", "UG Result", "Budget", "Civil Services", "Politics", "Scholarship", "Education", "Banking Sector", "Pharma Sector", "Olympics", "Electronics", "Business", "Social Media"};



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        mApiService = UtilsApi.getAPIService();

//        lyt_not_found = rootView.findViewById(R.id.lyt_not_found);
//        progressBar = rootView.findViewById(R.id.progressBar);
//        recyclerView = rootView.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);


        listView = rootView.findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, name);
        listView.setAdapter(arrayAdapter);

        setHasOptionsMenu(true); // Important to notify that the fragment has its own menu



//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
//        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView




//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
//        recyclerView.addItemDecoration(itemDecoration);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
//        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));
        if (NetworkUtils.isConnected(getActivity())) {
//            showProgress(true);
//            getevent();
        } else {
            Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search operation with the query
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update search suggestions as the user types
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void performSearch(String query) {
        Toast.makeText(requireContext(), "Search query: " + query, Toast.LENGTH_SHORT).show();

    }


    //    private void getevent() {
//        mApiService.dsseventRequest("event_list")
//                .enqueue(new Callback<Eventdata>() {
//                    @Override
//                    public void onResponse(Call<Eventdata> call, Response<Eventdata> response) {
//                        if (response.isSuccessful()){
//                            Log.e("msg",""+response.code());
//                            showProgress(false);
//                            Eventdata degdata=response.body();
//                            Log.e("msg2",degdata.getMsg());
//                            if (degdata.getStatus().equals("1")){
//                                String error_message = degdata.getMsg();
//                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
//                                displayData(degdata.getEvent_list());
//                            } else {
//                                String error_message = degdata.getMsg();
//                                Toast.makeText(getActivity(), error_message, Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Log.e("msg1",""+response.code());
//                            Log.e("msg5",""+call.request().url());
//                            showProgress(false);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Eventdata> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        showProgress(false);
//                    }
//                });
//    }
//    private void showProgress(boolean show) {
//        if (show) {
//            progressBar.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//            lyt_not_found.setVisibility(View.GONE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//    }
//    private void displayData(ArrayList<Eventlistdata> degree_list) {
//        adapter = new EventAdapter(getActivity(), degree_list);
//        recyclerView.setAdapter(adapter);
//
//        if (adapter.getItemCount() == 0) {
//            lyt_not_found.setVisibility(View.VISIBLE);
//        } else {
//            lyt_not_found.setVisibility(View.GONE);
//        }
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}