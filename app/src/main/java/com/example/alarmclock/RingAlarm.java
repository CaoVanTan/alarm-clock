package com.example.alarmclock;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.Provider;

public class RingAlarm  extends Service {
    MediaPlayer alarmPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("REciver","Đã chạy 2");
        alarmPlayer = MediaPlayer.create(this,R.raw.alarm_clock);
        alarmPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
