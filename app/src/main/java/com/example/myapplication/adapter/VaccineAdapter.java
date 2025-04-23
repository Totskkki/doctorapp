package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;
import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.birthing.PatientBIrthing;
import com.example.myapplication.viewmodel.vaccination.PatientVaccine;

import java.util.List;
import java.util.Locale;

public class VaccineAdapter  extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder> {

    private final Context context;
    private final List<PatientVaccine> vaclist;
    private final VaccineAdapterClickListener listener;

    public VaccineAdapter(Context context, List<PatientVaccine> vaclist, VaccineAdapterClickListener listener) {
        this.context = context;
        this.vaclist = vaclist;
        this.listener = listener;
    }

    public interface VaccineAdapterClickListener {
        void onPatientClick(PatientVaccine patientPrenatal);
    }


    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckuplistBinding binding = ItemCheckuplistBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new VaccineViewHolder(binding); // Use BirthingViewHolder, not BirthingAdapter
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineAdapter.VaccineViewHolder holder, int position) {
        PatientVaccine vaccine = vaclist.get(position);

        if (vaccine != null) {
            PatientInfo patientInfo = vaccine.getPatientInfo();
            holder.binding.patientname.setText(patientInfo.getFirstname());
            holder.binding.genderage.setText(
                    String.format(Locale.getDefault(), "%s - %s", patientInfo.getGender(), patientInfo.getAge())
            );
            holder.binding.dateadmitted.setText(patientInfo.getDate());



            holder.binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPatientClick(vaccine);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return vaclist.size();
    }
    static  class VaccineViewHolder extends  RecyclerView.ViewHolder{
        ItemCheckuplistBinding binding;

        public VaccineViewHolder(@NonNull ItemCheckuplistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
