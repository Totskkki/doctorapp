package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AnnouncementAdapter;
import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.viewmodel.AnnouncementResponse;

import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAnnouncementsActivity extends AppCompatActivity {


    public static final String SHARED_PREFS = "UserPrefs";
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private ProgressBar progressBar;
    private List<AnnouncementResponse> announcementList;
    private AnnouncementAdapter announcementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_announcements);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String specialty = sharedPreferences.getString("Specialty", "");
        String Email = sharedPreferences.getString("email", "");


        fetchAnnouncements();



    }

    private void fetchAnnouncements() {


        progressBar = findViewById(R.id.progressbarnotification);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        announcementList = new ArrayList<>();
        announcementAdapter = new AnnouncementAdapter(announcementList);
        recyclerView.setAdapter(announcementAdapter);

        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AnnouncementResponse> call = apiService.getAnnouncements("announcements");

        call.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    if (response.body() != null && response.body().getAnnouncements() != null) {

                        announcementList.clear();
                        announcementList.addAll(response.body().getAnnouncements());

                        // Notify the adapter about data changes
                        announcementAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(AllAnnouncementsActivity.this, "Response body or announcements list is null", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(AllAnnouncementsActivity.this, "Failed to load announcements", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(AllAnnouncementsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


