package com.app.Regional_News;

import static com.app.Regional_News.extra.UtilsApi.BASE_URL_API;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.Regional_News.data.Profile_Updationdata;
import com.app.Regional_News.data.RN_Udata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.SharedPrefManager;
import com.app.Regional_News.extra.UtilsApi;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdationActivation extends AppCompatActivity {

    Button update_btn;
    EditText u_name, u_email, u_pwd;
    BaseApiService mApiService;
    RN_Udata fp;
    SharedPrefManager sharedPrefManager;
    ImageView success_img,imageView;
    CardView cardview;
    RelativeLayout main;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView current_name, current_email;
    public  static  String uid;


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
        fp = gson.fromJson(fdata, RN_Udata.class);

        u_name = findViewById(R.id.u_name);
        u_email = findViewById(R.id.u_email);
        u_pwd = findViewById(R.id.u_pwd);
        update_btn = findViewById(R.id.update_btn);
        success_img = findViewById(R.id.success_img);
        cardview = findViewById(R.id.cardview);
        main = findViewById(R.id.main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        current_name = findViewById(R.id.current_name);
        current_email = findViewById(R.id.current_email);
        imageView = findViewById(R.id.imageView);


        getCurrentUserdata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCurrentUserdata();
            }

        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create JSON Object with only filled fields
                JSONObject j1 = new JSONObject();
                try {
                    j1.put("u_id", fp.getU_id());
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

    private void getCurrentUserdata() {
        // DATA FETCHING FROM USER
        String fdata = sharedPrefManager.getFdata();
        Gson gson = new Gson();
        fp = gson.fromJson(fdata, RN_Udata.class);
        swipeRefreshLayout.setRefreshing(false);

        String c_pic = BASE_URL_API + fp.getU_pic();
        uid = fp.getU_id();
        Picasso.get()
                .load(c_pic)
                .resize(100, 100)
                .centerCrop()
                .into(imageView);
        current_name.setText(fp.getU_name());
        current_email.setText(fp.getU_email());
    }

    private void updateProfile(String data) {
        mApiService.rnsProfileUpdationRequest("profile_update", data).enqueue(new Callback<Profile_Updationdata>() {
            @Override
            public void onResponse(Call<Profile_Updationdata> call, Response<Profile_Updationdata> response) {
                if (response.isSuccessful()) {
                    Profile_Updationdata RN_Udata = response.body();
                    if (RN_Udata.getStatus().equals("1")) {
                        String msg = RN_Udata.getMsg();
                        Toast.makeText(ProfileUpdationActivation.this, "" + msg, Toast.LENGTH_SHORT).show();
                        successMessage();

                        // Update `fp` object with the new values
                        if (!u_name.getText().toString().isEmpty()) {
                            fp.setU_name(u_name.getText().toString());
                        }
                        if (!u_email.getText().toString().isEmpty()) {
                            fp.setU_email(u_email.getText().toString());
                        }
                        if (!u_pwd.getText().toString().isEmpty()) {
                            fp.setU_pwd(u_pwd.getText().toString());
                        }

                        // Clear the EditText fields
                        u_name.setText("");
                        u_email.setText("");
                        u_pwd.setText("");

                        // Update SharedPreferences
                        updateSharedPreferences();

                        // Refresh UI with updated data
                        getCurrentUserdata();
                    } else {
                        String msg = RN_Udata.getMsg();
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

    private void updateSharedPreferences() {
        // Create a new SharedPrefManager instance
        SharedPrefManager sharedPrefManager = new SharedPrefManager(ProfileUpdationActivation.this);

        // Convert the updated user data to JSON
        Gson gson = new Gson();
        String updatedFdata = gson.toJson(fp);

        // Save the updated data to SharedPreferences
        sharedPrefManager.saveFdata(updatedFdata);
    }

    private void successMessage() {
//        success_img.setVisibility(View.VISIBLE);
//        cardview.setVisibility(View.GONE);
//        main.setBackgroundColor(ContextCompat.getColor(ProfileUpdationActivation.this, R.color.background_of_success_img));
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
