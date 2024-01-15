package com.example.cimly_mobile_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity {
    private DisplayAdapter displayAdapter;
    private ListView listView;
    private String currentDate;

    private ProgressBar progressBar;

    ImageView calendarIcon ;
    ImageView noDataIcon;
    MyDataBase db = new MyDataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        currentDate = getCurrentDate();
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.circularProgressBar);
         noDataIcon = findViewById(R.id.noDataIcon);
         calendarIcon = findViewById(R.id.calendar_icon);



        // Change status bar and navigation bar color and items color
        changeStatusBarColorAndTextColor(getResources().getColor(R.color.btn_blue));

        // Show the progress bar while loading data
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
        // Get data from the database
            List<Intern> interns = db.getAllByCurrentDate(currentDate);
            displayAdapter = new DisplayAdapter(getApplicationContext(), R.layout.customlistview, interns);
            listView.setAdapter(displayAdapter);



            if (!interns.isEmpty()){
            noDataIcon.setVisibility(View.GONE);

            }else {
            noDataIcon.setVisibility(View.VISIBLE);

            }
        // Hide the progress bar after loading data
            progressBar.setVisibility(View.GONE);
        },2000);


        setUpToolbar();
    }




    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.btn_blue));
        setSupportActionBar(toolbar); // Add this line

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.display_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.lightGrey));
            }
        }




        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the progress bar while loading data
                //progressBar.setVisibility(View.VISIBLE);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DisplayActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;


                               if (!selectedDate.equals(currentDate)) {
                                    // Clear the list view
                                    displayAdapter.clear();
                                    displayAdapter.notifyDataSetChanged();

                                    // Update the current date
                                    currentDate = selectedDate;
                                }

                                List<Intern> internsForSelectedDate = db.getAllByCurrentDate(selectedDate);






                                /* Use a handler to delay the loading for demonstration purposes
                                new Handler().postDelayed(() -> {
                                    // Get interns for the selected date
                                    List<Intern> internsForSelectedDate = db.getAllByCurrentDate(selectedDate);

                                    // Check if the list is not empty before clearing the adapter
                                    if (!internsForSelectedDate.isEmpty()) {
                                        // Clear and update the adapter with the new list of interns
                                        displayAdapter.clear();
                                        displayAdapter.addAll(internsForSelectedDate);
                                        displayAdapter.notifyDataSetChanged();
                                        noDataIcon.setVisibility(View.GONE);


                                        Toast.makeText(DisplayActivity.this, "Interns for " + selectedDate, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Optionally, you can show a message indicating no interns for the selected date
                                        Toast.makeText(DisplayActivity.this, "No interns for selected date", Toast.LENGTH_SHORT).show();

                                        noDataIcon.setVisibility(View.VISIBLE);
                                    }

                                    // Hide the progress bar after loading data
                                    progressBar.setVisibility(View.GONE);
                                }, 2000);*/
                            }
                        },
                        year,
                        month,
                        day
                );


                datePickerDialog.show();
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    //region Method responsible for changing bars color
    private void changeStatusBarColorAndTextColor(int color) {
        Window window = getWindow();

        // Set color for status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }

        // Set text color to dark
        View decor = window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    //endregion

}