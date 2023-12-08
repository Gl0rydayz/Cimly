package com.example.cimly_mobile_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Inside DisplayActivity
        ArrayList<Intern> internList = new ArrayList<>();
        internList.add(new Intern("Mohamed Essebaiy", new Time(9, 5, 0), new Time(16, 5, 0), 1));

        listView = findViewById(R.id.listView);
        DisplayAdapter displayAdapter = new DisplayAdapter(getApplicationContext(), internList);
        listView.setAdapter(displayAdapter);

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
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.lightGrey));
        }
    }

}