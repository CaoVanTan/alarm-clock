package com.example.alarmclock;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent( context, RingAlarm.class);
        Log.e("REciver","Đã chạy");
        context.startService(alarmIntent);
    }

}
