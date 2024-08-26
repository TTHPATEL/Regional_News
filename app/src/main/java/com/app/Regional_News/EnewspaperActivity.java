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
        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
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

    public static class SearchFragment extends Fragment {

        BaseApiService mApiService;
        private ListView listView;
        private ArrayAdapter<String> arrayAdapter;
        private LinearLayout searchLinearLayout;
        public ImageView tv_Narendra_Modi,tv_Sushant_Singh_Rajput,S_Jaishankar,tv_ratan_tata,tv_pratik,tv_Joe_Biden ;
        private SwipeRefreshLayout swipeRefreshLayout;


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
            } else {
                Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
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

    public static class NewsShowActivity extends AppCompatActivity {
        BaseApiService mApiService;
        TextView news_headline,news_des_1,news_des_2,timetext,keywordtext;
        ImageView news_images;
        CheckBox save_check;
        LinearLayout share_lay;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_news_show);
            Bundle extras = getIntent().getExtras();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.newshowtitle);


            String getNews_id = extras.getString("getNews_id");
            String news_imgurl = extras.getString("news_imgurl");
            boolean isChecked = extras.getBoolean("isChecked");


            mApiService = UtilsApi.getAPIService();
            news_headline = findViewById(R.id.news_headline);
            news_des_1 = findViewById(R.id.news_des_1);
            news_des_2 = findViewById(R.id.news_des_2);
            timetext = findViewById(R.id.timetext);
            news_images = findViewById(R.id.news_images);
            keywordtext = findViewById(R.id.keywordtext);
            save_check = findViewById(R.id.save_check);
            share_lay = findViewById(R.id.share_lay);


            // Load image using Picasso
            if (news_imgurl != null && !news_imgurl.isEmpty()) {
                Picasso.get().load(news_imgurl).placeholder(R.drawable.image_not_found).into(news_images);
            } else {
                news_images.setImageResource(R.drawable.image_not_found); // Default image if no URL provided
            }

                // Initialize the checkbox based on the passed state
            save_check.setChecked(isChecked);

            // Set up checkbox listener to update SharedPreferences
            save_check.setOnCheckedChangeListener((buttonView, isChecked1) -> {
                SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getNews_id, isChecked1);
                editor.apply();
            });

            share_lay.setOnClickListener(view -> {
                // Retrieve the data you want to share
                String headline = news_headline.getText().toString();
                String description = news_des_1.getText().toString();

                // Create the share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                // Prepare the text to share
                String shareText = "Headline: " + headline + "\n" + description + "\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                // Start the share intent
                startActivity(Intent.createChooser(shareIntent, "Share news via"));
            });


    //        String mm_id = extras.getString("mm_id");
    //        String mm_m_year = extras.getString("mm_m_year");
    //        tv_m_month.setText("Maintance Month:-" + mm_m_year);
    //        Log.e("Pay Man id", "" + mm_id);


            if (NetworkUtils.isConnected(this)) {
                getnews(getNews_id);
            } else {
                Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
            }

        }


        private void getnews(String getNewsId) {

            mApiService.rnNewsshowRequest("news_show",getNewsId)
                    .enqueue(new Callback<News_showdata>() {
                        @Override
                        public void onResponse(Call<News_showdata> call, Response<News_showdata> response) {
                            if (response.isSuccessful()){
                                Log.e("msg",""+response.code());
                                News_showdata degdata=response.body();
                                Log.e("msg2",degdata.getMsg());
                                if (degdata.getStatus().equals("1")){
                                    String error_message = degdata.getMsg();
                                    Toast.makeText(NewsShowActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                    displayData(degdata.getNews_show());
                                } else {
                                    String error_message = degdata.getMsg();
                                    Toast.makeText(NewsShowActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("msg1",""+response.code());
                                Log.e("msg5",""+call.request().url());
                            }
                        }

                        // Add this method to handle data display
                        private void displayData(ArrayList<News_showlistdata> newsListUser) {
                            if (newsListUser != null && !newsListUser.isEmpty()) {
                                News_showlistdata firstNewsItem = newsListUser.get(0);
                                news_headline.setText(firstNewsItem.getNews_headline());
                                news_des_1.setText(firstNewsItem.getNews_des_1());
                                news_des_2.setText(firstNewsItem.getNews_des_2());
                                timetext.setText(firstNewsItem.getNews_datetime());
                                keywordtext.setText((firstNewsItem.getKeyword()));
                            }
                            else
                            {
                                Toast.makeText(NewsShowActivity.this,"Display mee Kuch to gadbad hai dayaa! ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<News_showdata> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());

                        }
                    });
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

    public static class HomeFragment extends Fragment {

        private TabLayout tabLayout;
        private ViewPager viewPager;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            tabLayout = rootView.findViewById(R.id.tablayout);
            viewPager = rootView.findViewById(R.id.viewpager);

            VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            vpAdapter.addFragment(new latest_news(),getString(R.string.home_tab_latest_news));
            vpAdapter.addFragment(new OlympicsFragment(),getString(R.string.home_olympic));
            vpAdapter.addFragment(new StockMarketFragment(),getString(R.string.home_stock_news));
            vpAdapter.addFragment(new SportsFragment(),getString(R.string.home_sport_news));
            vpAdapter.addFragment(new EntertainmentFragment(),getString(R.string.home_entertainment_news));
            vpAdapter.addFragment(new EditorialFragment(),getString(R.string.home_editoral_news));
            viewPager.setAdapter(vpAdapter);
            tabLayout.setupWithViewPager(viewPager);

            setUpTabIcons();


            return rootView;
        }

        private void setUpTabIcons() {

            int[] tabIcons = {
                    R.drawable.ic_trending,
                    R.drawable.ic_olympic,
                    R.drawable.ic_news_headline,
                    R.drawable.ic_cricket,
                    R.drawable.ic_entertainment,
                    R.drawable.ic_tentri_lekh        };


            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) {
                    View customView = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                    ImageView tabIcon = customView.findViewById(R.id.tab_icon);
                    TextView tabText = customView.findViewById(R.id.tab_text);
                    tabIcon.setImageResource(tabIcons[i]);
                    tabText.setText(tab.getText());
                    tab.setCustomView(customView);
                }
            }
        }


        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }
    }
}
