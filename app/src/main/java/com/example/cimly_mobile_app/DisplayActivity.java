package com.example.cimly_mobile_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private DisplayAdapter displayAdapter;
    private ListView listView;

    MyDataBase db = new MyDataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Change status bar and navigation bar color and items color
        changeStatusBarColorAndTextColor(getResources().getColor(R.color.btn_blue));

        // Initialize database
        MyDataBase db = new MyDataBase(this);

        // Get data from the database
        List<Intern> interns = db.getAll();

        listView = findViewById(R.id.listView);

        // Set up the adapter
        displayAdapter = new DisplayAdapter(getApplicationContext(), R.layout.customlistview, interns);

        // Set the adapter to the ListView
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

        Button btn_yes = customDialogView.findViewById(R.id.btn_dialogYes);
        Button btn_no = customDialogView.findViewById(R.id.btn_dialogNo);


        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btn_yes.setOnClickListener(v -> {
            // Delete the selected intern from the database
            db.deleteInternByName(intern.getName());

            // Notify the adapter that the data has changed
            displayAdapter.notifyDataSetChanged();

            Toast.makeText(this, "Intern removed", Toast.LENGTH_SHORT).show();

            // Dismiss the dialog
            dialog.dismiss();

            recreate();
        });
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