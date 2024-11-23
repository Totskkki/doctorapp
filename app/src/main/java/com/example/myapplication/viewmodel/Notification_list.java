package com.example.myapplication.viewmodel;

public class Notification_list {
    private String message,userID;
    private String approvedTimeAgo,rejectedTimeAgo,leave_start_date,leave_end_date,date_schedule;

    private boolean isApproved;

    private boolean isUnread;

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public Notification_list(boolean isUnread, boolean isApproved, String date_schedule, String leave_end_date, String leave_start_date, String rejectedTimeAgo, String approvedTimeAgo, String userID, String message) {
        this.isUnread = isUnread;
        this.isApproved = isApproved;
        this.date_schedule = date_schedule;
        this.leave_end_date = leave_end_date;
        this.leave_start_date = leave_start_date;
        this.rejectedTimeAgo = rejectedTimeAgo;
        this.approvedTimeAgo = approvedTimeAgo;
        this.userID = userID;
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public String getLeave_start_date() {
        return leave_start_date;
    }

    public String getLeave_end_date() {
        return leave_end_date;
    }

    public String getDate_schedule() {
        return date_schedule;
    }

    public String getMessage() {
        return message;
    }

    public String getApprovedTimeAgo() {
        return approvedTimeAgo;
    }

    public String getRejectedTimeAgo() {
        return rejectedTimeAgo;
    }

    public boolean isApproved() {
        return isApproved;
    }
}
