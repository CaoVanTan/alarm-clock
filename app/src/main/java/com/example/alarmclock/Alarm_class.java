package com.example.alarmclock;

public class Alarm_class {
    int Id;
    String Thoigian;
    Long Thoigian_long;
    boolean On_off;

    public Long getThoigian_long() {
        return Thoigian_long;
    }

    public Alarm_class(int id, String thoigian, Long thoigian_long, boolean on_off) {
        Id = id;
        Thoigian = thoigian;
        Thoigian_long = thoigian_long;
        On_off = on_off;
    }

    public Alarm_class(String thoigian, Long thoigian_long, boolean on_off) {
        Thoigian = thoigian;
        Thoigian_long = thoigian_long;
        On_off = on_off;
    }

    public void setThoigian_long(Long thoigian_long) {
        Thoigian_long = thoigian_long;
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
