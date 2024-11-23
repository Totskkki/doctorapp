package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.PatientDetailsActivity;
import com.example.myapplication.activity.PatientSearchActivity;
import com.example.myapplication.viewmodel.Announcement;
import com.example.myapplication.viewmodel.DoctorSchedule;
import com.example.myapplication.viewmodel.PatientInfo;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> items; // List of mixed data (Announcement, DoctorSchedule, PatientCheckup )

    public SearchAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    // Method to update data dynamically
    public void updateData(List<Object> newItems) {
        this.items.clear(); // Clear the old data
        this.items.addAll(newItems); // Add the new data
        notifyDataSetChanged(); // Notify adapter of data changes
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Announcement) {
            return 0;

        } else if (items.get(position) instanceof PatientInfo) {
            return 1;
        }
        return -1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0) {
            View view = inflater.inflate(R.layout.events_item, parent, false);
            return new AnnouncementViewHolder(view);

        } else {
            View view = inflater.inflate(R.layout.item_patient_checkup, parent, false);
            return new PatientInfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);

        if (holder instanceof AnnouncementViewHolder && item instanceof Announcement) {
            ((AnnouncementViewHolder) holder).bind((Announcement) item);

        }  else if (holder instanceof PatientInfoViewHolder && item instanceof PatientInfo) {
            PatientInfo patientInfo = (PatientInfo) item;
            ((PatientInfoViewHolder) holder).bind(patientInfo);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), PatientSearchActivity.class);
                intent.putExtra("patientID", patientInfo.getPatientID());
                intent.putExtra("patient_name",patientInfo.getName());
                v.getContext().startActivity(intent);
            });
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Define the ViewHolder classes for each type of item

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, detailsTextView;

        public AnnouncementViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            detailsTextView = itemView.findViewById(R.id.patientDetailsTextView);
        }

        public void bind(Announcement announcement) {
            titleTextView.setText(announcement.getTitle());

        }
    }



    static class PatientInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView name, age, gender;

        public PatientInfoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Patientname);
            age = itemView.findViewById(R.id.age);

        }

        public void bind(PatientInfo patientInfo) {
            if (patientInfo != null) {

                name.setText(patientInfo.getName()  + " " + patientInfo.getMiddle_name() + " " +patientInfo.getLast_name());
                age.setText(patientInfo.getGender() + " - " + patientInfo.getAge());
            }
        }
    }
}
