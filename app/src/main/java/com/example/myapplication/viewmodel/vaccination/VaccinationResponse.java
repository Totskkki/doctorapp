package com.example.myapplication.viewmodel.vaccination;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VaccinationResponse implements Serializable {
    @SerializedName("patients")
    private List<PatientVaccine> patients = new ArrayList<>();  // List of patient checkups

    public List<PatientVaccine> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientVaccine> patients) {
        this.patients = patients;
    }
}

