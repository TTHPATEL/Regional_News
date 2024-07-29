package com.app.Regional_News;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.app.Regional_News.data.Udata;
import com.app.Regional_News.extra.SharedPrefManager;
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
    Udata fp;
    public  static  String uid;
    private Toolbar toolbar;

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
                View searchView = toolbar.getMenu().findItem(R.id.action_search).getActionView();
                // Add listeners or any additional setup for searchView
            } else if (destination.getId() == R.id.nav_setting) {
                toolbar.setTitle("Setting");
                toolbar.setLogo(null);
                toolbar.getMenu().clear();
            }
        });
        
        
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
