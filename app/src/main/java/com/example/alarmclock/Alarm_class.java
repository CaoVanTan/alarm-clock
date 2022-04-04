package com.example.alarmclock;

public class Alarm_class {
    int Id;
    String Thoigian;
    boolean On_off;

    public Alarm_class(int id, String thoigian, boolean on_off) {
        Id = id;
        Thoigian = thoigian;
        On_off = on_off;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getThoigian() {
        return Thoigian;
    }

    public void setThoigian(String thoigian) {
        Thoigian = thoigian;
    }

    public boolean isOn_off() {
        return On_off;
    }

    public void setOn_off(boolean on_off) {
        On_off = on_off;
    }
}
