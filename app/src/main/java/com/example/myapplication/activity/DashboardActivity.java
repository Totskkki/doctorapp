package com.example.myapplication.activity;



import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.adapter.GridAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.AnnouncementResponse;
import com.example.myapplication.viewmodel.GridItem;
import com.example.myapplication.viewmodel.LogoutResponse;
import com.example.myapplication.viewmodel.NotificationResponse;
import com.example.myapplication.viewmodel.TodaysPatientCountResponse;
import com.example.myapplication.viewmodel.TotalCheckupResponse;
import com.example.myapplication.viewmodel.Totalbirthing;
import com.example.myapplication.viewmodel.TotalbiteResponse;
import com.example.myapplication.viewmodel.Totalimmunization;
import com.example.myapplication.viewmodel.Totalprenatal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Toast;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.myapplication.adapter.AnnouncementAdapter;
import com.google.android.material.navigation.NavigationView;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.facebook.drawee.view.SimpleDraweeView;

public class DashboardActivity extends AppCompatActivity {

    private ImageView profileIcon, notifications;
    private TextView hiDoctor, navHeaderName, navHeaderSpecialty, todaysPatientCount, badge, patientCountTextView,todaysPatientTextView,viewall;

    private RecyclerView gridItemRecycle, recyclerViewAnnouncements;
    private RecyclerView announcements;
    private ProgressBar progressBar , progressbarPatient;
    private List<AnnouncementResponse> announcementList;
    private AnnouncementAdapter announcementAdapter;
    BottomNavigationView nav;
    private DrawerLayout drawerLayout;
    private SimpleDraweeView navHeaderProfilePic;

    private GridAdapter adapter;
    public static final String SHARED_PREFS = "UserPrefs";

    private int totalPatientsToday = 0;
    private int maxPatients = 50;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        profileIcon = findViewById(R.id.profileIcon);
        hiDoctor = findViewById(R.id.hiDoctor);
        todaysPatientCount = findViewById(R.id.todayspatient);
        gridItemRecycle = findViewById(R.id.gridItemrecycle);

        announcements = findViewById(R.id.announcements);

        notifications = findViewById(R.id.notification);
        badge = findViewById(R.id.notification_badge);

        notifications.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, Notification_activity.class);
            startActivity(intent);
        });


        patientCountTextView = findViewById(R.id.patienttxtcount);
        progressbarPatient = findViewById(R.id.patientcount);
        todaysPatientTextView = findViewById(R.id.todayspatient);
        getTodaysPatientCount();




       viewall = findViewById(R.id.viewallannouncements);
       viewall.setOnClickListener(v -> {
           Intent intent = new Intent(DashboardActivity.this, AllAnnouncementsActivity.class);
           startActivity(intent);
       });



//
//        NavigationView navigationView1 = findViewById(R.id.nav_view);
//        View headerView = navigationView1.getHeaderView(0);
//        navHeaderName = headerView.findViewById(R.id.header_doctor_name);
//        navHeaderSpecialty = headerView.findViewById(R.id.header_doctor_specialty);

