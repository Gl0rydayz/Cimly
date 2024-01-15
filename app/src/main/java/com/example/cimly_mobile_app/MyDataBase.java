package com.example.cimly_mobile_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String DATABASE_NAME = "Interns.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_INTERNS = "interns";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_NUMERO = "numero";
    private static final String COLUMN_TIME_ARR = "time_arrive";
    private static final String COLUMN_TIME_LEF = "time_left";
    private static final String COLUMN_DATE_ARR = "date_arrive";
    private static final String COLUMN_DATE_LEF = "date_left";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String CREATE_INTERN_TABLE = "CREATE TABLE " + TABLE_INTERNS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_NUMERO + " TEXT NOT NULL, "
            + COLUMN_IMAGE_URI + " TEXT NOT NULL, "
            + COLUMN_TIME_LEF + " TEXT NOT NULL,"
            + COLUMN_TIME_ARR + " TEXT NOT NULL, "
            + COLUMN_DATE_LEF + " DATE NOT NULL,"
            + COLUMN_DATE_ARR + " DATE NOT NULL"
            + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INTERN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop table if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERNS);
        // Recreate table
        onCreate(db);
    }


    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }


    @SuppressLint("Range")
    public ArrayList<Intern> getAllByCurrentDate(String selectedDate) {
        ArrayList<Intern> interns = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "SELECT * FROM " + TABLE_INTERNS +
                " WHERE (" + COLUMN_DATE_ARR + " = ? )";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{selectedDate});

        if (cursor.moveToFirst()) {
            do {
                Intern intern = new Intern();
                intern.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                intern.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                intern.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                intern.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
                intern.setImageData(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI)));
                intern.setArrivetime(Time.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ARR))));
                intern.setLeftime(Time.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_LEF))));
                interns.add(intern);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return interns;
    }






    public boolean isInternExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_INTERNS + " WHERE " + COLUMN_NAME + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        boolean exist = cursor.getCount() > 0;

        cursor.close();
        return exist;
    }







    public void updateLeaveTimeByName(String name,Time leftime, Date leftDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_LEF, leftime.toString());
        values.put(COLUMN_DATE_LEF, formatDate(leftDate));
        String whereClause = COLUMN_NAME + "=? AND " + COLUMN_DATE_ARR + "=?";
        Date currentDate = new Date();
        String[] whereArgs = new String[]{name, formatDate(currentDate)};
        db.update(TABLE_INTERNS, values, whereClause, whereArgs);
        db.close();
    }



    public void insertinterns(Intern intern) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,intern.getName());
        values.put(COLUMN_EMAIL,intern.getEmail());
        values.put(COLUMN_NUMERO,intern.getNumero());
        values.put(COLUMN_IMAGE_URI, intern.getImageData());
        values.put(COLUMN_TIME_ARR, intern.getArrivetime().toString());
        values.put(COLUMN_TIME_LEF, intern.getLeftime().toString());
        values.put(COLUMN_DATE_ARR, formatDate(intern.getArriveDate()));
        values.put(COLUMN_DATE_LEF, formatDate(intern.getLeftDate()));
        db.insert(TABLE_INTERNS, null, values);
    }

    public boolean hasInternScannedToday(String name) {
        SQLiteDatabase db = this.getReadableDatabase();


            String selectQuery = "SELECT * FROM " + TABLE_INTERNS +
                    " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_DATE_ARR + "=?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{name, formatDate(new Date())});

            boolean exists = cursor.getCount() > 0;

            cursor.close();
            return exists;

    }

    public boolean hasInternExitToday(String name) {
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "SELECT * FROM " + TABLE_INTERNS +
                " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_DATE_ARR + "=? AND "+ COLUMN_TIME_LEF+"=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, formatDate(new Date()),"00:00:00"});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;

    }





}
