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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Intent intent;

    //declare EditTexts
    EditText titleEditText;
    EditText dateEditText;

    Bundle bundle;
    long id;

    Spinner typeSpinner;
    String entry;

    //declare a calendar
    Calendar calendar;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initial EditTexts
        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);

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





        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        dbHandler = new DBHandler(this, null);
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

        if(title.trim().equals("") || date.trim().equals("") || entry.trim().equals("")){
            //if any of the Strings are empty, display Please enter ... Toast
            Toast.makeText(this, "Please enter a title, date, and type!", Toast.LENGTH_LONG).show();
        }else {
            //add reminder to database
            dbHandler.addReminder(title, date, entry);
            //if none of the Strings are empty, display Reminder Added Toast
            Toast.makeText(this, "Reminder Added!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        entry = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
