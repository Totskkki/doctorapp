package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class TotalCheckupResponse {

    @SerializedName("total_checkups")
    private int totalCheckups;

    // Getter and Setter methods for total_checkups
    public int getTotalCheckups() {
        return totalCheckups;
    }

    public void setTotalCheckups(int totalCheckups) {
        this.totalCheckups = totalCheckups;
    }


}
