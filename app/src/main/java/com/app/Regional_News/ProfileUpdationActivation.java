package com.app.Regional_News;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.app.Regional_News.data.Profile_Updationdata;
import com.app.Regional_News.data.Udata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.SharedPrefManager;
import com.app.Regional_News.extra.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdationActivation extends AppCompatActivity {

    Button update_btn;
    EditText u_name, u_email, u_pwd;
    BaseApiService mApiService;
    Udata fp;
    SharedPrefManager sharedPrefManager;
    ImageView success_img;
    CardView cardview;
    RelativeLayout main;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_updation_activation);

        mApiService = UtilsApi.getAPIService();

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the toolbar title
        getSupportActionBar().setTitle("Profile Updation");

        // DATA FETCHING FROM USER
        sharedPrefManager = new SharedPrefManager(this);
        String fdata = sharedPrefManager.getFdata();
        Gson gson = new Gson();
        fp = gson.fromJson(fdata, Udata.class);

        u_name = findViewById(R.id.u_name);
        u_email = findViewById(R.id.u_email);
        u_pwd = findViewById(R.id.u_pwd);
        update_btn = findViewById(R.id.update_btn);
        success_img = findViewById(R.id.success_img);
        cardview = findViewById(R.id.cardview);
        main = findViewById(R.id.main);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create JSON Object with only filled fields
                JSONObject j1 = new JSONObject();
                try {
                    j1.put("sm_u_id", fp.getU_id());
                    boolean hasData = false; // Flag to check if any field has data

                    if (!u_name.getText().toString().isEmpty()) {
                        j1.put("u_name", u_name.getText().toString());
                        hasData = true;
                    }
                    if (!u_email.getText().toString().isEmpty()) {
                        j1.put("u_email", u_email.getText().toString());
                        hasData = true;
                    }
                    if (!u_pwd.getText().toString().isEmpty()) {
                        j1.put("u_pwd", u_pwd.getText().toString());
                        hasData = true;
                    }

                    if (!hasData) {
                        Toast.makeText(ProfileUpdationActivation.this, "Please fill at least one field", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String data = j1.toString();
                updateProfile(data);
            }
        });
    }

    private void updateProfile(String data) {
        mApiService.rnsProfileUpdationRequest("profile_update", data).enqueue(new Callback<Profile_Updationdata>() {
            @Override
            public void onResponse(Call<Profile_Updationdata> call, Response<Profile_Updationdata> response) {
                if (response.isSuccessful()) {
                    Profile_Updationdata udata = response.body();
                    if (udata.getStatus().equals("1")) {
                        String msg = udata.getMsg();
                        Toast.makeText(ProfileUpdationActivation.this, "" + msg, Toast.LENGTH_SHORT).show();
                        successMessage();
                        u_name.setText("");
                        u_email.setText("");
                        u_pwd.setText("");
                    } else {
                        String msg = udata.getMsg();
                        Toast.makeText(ProfileUpdationActivation.this, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Profile_Updationdata> call, Throwable t) {
                Toast.makeText(ProfileUpdationActivation.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void successMessage() {
        success_img.setVisibility(View.VISIBLE);
        cardview.setVisibility(View.GONE);
//        main.setBackground(null);
        main.setBackgroundColor(ContextCompat.getColor(ProfileUpdationActivation.this, R.color.background_of_success_img));

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
