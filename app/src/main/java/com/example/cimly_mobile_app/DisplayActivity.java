package com.example.cimly_mobile_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Time;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Change status bar and navigation bar color and items color
        changeStatusBarColorAndTextColor(getResources().getColor(R.color.btn_blue));

        // Inside DisplayActivity
        ArrayList<Intern> internList = new ArrayList<>();
        internList.add(new Intern("Mohamed Essebaiy", new Time(9, 5, 0), new Time(16, 5, 0), 1));

        listView = findViewById(R.id.listView);
        DisplayAdapter displayAdapter = new DisplayAdapter(getApplicationContext(), internList);
        listView.setAdapter(displayAdapter);

        // Set up item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked Intern
                Intern clickedIntern = (Intern) parent.getItemAtPosition(position);

                // Show a dialog with information about the clicked intern
                showInternDialog(clickedIntern);
            }
        });

        setUpToolbar();

    }

    private void showInternDialog(Intern intern) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View customDialogView = inflater.inflate(R.layout.dialog_layout, null);


        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
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

    //region Method responsible for changing bars color
    private void changeStatusBarColorAndTextColor(int color) {
        Window window = getWindow();

        // Set color for status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);

        // Set text color to dark
        View decor = window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    //endregion

}