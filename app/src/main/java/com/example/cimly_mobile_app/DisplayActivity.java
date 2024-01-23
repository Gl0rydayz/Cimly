package com.example.cimly_mobile_app;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private DisplayAdapter displayAdapter;
    private ListView listView;
    private ProgressBar progressBar;

    ImageView noDataIcon,searchIcon,closeIcon;
    TextView personCount;
    MyDataBase db = new MyDataBase(this);
    private boolean isSearchVisible = false;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.circularProgressBar);
        noDataIcon = findViewById(R.id.noDataIcon);
        searchIcon = findViewById(R.id.search_icon);
        closeIcon = findViewById(R.id.close_icon);
        personCount = findViewById(R.id.count_person);

        updatePersonCount();

        // Change status bar and navigation bar color and items color
        changeStatusBarColorAndTextColor(getResources().getColor(R.color.btn_blue));

        // Get reference to the SearchView
        SearchView searchView = findViewById(R.id.search_view);

        // Set an OnQueryTextListener to handle search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Intern> searchResults = db.search(query);
                displayAdapter = new DisplayAdapter(getApplicationContext(), R.layout.customlistview, searchResults);
                listView.setAdapter(displayAdapter);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the data based on the new text
                displayAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Set an OnClickListener on the search icon to toggle the visibility of the SearchView
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the search visibility
                isSearchVisible = !isSearchVisible;

                // Update UI based on search visibility
                updateUI();
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.GONE);
                closeIcon.setVisibility(View.GONE);
                searchIcon.setVisibility(View.VISIBLE);
            }
        });

        // Show the progress bar while loading data
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            // Get data from the database
            List<Intern> interns = db.getAll();
            displayAdapter = new DisplayAdapter(getApplicationContext(), R.layout.customlistview, interns);
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

    private void updatePersonCount() {
        // Update the count
        count = db.getAll().size();

        // Update the person count in UI
        personCount.setText(String.valueOf(count));
    }
    private void updateUI() {
        if (isSearchVisible) {
            // Show the search view
            showSearchView();
        } else {
            // Hide the search view
            hideSearchView();
        }
    }

    private void showSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchIcon.setVisibility(View.GONE);
        closeIcon.setVisibility(View.VISIBLE);
    }

    private void hideSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);
        closeIcon.setVisibility(View.GONE);
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

            Toast.makeText(this, "Intern removed", Toast.LENGTH_SHORT).show();

            // Dismiss the dialog
            dialog.dismiss();

            updatePersonCount();

            List<Intern> interns = db.getAll();
            displayAdapter = new DisplayAdapter(getApplicationContext(), R.layout.customlistview, interns);
            listView.setAdapter(displayAdapter);
            if (!interns.isEmpty()){
                noDataIcon.setVisibility(View.GONE);

            }else {
                noDataIcon.setVisibility(View.VISIBLE);

            }

        });

    }


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.btn_blue));
        setSupportActionBar(toolbar);

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