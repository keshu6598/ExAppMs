package com.example.exappms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String room_name, String rasp_id) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, room_name);
        contentValue.put(DatabaseHelper.DESC, rasp_id);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String room_number_id,String room_number, String resp_details) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, room_number);
        contentValues.put(DatabaseHelper.DESC, resp_details);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.SUBJECT + " =  \"" + room_number_id + "\"", null);
        return i;
    }

    public void delete(String room_number) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.SUBJECT + "= \"" + room_number + "\"", null);
    }

}
