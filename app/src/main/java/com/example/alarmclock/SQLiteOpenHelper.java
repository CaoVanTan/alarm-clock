package com.example.alarmclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DATABASE_NAME = "clock.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CLOCK = "clock";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_Name ="name";
    private static final String COLUMN_TimeZone = "timeZone";

    public SQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_CLOCK + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_Name + " VARCHAR (255) NOT NULL, " +
                    COLUMN_TimeZone + " VARCHAR (255) NOT NULL" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOCK);
        onCreate(db);
    }
}
