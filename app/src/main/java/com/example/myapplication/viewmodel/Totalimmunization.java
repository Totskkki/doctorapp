package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class Totalimmunization {
    @SerializedName("Totalimmunization")
    private int Totalimmunization;

    public int getTotalimmunization() {
        return Totalimmunization;
    }

    public void setTotalimmunization(int totalimmunization) {
        Totalimmunization = totalimmunization;
    }
}
