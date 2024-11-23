package com.example.myapplication.viewmodel;

public class UpdateprofileResponse {
    private String status;
    private String message;
    private Data data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private String profile_picture_url;

        public String getProfilePictureUrl() {
            return profile_picture_url;
        }
    }
}

