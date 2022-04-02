package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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

        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
        txtTime.setText(getDisplayTime(arrayList.get(position).getTime()));

        return convertView;
    }

    private String getDisplayTime(String timeName) {
        String displayName = timeName;
        int sep = timeName.indexOf(", ");
        if (sep != -1) {
            displayName = timeName.substring(sep + 1);
        }

        return displayName;
    }
}
