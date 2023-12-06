package com.example.cimly_mobile_app;

import java.sql.Time;

public class Intern {
    private String name;
    private Time arrivetime;
    private Time leftime;
    private int id;

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

    public Intern(String name, Time arrivetime, Time leftime, int id) {
        this.name = name;
        this.arrivetime = arrivetime;
        this.leftime = leftime;
        this.id = id;
    }
}
