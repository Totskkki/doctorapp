package com.example.myapplication.viewmodel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginResponse {
    private boolean success;
    private String message,first_name,middlename,
            personnel_id,position_id,lastname,profile_picture,
            userID,LicenseNo,Specialty,email,ProfessionalType,address;

    public String getPersonnel_id() {
        return personnel_id;
    }

    public String getPosition_id() {
        return position_id;
    }

    public String getAddress() {  return address; }

    public String getSpecialty() {
        return Specialty;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLicenseno() {
        return LicenseNo;
    }

    public String getMiddle_name() {
        return middlename;
    }

    public String getLast_name() {
        return lastname;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUserID() {
        return userID;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public String getMessage() {
        return message;
    }

    public String getProfessionalType() {
        return ProfessionalType;
    }

    public static interface ApiService {

        @FormUrlEncoded
        @POST("login.php")
        Call<LoginResponse> loginUser(
                @Field("username") String username,
                @Field("password") String password
        );
    }
}
