package com.app.Regional_News;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Search_newslistActivity extends AppCompatActivity {



    private TextView keywordDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_newslist);


        keywordDetail = findViewById(R.id.keywordDetail);

        Intent intent = getIntent();
        if (intent != null) {
            String keyword = intent.getStringExtra("keyword");
            keywordDetail.setText(keyword);
        }

    }
}