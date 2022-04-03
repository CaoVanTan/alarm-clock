package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomTimeZonekArrayAdapter extends ArrayAdapter<TimeZoneData> {
    Context context;
    ArrayList<TimeZoneData> arrayList;
    int layoutResource;

    public CustomTimeZonekArrayAdapter(Context context, int resource, ArrayList<TimeZoneData> objects) {
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

        TextView txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
        txtLocation.setText(arrayList.get(position).getName());

        TextView txtGMT = (TextView) convertView.findViewById(R.id.txtGMT);
        txtGMT.setText(getDisplayTimeZone(arrayList.get(position).getTime()));

        return convertView;
    }

    private String getDisplayTimeZone(String timeZoneName) {
        String displayName = timeZoneName;
        int sep = timeZoneName.indexOf(", ");
        if (sep != -1) {
            displayName = timeZoneName.substring(0, sep);
        }

        return displayName;
    }
}
