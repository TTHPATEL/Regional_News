package com.app.Regional_News;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.Regional_News.data.Registrationdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.SharedPrefManager;
import com.app.Regional_News.extra.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    TextView loginText;
    Button bt_register;
    EditText et_name, et_email, et_pwd;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getUserLogin()) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }
        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(RegistrationActivity.this, GMainActivity.class));
            finish();
        }

        loginText = findViewById(R.id.loginText);
        et_name = findViewById(R.id.u_name);
        et_email = findViewById(R.id.u_email);
        et_pwd = findViewById(R.id.u_pwd);
        bt_register = findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name = et_name.getText().toString();
                String u_email = et_email.getText().toString();
                String u_pwd = et_pwd.getText().toString();

                if (!u_name.isEmpty() && !u_email.isEmpty() && !u_pwd.isEmpty()) {
                    JSONObject j1 = new JSONObject();
                    try {
                        j1.put("u_name", u_name);
                        j1.put("u_email", u_email);
                        j1.put("u_pwd", u_pwd);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String data = j1.toString();
                    checkRegistration(data);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        checkAndRequestPermissions();
    }

    private void checkRegistration(String data) {
        Log.e("Registration Data", data);
        mApiService.rnsRegistrationRequest("user_registration", data).enqueue(new Callback<Registrationdata>() {
            @Override
            public void onResponse(Call<Registrationdata> call, Response<Registrationdata> response) {
                if (response.isSuccessful()) {
                    Registrationdata registrationData = response.body();
                    if (registrationData != null && registrationData.getStatus().equals("1")) {
                        String msg = registrationData.getMsg();
                        Toast.makeText(RegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();

                        // Save registration data in SharedPreferences
                        String u_name = et_name.getText().toString();
                        String u_email = et_email.getText().toString();
                        String u_pwd = et_pwd.getText().toString();
                        String u_pic = "dss_pic/user.png";

                        sharedPrefManager.saveUserData(u_name, u_email, u_pwd, u_pic);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_F_LOGIN, true);

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        String msg = registrationData.getMsg();
                        Toast.makeText(RegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Registrationdata> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
                Log.e("Registration Error", t.getMessage());
            }
        });
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
