package com.app.Regional_News;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.app.Regional_News.data.Udata;
import com.app.Regional_News.extra.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    SharedPrefManager sharedPrefManager;
    Udata fp;
    public  static  String uid;
    private Toolbar toolbar;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this points to the correct layout file


        sharedPrefManager = new SharedPrefManager(this);
        String fdata=sharedPrefManager.getFdata();
        Gson gson = new Gson();
        fp = gson.fromJson(fdata, Udata.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Ensure this ID matches your layout

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_event, R.id.nav_setting)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_home) {
                toolbar.setTitle("Regional News");
                toolbar.setLogo(R.drawable.icon_news);
                toolbar.getMenu().clear();
                getMenuInflater().inflate(R.menu.main, toolbar.getMenu());
            } else if (destination.getId() == R.id.nav_event) {
                toolbar.setTitle("");
                toolbar.setLogo(null);
                toolbar.getMenu().clear();

                getMenuInflater().inflate(R.menu.event_menu, toolbar.getMenu());
                MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
                searchView = (SearchView) searchItem.getActionView();

//                searchItem.expandActionView();
//                searchView.requestFocus();
                // Set up the SearchView listener
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
                        updateSuggestions(newText);
                        return false;
                    }
                });


//                getMenuInflater().inflate(R.menu.event_menu, toolbar.getMenu());
//                View searchView = toolbar.getMenu().findItem(R.id.action_search).getActionView();
//                // Add listeners or any additional setup for searchView
            } else if (destination.getId() == R.id.nav_setting) {
                toolbar.setTitle("Setting");
                toolbar.setLogo(null);
                toolbar.getMenu().clear();
            }
        });


    }

    private void performSearch(String query) {
        // Implement search functionality here
        // For example, query the database and show results
        Toast.makeText(this, "Search query: " + query, Toast.LENGTH_SHORT).show();
    }

    private void updateSuggestions(String query) {
        // Implement suggestion fetching from the database here
        // For example, update search suggestions dynamically
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}