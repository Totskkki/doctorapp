package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

public class Announcement {
    @SerializedName("announceID")
    private int announceID;

    @SerializedName("date")
    private String date;

    @SerializedName("title")
    private String title;

    @SerializedName("details")
    private String details;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("sync_status")
    private int syncStatus;

    // Getters and setters
    public int getAnnounceID() {
        return announceID;
    }

    public void setAnnounceID(int announceID) {
        this.announceID = announceID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }
}
