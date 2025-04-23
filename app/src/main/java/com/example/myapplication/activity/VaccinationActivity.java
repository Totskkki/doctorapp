package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BirthingAdapter;
import com.example.myapplication.adapter.VaccineAdapter;
import com.example.myapplication.viewmodel.birthing.BirthingResponse;
import com.example.myapplication.viewmodel.vaccination.VaccinationResponse;

import java.util.ArrayList;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccinationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private VaccineAdapter vaccineAdapter;
    public static final String SHARED_PREFS = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vaccination);

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
        fetchVaccinationList(Integer.parseInt(userID));

    }

    private void fetchVaccinationList(int i) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<VaccinationResponse> call = apiService.getVaccineList();

        call.enqueue(new Callback<VaccinationResponse>() {
            @Override
            public void onResponse(Call<VaccinationResponse> call, Response<VaccinationResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    VaccinationResponse vaccineResponse = response.body();

                    vaccineAdapter = new VaccineAdapter(
                            VaccinationActivity.this,
                            vaccineResponse.getPatients(),
                            patientVaccine -> {
                                Intent intent = new Intent(VaccinationActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("name", patientVaccine.getPatientInfo().getFirstname());
                                intent.putExtra("gender", patientVaccine.getPatientInfo().getGender());
                                intent.putExtra("age", patientVaccine.getPatientInfo().getAge());
                                intent.putExtra("phone_number", patientVaccine.getPatientInfo().getPhone_number());
                                intent.putExtra("recordType", "Vaccination");
                                intent.putParcelableArrayListExtra(
                                        "present_records",
                                        new ArrayList<>(patientVaccine.getPresentRecords())
                                );
                                intent.putParcelableArrayListExtra(
                                        "past_records",
                                        new ArrayList<>(patientVaccine.getPastRecords())
                                );
                                startActivity(intent);
                            }
                    );

                    recyclerView.setAdapter(vaccineAdapter);
                } else {
                    Toast.makeText(VaccinationActivity.this, "Failed to load checkups.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VaccinationResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


