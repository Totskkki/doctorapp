package com.example.myapplication.viewmodel;

import java.util.List;

public class AnnouncementResponse {

    private int announceID;
    private String title;
    private String date;
    private String details;
    private List<Appointment> appointments;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskDescription() {
        return details;
    }

    private List<AnnouncementResponse> announcements;

    public List<AnnouncementResponse> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementResponse> announcements) {
        this.announcements = announcements;
    }


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

    public void notifyDataSetChanged() {


    }
}
