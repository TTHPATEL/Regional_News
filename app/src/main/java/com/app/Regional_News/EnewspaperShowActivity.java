package com.app.Regional_News;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.Regional_News.data.Enewspaper_PDF_data;
import com.app.Regional_News.data.Enewspaper_PDF_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.UtilsApi;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnewspaperShowActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    private String getEnewspaper_title, getEnewspaper_id;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enewspaper_show);

        // Retrieve data from Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getEnewspaper_title = extras.getString("getEnewspaper_title");
            getEnewspaper_id = extras.getString("getEnewspaper_id");
        }
        mApiService = UtilsApi.getAPIService();
        // Initialize views
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);
        lyt_not_found = findViewById(R.id.lyt_not_found);

        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getEnewspaper_title + " eNewspaper ");
        }

        // Fetch and display the PDF data using the specific API method
        if (getEnewspaper_id != null) {
            fetchPdfData(getEnewspaper_id);
        } else {
            Toast.makeText(this, "No eNewspaper ID provided", Toast.LENGTH_SHORT).show();
            lyt_not_found.setVisibility(View.VISIBLE);
        }
    }

    private void fetchPdfData(String enewspaperId) {
        showProgress(true);

        // Make the API call with the specified parameters
        mApiService.rnEnewspaperPDFRequest("Enewspaper_fetch_PDF", enewspaperId)
                .enqueue(new Callback<Enewspaper_PDF_data>() {
                    @Override
                    public void onResponse(Call<Enewspaper_PDF_data> call, Response<Enewspaper_PDF_data> response) {
                        showProgress(false);
                        if (response.isSuccessful()) {
                            Enewspaper_PDF_data pdfData = response.body();
                            if (pdfData != null && pdfData.getStatus().equals("1")) {
                                // Get the list of PDFs
                                ArrayList<Enewspaper_PDF_listdata> pdfList = pdfData.getSelected_pdf_listdata();
                                if (pdfList != null && !pdfList.isEmpty()) {
                                    // Assuming you want to display the first PDF in the list
                                    Enewspaper_PDF_listdata selectedPdf = pdfList.get(0);
                                    String base64PdfData = selectedPdf.getEnews_pdf_data();
                                    displayPDF(base64PdfData);
                                } else {
                                    Toast.makeText(EnewspaperShowActivity.this, "PDF data not found for the provided ID", Toast.LENGTH_SHORT).show();
                                    lyt_not_found.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(EnewspaperShowActivity.this, "Error: " + pdfData.getMsg(), Toast.LENGTH_SHORT).show();
                                lyt_not_found.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.e("EnewspaperShowActivity", "Response code: " + response.code());
                            Toast.makeText(EnewspaperShowActivity.this, "Failed to load PDF data", Toast.LENGTH_SHORT).show();
                            lyt_not_found.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Enewspaper_PDF_data> call, Throwable t) {
                        Log.e("EnewspaperShowActivity", "API call failed: " + t.getMessage());
                        Toast.makeText(EnewspaperShowActivity.this, "Failed to load PDF data", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                        lyt_not_found.setVisibility(View.VISIBLE);
                    }
                });
    }


    private void displayPDF(String base64PdfData) {
        try {
            byte[] pdfAsBytes = Base64.decode(base64PdfData, Base64.DEFAULT);
            pdfView.fromBytes(pdfAsBytes)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .load();
            lyt_not_found.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e("EnewspaperShowActivity", "Base64 decoding error: " + e.getMessage());
            Toast.makeText(EnewspaperShowActivity.this, "Error decoding PDF", Toast.LENGTH_SHORT).show();
            lyt_not_found.setVisibility(View.VISIBLE);
        } finally {
            showProgress(false);
        }
    }

    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            pdfView.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
