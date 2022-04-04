package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AddClockActivity extends AppCompatActivity {
    RecyclerView rvTimeZone;
    EditText inputSearch;
    SimpleDateFormat dateFormat;
    TimeZone timeZone;
    Date now;
    String[] listItems;
    ArrayList<ClockData> timeZoneList;
    TimeZoneAdapter timeZoneAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);

        createTimeZoneList();
        buildRecyclerView();

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void createTimeZoneList() {
        timeZoneList = new ArrayList<>();
        listItems = TimeZone.getAvailableIDs();
        dateFormat = new SimpleDateFormat("ZZZZ");
        now = new Date();

        for (int i = 0; i < listItems.length; ++i) {
            timeZone = TimeZone.getTimeZone(listItems[i]);
            dateFormat.setTimeZone(timeZone);
            ClockData clockData = new ClockData(i, listItems[i], dateFormat.format(now));
            timeZoneList.add(clockData);
        }
    }

    private void buildRecyclerView() {
        rvTimeZone = (RecyclerView) findViewById(R.id.rvTimeZone);
        rvTimeZone.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTimeZone.setLayoutManager(layoutManager);
        timeZoneAdapter = new TimeZoneAdapter(AddClockActivity.this, timeZoneList);
        rvTimeZone.setAdapter(timeZoneAdapter);
    }

    private void filter(String text) {
        ArrayList<ClockData> filteredList = new ArrayList<>();

        for (ClockData timezone : timeZoneList) {
            if (getDisplayName(timezone.getName()).toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(timezone);
            }
        }

        timeZoneAdapter.filterList(filteredList);
    }

    private String getDisplayName(String timeZoneName) {
        String displayName = timeZoneName;
        int sep = timeZoneName.indexOf("/");
        if (sep != -1) {
            displayName = timeZoneName.substring(0, sep) + ", " + timeZoneName.substring(sep + 1);
            displayName = displayName.replace("_", " ");
        }

        return displayName;
    }
}