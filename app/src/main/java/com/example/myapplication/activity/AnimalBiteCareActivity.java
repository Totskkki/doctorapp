package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PatientcheckupAdapter;
import com.example.myapplication.viewmodel.PatientInfo;


import java.util.ArrayList;
import java.util.List;

public class AnimalBiteCareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PatientcheckupAdapter patientAdapter;
    private List<PatientInfo> patientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animal_bite_care);


        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> onBackPressed());


        recyclerView = findViewById(R.id.recyclerViewcheckup);
        progressBar = findViewById(R.id.progressbarnotification);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientList = new ArrayList<>();



    }
//    private void fetchPatients() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        // Simulating data fetch (Replace this with actual database/API logic)
//        new Handler().postDelayed(() -> {
//            patientList.add(new PatientInfo("Teeet test", "Male - 30 years old", "Completed"));
//            patientList.add(new PatientInfo("Tes fdsa", "Female - 25 years old", "Ongoing"));
//            patientAdapter.notifyDataSetChanged();
//            progressBar.setVisibility(View.GONE);
//        }, 2000);
//    }
}