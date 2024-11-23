package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("announcements")
    private List<Announcement> announcements;

    @SerializedName("doctor_schedules")
    private List<DoctorSchedule> doctorSchedules;

    @SerializedName("patients")
    private List<PatientInfo> patients;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<DoctorSchedule> getDoctorSchedules() {
        return doctorSchedules;
    }

    public void setDoctorSchedules(List<DoctorSchedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public List<PatientInfo> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientInfo> patients) {
        this.patients = patients;
    }
}
