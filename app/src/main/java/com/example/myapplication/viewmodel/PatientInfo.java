package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientInfo implements Serializable {


    @SerializedName("patientID")
    private String patientID;
    @SerializedName("patient_name")
    private String name;
    @SerializedName("middle_name")
    private String middle_name;
    @SerializedName("last_name")
    private String last_name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;
    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("doctor_order")
    private String doctorOrder;
    @SerializedName("admitted")
    private String admitted;

    public String getName() {
        return name;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAdmitted() {
        return admitted;
    }

    public String getDateadmitted() {
        return admitted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDoctorOrder() {
        return doctorOrder;
    }

    public void setDoctorOrder(String doctorOrder) {
        this.doctorOrder = doctorOrder;
    }
}
