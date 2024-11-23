package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.viewmodel.NotificationResponse;
import com.example.myapplication.viewmodel.Notification_list;


public class Notification_activity  extends AppCompatActivity {

    public static final String SHARED_PREFS = "UserPrefs";
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification_list> notificationList = new ArrayList<>();

    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);



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


        fetchNotifications(userID);

    }


    public void fetchNotifications(String userID) {

        // Fix this line: progressBar should be assigned to the ProgressBar, not RecyclerView
        progressBar = findViewById(R.id.progressbarnotification); // This is the correct ProgressBar ID
        recyclerView = findViewById(R.id.notificationsrecycleview); // Correct RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);  // Show progress bar

        // Create the Retrofit API service
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Call the API to get the list of notifications
        Call<NotificationResponse> call = apiService.getNotifications(Integer.parseInt(userID));

        // Make the API call asynchronously
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE); // Hide progress bar once the data is loaded
                    NotificationResponse notificationResponse = response.body();

                    // Check if the response is successful and contains notifications
                    if (notificationResponse.isSuccess()) {
                        // Clear previous notifications
                        notificationList.clear();

                        notificationList.addAll(notificationResponse.getNotifications());

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Notification_activity.this, "No notifications found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("NotificationError", "Response error: " + response.errorBody());
                    Toast.makeText(Notification_activity.this, "Failed to fetch notifications", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Hide progress bar on failure

                Log.e("NotificationError", t.getMessage());
                Toast.makeText(Notification_activity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
            }
        });
    }






}

