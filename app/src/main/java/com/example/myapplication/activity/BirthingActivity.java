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
import com.example.myapplication.adapter.BirthingAdapter;
import com.example.myapplication.adapter.BiteAdapter;
import com.example.myapplication.adapter.PrenatalAdapter;
import com.example.myapplication.viewmodel.animalbite.Bite;
import com.example.myapplication.viewmodel.birthing.BirthingResponse;
import com.example.myapplication.viewmodel.birthing.birthing;
import com.example.myapplication.viewmodel.prenatal.PrenatalResponse;

import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BirthingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BirthingAdapter birthingAdapter;

    public static final String SHARED_PREFS = "UserPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthing);

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
        fetchbirthList(Integer.parseInt(userID));

    }

    private void fetchbirthList(int i) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<BirthingResponse> call = apiService.getBirthinglList();

        call.enqueue(new Callback<BirthingResponse>() {
            @Override
            public void onResponse(Call<BirthingResponse> call, Response<BirthingResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    BirthingResponse birthingResponse = response.body();

                    birthingAdapter = new BirthingAdapter(
                            BirthingActivity.this,
                            birthingResponse.getPatients(),
                            patientBIrthing -> {
                                Intent intent = new Intent(BirthingActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("name", patientBIrthing.getPatientInfo().getFirstname());
                                intent.putExtra("gender", patientBIrthing.getPatientInfo().getGender());
                                intent.putExtra("age", patientBIrthing.getPatientInfo().getAge());
                                intent.putExtra("phone_number", patientBIrthing.getPatientInfo().getPhone_number());
                                intent.putExtra("recordType", "Birthing");
                                intent.putParcelableArrayListExtra(
                                        "present_records",
                                        new ArrayList<>(patientBIrthing.getPresentRecords())
                                );
                                intent.putParcelableArrayListExtra(
                                        "past_records",
                                        new ArrayList<>(patientBIrthing.getPastRecords())
                                );
                                startActivity(intent);
                            }
                    );

                    recyclerView.setAdapter(birthingAdapter);
                } else {
                    Toast.makeText(BirthingActivity.this, "Failed to load checkups.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BirthingResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BirthingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
