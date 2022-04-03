package com.example.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlarmDbHelper extends SQLiteOpenHelper{
    private static final String TAG = "AlarmDbHelper";
    private static final String DATABASE_NAME = "Alarm.db";
    private static final String TABLE_ALARM = "alarm";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TIMELONG = "timelong";


    public AlarmDbHelper(@Nullable Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "ClockDbHelper.onCreate ... ");
        String queryCreateTable = "CREATE TABLE " + TABLE_ALARM + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_STATUS + " BOOlEAN NOT NULL, " +
                COLUMN_TIME + " VARCHAR (255) NOT NULL, " +
                COLUMN_TIMELONG + " LONG NOT NULL" + ")";
        db.execSQL(queryCreateTable);
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, "AlarmDbHelper.onUpgrade ... ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);
    }

    public void insertAlarm(Alarm_class Alarm) {
        Log.i(TAG, "AlarmDbHelper.insertAlarm ... " + Alarm.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, Alarm.isOn_off());
        values.put(COLUMN_TIME, Alarm.getThoigian());
        values.put(COLUMN_TIMELONG, Alarm.getThoigian_long());
        db.insert(TABLE_ALARM, null, values);
        db.close();
    }
    public void updateAlarm_isChecked(int id, boolean status){
        Log.i(TAG, "ClockDbHelper.insertAlarm ... " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        db.update(TABLE_ALARM, values,COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteAlarm(int id) {
        Log.i(TAG, "AlarmDbHelper.deleteAlarm ... " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    public ArrayList<Alarm_class> getAlarm() {
        Log.i(TAG, "AlarmDbHelper.getAlarm ... ");

        ArrayList<Alarm_class> alarm_ArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ALARM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                boolean On_off = cursor.getInt(1) > 0;
                Alarm_class Alarm = new Alarm_class(cursor.getInt(0), cursor.getString(2),cursor.getLong(3), On_off);
                alarm_ArrayList.add(Alarm);
            } while (cursor.moveToNext());
        }
        db.close();
        return alarm_ArrayList;
    }

    public int getID(String time, long time_long){
        Log.i(TAG, "ClockDbHelper.getIDbyHour");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALARM, new String[] { COLUMN_ID }, COLUMN_TIME + "= ?" + " AND " +COLUMN_TIMELONG + "= ?",
                new String[] { time, String.valueOf(time_long) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Integer id = cursor.getInt(0);
        db.close();
        return id;
    }
}
