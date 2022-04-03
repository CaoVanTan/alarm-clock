package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AddClockActivity extends AppCompatActivity {
    ListView lvTimeZone;
    EditText inputSearch;
    SimpleDateFormat dateFormat;
    TimeZone timeZone;
    Date now;
    String[] listItems;
    ArrayList<TimeZoneData> timeZoneList;
    CustomTimeZonekArrayAdapter timeZoneArrayAdapter;
    ClockDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);

        lvTimeZone = (ListView) findViewById(R.id.lvTimeZone);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        timeZoneList = new ArrayList<>();
        listItems = TimeZone.getAvailableIDs();
        dateFormat = new SimpleDateFormat("ZZZZ");
        now = new Date();

        for (int i = 0; i < listItems.length; ++i) {
            timeZone = TimeZone.getTimeZone(listItems[i]);
            dateFormat.setTimeZone(timeZone);
            TimeZoneData timeZoneData = new TimeZoneData(i, getDisplayName(listItems[i]), dateFormat.format(now));
            timeZoneList.add(timeZoneData);
        }

        timeZoneArrayAdapter = new CustomTimeZonekArrayAdapter(AddClockActivity.this, R.layout.activity_time_zone_show, timeZoneList);
        lvTimeZone.setAdapter(timeZoneArrayAdapter);

        db = new ClockDbHelper(this);

        lvTimeZone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Kiểm tra tồn tại ID
                TimeZoneData timeZoneData;
                timeZoneData = new TimeZoneData(position, timeZoneList.get(position).getName(), timeZoneList.get(position).getTimeZone());
                db.insertClock(timeZoneData);

                Toast.makeText(AddClockActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(AddClockActivity.this, ClockFragment.class);
//                        startActivity(intent);
//                    }
//                }, 100);
                Intent intent = new Intent(AddClockActivity.this, ClockFragment.class);
                startActivity(intent);
//                finish();
            }
        });

//        inputSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(s.toString());
//            }
//        });
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

//    private void filter(String text) {
//        timeZoneList = new ArrayList<>();
//    }
}