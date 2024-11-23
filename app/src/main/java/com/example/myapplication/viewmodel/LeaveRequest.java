package com.example.myapplication.viewmodel;

public class LeaveRequest {
    private String action;
    private int userID;
    private String leaveDate;
    private String message;
    private String startTime;
    private String endTime;


    public LeaveRequest(String endTime, String startTime, String message, String leaveDate, String userID, String action) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.message = message;
        this.leaveDate = leaveDate;
        this.userID = Integer.parseInt(userID);
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
