package com.app.Regional_News;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Regional_News.data.RN_Udata;
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

public class LoginActivity extends AppCompatActivity {

    Button bt_login,bt_slogin,bt_play;
    EditText et_email,et_password;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    TextView registrationText; // Declare signupText

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(R.string.logintitle);




        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getUserLogin()){
            Intent i =new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        et_email=findViewById(R.id.ed_email);
        et_password=findViewById(R.id.ed_password);
        bt_login=findViewById(R.id.bt_login);
        registrationText = findViewById(R.id.registrationText);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=et_email.getText().toString();
                String paswor=et_password.getText().toString();

                if(!email.isEmpty()&&!paswor.isEmpty())
                {
                    JSONObject j1=new JSONObject();
                    try {
                        j1.put("u_email",email);
                        j1.put("u_pwd",paswor);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String data=j1.toString();
                    checklogin(data);

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Enter email and Password",Toast.LENGTH_SHORT).show();
                }
//
            }
        });

        registrationText.setOnClickListener(new View.OnClickListener() { // Set onClickListener for signupText
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        checkAndRequestPermissions();
    }

    private void checklogin(String data) {
        Log.e("Login Data",data);
        mApiService.rnsLoginRequest("rns_user_login",data).enqueue(new Callback<RN_Udata>() {
            @Override
            public void onResponse(Call<RN_Udata> call, Response<RN_Udata> response) {
                if (response.isSuccessful()){

                    RN_Udata RN_Udata=response.body();
                    if (RN_Udata.getStatus().equals("1")){
                        String msg=RN_Udata.getMsg();
//    Toast.makeText(LoginActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String json = gson.toJson(RN_Udata);
                        sharedPrefManager.saveSPString(SharedPrefManager.F_ldata, json);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_F_LOGIN, true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }
                    else
                    {
                        String msg=RN_Udata.getMsg();
                        Toast.makeText(LoginActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }


            }

            @Override
            public void onFailure(Call<RN_Udata> call, Throwable t) {

            }
        });
    }



    private  boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}