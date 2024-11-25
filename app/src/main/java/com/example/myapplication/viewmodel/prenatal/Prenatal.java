package com.example.myapplication.viewmodel.prenatal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Prenatal implements Parcelable {

    @SerializedName("prenatalID")
    private int prenatalID;

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

    @SerializedName("comments")
    private String comments;

    public Prenatal(int prenatalID, int patient_id, String date, String name, String gender, String age, String comments) {
        this.prenatalID = prenatalID;
        this.patient_id = patient_id;
        this.date = date;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.comments = comments;
    }

    // Constructor that reads from Parcel
    protected Prenatal(Parcel in) {
        prenatalID = in.readInt();
        patient_id = in.readInt();
        date = in.readString();
        name = in.readString();
        gender = in.readString();
        age = in.readString();
        comments = in.readString(); // Missing this field earlier
    }

    // Parcelable Creator
    public static final Creator<Prenatal> CREATOR = new Creator<Prenatal>() {
        @Override
        public Prenatal createFromParcel(Parcel in) {
            return new Prenatal(in);
        }

        @Override
        public Prenatal[] newArray(int size) {
            return new Prenatal[size];
        }
    };

    // Getters
    public int getPrenatalID() {
        return prenatalID;
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
        parcel.writeInt(prenatalID);
        parcel.writeInt(patient_id);
        parcel.writeString(date);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(comments);
    }
}
