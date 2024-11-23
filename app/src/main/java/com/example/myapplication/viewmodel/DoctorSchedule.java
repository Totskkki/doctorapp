package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class DoctorSchedule {
    // Other fields
    private Map<String, List<Schedule>> schedules;

    private String reapet;
    @SerializedName("doc_scheduleID")
    private int docScheduleID;



    @SerializedName("date_schedule")
    private String dateSchedule;

    @SerializedName("message")
    private String message;

    @SerializedName("day_of_week")
    private String dayOfWeek;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("is_available")
    private int isAvailable;

    public String getDateSchedule() {
        return dateSchedule;
    }

    public String getMessage() {
        return message;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public int getDocScheduleID() {
        return docScheduleID;
    }

    public String getReapet() {
        return reapet;
    }

    public Map<String, List<Schedule>> getSchedules() {
        return schedules;
    }

    public void setSchedules(Map<String, List<Schedule>> schedules) {
        this.schedules = schedules;
    }

    // Inner class for Schedule details
    public static class Schedule {
        private String fromtime;
        private String totime;
        private String worklength;
        private String scheduleDate;
        private String leaveStartDate;  // New field for leave start date
        private String leaveEndDate;    // New field for leave end date

        public Schedule(String fromTime, String toTime) {
            this.fromtime = fromTime;
            this.totime = toTime;
        }

        // Getters and setters
        public String getFromtime() { return fromtime; }
        public void setFromtime(String fromtime) { this.fromtime = fromtime; }

        public String getTotime() { return totime; }
        public void setTotime(String totime) { this.totime = totime; }

        public String getWorklength() { return worklength; }
        public void setWorklength(String worklength) { this.worklength = worklength; }

        public String getScheduleDate() { return scheduleDate; }
        public void setScheduleDate(String scheduleDate) { this.scheduleDate = scheduleDate; }

        // Getters and setters for the new leave dates
        public String getLeaveStartDate() { return leaveStartDate; }
        public void setLeaveStartDate(String leaveStartDate) { this.leaveStartDate = leaveStartDate; }

        public String getLeaveEndDate() { return leaveEndDate; }
        public void setLeaveEndDate(String leaveEndDate) { this.leaveEndDate = leaveEndDate; }
    }

}


