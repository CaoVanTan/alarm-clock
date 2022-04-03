package com.example.alarmclock;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent( context, RingAlarm.class);
        String extra = intent.getStringExtra("toggle");
        String txt_extra = intent.getStringExtra("text");
        alarmIntent.putExtra("toggle","extra");
        Log.e("REciver","Đã chạy");
        context.startService(alarmIntent);
        Intent i = new Intent(context,Destination.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
        String alarmTitle = "Báo thức";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Alarm")
                .setContentTitle(alarmTitle)
                .setContentText("Báo thức của bạn vào "+txt_extra+"!")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }

}
