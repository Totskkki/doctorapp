package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BiteAdapter;
import com.example.myapplication.adapter.PatientcheckupAdapter;
import com.example.myapplication.viewmodel.Checkup;
import com.example.myapplication.viewmodel.CheckupResponse;
import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.animalbite.AnimalbiteResponse;
import com.example.myapplication.viewmodel.animalbite.Bite;


import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalBiteCareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BiteAdapter patientAdapter;
    private List<Bite> checkupList;
    private TextView patientNameTextView;
    private TextView patientDetailsTextView;
    private TextView patientStatusTextView;
    private TextView presentRecordsTextView;
    private TextView pastRecordsTextView;
    public static final String SHARED_PREFS = "UserPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animal_bite_care);
        ImageView closeButton = findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> onBackPressed());


        recyclerView = findViewById(R.id.recyclerViewcheckup);
        progressBar = findViewById(R.id.progressbarnotification);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");


        if (userID.isEmpty()) {
            Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call API to fetch checkup list
        fetchbiteList(Integer.parseInt(userID));


    }

    private void fetchbiteList(int userId) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AnimalbiteResponse> call = apiService.getAnimalbiteList();

        call.enqueue(new Callback<AnimalbiteResponse>() {
            @Override
            public void onResponse(Call<AnimalbiteResponse> call, Response<AnimalbiteResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    AnimalbiteResponse checkupResponse = response.body();

                    // Initialize and set up the adapter
                    patientAdapter= new BiteAdapter(
                            patientBite -> {
                                Intent intent = new Intent(AnimalBiteCareActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("name", patientBite.getPatientInfo().getFirstname());
                                intent.putExtra("gender", patientBite.getPatientInfo().getGender());
                                intent.putExtra("age", patientBite.getPatientInfo().getAge());
                                intent.putExtra("phone_number", patientBite.getPatientInfo().getPhone_number());

                                intent.putExtra("recordType", "Animal Bite");

                                intent.putParcelableArrayListExtra("present_records",
                                        new ArrayList<>(patientBite.getPresentRecords()));
                                intent.putParcelableArrayListExtra("past_records",
                                        new ArrayList<>(patientBite.getPastRecords()));
                                startActivity(intent);
                            },
                            checkupResponse.getPatients(),
                            AnimalBiteCareActivity.this
                    );

                    recyclerView.setAdapter(patientAdapter);
                } else {
                    Toast.makeText(AnimalBiteCareActivity.this, "Failed to load checkups.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimalbiteResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AnimalBiteCareActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}