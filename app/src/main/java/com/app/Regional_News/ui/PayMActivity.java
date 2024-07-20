package com.app.Regional_News.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Regional_News.R;
import com.app.Regional_News.data.Newsdata;
import com.app.Regional_News.data.Newslistdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.NetworkUtils;
import com.app.Regional_News.extra.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayMActivity extends AppCompatActivity {
    BaseApiService mApiService;
    TextView news_headline,news_des_1,news_des_2;
    ImageView news_images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_mactivity);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String getNews_id = extras.getString("getNews_id");
        String news_imgurl = extras.getString("news_imgurl");

        mApiService = UtilsApi.getAPIService();
        news_headline = findViewById(R.id.news_headline);
        news_des_1 = findViewById(R.id.news_des_1);
        news_des_2 = findViewById(R.id.news_des_2);

        news_images = findViewById(R.id.news_images);


        // Load image using Picasso
        if (news_imgurl != null && !news_imgurl.isEmpty()) {
            Picasso.get().load(news_imgurl).placeholder(R.drawable.image_not_found).into(news_images);
        } else {
            news_images.setImageResource(R.drawable.image_not_found); // Default image if no URL provided
        }








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

        mApiService.dssNewsRequest("news_list_user",getNewsId)
                .enqueue(new Callback<Newsdata>() {
                    @Override
                    public void onResponse(Call<Newsdata> call, Response<Newsdata> response) {
                        if (response.isSuccessful()){
                            Log.e("msg",""+response.code());
                            Newsdata degdata=response.body();
                            Log.e("msg2",degdata.getMsg());
                            if (degdata.getStatus().equals("1")){
                                String error_message = degdata.getMsg();
                                Toast.makeText(PayMActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                displayData(degdata.getNews_list_user());
                            } else {
                                String error_message = degdata.getMsg();
                                Toast.makeText(PayMActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("msg1",""+response.code());
                            Log.e("msg5",""+call.request().url());
                        }
                    }

                    // Add this method to handle data display
                    private void displayData(ArrayList<Newslistdata> newsListUser) {
                        if (newsListUser != null && !newsListUser.isEmpty()) {
                            Newslistdata firstNewsItem = newsListUser.get(0);
                            news_headline.setText(firstNewsItem.getNews_headline());
                            news_des_1.setText(firstNewsItem.getNews_des_1());
                            news_des_2.setText(firstNewsItem.getNews_des_2());
                        }
                        else
                        {
                            Toast.makeText(PayMActivity.this,"Display mee Kuch to gadbad hai dayaa! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Newsdata> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            return true;
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





