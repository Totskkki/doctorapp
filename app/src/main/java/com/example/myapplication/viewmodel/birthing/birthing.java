package com.example.myapplication.viewmodel.birthing;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class birthing implements Parcelable {
    @SerializedName("prenatalID")
    private int prenatalID;

    @SerializedName("patient_id")
    private int patient_id;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;

    @SerializedName("case_no")
    private String caseno;

    @SerializedName("date")
    private String date;

    public birthing(int prenatalID, int patient_id, String name, String gender, String age, String caseno, String date) {
        this.prenatalID = prenatalID;
        this.patient_id = patient_id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.caseno = caseno;
        this.date = date;
    }

    protected birthing(Parcel in) {
        prenatalID = in.readInt();
        patient_id = in.readInt();
        name = in.readString();
        gender = in.readString();
        age = in.readString();
        caseno = in.readString();
        date = in.readString();
    }

    public static final Creator<birthing> CREATOR = new Creator<birthing>() {
        @Override
        public birthing createFromParcel(Parcel in) {
            return new birthing(in);
        }

        @Override
        public birthing[] newArray(int size) {
            return new birthing[size];
        }
    };

    public int getPrenatalID() {
        return prenatalID;
    }

    public void setPrenatalID(int prenatalID) {
        this.prenatalID = prenatalID;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(prenatalID);
        parcel.writeInt(patient_id);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(caseno);
        parcel.writeString(date);
    }
}
