package com.example.myapplication.viewmodel.prenatal;

import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.animalbite.Bite;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientPrenatal  implements Serializable {

    @SerializedName("patient_info")
    private PatientInfo patientInfo;

    @SerializedName("present_records")
    private List<Prenatal> presentRecords = new ArrayList<>();

    @SerializedName("past_records")
    private List<Prenatal> pastRecords = new ArrayList<>();

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public List<Prenatal> getPresentRecords() {
        return presentRecords;
    }

    public void setPresentRecords(List<Prenatal> presentRecords) {
        this.presentRecords = presentRecords;
    }

    public List<Prenatal> getPastRecords() {
        return pastRecords;
    }

    public void setPastRecords(List<Prenatal> pastRecords) {
        this.pastRecords = pastRecords;
    }
}
