package com.example.alarmclock;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ClockAdapter extends ArrayAdapter<TimeZoneData> {
    Context context;
    ArrayList<TimeZoneData> arrayList;
    int layoutResource;
    ClockDbHelper db;

    public ClockAdapter(Context context, int resource, ArrayList<TimeZoneData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layoutResource, null);

        TextView txtLocation = (TextView) convertView.findViewById(R.id.txtLocation1);
        txtLocation.setText(getLocation(getLocation(arrayList.get(position).getName())));

        TextView txtCountry = (TextView) convertView.findViewById(R.id.txtCountry1);
        txtCountry.setText(getLocation(getCountryName(arrayList.get(position).getName())));

        db = new ClockDbHelper(context);
        TextClock textClock = (TextClock) convertView.findViewById(R.id.txtTime);
        textClock.setTimeZone(db.getTimeZone().get(position).getName());

        TextView txtTimeZone = (TextView) convertView.findViewById(R.id.txtTimeZone);
        txtTimeZone.setText(arrayList.get(position).getTimeZone());

        return convertView;
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

    private String getLocation(String timeZoneName) {
        String displayName = timeZoneName;
        StringBuilder str = new StringBuilder(timeZoneName);
        String timeZoneNameRev = str.reverse().toString();
        int sepRev = timeZoneNameRev.indexOf("/");
        if (sepRev != -1) {
            displayName = timeZoneNameRev.substring(0, sepRev);
            StringBuilder str1 = new StringBuilder(displayName);
            displayName = str1.reverse().toString();
            displayName = displayName.replace("_", " ");
        } else {
            displayName = timeZoneName;
        }

        return displayName;
    }

    private String getCountryName(String timeZoneName) {
        String displayName = timeZoneName;
        int count = 0;
        int sep = timeZoneName.indexOf("/");

        for (int i = 0; i < displayName.length(); i++) {
            if (displayName.charAt(i) == '/') {
                count++;
            }
        }

        if (count == 0) {
            displayName = "";
        }
        else if (count == 1) {
            displayName = timeZoneName.substring(0, sep);
            displayName = displayName.replace("_", " ");
        }
        else {
            StringBuilder str = new StringBuilder(timeZoneName);
            String timeZoneNameRev = str.reverse().toString();
            int sepRev = timeZoneNameRev.indexOf("/");
            if (sepRev != -1) {
                displayName = timeZoneNameRev.substring(sepRev + 1);
                StringBuilder str1 = new StringBuilder(displayName);
                displayName = str1.reverse().toString();
                displayName = displayName.replace("/", ", ");
            }
        }

        return displayName;
    }
}
