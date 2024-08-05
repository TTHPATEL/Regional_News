package com.app.Regional_News;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.Regional_News.data.Event_cal_data;
import com.app.Regional_News.data.Event_cal_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private BaseApiService mApiService;
    private ArrayList<Event_cal_listdata> eventList;
    private HashMap<String, Event_cal_listdata> eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the toolbar title
        getSupportActionBar().setTitle("Event");

        calendarView = findViewById(R.id.calendarView);
        mApiService = UtilsApi.getAPIService();

        eventList = new ArrayList<>();
        eventMap = new HashMap<>();

        // Fetch events from the server
        fetchEventList();

        // Set a listener for date changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                if (eventMap.containsKey(selectedDate)) {
                    Event_cal_listdata event = eventMap.get(selectedDate);
                    showEventDetails(event);
                } else {
                    Toast.makeText(EventActivity.this, "No events on this date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchEventList() {
        mApiService.rnEventCalRequest("event_cal_list").enqueue(new Callback<Event_cal_data>() {
            @Override
            public void onResponse(Call<Event_cal_data> call, Response<Event_cal_data> response) {
                if (response.isSuccessful()) {
                    eventList = response.body().getEvent_cal_listdata();
                    for (Event_cal_listdata event : eventList) {
                        eventMap.put(event.getEvent_date(), event);
                    }
                } else {
                    Toast.makeText(EventActivity.this, "Failed to fetch events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Event_cal_data> call, Throwable t) {
                Toast.makeText(EventActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEventDetails(Event_cal_listdata event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(event.getEvent_name());
        builder.setMessage(event.getEvent_desc());
        builder.setPositiveButton("OK", null);
        builder.show();
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
