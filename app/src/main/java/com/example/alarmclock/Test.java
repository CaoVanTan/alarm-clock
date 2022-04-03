package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextClock;

import java.util.TimeZone;

public class Test extends AppCompatActivity {
    TextClock textClock;
    TimeZone tz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tz = tz.getTimeZone("Africa/Abidjan");
        textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setTimeZone(tz.toString());

    }
}