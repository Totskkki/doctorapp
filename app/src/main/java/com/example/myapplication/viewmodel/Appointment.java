package com.example.myapplication.viewmodel;

public class Appointment {

    private String title;
    private String time;

    public Appointment(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
