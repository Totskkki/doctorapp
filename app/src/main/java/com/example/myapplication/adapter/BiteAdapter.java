package com.example.myapplication.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;

import com.example.myapplication.viewmodel.PatientCheckup;
import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.animalbite.Bite;
import com.example.myapplication.viewmodel.animalbite.PatientBite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BiteAdapter extends RecyclerView.Adapter<BiteAdapter.BiteViewHolder> {

    private final Context context;
    private final List<PatientBite> patientCheckupList; // Use strong typing
    private final PatientBiteClickListener listener;

    public interface PatientBiteClickListener {
        void onPatientClick(PatientBite patientCheckup);
    }

    public BiteAdapter(PatientBiteClickListener listener, List<PatientBite> patientCheckupList, Context context) {
        this.listener = listener;
        this.patientCheckupList = patientCheckupList != null ? patientCheckupList : new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public BiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckuplistBinding binding = ItemCheckuplistBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new BiteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BiteViewHolder holder, int position) {
        PatientBite patientCheckup = patientCheckupList.get(position);

        if (patientCheckup != null) {
            PatientInfo patientInfo = patientCheckup.getPatientInfo();
            holder.binding.patientname.setText(patientInfo.getFirstname());
            holder.binding.genderage.setText(patientInfo.getGender() + " - " + patientInfo.getAge());
            String formattedDate = formatDateTime(patientInfo.getVaccinationDate());
            holder.binding.dateadmitted.setText(formattedDate);





            holder.binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPatientClick(patientCheckup);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return patientCheckupList.size(); // Ensure the correct size is returned
    }

    public static class BiteViewHolder extends RecyclerView.ViewHolder {
        ItemCheckuplistBinding binding;

        public BiteViewHolder(@NonNull ItemCheckuplistBinding binding) {
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
