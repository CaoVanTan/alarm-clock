package com.example.alarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeZoneAdapter extends RecyclerView.Adapter<TimeZoneAdapter.TimeZoneViewHolder> {
    private Context context;
    private ArrayList<TimeZoneData> timeZoneList;
    ClockDbHelper db;

    public TimeZoneAdapter(Context context, ArrayList<TimeZoneData> timeZoneList) {
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
        holder.txtLocation.setText(getDisplayName(timeZoneList.get(position).getName()));
        holder.txtGMT.setText(timeZoneList.get(position).getTimeZone());

        db = new ClockDbHelper(context);
        holder.layoutShowTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeZoneData timeZoneData;

                if(!db.getTimeZoneById(position)) {
                    timeZoneData = new TimeZoneData(timeZoneList.get(position).getId(), timeZoneList.get(position).getName(), timeZoneList.get(position).getTimeZone());
                    db.insertClock(timeZoneData);
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
        TextView txtId, txtLocation, txtGMT;
        LinearLayout layoutShowTimeZone;

        public TimeZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtGMT = itemView.findViewById(R.id.txtGMT);
            layoutShowTimeZone = itemView.findViewById(R.id.layoutShowTimeZone);
        }
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

    public void filterList(ArrayList<TimeZoneData> filteredList) {
        timeZoneList = filteredList;
        notifyDataSetChanged();
    }
}
