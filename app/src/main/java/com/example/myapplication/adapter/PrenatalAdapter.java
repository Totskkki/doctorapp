package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;
import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.animalbite.PatientBite;
import com.example.myapplication.viewmodel.prenatal.PatientPrenatal;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class PrenatalAdapter extends RecyclerView.Adapter<PrenatalAdapter.PrenatalViewHolder> {

    private final Context context;
    private final List<PatientPrenatal> prenatalList;
    private final PatientPrenatalClickListener listener;

    public interface PatientPrenatalClickListener {
        void onPatientClick(PatientPrenatal patientPrenatal);
    }

    public PrenatalAdapter(Context context, List<PatientPrenatal> prenatalList, PatientPrenatalClickListener listener) {
        this.context = context;
        this.prenatalList = prenatalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PrenatalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckuplistBinding binding = ItemCheckuplistBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new PrenatalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrenatalViewHolder holder, int position) {
        PatientPrenatal patientPrenatal = prenatalList.get(position);

        if (patientPrenatal != null) {
            PatientInfo patientInfo = patientPrenatal.getPatientInfo();
            holder.binding.patientname.setText(patientInfo.getFirstname());
            holder.binding.genderage.setText(
                    String.format(Locale.getDefault(), "%s - %s", patientInfo.getGender(), patientInfo.getAge())
            );
            holder.binding.dateadmitted.setText(patientInfo.getDate());



            holder.binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPatientClick(patientPrenatal);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return prenatalList.size();
    }

    static class PrenatalViewHolder extends RecyclerView.ViewHolder {
        ItemCheckuplistBinding binding;

        public PrenatalViewHolder(@NonNull ItemCheckuplistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

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



