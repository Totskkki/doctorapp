package com.example.myapplication.viewmodel.animalbite;

import com.example.myapplication.viewmodel.Checkup;
import com.example.myapplication.viewmodel.PatientInfo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientBite implements Serializable {

    @SerializedName("patient_info")
    private PatientInfo patientInfo;

    @SerializedName("present_records")
    private List<Bite> presentRecords = new ArrayList<>();

    @SerializedName("past_records")
    private List<Bite> pastRecords = new ArrayList<>();

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public List<Bite> getPresentRecords() {
        return presentRecords;
    }

    public void setPresentRecords(List<Bite> presentRecords) {
        this.presentRecords = presentRecords;
    }

    public List<Bite> getPastRecords() {
        return pastRecords;
    }

    public void setPastRecords(List<Bite> pastRecords) {
        this.pastRecords = pastRecords;
    }
}

