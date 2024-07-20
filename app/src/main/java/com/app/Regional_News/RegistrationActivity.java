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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.Regional_News.data.Registrationdata;
import com.app.Regional_News.data.Udata;
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
    EditText et_name,et_email,et_pwd;
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
        if (sharedPrefManager.getUserLogin()){
            Intent i =new Intent(RegistrationActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        if (sharedPrefManager.getSPSudahLogin()){
            Intent i =new Intent(RegistrationActivity.this,GMainActivity.class);
            startActivity(i);
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

                String u_name= et_name.getText().toString();
                String u_email= et_email.getText().toString();
                String u_pwd= et_email.getText().toString();

                if(!u_name.isEmpty()&&!u_email.isEmpty()&&!u_pwd.isEmpty())
                {
                    JSONObject j1=new JSONObject();
                    try {
                        j1.put("u_name",u_name);
                        j1.put("u_email",u_email);
                        j1.put("u_pwd",u_pwd);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String data=j1.toString();
                    checklogin(data);

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this,"Please Enter email and Password",Toast.LENGTH_SHORT).show();
                }
//
            }
        });






        loginText.setOnClickListener(new View.OnClickListener() { // Set onClickListener for signupText
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        checkAndRequestPermissions();



    } private void checklogin(String data) {
        Log.e("Registration Data",data);
        mApiService.rnsRegistrationRequest("user_registration",data).enqueue(new Callback<Registrationdata>() {
            @Override
            public void onResponse(Call<Registrationdata> call, Response<Registrationdata> response) {
                if (response.isSuccessful()){

                    Registrationdata Registrationdata=response.body();
                    if (Registrationdata.getStatus().equals("1")){
                        String msg=Registrationdata.getMsg();
//    Toast.makeText(LoginActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String json = gson.toJson(Registrationdata);
                        sharedPrefManager.saveSPString(SharedPrefManager.F_ldata, json);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_F_LOGIN, true);
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }
                    else
                    {
                        String msg=Registrationdata.getMsg();
                        Toast.makeText(RegistrationActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }


            }

            @Override
            public void onFailure(Call<Registrationdata> call, Throwable t) {

            }
        });
    }

    private  boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
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