package com.example.myapplication.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class NotificationResponse {
    @SerializedName("success")
    private boolean success;
    private List<Notification_list> notifications;

    @SerializedName("notificationCount")
    private int notificationCount;

    private int unreadCount;  // This is fine for tracking the number of unread notifications

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public NotificationResponse(boolean success, List<Notification_list> notifications) {
        this.success = success;
        this.notifications = notifications;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public List<Notification_list> getNotifications() {
        return notifications;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}

