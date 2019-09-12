package com.example.myreminders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {
    Intent intent;

    //declare EditTexts
    EditText titleEditText;
    EditText dateEditText;
    EditText typeEditText;

    //declare a calendar
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initial EditTexts
        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        typeEditText = findViewById(R.id.typeEditText);

        //initialize the Calendar
        calendar = Calendar.getInstance();

        //initialize a DatePickerDialog and register and OnDateSetListener to it
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            //this method gets called when a date is set in the DatePickerDialog
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // set the Calendar year, month, and day to year, month, and day selected
                // in DatePickerDialog
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //call method that updates dat EditText with date set in DatePickerDialog
                updateDueDate();
            }
        };

        //registered OnClickListener on date EditText
        dateEditText.setOnClickListener(new View.OnClickListener() {
            //this method gets called when the date EditText is clicked
            @Override
            public void onClick(View view) {
                //display DatePickerDialog with current date selected
                new DatePickerDialog(AddReminder.this, date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void updateDueDate() {

        //create a SimpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        //apply SimpleDateFormat to date in Calendar and it in the date EditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
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

    public void addReminder(MenuItem menuItem){
    // get data input in EditTExts and store it in Strings
        String title = titleEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String type = typeEditText.getText().toString();

        if(title.trim().equals("") || date.trim().equals("") || type.trim().equals("")){
            //if any of the Strings are empty, display Please enter ... Toast
            Toast.makeText(this, "Please enter a name, store, and date!", Toast.LENGTH_LONG).show();
        } else {
            //if none of the Strings are empty, display Shopping List Added Toast
            Toast.makeText(this, "Shopping List Added!", Toast.LENGTH_LONG).show();
        }
    }

}
