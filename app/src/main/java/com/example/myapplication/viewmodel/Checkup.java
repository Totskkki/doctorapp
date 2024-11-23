package com.example.myapplication.viewmodel;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Checkup  implements Parcelable {
    @SerializedName("checkupID")
    private int checkupID;

    @SerializedName("patient_id")
    private int patient_id;

    @SerializedName("admitted")
    private String admitted;

    @SerializedName("doctor_order")
    private String doctor_order;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private String age;

    public Checkup(int checkupID, int patient_id, String admitted, String doctor_order, String name, String gender, String age) {
        this.checkupID = checkupID;
        this.patient_id = patient_id;
        this.admitted = admitted;
        this.doctor_order = doctor_order;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    protected Checkup(Parcel in) {
        checkupID = in.readInt();
        patient_id = in.readInt();
        admitted = in.readString();
        doctor_order = in.readString();
        name = in.readString();
        gender = in.readString();
        age = in.readString();
    }

    public static final Creator<Checkup> CREATOR = new Creator<Checkup>() {
        @Override
        public Checkup createFromParcel(Parcel in) {
            return new Checkup(in);
        }

        @Override
        public Checkup[] newArray(int size) {
            return new Checkup[size];
        }
    };

    public int getCheckupID() {
        return checkupID;
    }

    public void setCheckupID(int checkupID) {
        this.checkupID = checkupID;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getAdmitted() {
        return admitted;
    }

    public void setAdmitted(String admitted) {
        this.admitted = admitted;
    }

    public String getDoctor_order() {
        return doctor_order;
    }

    public void setDoctor_order(String doctor_order) {
        this.doctor_order = doctor_order;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(checkupID);
        parcel.writeInt(patient_id);
        parcel.writeString(admitted);
        parcel.writeString(doctor_order);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(age);
    }
}
