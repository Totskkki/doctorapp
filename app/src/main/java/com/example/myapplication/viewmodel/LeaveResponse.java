package com.example.myapplication.viewmodel;

public class LeaveResponse {

    private boolean success;  // success flag
    private String message;   // response message

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Notification {
        private String message;
        private String timeAgo;
        private boolean isApproved;

        // Constructor, getters, and setters
        public Notification(String message, String timeAgo, boolean isApproved) {
            this.message = message;
            this.timeAgo = timeAgo;
            this.isApproved = isApproved;
        }

        public String getMessage() { return message; }
        public String getTimeAgo() { return timeAgo; }
        public boolean isApproved() { return isApproved; }
    }
}

