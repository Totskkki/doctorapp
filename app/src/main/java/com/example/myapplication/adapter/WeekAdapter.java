package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekViewHolder> {
    private List<String> weekDates;

    public void setWeekDates(List<String> weekDates) {
        this.weekDates = weekDates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_date, parent, false);
        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        holder.dateTextView.setText(weekDates.get(position));
    }

    @Override
    public int getItemCount() {
        return weekDates != null ? weekDates.size() : 0;
    }

    static class WeekViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;

        WeekViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}

