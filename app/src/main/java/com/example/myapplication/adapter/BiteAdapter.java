package com.example.myapplication.adapter;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemCheckuplistBinding;

import com.example.myapplication.viewmodel.PatientInfo;

import java.util.List;

public class BiteAdapter extends RecyclerView.Adapter<BiteAdapter.BiteViewHolder>{

    private Context context;
    private List<PatientInfo> patientList;

    private final BiteAdapter.OnpatientClickListener listener;
    public interface OnpatientClickListener {
        void onItemClick(PatientInfo patient);
    }

    public BiteAdapter(OnpatientClickListener listener, List<PatientInfo> patientList, Context context) {
        this.listener = listener;
        this.patientList = patientList;
        this.context = context;
    }

    @NonNull
    @Override
    public BiteAdapter.BiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BiteAdapter.BiteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BiteViewHolder extends RecyclerView.ViewHolder {
        ItemCheckuplistBinding binding;

        public BiteViewHolder(@NonNull ItemCheckuplistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
