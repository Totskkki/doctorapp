package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;
import com.example.myapplication.viewmodel.Checkup;
import com.example.myapplication.viewmodel.PatientCheckup;
import com.example.myapplication.viewmodel.PatientInfo;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import api.TimeUtils;
public class PatientcheckupAdapter extends RecyclerView.Adapter<PatientcheckupAdapter.PatientViewHolder> {

    private final Context context;
    private final List<PatientCheckup> patientCheckupList; // Use PatientCheckup directly
    private final OnPatientClickListener listener;

    public interface OnPatientClickListener {
        void onPatientClick(PatientCheckup patientCheckup);
    }

    public PatientcheckupAdapter(OnPatientClickListener listener, List<PatientCheckup> patientCheckupList, Context context) {
        this.listener = listener;
        this.patientCheckupList = patientCheckupList != null ? patientCheckupList : new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckuplistBinding binding = ItemCheckuplistBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new PatientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientCheckup patientCheckup = patientCheckupList.get(position);

        if (patientCheckup != null) {
            PatientInfo patientInfo = patientCheckup.getPatientInfo();
            holder.binding.name.setText(patientInfo.getName());
            holder.binding.genderage.setText(patientInfo.getGender() + " - " + patientInfo.getAge());
            holder.binding.dateadmitted.setText(patientInfo.getDateadmitted());
            // Display other relevant information if necessary

            holder.binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPatientClick(patientCheckup);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return patientCheckupList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        ItemCheckuplistBinding binding;

        public PatientViewHolder(@NonNull ItemCheckuplistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    // Utility method to format date
    private String formatDateTime(String datePart) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date date = originalFormat.parse(datePart);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }
}






