package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class PatientCheckup implements Serializable {

    @SerializedName("patient_info")
    private PatientInfo patientInfo;

    @SerializedName("present_records")
    private List<Checkup> presentRecords = new ArrayList<>();

    @SerializedName("past_records")
    private List<Checkup> pastRecords = new ArrayList<>();

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public List<Checkup> getPresentRecords() {
        return presentRecords;
    }

    public void setPresentRecords(List<Checkup> presentRecords) {
        this.presentRecords = presentRecords;
    }

    public List<Checkup> getPastRecords() {
        return pastRecords;
    }

    public void setPastRecords(List<Checkup> pastRecords) {
        this.pastRecords = pastRecords;
    }
}
