package com.example.myreminders;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //delcare an intent
    Intent intent;

    DBHandler dbHandler;

    Reminders remindersAdapter;

    ListView remindersView;

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
                //launching the ViewList Activity and sending it the id of the shopping list
                intent = new Intent(MainActivity.this, ViewList.class);
                intent.putExtra("_id", id);
                startActivity(intent);
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
}
