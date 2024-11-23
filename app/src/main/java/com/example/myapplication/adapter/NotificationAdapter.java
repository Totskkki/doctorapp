package com.example.myapplication.adapter;

import android.app.Notification;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.LeaveResponse;
import com.example.myapplication.viewmodel.Notification_list;

import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification_list> notificationList;  // Specify custom Notification class

    public NotificationAdapter(List<Notification_list> notificationList) {
        this.notificationList = notificationList;
    }


    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification_list notification = notificationList.get(position);

        holder.notificationMessage.setText(notification.getMessage());

        String leaveStart = (notification.getLeave_start_date() != null) ? notification.getLeave_start_date() : "";
        String leaveEnd = (notification.getLeave_end_date() != null) ? notification.getLeave_end_date() : "";
        String dateSchedule = (notification.getDate_schedule() != null) ? notification.getDate_schedule() : "";

        // Combine the values into a single string
        String dateInfo = "Date leave: " + leaveStart + " - " + leaveEnd + " | Schedule: " + dateSchedule;
        holder.dateleave.setText(dateInfo);

        // Show/hide the red dot based on whether the notification is read or not
        if (notification.isUnread()) {  // Check the isUnread() method instead of isRead()
            holder.redDot.setVisibility(View.VISIBLE);  // Show the red dot if unread
        } else {
            holder.redDot.setVisibility(View.GONE);  // Hide the red dot if read
        }

        if (notification.isApproved()) {
            holder.timeAgo.setText(notification.getApprovedTimeAgo());
            holder.notificationBadge.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.notification_shape));
            holder.notificationBadge.setText("Approved");
        } else {
            holder.timeAgo.setText(notification.getRejectedTimeAgo());
            holder.notificationBadge.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.notification_shape_rejected));
            holder.notificationBadge.setText("Rejected");
        }

        holder.notificationBadge.setTextColor(Color.WHITE);

        holder.itemView.setOnClickListener(v -> {
            // Decrement notification count by calling the API
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<Void> call = apiService.decrementNotificationCount(notification.getUserID());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        notification.setUnread(false); // Update local notification state
                        notifyDataSetChanged(); // Refresh UI
                    } else {
                        Log.e("NotificationAdapter", "Failed to decrement count: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("NotificationAdapter", "Error: " + t.getMessage());
                }
            });
        });

        // Show/hide the red dot based on whether the notification is unread
        if (notification.isUnread()) {
            holder.redDot.setVisibility(View.VISIBLE); // Show the red dot
        } else {
            holder.redDot.setVisibility(View.GONE); // Hide the red dot
        }
    }





    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView notificationMessage, timeAgo, notificationBadge,dateleave;
        View redDot;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            dateleave = itemView.findViewById(R.id.dateleave);
            timeAgo = itemView.findViewById(R.id.time_ago);
            notificationBadge = itemView.findViewById(R.id.notification_badge);
            redDot = itemView.findViewById(R.id.red_dot);
        }
    }
}

