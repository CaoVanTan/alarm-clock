package com.example.alarmclock;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;
public class RingAlarm  extends Service {

    MediaPlayer alarmPlayer;
    int id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String get_toggle = intent.getExtras().getString("toggle");
        alarmPlayer = MediaPlayer.create(this,R.raw.alarm);
        if(get_toggle.equals("on"))
        {
            id = 1;
        }
        else if(get_toggle.equals("off"))
        {
            id = 0 ;
        }
        if(id == 1 )
        {
            alarmPlayer.start();
            alarmPlayer.setLooping(true);
            id = 0;
            Log.i("Id", String.valueOf(id));
        }else if(id == 0)
        {
            alarmPlayer.stop();
//            alarmPlayer.reset();
            Log.i("Id", String.valueOf(id));
        }
        return START_NOT_STICKY;
    }
    //    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
//        Log.e("Alarm","Đã chạy 2");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//        String alarmTitle = "Báo thức";
//
//        Notification notification = new NotificationCompat.Builder(this, "Alarm")
//                .setContentTitle(alarmTitle)
//                .setContentText("Báo thức")
//                .setSmallIcon(R.drawable.ic_alarm)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .build();
//        alarmPlayer.start();
//        long[] pattern = { 0, 100, 1000 };
//        vibrator.vibrate(pattern, 0);
//        startForeground(1, notification);
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        alarmPlayer.stop();
//        vibrator.cancel();
//    }

}