//
//        AppCompatImageView navHeaderProfilePic = headerView.findViewById(R.id.header_profile_image);


        // Retrieve shared preferences

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String specialty = sharedPreferences.getString("Specialty", "");
        String Email = sharedPreferences.getString("email", "");
        String License = sharedPreferences.getString("LicenseNo","");
        String ProfessionalType = sharedPreferences.getString("ProfessionalType", "");


        // Check if user is logged in
        if (userID.isEmpty() || firstName.isEmpty()) {
            // User not logged in, redirect to LoginActivity
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Set the greeting message
        hiDoctor.setText("Hi! Dr. " + firstName);

        // Load profile picture if available
        if (profilePictureFileName != null && !profilePictureFileName.isEmpty()) {
            String profilePictureUrl = "http://192.168.1.2/websitedeployed/user_images/" + profilePictureFileName;
            Glide.with(this)
                    .load(profilePictureUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .error(R.drawable.doctor) // Optional: error image
                    .into(profileIcon);
        }


        // Set up drawer layout and toggle button for drawer
//        drawerLayout = findViewById(R.id.drawer_layout);
//        profileIcon.setOnClickListener(v -> {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            } else {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

//        NavigationView navigationView = findViewById(R.id.nav_view);
//        MenuItem logoutItem = navigationView.getMenu().findItem(R.id.nav_logout);
//
//        SpannableString s = new SpannableString(logoutItem.getTitle());
//        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
//        logoutItem.setTitle(s);
//
//          // Set the NavigationView item selected listener
//        navigationView.setNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//            if (id == R.id.nav_logout) {
//                logout();
//                return true; // Return true if the item is handled
//            }
//            // Handle other items if needed
//            return false;
//        });




        todaysPatientCount.setText("5 out of 20");


        setupRecyclerViews();
        setupdHomenavigation();
        fetchAnnouncements();
        getNotificationCount();
    }



    public void getNotificationCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Call the API with the user ID to get notification count
        Call<NotificationResponse> call = apiService.getNotificationCount(userID);

        // Make the API call asynchronously
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int notificationCount = response.body().getNotificationCount();
                    int unreadCount = response.body().getUnreadCount();  // Get unread notifications count

                    // Update the notification badge UI
                    setNotificationBadge(notificationCount, unreadCount);
                } else {
                    Log.e("NotificationError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e("NotificationError", t.getMessage());
            }
        });
    }




    private void setNotificationBadge(int count, int unreadCount) {
        badge = findViewById(R.id.notification_badge);
        View redDot = findViewById(R.id.red_dot);

        if (badge != null) {
            if (count > 0) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(count)); // Display total notification count
            } else {
                badge.setVisibility(View.GONE);
            }
        }

        if (redDot != null) {
            if (unreadCount > 0) {
                redDot.setVisibility(View.VISIBLE); // Show the red dot if there are unread notifications
            } else {
                redDot.setVisibility(View.GONE); // Hide the red dot if no unread notifications
            }
        }
    }




    private void setupRecyclerViews() {
        // Initialize the RecyclerView
        announcements = findViewById(R.id.gridItemrecycle);

        // Set the layout manager to horizontal
        announcements.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize the itemList
        List<GridItem> itemList = new ArrayList<>();

        // Add items to the list without the check-up count initially
        itemList.add(new GridItem("Check-up", R.drawable.heart, 0));  // Set initial count to 0
        itemList.add(new GridItem("Animal bite care", R.drawable.animal, 0));
        itemList.add(new GridItem("Prenatal", R.drawable.prenatal, 0));
        itemList.add(new GridItem("Birthing", R.drawable.maternitiy, 0));
        itemList.add(new GridItem("Immunizations", R.drawable.vaccination, 0));

        // Set the adapter with the OnItemClickListener
        adapter = new GridAdapter(this, itemList, item -> {
            // Handle the click event and open activities
            switch (item.getTitle()) {
                case "Check-up":
                    startActivity(new Intent(this, CheckupActivity.class));
                    break;
            case "Animal bite care":
                startActivity(new Intent(this, AnimalBiteCareActivity.class));
                break;
            case "Prenatal":
                startActivity(new Intent(this, PrenatalActivity.class));
                break;
//            case "Birthing":
//                startActivity(new Intent(this, BirthingActivity.class));
//                break;
//            case "Immunizations":
//                startActivity(new Intent(this, ImmunizationsActivity.class));
//                break;
            }
        });

        // Set the adapter to the RecyclerView
        announcements.setAdapter(adapter);

        // Fetch the counts and update the list
        fetchCheckupCounts(itemList);
        fetchbiteCounts(itemList);
        fetchprenatalCounts(itemList);
        fetchbirthingCounts(itemList);
        fetchImmunizationsCounts(itemList);
    }

    private void fetchImmunizationsCounts(List<GridItem> itemList) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<Totalimmunization> call = apiService.getTotalimuunization("total_bite");



        call.enqueue(new Callback<Totalimmunization>() {

            @Override
            public void onResponse(Call<Totalimmunization> call, Response<Totalimmunization> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the total checkups count from the response
                    int biteCount = response.body().getTotalimmunization();

                    // Update the "Check-up" item count
                    UpdateGridCounts("Immunizations", biteCount, itemList);

                    // Notify the adapter to refresh the view with the updated count
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("ImmunizationsError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Totalimmunization> call, Throwable t) {
                Log.e("ImmunizationsError", t.getMessage());
            }
        });
    }
    private void fetchbirthingCounts(List<GridItem> itemList) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<Totalbirthing> call = apiService.getTotalbirthing("total_bite");
        call.enqueue(new Callback<Totalbirthing>() {

            @Override
            public void onResponse(Call<Totalbirthing> call, Response<Totalbirthing> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the total checkups count from the response
                    int biteCount = response.body().getTotal_birthing();

                    // Update the "Check-up" item count
                    UpdateGridCounts("Birthing", biteCount, itemList);

                    // Notify the adapter to refresh the view with the updated count
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("BirthingCountError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Totalbirthing> call, Throwable t) {
                Log.e("BirthingCountError", t.getMessage());
            }
        });
    }
    private void fetchprenatalCounts(List<GridItem> itemList) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<Totalprenatal> call = apiService.getTotalprenatal("total_bite");
        call.enqueue(new Callback<Totalprenatal>() {

            @Override
            public void onResponse(Call<Totalprenatal> call, Response<Totalprenatal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the total checkups count from the response
                    int biteCount = response.body().getTotaltotal_prenatal();

                    // Update the "Check-up" item count
                    UpdateGridCounts("Prenatal", biteCount, itemList);

                    // Notify the adapter to refresh the view with the updated count
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("PrenatalCountError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Totalprenatal> call, Throwable t) {
                Log.e("PrenatalCountError", t.getMessage());
            }
        });
    }
    private void fetchbiteCounts(List<GridItem> itemList) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Call the API with the necessary parameters to get total checkups count
        Call<TotalbiteResponse> call = apiService.getTotalbite("total_bite");

        // Asynchronous API call
        call.enqueue(new Callback<TotalbiteResponse>() {

            @Override
            public void onResponse(Call<TotalbiteResponse> call, Response<TotalbiteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the total checkups count from the response
                    int biteCount = response.body().getTotalbites();

                    // Update the "Check-up" item count
                    UpdateGridCounts("Animal bite care", biteCount, itemList);

                    // Notify the adapter to refresh the view with the updated count
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("biteCountError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TotalbiteResponse> call, Throwable t) {
                Log.e("biteCountError", t.getMessage());
            }
        });
    }
    private void fetchCheckupCounts(final List<GridItem> itemList) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Call the API with the necessary parameters to get total checkups count
        Call<TotalCheckupResponse> call = apiService.getTotalCheckup("total_checkups");

        // Asynchronous API call
        call.enqueue(new Callback<TotalCheckupResponse>() {

            @Override
            public void onResponse(Call<TotalCheckupResponse> call, Response<TotalCheckupResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the total checkups count from the response
                    int checkupCount = response.body().getTotalCheckups();

                    // Update the "Check-up" item count
                    UpdateGridCounts("Check-up", checkupCount, itemList);

                    // Notify the adapter to refresh the view with the updated count
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("CheckupCountError", "Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TotalCheckupResponse> call, Throwable t) {
                Log.e("CheckupCountError", t.getMessage());
            }
        });
    }
    private void UpdateGridCounts(String title, int count, List<GridItem> itemList) {
        // Iterate through the itemList and update the item based on the title
        for (GridItem item : itemList) {
            if (item.getTitle().equals(title)) {
                item.setCount(count);
                break; // Exit once the item is found and updated
            }
        }
    }











    public void setupdHomenavigation() {
        nav = findViewById(R.id.nav);

        // Set the selected item to Settings to make it appear active
        nav.setSelectedItemId(R.id.home);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    return true;
                } else if (item.getItemId() == R.id.search) {
                    startActivity(new Intent(getApplicationContext(), SearchResultsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.calendar) {
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchAnnouncements() {

        progressBar = findViewById(R.id.progressBarannouncements);

        recyclerViewAnnouncements = findViewById(R.id.announcements);
        recyclerViewAnnouncements.setLayoutManager(new LinearLayoutManager(this));


        announcementList = new ArrayList<>();
        announcementAdapter = new AnnouncementAdapter(announcementList);
        recyclerViewAnnouncements.setAdapter(announcementAdapter);

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
                        Toast.makeText(DashboardActivity.this, "Response body or announcements list is null", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(DashboardActivity.this, "Failed to load announcements", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(DashboardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//===================================================================================================

    private void getTodaysPatientCount() {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<TodaysPatientCountResponse> call = apiService.getTodaysPatientCount("patientcounttoday");

        call.enqueue(new Callback<TodaysPatientCountResponse>() {
            @Override
            public void onResponse(Call<TodaysPatientCountResponse> call, Response<TodaysPatientCountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    totalPatientsToday = response.body().getToday_patient_count();
                    patientCountTextView.setText(String.valueOf(totalPatientsToday));

                    updateProgressBar(totalPatientsToday);
                }
            }

            @Override
            public void onFailure(Call<TodaysPatientCountResponse> call, Throwable t) {
                // Handle error
            }
        });
    }


    private void updateProgressBar(int patientVisitCount) {
        // Update patient count text
        patientCountTextView.setText(String.valueOf(patientVisitCount));

        String patientCountText = patientVisitCount + " out of " + maxPatients;
        todaysPatientTextView.setText(patientCountText);
        // Calculate the percentage progress (ensure no division by zero)
        int progress = 0;
        if (maxPatients > 0) {
            progress = (int) ((patientVisitCount / (float) maxPatients) * 100);
        }

        // Set the progress in the ProgressBar
        progressbarPatient.setProgress(progress);
    }



//    private void logout() {
//        // Get the Retrofit instance and create the ApiService
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        // Get the userID to send to the server
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        String userID = sharedPreferences.getString("userID", "");
//        // Make the API call to logout the user
//        Call<LogoutResponse> call = apiService.logoutUser(userID);
//
//        // Handle the API response asynchronously
//        call.enqueue(new Callback<LogoutResponse>() {
//            @Override
//            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    LogoutResponse logoutResponse = response.body();
//                    if (logoutResponse.isSuccess()) {
//                        // Successfully logged out, proceed with clearing session
//                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.apply();
//
//                        // Redirect to login activity
//                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish(); // End the dashboard activity
//                    } else {
//                        // Handle the error
//                        Toast.makeText(DashboardActivity.this, "Logout failed: " + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(DashboardActivity.this, "Error: Could not log out", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LogoutResponse> call, Throwable t) {
//                // Handle request failure
//                Toast.makeText(DashboardActivity.this, "Logout failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}

