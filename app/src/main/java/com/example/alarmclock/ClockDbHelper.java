package com.example.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ClockDbHelper extends android.database.sqlite.SQLiteOpenHelper {
    Context context;
    private static final String TAG = "ClockDbHelper";
    private static final String DATABASE_NAME = "Clock.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CLOCK = "clock";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIMEZONE = "timeZone";

    public ClockDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "ClockDbHelper.onCreate ... ");
        String queryCreateTable = "CREATE TABLE " + TABLE_CLOCK + "(" +
                                   COLUMN_ID + " INTEGER PRIMARY KEY, " +
                                   COLUMN_NAME + " VARCHAR (255) NOT NULL, " +
                                   COLUMN_TIMEZONE + " VARCHAR (255) NOT NULL" + ")";
        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, "ClockDbHelper.onUpgrade ... ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOCK);
        onCreate(db);
    }

    public boolean getTimeZoneById(int id) {
        Log.i(TAG, "ClockDbHelper.getTimeZoneById ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLOCK, new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_TIMEZONE }, COLUMN_ID + "= ?",
                                new String[] { String.valueOf(id) }, null, null, null, null);

        boolean check;
        if (cursor.getCount() > 0) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }

    public ArrayList<ClockData> getTimeZone() {
        Log.i(TAG, "ClockDbHelper.getTimeZone ... ");

        ArrayList<ClockData> timeZoneList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLOCK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ClockData timeZone = new ClockData(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                timeZoneList.add(timeZone);
            } while (cursor.moveToNext());
        }

        return timeZoneList;
    }

    public void insertClock(ClockData timeZone) {
        Log.i(TAG, "ClockDbHelper.insertClock ... " + timeZone.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, timeZone.getId());
        values.put(COLUMN_NAME, timeZone.getName());
        values.put(COLUMN_TIMEZONE, timeZone.getTimeZone());
        db.insert(TABLE_CLOCK, null, values);

        db.close();
    }

    public void deleteTimeZone(int id) {
        Log.i(TAG, "ClockDbHelper.deleteTimeZone ... " + id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLOCK, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });

        db.close();
    }
}
