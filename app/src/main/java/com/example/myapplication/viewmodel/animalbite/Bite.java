package com.example.myapplication.viewmodel.animalbite;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Bite implements Parcelable {


    @SerializedName("animal_biteID")
    private int animalbiteID ;

    @SerializedName("vaccination_name")
    private String vaccination_name ;
    @SerializedName("vaccination_date")
    private String vaccination_date ;

    @SerializedName("patient_id")
    private int patient_id;

    @SerializedName("date")
    private String date;


    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("date_bite")
    private String date_bite;

    public Bite(int animalbiteID, String vaccination_name, String vaccination_date, int patient_id, String date, String name, String gender, String age) {
        this.animalbiteID = animalbiteID;
        this.vaccination_name = vaccination_name;
        this.vaccination_date = vaccination_date;
        this.patient_id = patient_id;
        this.date = date;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    protected Bite(Parcel in) {
        animalbiteID = in.readInt();
        vaccination_name = in.readString();
        vaccination_date = in.readString();
        patient_id = in.readInt();
        date = in.readString();
        name = in.readString();
        gender = in.readString();
        age = in.readString();
        remarks = in.readString();
        date_bite = in.readString();
    }

    public static final Creator<Bite> CREATOR = new Creator<Bite>() {
        @Override
        public Bite createFromParcel(Parcel in) {
            return new Bite(in);
        }

        @Override
        public Bite[] newArray(int size) {
            return new Bite[size];
        }
    };

    public String getDate_bite() {
        return date_bite;
    }

    public int getAnimalbiteID() {
        return animalbiteID;
    }

    public String getVaccination_name() {
        return vaccination_name;
    }

    public String getVaccination_date() {
        return vaccination_date;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }




    public String getRemarks() {
        return remarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(animalbiteID);
        parcel.writeString(vaccination_name);
        parcel.writeString(vaccination_date);
        parcel.writeInt(patient_id);
        parcel.writeString(date);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(remarks);
        parcel.writeString(date_bite);
    }
}
