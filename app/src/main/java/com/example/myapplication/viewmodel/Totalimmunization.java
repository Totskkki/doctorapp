package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class Totalimmunization {
    @SerializedName("total_vaccine")
    private int Totalimmunization;

    public int getTotalimmunization() {
        return Totalimmunization;
    }

    public void setTotalimmunization(int totalimmunization) {
        Totalimmunization = totalimmunization;
    }
}
