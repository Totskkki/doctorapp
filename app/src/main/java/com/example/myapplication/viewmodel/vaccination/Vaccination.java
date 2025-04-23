package com.example.myapplication.viewmodel.vaccination;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Vaccination implements Parcelable {

    @SerializedName("immunID")
    private int immunID;

    @SerializedName("patient_id")
    private int patient_id;

    @SerializedName("immunization_date")
    private String date;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;

    @SerializedName("vaccine")
    private String comments;

    public Vaccination(int immunID, int patient_id, String date, String name, String gender, String age, String comments) {
        this.immunID = immunID;
        this.patient_id = patient_id;
        this.date = date;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.comments = comments;
    }

    // Constructor that reads from Parcel
    protected Vaccination(Parcel in) {
        immunID = in.readInt();
        patient_id = in.readInt();
        date = in.readString();
        name = in.readString();
        gender = in.readString();
        age = in.readString();
        comments = in.readString(); // Missing this field earlier
    }

    // Parcelable Creator
    public static final Creator<Vaccination> CREATOR = new Creator<Vaccination>() {
        @Override
        public Vaccination createFromParcel(Parcel in) {
            return new Vaccination(in);
        }

        @Override
        public Vaccination[] newArray(int size) {
            return new Vaccination[size];
        }
    };

    // Getters
    public int getPrenatalID() {
        return immunID;
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

    public String getComments() {
        return comments;
    }

    // Parcelable Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(immunID);
        parcel.writeInt(patient_id);
        parcel.writeString(date);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(comments);
    }
}
