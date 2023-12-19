package com.example.cimly_mobile_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String DATABASE_NAME = "interns.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_INTERNS = "interns";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_NUMERO = "numero";
    private static final String COLUMN_TIME_ARR = "time_arrive";
    private static final String COLUMN_TIME_LEF = "time_left";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String CREATE_INTERN_TABLE = "CREATE TABLE " + TABLE_INTERNS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_NUMERO + " TEXT NOT NULL, "
            + COLUMN_IMAGE_URI + " TEXT NOT NULL, "
            + COLUMN_TIME_ARR + " TEXT NOT NULL, "
            + COLUMN_TIME_LEF + " TEXT NOT NULL"
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

    public void insertinterns(Intern intern) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,intern.getName());
        values.put(COLUMN_EMAIL,intern.getEmail());
        values.put(COLUMN_NUMERO,intern.getNumero());
        values.put(COLUMN_IMAGE_URI, intern.getImageData());
        values.put(COLUMN_TIME_ARR, intern.getArrivetime().toString());
        values.put(COLUMN_TIME_LEF, intern.getLeftime().toString());
        db.insert(TABLE_INTERNS, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Intern> getAll() {
        ArrayList<Intern> interns = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INTERNS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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
    public void updateLeaveTimeByName(String name, String leaveTimeStr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_LEF, leaveTimeStr);
        db.update(TABLE_INTERNS, values, COLUMN_NAME + "=?", new String[]{name});
    }
    public void updateArriveTimeByName(String name, String ArriveTimeStr,String leaveTimeStr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_LEF, ArriveTimeStr);
        values.put(COLUMN_TIME_LEF, leaveTimeStr);
        db.update(TABLE_INTERNS, values, COLUMN_NAME + "=?", new String[]{name});

    }
}
