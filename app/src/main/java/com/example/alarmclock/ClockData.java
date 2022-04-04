package com.example.alarmclock;

public class ClockData {
    private int id;
    private String name;
    private String timeZone;

    public ClockData(int id, String name, String timeZone) {
        this.id = id;
        this.name = name;
        this.timeZone = timeZone;
    }

    public ClockData() {
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
