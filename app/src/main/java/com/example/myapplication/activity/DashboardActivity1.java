//package com.example.myapplication.activity;
//
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.R;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//import com.google.android.material.navigation.NavigationView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.myapplication.adapter.AnnouncementAdapter;
//import api.AnnouncementResponse;
//import api.ApiService;
//import api.DoctorSchedule;
//import api.LogoutResponse;
//import api.RetrofitClient;
//import api.StringUtils;
//import api.TimeUtils;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DashboardActivity1 extends AppCompatActivity {
//
//    private TextView welcomeMessage, userid,upcomingTime, upcomingDate, upcomingLocation,EditText;
//    private ApiService apiService;
//    private DrawerLayout drawerLayout;
//    private SimpleDraweeView profileImageView; // Change to SimpleDraweeView
//    private TextView navHeaderName,navHeaderSpecialty;
//    private SimpleDraweeView navHeaderProfilePic; // Change to SimpleDraweeView
//
//    private RecyclerView recyclerViewAnnouncements,recyclerView;
//    private AnnouncementAdapter announcementAdapter;
//    private List<AnnouncementResponse> announcementList;
//    BottomNavigationView nav;
//    private List<TimeUtils.GridItem> itemList;
//    private TimeUtils.GridAdapter adapter;
//    private ProgressBar progressBar;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Fresco.initialize(this);
//        setContentView(R.layout.dashboard_test);
//
//        progressBar = findViewById(R.id.progressBar);
//
//        recyclerView = findViewById(R.id.gridItemrecycle);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//
//        itemList = new ArrayList<>();
//        itemList.add(new TimeUtils.GridItem("Today's Patient",R.drawable.doctor));
//        itemList.add(new TimeUtils.GridItem("Check-up",R.drawable.doctor));
//        itemList.add(new TimeUtils.GridItem("Animal bite care",R.drawable.doctor));
//        itemList.add(new TimeUtils.GridItem("Prenatal",R.drawable.doctor));
//        itemList.add(new TimeUtils.GridItem("Birthing",R.drawable.doctor));
//        itemList.add(new TimeUtils.GridItem("Immunizations",R.drawable.doctor));
//
//        adapter = new TimeUtils.GridAdapter(this, itemList);
//        recyclerView.setAdapter(adapter);
//
//
//
//
//
//
//
//
//
//
//
//
//
////=========================star Navbar bottom================================
//        nav = findViewById(R.id.nav);
//
//        // Set the selected item to Settings to make it appear active
//        nav.setSelectedItemId(R.id.home);
//
//        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.home) {
//
//                    return true;
//                } else if (item.getItemId() == R.id.search) {
//                    startActivity(new Intent(getApplicationContext(), SearchResultsActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                } else if (item.getItemId() == R.id.calendar) {
//                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                } else if (item.getItemId() == R.id.settings) {
//                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                return false;
//            }
//        });
//
////=============================start Search===================================
//
//
//
//
//
////=============================DOCTORS SCHEDULE======================================
////        upcomingTime = findViewById(R.id.upcomingTime);
////        upcomingDate = findViewById(R.id.upcomingDate);
////        upcomingLocation = findViewById(R.id.upcomingLocation);
////====================================================================
//
//        // Initialize your views
//        drawerLayout = findViewById(R.id.drawer_layout);
//        welcomeMessage = findViewById(R.id.hiDoctor);
////        userid = findViewById(R.id.userid);
////        profileImageView = findViewById(R.id.profileIcon); // Change to SimpleDraweeView
//        profileImageView = findViewById(R.id.profileIcon);
//
//        NavigationView navigationView1 = findViewById(R.id.nav_view);
//        View headerView = navigationView1.getHeaderView(0);
//
//        navHeaderName = headerView.findViewById(R.id.header_doctor_name);
//        navHeaderSpecialty = headerView.findViewById(R.id.header_doctor_specialty);
//        navHeaderProfilePic = headerView.findViewById(R.id.header_profile_image);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        String userID = sharedPreferences.getString("userID", "");
//        String firstName = sharedPreferences.getString("first_name", "");
//        String middleName = sharedPreferences.getString("middlename", "");
//        String lastName = sharedPreferences.getString("lastname", "");
//        String profilePicture = sharedPreferences.getString("profile_picture", "");
//
//        String Specialty = sharedPreferences.getString("Specialty", "");
//        Log.d("ProfilePicture", "URI: " + profilePicture + "Userid"  + userID);
//
//
//        navHeaderName.setText(firstName + " " + middleName + " " + lastName);
//        navHeaderSpecialty.setText(Specialty);
//
//
//        if (!profilePicture.isEmpty()) {
//            // Use Fresco to load the profile picture
//            navHeaderProfilePic.setImageURI("http://10.0.2.2/doctor_schedulingPHP/user_images/"+(profilePicture));
//            profileImageView.setImageURI("http://10.0.2.2/doctor_schedulingPHP/user_images/"+(profilePicture));
//
//
//        }
//        else {
//            // Optionally set a placeholder or default image if no picture is available
//            navHeaderProfilePic.setImageURI(Uri.parse("res:///" + R.drawable.doctor)); // Placeholder image
//        }
//
//        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        // Fetch the doctor's schedule using Retrofit
//        fetchDoctorSchedule(userID);
//
//
//        // Retrieve data from Intent
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");
//        String userIDs = intent.getStringExtra("userID");
//
//        // Set welcome message and user ID
//        welcomeMessage.setText(username != null ? "Hi!, Dr. " + firstName + " " + middleName + " " + lastName + "," : "Hi, Guest!");
////        userid.setText(userIDs);
//
//        // Handle profile icon click to open/close drawer
//        findViewById(R.id.profileIcon).setOnClickListener(v -> {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            } else {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//        // Set the logout menu item text color to red
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        MenuItem logoutItem = navigationView.getMenu().findItem(R.id.nav_logout);
//        SpannableString s = new SpannableString(logoutItem.getTitle());
//        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
//        logoutItem.setTitle(s);
//
//        // Set the NavigationView item selected listener
//        navigationView.setNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//            if (id == R.id.nav_logout) {
//                logout();
//                return true; // Return true if item is handled
//            }
//            return false; // Return false for unhandled items
//        });
//
////        ImageView calendarIcon = findViewById(R.id.calendar);
////        calendarIcon.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // Start the CalendarActivity
////                Intent intent = new Intent(DashboardActivity.this, CalendarActivity.class);
////                startActivity(intent);
////            }
////        });
//
//
//
//
//        progressBar = findViewById(R.id.progressBar);
//        recyclerViewAnnouncements = findViewById(R.id.announcements);
//        recyclerViewAnnouncements.setLayoutManager(new LinearLayoutManager(this));
//
//
//        announcementList = new ArrayList<>();
//        announcementAdapter = new AnnouncementAdapter(announcementList);
//        recyclerViewAnnouncements.setAdapter(announcementAdapter);
//        fetchAnnouncements();
//
//
//
//    }
//    private void fetchAnnouncements() {
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//        Call<AnnouncementResponse> call = apiService.getAnnouncements("announcements");
//
//        call.enqueue(new Callback<AnnouncementResponse>() {
//            @Override
//            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
//
//                if (response.isSuccessful()) {
//                    progressBar.setVisibility(View.GONE);
//
//                    if (response.body() != null && response.body().getAnnouncements() != null) {
//
//                        announcementList.clear();
//                        announcementList.addAll(response.body().getAnnouncements());
//
//                        // Notify the adapter about data changes
//                        announcementAdapter.notifyDataSetChanged();
//
//                    } else {
//                        Toast.makeText(DashboardActivity1.this, "Response body or announcements list is null", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//
//                    Toast.makeText(DashboardActivity1.this, "Failed to load announcements", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//
//                Toast.makeText(DashboardActivity1.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//
//
//
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
//                        Intent intent = new Intent(DashboardActivity1.this, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish(); // End the dashboard activity
//                    } else {
//                        // Handle the error
//                        Toast.makeText(DashboardActivity1.this, "Logout failed: " + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(DashboardActivity1.this, "Error: Could not log out", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LogoutResponse> call, Throwable t) {
//                // Handle request failure
//                Toast.makeText(DashboardActivity1.this, "Logout failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void fetchDoctorSchedule(String userID) {
//        // Make the API call
//        Call<List<DoctorSchedule>> call = apiService.getDoctorSchedule(userID);
//        call.enqueue(new Callback<List<DoctorSchedule>>() {
//            @Override
//            public void onResponse(Call<List<DoctorSchedule>> call, Response<List<DoctorSchedule>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<DoctorSchedule> schedules = response.body();
//
//                    if (!schedules.isEmpty()) {
//                        // Display the first schedule (you can loop through if needed)
//                        DoctorSchedule schedule = schedules.get(0);
//
//                        // Convert start and end times to AM/PM format if available
//                        String startTimeAmPm = schedule.getStartTime() != null ? TimeUtils.convertToAmPm(schedule.getStartTime()) : "No time available";
//                        String endTimeAmPm = schedule.getEndTime() != null ? TimeUtils.convertToAmPm(schedule.getEndTime()) : "";
//
//                        // Set the formatted time in the TextViews
//                        upcomingTime.setText(startTimeAmPm + (endTimeAmPm.isEmpty() ? "" : " - " + endTimeAmPm));
//
//                        // Set the date if available
//                        if (schedule.getScheduled_date() != null) {
//                            upcomingDate.setText(schedule.getFormattedScheduledDate()); // Use your formatting function
//                        } else {
//                            upcomingDate.setText("No date scheduled");
//                        }
//
//                        // Set the location if available
//                        if (schedule.getLocation() != null && !schedule.getLocation().isEmpty()) {
//                            upcomingLocation.setText(schedule.getLocation());
//                        } else {
//                            upcomingLocation.setText("Location not set");
//                        }
//                        if (schedule.getLocation() != null && !schedule.getLocation().isEmpty()) {
//                            upcomingLocation.setText(StringUtils.capitalizeWords(schedule.getLocation()));  // Use helper class method
//                        } else {
//                            upcomingLocation.setText("Location not set");
//                        }
//
//                    } else {
//                        // Handle when no schedules are found
//                        Log.d("ScheduleFetch", "No schedules found");
//                        upcomingTime.setText("No time available");
//                        upcomingDate.setText("No date scheduled");
//                        upcomingLocation.setText("");
//                    }
//                } else {
//                    // Handle an unsuccessful response
//                    Log.e("ScheduleFetch", "Response unsuccessful or empty");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DoctorSchedule>> call, Throwable t) {
//                // Handle the failure scenario
//                Log.e("ScheduleFetch", "Error fetching schedule: " + t.getMessage());
//            }
//        });
//    }
//
//
//
//}
