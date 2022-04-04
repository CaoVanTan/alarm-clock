package com.example.alarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeZoneAdapter extends RecyclerView.Adapter<TimeZoneAdapter.TimeZoneViewHolder> {
    private Context context;
    private ArrayList<ClockData> timeZoneList;
    ClockDbHelper db;

    public TimeZoneAdapter(Context context, ArrayList<ClockData> timeZoneList) {
        this.timeZoneList = timeZoneList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeZoneAdapter.TimeZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_time_zone_show, parent, false);
        TimeZoneViewHolder evh = new TimeZoneViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeZoneAdapter.TimeZoneViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtId.setText(String.valueOf(timeZoneList.get(position).getId()));
        holder.txtLocation.setText(getLocation(timeZoneList.get(position).getName()));
        holder.txtGMT.setText(timeZoneList.get(position).getTimeZone());
        holder.txtCountry.setText(getCountryName(timeZoneList.get(position).getName()));

        db = new ClockDbHelper(context);
        holder.layoutShowTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClockData clockData;

                if (!db.getTimeZoneById(position)) {
                    clockData = new ClockData(timeZoneList.get(position).getId(), timeZoneList.get(position).getName(), timeZoneList.get(position).getTimeZone());
                    db.insertClock(clockData);
                    Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, ClockFragment.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Khu vực đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeZoneList.size();
    }

    public class TimeZoneViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtLocation, txtGMT, txtCountry;
        LinearLayout layoutShowTimeZone;

        public TimeZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtGMT = itemView.findViewById(R.id.txtGMT);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            layoutShowTimeZone = itemView.findViewById(R.id.layoutShowTimeZone);
        }
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

    public void filterList(ArrayList<ClockData> filteredList) {
        timeZoneList = filteredList;
        notifyDataSetChanged();
    }
}
