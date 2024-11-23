package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class TotalbiteResponse {
    @SerializedName("total_bite")
    private int totalbites;


    public int getTotalbites() {
        return totalbites;
    }

    public void setTotalbites(int totalbites) {
        this.totalbites = totalbites;
    }
}

