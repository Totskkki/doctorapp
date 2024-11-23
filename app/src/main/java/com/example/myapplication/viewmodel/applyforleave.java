package com.example.myapplication.viewmodel;

public class applyforleave {
    private String status;
    private String message;
    private LeaveData data;

    public class LeaveData {
        private String userID;
        private String leave_start_date;
        private String leave_end_date;
        private String message;
        private int is_available;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LeaveData getData() {
        return data;
    }
}

