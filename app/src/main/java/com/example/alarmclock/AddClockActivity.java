package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
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
    ListView listView;
    EditText inputSearch;
    SimpleDateFormat dateFormat;
    TimeZone timeZone;
    Date now;
    String[] listItems;
    ArrayList<TimeZoneData> timeZoneList;
    CustomTimeZonekArrayAdapter timeZoneArrayAdapter;
    ClockDbHelper db;

//    TimeZoneAdapter timeZoneAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);

        listView = (ListView) findViewById(R.id.lvTimeZone);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        timeZoneList = new ArrayList<>();
        listItems = TimeZone.getAvailableIDs();
        dateFormat = new SimpleDateFormat("ZZZZ, hh:mm");
        now = new Date();

        for (int i = 0; i < listItems.length; ++i) {
            timeZone = TimeZone.getTimeZone(listItems[i]);
            dateFormat.setTimeZone(timeZone);
            TimeZoneData timeZoneData = new TimeZoneData(i, getDisplayName(listItems[i]), dateFormat.format(now));
            timeZoneList.add(timeZoneData);
        }

        Log.i("Format", dateFormat.toString());

        timeZoneArrayAdapter = new CustomTimeZonekArrayAdapter(AddClockActivity.this, R.layout.activity_time_zone_show, timeZoneList);
        listView.setAdapter(timeZoneArrayAdapter);

        db = new ClockDbHelper(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TimeZoneData timeZoneData;
                timeZoneData = db.getTimeZoneById(position);
                Log.i("Time", String.valueOf(timeZoneData.getId()) + String.valueOf(position));
//                if(timeZoneData.getId() != position) {
//                    timeZoneData = new TimeZoneData(position, timeZoneList.get(position).getName(), timeZoneList.get(position).getTime());
//                    db.insertClock(timeZoneData);
//                }

//                Intent intent = new Intent(AddClockActivity.this, ClockFragment.class);
//                startActivity(intent);
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
//
//    }

//    public class TimeZoneAdapter extends ArrayAdapter<TimeZoneData> {
//        List<TimeZoneData> objects = null;
//
//        public TimeZoneAdapter(@NonNull Context context, int textViewResourceId, ArrayList<TimeZoneData> objects) {
//            super(context, textViewResourceId, objects);
//            this.objects = objects;
//        }
//
//        @Override
//        public int getCount() {
//            return (objects != null) ? objects.size() : 0;
//        }
//
//        @Nullable
//        @Override
//        public TimeZoneData getItem(int position) {
//            return (objects != null) ? objects.get(position) : null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            View view = convertView;
//
//            if (null == view) {
//                LayoutInflater layoutInflater = (LayoutInflater) AddClockActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = layoutInflater.inflate(R.layout.fragment_clock_show, null);
//            }
//
//            TimeZoneData data = objects.get(position);
//
//            if (null != data) {
//                txtLocation = (TextView) findViewById(R.id.txtLocation);
//                txtGMT = (TextView) findViewById(R.id.txtGMT);
//
//                txtLocation.setText(data.name);
//                txtGMT.setText(data.time);
//            }
//            return view;
//        }
//    }
}