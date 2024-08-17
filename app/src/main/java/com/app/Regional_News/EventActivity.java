package com.app.Regional_News;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.Regional_News.adapter.EventlistAdapter;
import com.app.Regional_News.extra.ItemOffsetDecoration;
import com.app.Regional_News.extra.NetworkUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import com.app.Regional_News.data.Event_cal_data;
import com.app.Regional_News.data.Event_cal_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private BaseApiService mApiService;
    private Map<String, Event_cal_listdata> eventDetailsMap;

    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    EventlistAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // USE FOR DISPLAY SYSTEM INBUILT BACK NAVIGATION ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the toolbar title
        getSupportActionBar().setTitle(R.string.eventtitle);



        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        calendarView = findViewById(R.id.calendarView);
        mApiService = UtilsApi.getAPIService();
        eventDetailsMap = new HashMap<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                fetchEventDates();
            }
        });

        if (NetworkUtils.isConnected(this)) {
            showProgress(true);
            getdata();
            fetchEventDates();
        } else {
            Toast.makeText(this, getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }
    }

    private void getdata() {
        mApiService.rnEventCalRequest("event_cal_list").enqueue(new Callback<Event_cal_data>() {
            @Override
            public void onResponse(Call<Event_cal_data> call, Response<Event_cal_data> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    showProgress(false);
                    Event_cal_data eventData = response.body();
                    if (eventData.getStatus().equals("1")) {
                        Toast.makeText(EventActivity.this, eventData.getMsg(), Toast.LENGTH_SHORT).show();
                        displayData(eventData.getEvent_cal_listdata());
                    } else {
                        Toast.makeText(EventActivity.this, eventData.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<Event_cal_data> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showProgress(false);
                Toast.makeText(EventActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void displayData(ArrayList<Event_cal_listdata> eventList) {
        adapter = new EventlistAdapter(this, eventList);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }
    }

    private void fetchEventDates() {
        Call<Event_cal_data> call = mApiService.rnEventCalRequest("event_cal_list");
        call.enqueue(new Callback<Event_cal_data>() {
            @Override
            public void onResponse(Call<Event_cal_data> call, Response<Event_cal_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Event_cal_listdata> eventCalList = response.body().getEvent_cal_listdata();
                    List<EventDay> events = new ArrayList<>();

                    for (Event_cal_listdata eventData : eventCalList) {
                        Calendar calendar = Calendar.getInstance();
                        String[] dateParts = eventData.getEvent_date().split("-");
                        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
                        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1); // Months are 0-based
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));

                        EventDay eventDay = new EventDay(calendar, R.drawable.event_marker);
                        events.add(eventDay);

                        eventDetailsMap.put(eventData.getEvent_date(), eventData);
                    }

                    calendarView.setEvents(events);
                    setEventClickListeners(events);
                }
            }

            @Override
            public void onFailure(Call<Event_cal_data> call, Throwable t) {
                Toast.makeText(EventActivity.this, "Failed to fetch events", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEventClickListeners(List<EventDay> events) {
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();
            String clickedDate = String.format("%04d-%02d-%02d",
                    clickedDay.get(Calendar.YEAR),
                    clickedDay.get(Calendar.MONTH) + 1,
                    clickedDay.get(Calendar.DAY_OF_MONTH));

            Event_cal_listdata eventData = eventDetailsMap.get(clickedDate);

            if (eventData != null) {
                String eventDetails = getString(R.string.eventdate) + formatDate(eventData.getEvent_date()) + "\n\n" +
                        getString(R.string.eventname) + eventData.getEvent_name() + "\n\n" +
                        getString(R.string.eventdesc) + "\n" + eventData.getEvent_desc() + "\n\n";

                new AlertDialog.Builder(EventActivity.this)
                        .setTitle(getString(R.string.eventdialogboxtitle))
                        .setMessage(eventDetails)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else {
                Toast.makeText(EventActivity.this, "No event on this date", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static String formatDate(String dateStr) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            return targetFormat.format(originalFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
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
