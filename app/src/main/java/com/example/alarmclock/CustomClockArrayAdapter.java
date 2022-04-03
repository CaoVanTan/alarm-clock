package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.TimeZone;

public class CustomClockArrayAdapter extends ArrayAdapter<TimeZoneData> {
    Context context;
    ArrayList<TimeZoneData> arrayList;
    int layoutResource;

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
        txtLocation.setText(arrayList.get(position).getName());

        TextClock textClock = (TextClock) convertView.findViewById(R.id.txtTime);
        TimeZone tz = TimeZone.getTimeZone(arrayList.get(position).getName());
        textClock.setTimeZone(tz.toString());

        return convertView;
    }
}
