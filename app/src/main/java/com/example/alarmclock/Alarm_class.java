package com.example.alarmclock;

public class Alarm_class {
    int Id;
    String Thoigian;
    int Gio;
    int Phut;
    boolean On_off;

    public Alarm_class(String thoigian, int gio, int phut, boolean on_off) {
        Thoigian = thoigian;
        Gio = gio;
        Phut = phut;
        On_off = on_off;
    }

    public Alarm_class(int id, String thoigian, int gio, int phut, boolean on_off) {
        Id = id;
        Thoigian = thoigian;
        Gio = gio;
        Phut = phut;
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

    public int getGio() {
        return Gio;
    }

    public void setGio(int gio) {
        Gio = gio;
    }

    public int getPhut() {
        return Phut;
    }

    public void setPhut(int phut) {
        Phut = phut;
    }

    public boolean isOn_off() {
        return On_off;
    }

    public void setOn_off(boolean on_off) {
        On_off = on_off;
    }
}



