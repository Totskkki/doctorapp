package com.example.myapplication.viewmodel.prenatal;

import com.example.myapplication.viewmodel.animalbite.PatientBite;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrenatalResponse implements Serializable {
    @SerializedName("patients")
    private List<PatientPrenatal> patients = new ArrayList<>();  // List of patient checkups

    public List<PatientPrenatal> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientPrenatal> patients) {
        this.patients = patients;
    }
}

