package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientInfo implements Serializable {


    @SerializedName("patientID")
    private String patientID;
    @SerializedName("patient_name")
    private String name;
    @SerializedName("name")
    private String firstname;
    @SerializedName("middle_name")
    private String middle_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("suffix")
    private String suffix;

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
    private String address;
    private String date_of_birth;
    private String blood_type;
    private String Height;
    private String weight;
    private String rr;
    @SerializedName("vaccination_name")
    private String vaccinationName;  // Specific to animal bite or checkup
    @SerializedName("vaccination_date")
    private String vaccinationDate;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("date_bite")
    private String date_bite;

    @SerializedName("date")
    private String date;



    public PatientInfo(String patientID, String name, String middle_name, String last_name, String suffix, String gender, String age, String phone_number, String Address) {
        this.patientID = patientID;
        this.name = name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.suffix = suffix;
        this.gender = gender;
        this.age = age;
        this.phone_number = phone_number;
        this.address = Address;

    }

    public String getDate() {
        return date;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getVaccinationName() {
        return vaccinationName;
    }

    public String getDate_bite() {
        return date_bite;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public String getHeight() {
        return Height;
    }

    public String getWeight() {
        return weight;
    }

    public String getRr() {
        return rr;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
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
