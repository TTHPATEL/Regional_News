package com.app.Regional_News;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.Regional_News.data.RN_Udata;
import com.app.Regional_News.extra.SharedPrefManager;
import com.app.Regional_News.ui.gallery.AddComplainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    SharedPrefManager sharedPrefManager;
    RN_Udata fp;
    public static String uid;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);
        String fdata = sharedPrefManager.getFdata();
        Gson gson = new Gson();
        fp = gson.fromJson(fdata, RN_Udata.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_event, R.id.nav_setting)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_home) {
                toolbar.setTitle(R.string.app_name);

                toolbar.setLogo(R.drawable.icon_news);
            } else if (destination.getId() == R.id.nav_event) {
                toolbar.setTitle(R.string.menu_search);
                toolbar.setLogo(null);
            } else if (destination.getId() == R.id.nav_setting) {
                toolbar.setTitle(R.string.menu_setting);
                toolbar.setLogo(null);
            }
            invalidateOptionsMenu(); // Request to recreate the options menu
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int currentFragmentId = navController.getCurrentDestination().getId();
        if (currentFragmentId == R.id.nav_home) {
            getMenuInflater().inflate(R.menu.main, menu);
        } else if (currentFragmentId == R.id.nav_event) {
            // Do not inflate any menu here, as the fragment will handle it
        } else if (currentFragmentId == R.id.nav_setting) {
            // Inflate the settings menu if needed
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Enewspaper) {
            // Open the new activity when Enewspaper is clicked
            Intent intent = new Intent(this, EnewspaperActivity.class); // Replace 'EnewspaperActivity' with your target activity
            startActivity(intent);
            return true;
        }



        // Handle other menu item clicks if needed
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
