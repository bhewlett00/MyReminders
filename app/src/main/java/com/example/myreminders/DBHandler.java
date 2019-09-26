package com.example.myreminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminders.db";

    private static final String TABLE_REMINDERS = "reminders";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_TITLE = "name";
    private static final String COLUMN_LIST_DATE = "date";
    private static final String COLUMN_LIST_TYPE = "type";

    public DBHandler(Context context, SQLiteDatabase.CursorFactory cursorFactory){
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_REMINDERS + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_TITLE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT, " +
                COLUMN_LIST_TYPE + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(sqLiteDatabase);
    }

    public void addReminder(String title, String date, String type){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_LIST_TITLE, title);
        values.put(COLUMN_LIST_DATE, date);
        values.put(COLUMN_LIST_TYPE, type);

        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    public Cursor getReminder(){
        //get writeable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        //select all data from shoppinglist table and return it as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_REMINDERS, null);
    }

    public String getReminderTitle(int id){

        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE " + COLUMN_LIST_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if(cursor.getString(cursor.getColumnIndex("title")) != null){
            dbString = cursor.getString(cursor.getColumnIndex("title"));
        }

        return dbString;
    }
}
