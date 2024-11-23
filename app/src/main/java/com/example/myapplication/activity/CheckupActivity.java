package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.adapter.PatientcheckupAdapter;
import com.example.myapplication.viewmodel.Checkup;
import com.example.myapplication.viewmodel.CheckupResponse;
import com.example.myapplication.viewmodel.PatientCheckup;
import com.google.gson.Gson;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckupActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PatientcheckupAdapter patientAdapter;
    private List<Checkup> checkupList;
    private TextView patientNameTextView;
    private TextView patientDetailsTextView;
    private TextView patientStatusTextView;
    private TextView presentRecordsTextView;
    private TextView pastRecordsTextView;
    public static final String SHARED_PREFS = "UserPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup);
        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerViewcheckup);
        progressBar = findViewById(R.id.progressbarnotification);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String specialty = sharedPreferences.getString("Specialty", "");
        String Email = sharedPreferences.getString("email", "");
        String License = sharedPreferences.getString("LicenseNo", "");
        String ProfessionalType = sharedPreferences.getString("ProfessionalType", "");

        if (userID.isEmpty()) {
            Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call API to fetch checkup list
        fetchCheckupList(Integer.parseInt(userID));


    }

    private void fetchCheckupList(int i) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<CheckupResponse> call = apiService.getCheckupList();

        call.enqueue(new Callback<CheckupResponse>() {
            @Override
            public void onResponse(Call<CheckupResponse> call, Response<CheckupResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    CheckupResponse checkupResponse = response.body();

                    // Create adapter using only the relevant patient list
                    patientAdapter = new PatientcheckupAdapter(
                            patientCheckup -> {
                                // Pass only this patient's data
                                Intent intent = new Intent(CheckupActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("name", patientCheckup.getPatientInfo().getName());
                                intent.putExtra("gender", patientCheckup.getPatientInfo().getGender());
                                intent.putExtra("age", patientCheckup.getPatientInfo().getAge());
                                intent.putExtra("admitted",patientCheckup.getPatientInfo().getDateadmitted());
                                intent.putExtra("phone_number", patientCheckup.getPatientInfo().getPhone_number());
                                intent.putParcelableArrayListExtra("present_records",
                                        new ArrayList<>(patientCheckup.getPresentRecords()));
                                intent.putParcelableArrayListExtra("past_records",
                                        new ArrayList<>(patientCheckup.getPastRecords()));
                                startActivity(intent);
                            },
                            checkupResponse.getPatients(),
                            CheckupActivity.this
                    );

                    recyclerView.setAdapter(patientAdapter);

                } else {
                    Toast.makeText(CheckupActivity.this, "Failed to load checkups.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckupResponse> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckupActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
