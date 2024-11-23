package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class Totalbirthing {

    @SerializedName("total_birth")
    private int total_birthing;

    public int getTotal_birthing() {
        return total_birthing;
    }

    public void setTotal_birthing(int total_birthing) {
        this.total_birthing = total_birthing;
    }
}
