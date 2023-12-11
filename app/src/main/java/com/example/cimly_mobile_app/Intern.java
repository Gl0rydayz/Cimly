package com.example.cimly_mobile_app;

import java.sql.Time;

public class Intern {
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    private String numero;
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

    public Intern(String name,String email,String numero, Time arrivetime, Time leftime, int id) {
        this.name = name;
        this.email=email;
        this.numero=numero;
        this.arrivetime = arrivetime;
        this.leftime = leftime;
        this.id = id;
    }
}
