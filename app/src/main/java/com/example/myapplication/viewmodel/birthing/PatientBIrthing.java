package com.example.myapplication.viewmodel.birthing;

import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.animalbite.Bite;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientBIrthing implements Serializable {
    @SerializedName("patient_info")
    private PatientInfo patientInfo;

    @SerializedName("present_records")
    private List<birthing> presentRecords = new ArrayList<>();

    @SerializedName("past_records")
    private List<birthing> pastRecords = new ArrayList<>();

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public List<birthing> getPresentRecords() {
        return presentRecords;
    }

    public void setPresentRecords(List<birthing> presentRecords) {
        this.presentRecords = presentRecords;
    }

    public List<birthing> getPastRecords() {
        return pastRecords;
    }

    public void setPastRecords(List<birthing> pastRecords) {
        this.pastRecords = pastRecords;
    }
}

