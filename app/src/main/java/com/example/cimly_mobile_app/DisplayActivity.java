package com.example.cimly_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    }
}