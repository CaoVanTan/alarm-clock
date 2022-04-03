package com.example.alarmclock;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
public class AlarmNoti extends Application{
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannnel();
    }
    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm";
            String des = "Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel serviceChannel = new NotificationChannel( "Alarm", name,importance);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}



