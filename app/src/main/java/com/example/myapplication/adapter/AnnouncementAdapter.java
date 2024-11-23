package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.AllAnnouncementsActivity;
import com.example.myapplication.activity.DetailActivity;
import com.example.myapplication.viewmodel.AnnouncementResponse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private List<AnnouncementResponse> announcementList;
    private Context context;

    public AnnouncementAdapter(List<AnnouncementResponse> announcementList) {
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        AnnouncementResponse announcement = announcementList.get(position);
        holder.tasktitle.setText(announcement.getTitle());  // Title of the announcement
        holder.taskDescription.setText(announcement.getTaskDescription());  // Description

        // Format the date as "17\nJUL"
        String formattedDate = formatDateToDayMonth(announcement.getDate());
        holder.date.setText(formattedDate);

        holder.itemView.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);

            // Pass data to DetailActivity
            intent.putExtra("title", announcement.getTitle());
            intent.putExtra("description", announcement.getTaskDescription());
            intent.putExtra("date", announcement.getDate());

            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView taskDescription;
        TextView date;
        TextView tasktitle;


        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            tasktitle = itemView.findViewById(R.id.title);
            taskDescription = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
    private String formatDateToDayMonth(String dateString) {
        try {
            // Input date format (example: 2024-10-16)
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());

            // Separate day and month

            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", java.util.Locale.getDefault());
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", java.util.Locale.getDefault());

            // Parse the date
            java.util.Date date = inputFormat.parse(dateString);

            // Return formatted string with newline between day and month
            return "\n"+monthFormat.format(date) + "\n" + dayFormat.format(date).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            // Return original date if parsing fails
            return dateString;
        }
    }


}
