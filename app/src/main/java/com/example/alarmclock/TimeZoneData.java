package com.example.alarmclock;

public class TimeZoneData {
    public int id;
    public String name;
    public String time;

    public TimeZoneData(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public TimeZoneData() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
