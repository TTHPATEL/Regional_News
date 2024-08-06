package com.app.Regional_News;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import com.app.Regional_News.data.Event_cal_data;
import com.app.Regional_News.data.Event_cal_listdata;
import com.app.Regional_News.extra.BaseApiService;
import com.app.Regional_News.extra.UtilsApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private BaseApiService mApiService;
    private Map<String, Event_cal_listdata> eventDetailsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        calendarView = findViewById(R.id.calendarView);
        mApiService = UtilsApi.getAPIService();
        eventDetailsMap = new HashMap<>();

        fetchEventDates();
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
                        // Set the calendar date to the event date (example format: 2024-08-09)
                        String[] dateParts = eventData.getEvent_date().split("-");
                        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
                        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1); // Months are 0-based
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));

                        EventDay eventDay = new EventDay(calendar, R.drawable.event_marker);
                        events.add(eventDay);

                        // Store event details in the map with the date as key
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
            String clickedDate = clickedDay.get(Calendar.YEAR) + "-" +
                    String.format("%02d", (clickedDay.get(Calendar.MONTH) + 1)) + "-" +
                    String.format("%02d", clickedDay.get(Calendar.DAY_OF_MONTH));

            Event_cal_listdata eventData = eventDetailsMap.get(clickedDate);

            if (eventData != null) {
                String eventDetails = "Event Date: " + eventData.getEvent_date() + "\n\n" + // Double newline after event name
                        "Event Name: " + eventData.getEvent_name() + "\n\n" + // Double newline after event name
                        "Description: "+ "\n" + eventData.getEvent_desc() + "\n\n"; // Double newline after description

                new AlertDialog.Builder(EventActivity.this)
                        .setTitle("Event Details")
                        .setMessage(eventDetails)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else {
                Toast.makeText(EventActivity.this, "No event on this date", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
