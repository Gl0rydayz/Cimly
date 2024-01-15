package com.example.cimly_mobile_app;

import android.util.Base64;
import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Intern {
    private String name;
    private String email;
    private String numero;


    private String imageData;
    private Date arriveDate;
    private Date leftDate;


    private Time arrivetime;
    private Time leftime;
    private int id;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImageData() {
        return imageData;
    }
    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //ArriveTime getter/setter
    public Time getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Time arrivetime) {
        this.arrivetime = arrivetime;
    }

    //LeaveTime getter/setter
    public Time getLeftime() {
        return leftime;
    }

    public void setLeftime(Time leftime) {
        this.leftime = leftime;
    }



    //ArriveDate getter/setter
    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getLeftDate() {
        return leftDate;
    }

    public void setLeftDate(Date leftDate) {
        this.leftDate = leftDate;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getImageId() {
        String url = getImageData();

        // Extract ID from the URL
        String id = url.substring(url.lastIndexOf("=") + 1);

        return id;
    }
    public Intern() {
    }
    public Intern(String name,String email,String numero,String imageData, Time arrivetime, Time leftime,Date arriveDate,Date leftDate) {
        this.name = name;
        this.email=email;
        this.numero=numero;
        this.imageData=imageData;
        this.arrivetime = arrivetime;
        this.leftime = leftime;
        this.arriveDate = arriveDate;
        this.leftDate = leftDate;
    }

    public Intern(String name, Time arrivetime, Time leftime,Date arriveDate,Date leftDate) {
        this.name = name;
        this.arrivetime = arrivetime;
        this.leftime = leftime;
        this.arriveDate = arriveDate;
        this.leftDate = leftDate;
    }

    public Intern(int id,Time leftime,Date leftDate) {
        this.id=id;
        this.leftime = leftime;
        this.leftDate = leftDate;
    }



}
