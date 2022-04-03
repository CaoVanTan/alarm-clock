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
import java.util.TimeZone;

public class CustomClockArrayAdapter extends ArrayAdapter<TimeZoneData> {
    Context context;
    ArrayList<TimeZoneData> arrayList;
    int layoutResource;
    ClockDbHelper db;

    public CustomClockArrayAdapter(Context context, int resource, ArrayList<TimeZoneData> objects) {
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
        txtLocation.setText(getDisplayName(arrayList.get(position).getName()));

        db = new ClockDbHelper(context);
        TextClock textClock = (TextClock) convertView.findViewById(R.id.txtTime);
        textClock.setTimeZone(db.getTimeZone().get(position).getName());

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
}
