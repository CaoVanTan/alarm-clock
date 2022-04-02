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

public class ListAdapter_Alarm extends ArrayAdapter<Alarm_class> {


    public ListAdapter_Alarm(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public ListAdapter_Alarm(Context context, int resource, List<Alarm_class> iteams){
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
            SwitchCompat switchCompat = (SwitchCompat) v.findViewById(R.id.SwitchAlarm);
            TextView txt1 = (TextView) v.findViewById(R.id.textAlarm);
            TextView txt_Id = (TextView) v.findViewById(R.id.textId);
            TextView txt_Longtime = (TextView) v.findViewById(R.id.textLongTime);


            txt1.setText(a.Thoigian);
            txt_Id.setText(""+a.Id);
            txt_Longtime.setText(""+a.Thoigian_long);
            switchCompat.setChecked(a.On_off);
        }
        return v;
    }
}
