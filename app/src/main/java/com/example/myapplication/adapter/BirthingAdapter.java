package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;
import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.birthing.PatientBIrthing;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BirthingAdapter  extends RecyclerView.Adapter<BirthingAdapter.BirthingViewHolder> {
    private final Context context;
    private final List<PatientBIrthing> birthList;
    private final BirthingClickListener listener;

    public interface BirthingClickListener {
        void onPatientClick(PatientBIrthing patientPrenatal);
    }

    public BirthingAdapter(Context context, List<PatientBIrthing> birthList, BirthingClickListener listener) {
        this.context = context;
        this.birthList = birthList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BirthingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckuplistBinding binding = ItemCheckuplistBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new BirthingViewHolder(binding); // Use BirthingViewHolder, not BirthingAdapter
    }


    @Override
    public void onBindViewHolder(@NonNull BirthingAdapter.BirthingViewHolder holder, int position) {
        PatientBIrthing patientbirhing = birthList.get(position);

        if (patientbirhing != null) {
            PatientInfo patientInfo = patientbirhing.getPatientInfo();
            holder.binding.patientname.setText(patientInfo.getFirstname());
            holder.binding.genderage.setText(
                    String.format(Locale.getDefault(), "%s - %s", patientInfo.getGender(), patientInfo.getAge())
            );
            holder.binding.dateadmitted.setText(patientInfo.getDate());



            holder.binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPatientClick(patientbirhing);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return birthList.size();
    }
    static class BirthingViewHolder extends  RecyclerView.ViewHolder{
        ItemCheckuplistBinding binding;

        public BirthingViewHolder(@NonNull ItemCheckuplistBinding binding) {
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
