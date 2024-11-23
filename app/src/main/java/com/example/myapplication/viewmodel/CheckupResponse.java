package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckupResponse implements Serializable {

    @SerializedName("patients")
    private List<PatientCheckup> patients = new ArrayList<>();  // List of patient checkups

    public List<PatientCheckup> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientCheckup> patients) {
        this.patients = patients;
    }
}


