package com.example.cimly_mobile_app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Space;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn_enter, btn_exit, btn_display;
    MyDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Disable Dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_enter = findViewById(R.id.btn_enter);
        btn_exit = findViewById(R.id.btn_exit);
        btn_display = findViewById(R.id.btn_display);

        db = new MyDataBase(this);
        // Change status bar and navigation bar color and items color
        changeStatusBarColorAndTextColor(getResources().getColor(R.color.status_nav_bar_color));

        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });

        btn_enter.setOnClickListener(v -> {
            scanCode();
        });

        btn_exit.setOnClickListener(v -> {
            scanCodexit();
        });
    }

    //region Scan Qr
    private void scanCode() {


            ScanOptions options = new ScanOptions();
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);

            barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            try {
                String[] parts = result.getContents().split("\n");

                if (parts.length >= 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String numero = parts[2];
                    String image = parts[3];

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String currentTime = sdf.format(new Date());
                    Time arrivetime = Time.valueOf(currentTime);
                    Time leftime = Time.valueOf("00:00:00");

                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = sdfDate.format(new Date());

                    Date arriveDate = null;
                    Date leftDate =null;
                    try {
                        arriveDate = sdfDate.parse(currentDate);
                        leftDate = sdfDate.parse(currentDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (db.hasInternScannedToday(name)){
                        Toast.makeText(this,"enter already scan today ",Toast.LENGTH_SHORT).show();

                    }else {
                        Intern intern = new Intern(name, email, numero, image, arrivetime, leftime, arriveDate, leftDate);

                        db.insertinterns(intern);
                        displayInternDialog(intern);
                    }




                } else {
                    showErrorDialog("Invalid QR code content");
                }
            } catch (Exception e) {
                showErrorDialog("Error processing QR code content: " + e.getMessage());
            }
        }

    });


    private void scanCodexit() {
        // User has not scanned for exit today, proceed with scanning
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);

        barexitLauncher.launch(options);
    }



    ActivityResultLauncher<ScanOptions> barexitLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            try {
                String[] parts = result.getContents().split("\n");

                if (parts.length >= 4) {
                    String name = parts[0];


                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String currentTime = sdf.format(new Date());
                    Time leftime = Time.valueOf(currentTime);

                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = sdfDate.format(new Date());
                    Date leftDate = null;
                    try {
                        leftDate = sdfDate.parse(currentDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (db.hasInternExitToday(name)){
                        db.updateLeaveTimeByName(name,leftime,leftDate);
                        Toast.makeText(this, "Success Update", Toast.LENGTH_SHORT).show();
                    }else if (!db.hasInternScannedToday(name)){
                        Toast.makeText(this, "no enter with that name : "+ name, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Time already Updated", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(this,"Invalid Update",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                showErrorDialog("Error processing QR code content.");

            }
        }
    });





    private void displayInternDialog(Intern intern) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage("WELCOME!  " + intern.getName());
        builder.setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }
    //endregion

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

    //region Method responsible for handling functions while switching from portrait to landskip or the opposite
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_land);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }

        // Reinitialize buttons after changing the layout
        btn_enter = findViewById(R.id.btn_enter);
        btn_exit = findViewById(R.id.btn_exit);
        btn_display = findViewById(R.id.btn_display);

        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });





        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            scanCodexit();
            }
        });
    }
    //endregion
}



