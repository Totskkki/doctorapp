package com.example.myapplication.viewmodel;

public class Events {
    private String name;
    private String status;

    public Events(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
