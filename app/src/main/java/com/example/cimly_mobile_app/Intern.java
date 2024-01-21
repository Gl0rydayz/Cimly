package com.example.cimly_mobile_app;

import android.util.Base64;
import android.util.Log;

import java.sql.Time;

public class Intern {
    private String name;
    private String email;
    private String numero;


    private String imageData;
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

    public Time getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Time arrivetime) {
        this.arrivetime = arrivetime;
    }

    public Time getLeftime() {
        return leftime;
    }

    public void setLeftime(Time leftime) {
        this.leftime = leftime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getImageId() {
        String url = getImageData();

        // Add a null check for imageData
        if (url != null) {
            // Extract ID from the URL
            int lastIndex = url.lastIndexOf("=");
            if (lastIndex != -1) {
                return url.substring(lastIndex + 1);
            }
        }

        return ""; // or handle the case when imageData is null or does not contain "="
    }

    public Intern() {
    }
    public Intern(String name,String email,String numero,String imageData, Time arrivetime, Time leftime) {
        this.name = name;
        this.email=email;
        this.numero=numero;
        this.imageData=imageData;
        this.arrivetime = arrivetime;
        this.leftime = leftime;
    }
}
