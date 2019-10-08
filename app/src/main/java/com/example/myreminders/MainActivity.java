package com.example.myreminders;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //delcare an intent
    Intent intent;

    DBHandler dbHandler;

    Reminders remindersAdapter;

    ListView remindersView;

    String entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null);

        remindersView = (ListView) findViewById(R.id.remindersView);

        remindersAdapter = new Reminders(this, dbHandler.getReminder(), 0);

        remindersView.setAdapter(remindersAdapter);

        remindersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String date = dbHandler.getReminderDate((int) id);
                try {
                    if(new SimpleDateFormat("yyyy-MM-dd").parse(date).compareTo(new Date())<0){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);

                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle("My Reminders");
                        builder.setContentText("Reminder is expired " + date);

                        intent = new Intent(MainActivity.this, MainActivity.class);

                        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        builder.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(2142, builder.build());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_AddReminder:
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    public void openAddReminder(View view) {
        // intializing an Intent for the AddReminder Activty and starting it
        intent = new Intent(this, AddReminder.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        entry = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
