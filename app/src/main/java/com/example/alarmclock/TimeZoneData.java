package com.example.alarmclock;

public class TimeZoneData {
    public int id;
    public String name;
    public String timeZone;

    public TimeZoneData(int id, String name, String timeZone) {
        this.id = id;
        this.name = name;
        this.timeZone = timeZone;
    }

    public TimeZoneData() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
