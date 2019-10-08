package com.example.myreminders;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reminders extends CursorAdapter {
    public Reminders(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_reminders, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        ((TextView) view.findViewById(R.id.titleTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));

        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));

        String date = cursor.getString(cursor.getColumnIndex("date"));

            ((TextView) view.findViewById(R.id.expiredTextView)).
                    setText("Expired ? " + isExpired(date));

    }

    public boolean isExpired(String date)  {
        try{
            if(new SimpleDateFormat("yyyy-MM-dd").parse(date).compareTo(new Date())<0)
                return false;
            else
                return true;
        }catch (Exception e) {
            return false;
        }

    }
}
