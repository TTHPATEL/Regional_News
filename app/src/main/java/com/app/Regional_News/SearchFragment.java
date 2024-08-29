package com.app.Regional_News;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.Regional_News.R;
import com.app.Regional_News.Search_newslistActivity;
import com.app.Regional_News.data.Keywords_fetch_data;
import com.app.Regional_News.data.Keywords_fetch_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    BaseApiService mApiService;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private LinearLayout searchLinearLayout;
    public ImageView tv_Narendra_Modi,tv_Sushant_Singh_Rajput,S_Jaishankar,tv_ratan_tata,tv_pratik,tv_Joe_Biden ;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageView noConnectionImage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mApiService = UtilsApi.getAPIService();

        searchLinearLayout = rootView.findViewById(R.id.searchlinearlayout);
        listView = rootView.findViewById(R.id.listview);
        tv_Narendra_Modi = rootView.findViewById(R.id.tv_Narendra_Modi);
        tv_Sushant_Singh_Rajput = rootView.findViewById(R.id.tv_Sushant_Singh_Rajput);
        S_Jaishankar = rootView.findViewById(R.id.S_Jaishankar);
        tv_ratan_tata = rootView.findViewById(R.id.tv_ratan_tata);
        tv_pratik = rootView.findViewById(R.id.tv_pratik);
        tv_Joe_Biden = rootView.findViewById(R.id.tv_Joe_Biden);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        noConnectionImage = rootView.findViewById(R.id.no_connection_image);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchKeywords();
            }
        });
        // Initialize ArrayAdapter with empty data
        arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(arrayAdapter);

        // Set an item click listener on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item's text
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Display a Toast message with the selected item's text
                Toast.makeText(requireContext(), "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
                // Start the KeywordDetailActivity and pass the selected item's text
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("keyword", selectedItem);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true); // Important to notify that the fragment has its own menu




        // Set click listener on tv_Narendra_Modi
        tv_Narendra_Modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Politics");
                startActivity(intent);
            }
        });


        // Set click listener on tv_Sushant_Singh_Rajput
        tv_Sushant_Singh_Rajput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Entertainment");
                startActivity(intent);
            }
        });


        // Set click listener on S_Jaishankar
        S_Jaishankar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Politics");
                startActivity(intent);
            }
        });

        // Set click listener on tv_ratan_tata
        tv_ratan_tata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Business");
                startActivity(intent);
            }
        });

        // Set click listener on tv_pratik
        tv_pratik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Entertainment");
                startActivity(intent);
            }
        });

        // Set click listener on tv_Joe_Biden
        tv_Joe_Biden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Search_newslistActivity.class);
                intent.putExtra("Image_direct_keyword", "Politics");
                startActivity(intent);
            }
        });





        if (NetworkUtils.isConnected(getActivity())) {
            fetchKeywords();
            noConnectionImage.setVisibility(View.GONE);  // Hide the image when there is a connection
        } else {
            Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
            noConnectionImage.setVisibility(View.VISIBLE);  // Show the image when there is no connectio
        }

        return rootView;
    }

    private void fetchKeywords() {
        mApiService.rnKeywordRequest("keyword_fetch")
                .enqueue(new Callback<Keywords_fetch_data>() {
                    @Override
                    public void onResponse(Call<Keywords_fetch_data> call, Response<Keywords_fetch_data> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful() && response.body() != null) {
                            Keywords_fetch_data data = response.body();
                            if (data.getStatus().equals("1")) {
                                ArrayList<Keywords_fetch_listdata> keywords = data.getKeyword_fetch_list();
                                if (keywords != null) {
                                    ArrayList<String> keywordStrings = new ArrayList<>();
                                    for (Keywords_fetch_listdata keywordData : keywords) {
                                        keywordStrings.add(keywordData.getKeyword());
                                    }
                                    arrayAdapter.clear();
                                    arrayAdapter.addAll(keywordStrings);
                                    arrayAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getActivity(), "No keywords found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Response Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Keywords_fetch_data> call, Throwable t) {
                        Toast.makeText(getActivity(), "API Call Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide searchLinearLayout and make listView match_parent
                searchLinearLayout.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                listView.setLayoutParams(params);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Show searchLinearLayout and restore listView size
                searchLinearLayout.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = getResources().getDimensionPixelSize(R.dimen.original_listview_height);
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                listView.setLayoutParams(params);
                return false;
            }
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
