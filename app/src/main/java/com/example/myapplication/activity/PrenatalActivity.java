package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PrenatalAdapter;
import com.example.myapplication.viewmodel.prenatal.PrenatalResponse;
import com.example.myapplication.viewmodel.prenatal.Prenatal;

import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrenatalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PrenatalAdapter prenatalAdapter;
    public static final String SHARED_PREFS = "UserPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenatal);


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
        fetchPrenatalList(Integer.parseInt(userID));


    }

    private void fetchPrenatalList(int userId) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<PrenatalResponse> call = apiService.getPrenatalList();

        call.enqueue(new Callback<PrenatalResponse>() {
            @Override
            public void onResponse(Call<PrenatalResponse> call, Response<PrenatalResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    PrenatalResponse prenatalResponse = response.body();

                    prenatalAdapter = new PrenatalAdapter(
                            PrenatalActivity.this,
                            prenatalResponse.getPatients(),
                            patientPrenatal -> {
                                Intent intent = new Intent(PrenatalActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("name", patientPrenatal.getPatientInfo().getFirstname());
                                intent.putExtra("gender", patientPrenatal.getPatientInfo().getGender());
                                intent.putExtra("age", patientPrenatal.getPatientInfo().getAge());
                                intent.putExtra("phone_number", patientPrenatal.getPatientInfo().getPhone_number());
                                intent.putExtra("recordType", "Prenatal");
                                intent.putParcelableArrayListExtra(
                                        "present_records",
                                        new ArrayList<>(patientPrenatal.getPresentRecords())
                                );
                                intent.putParcelableArrayListExtra(
                                        "past_records",
                                        new ArrayList<>(patientPrenatal.getPastRecords())
                                );
                                startActivity(intent);
                            }
                    );

                    recyclerView.setAdapter(prenatalAdapter);
                } else {
                    Toast.makeText(PrenatalActivity.this, "Failed to load checkups.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PrenatalResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PrenatalActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
