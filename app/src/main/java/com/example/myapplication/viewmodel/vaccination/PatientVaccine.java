package com.example.myapplication.viewmodel.vaccination;

import com.example.myapplication.viewmodel.PatientInfo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientVaccine implements Serializable {

    @SerializedName("patient_info")
    private PatientInfo patientInfo;

    @SerializedName("present_records")
    private List<Vaccination> presentRecords = new ArrayList<>();

    @SerializedName("past_records")
    private List<Vaccination> pastRecords = new ArrayList<>();

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public List<Vaccination> getPresentRecords() {
        return presentRecords;
    }

    public void setPresentRecords(List<Vaccination> presentRecords) {
        this.presentRecords = presentRecords;
    }

    public List<Vaccination> getPastRecords() {
        return pastRecords;
    }

    public void setPastRecords(List<Vaccination> pastRecords) {
        this.pastRecords = pastRecords;
    }
}
