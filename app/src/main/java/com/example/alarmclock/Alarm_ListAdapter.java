package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import java.util.List;

public class Alarm_ListAdapter extends ArrayAdapter<Alarm_class> {


    public Alarm_ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public Alarm_ListAdapter(Context context, int resource, List<Alarm_class> iteams){
        super(context, resource, iteams);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_view_alarrm, null);
        }

        Alarm_class a = getItem(position);
        if (a != null){
            TextView txt1 = (TextView) v.findViewById(R.id.textAlarm);
            txt1.setText(a.Thoigian);
            SwitchCompat switchCompat = (SwitchCompat) v.findViewById(R.id.SwitchAlarm);
            switchCompat.setChecked(a.On_off);
        }
        return v;
    }
}
