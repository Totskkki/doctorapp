package com.example.myapplication.viewmodel.birthing;

import com.example.myapplication.viewmodel.prenatal.PatientPrenatal;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BirthingResponse implements Serializable {

    @SerializedName("patients")
    private List<PatientBIrthing> patients = new ArrayList<>();  // List of patient checkups

    public List<PatientBIrthing> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientBIrthing> patients) {
        this.patients = patients;
    }
}


