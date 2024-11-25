package com.example.myapplication.viewmodel.animalbite;

import com.example.myapplication.viewmodel.PatientCheckup;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnimalbiteResponse implements Serializable {
    @SerializedName("patients")
    private List<PatientBite> patients = new ArrayList<>();  // List of patient checkups

    public List<PatientBite> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientBite> patients) {
        this.patients = patients;
    }
}
