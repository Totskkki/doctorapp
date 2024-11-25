package com.example.myapplication.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import com.example.myapplication.viewmodel.EditProfileBottomSheet;
import com.example.myapplication.viewmodel.LogoutResponse;
import com.example.myapplication.viewmodel.UpdatePassword;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SettingsActivity extends AppCompatActivity {

    BottomNavigationView nav;
    private TextView appVersionTextView;
    private TextView logouttext;
    private RelativeLayout view;

    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "UserPrefs";
    private ImageView profileIcon, userview;
    private TextView hiDoctor, email,changePasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Set up UI elements
        appVersionTextView = findViewById(R.id.appVersionTextView);
        logouttext = findViewById(R.id.logout);
        nav = findViewById(R.id.nav);
        profileIcon = findViewById(R.id.profileCircleImageView);
        hiDoctor = findViewById(R.id.usernameTextView);
        email = findViewById(R.id.email);
        changePasswordText = findViewById(R.id.changepassword);
        view = findViewById(R.id.viewall);

        // Set version
        setAppVersion();

        // Logout on click
        logouttext.setOnClickListener(view -> {
            // Set the background color animation
            ObjectAnimator animator = ObjectAnimator.ofArgb(
                    logouttext,
                    "backgroundColor",
                    getResources().getColor(android.R.color.transparent), // Start color
                    getResources().getColor(android.R.color.darker_gray)  // End color
            );

            animator.setDuration(200); // Animation duration in milliseconds
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(1); // Reverse back to original color
            animator.start();

            // Perform the logout action
            logout();
        });


        // Navigation setup
        setUpBottomNavigation();

        // Change password click listener
        changePasswordText.setOnClickListener(v -> showChangePasswordDialog());

        // Retrieve user data from SharedPreferences
        loadUserData();

        // Set up profile edit listener
        setUpProfileEditListener();

        // Profile icon loading
        loadProfileIcon();


        logouttext = findViewById(R.id.logout);
        logouttext.setOnClickListener(view -> logout());




        profileIcon = findViewById(R.id.profileCircleImageView);

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
        String personnel_id = sharedPreferences.getString("personnel_id", "");
        String position_id = sharedPreferences.getString("position_id", "");
        String address = sharedPreferences.getString("address", "");

        if (userID.isEmpty()) {
            Log.e("SharedPreferences Error", "User ID is not set in SharedPreferences");
            return;
        }



        TextView editProfileTextView = findViewById(R.id.editProfileTextView);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

                // Retrieve SharedPreferences data
                String userID = sharedPreferences.getString("userID", "");
                String firstName = sharedPreferences.getString("first_name", "");
                String middleName = sharedPreferences.getString("middlename", "");
                String lastName = sharedPreferences.getString("lastname", "");
                String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
                String specialty = sharedPreferences.getString("Specialty", "");
                String email = sharedPreferences.getString("email", "");
                String licenseNo = sharedPreferences.getString("LicenseNo", "");
                String professionalType = sharedPreferences.getString("ProfessionalType", "");
                String address = sharedPreferences.getString("address", "");
                String personnel_id = sharedPreferences.getString("personnel_id", "");
                String position_id = sharedPreferences.getString("position_id", "");

                // Create a new instance of the EditProfileBottomSheet with arguments
                EditProfileBottomSheet bottomSheet = EditProfileBottomSheet.newInstance(userID, firstName, middleName, lastName,
                        profilePictureFileName, specialty, email, licenseNo, professionalType,address,personnel_id,position_id);
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });



        hiDoctor = findViewById(R.id.usernameTextView);
        hiDoctor.setText(firstName + " " +middleName +  " " + lastName);
        email = findViewById(R.id.email);
        email.setText(Email);


        if (profilePictureFileName != null && !profilePictureFileName.isEmpty()) {
            String profilePictureUrl = "http://192.168.1.2/websitedeployed/user_images/" + profilePictureFileName;
            Glide.with(this)
                    .load(profilePictureUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .error(R.drawable.doctor)
                    .into(profileIcon);
        }


        view = findViewById(R.id.viewall);

        view.setOnClickListener(v -> {

            Intent intent = new Intent(SettingsActivity.this, DoctorDetailsActivity.class);


            startActivity(intent);
        });
    }

    private void setUpProfileEditListener() {
        TextView editProfileTextView = findViewById(R.id.editProfileTextView);
        editProfileTextView.setOnClickListener(v -> {
            String userID = sharedPreferences.getString("userID", "");
            String firstName = sharedPreferences.getString("first_name", "");
            String middleName = sharedPreferences.getString("middlename", "");
            String lastName = sharedPreferences.getString("lastname", "");
            String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
            String specialty = sharedPreferences.getString("Specialty", "");
            String email = sharedPreferences.getString("email", "");
            String licenseNo = sharedPreferences.getString("LicenseNo", "");
            String professionalType = sharedPreferences.getString("ProfessionalType", "");
            String address = sharedPreferences.getString("address", "");
            String personnel_id = sharedPreferences.getString("personnel_id", "");
            String position_id = sharedPreferences.getString("position_id", "");

            // Open Edit Profile bottom sheet
            EditProfileBottomSheet bottomSheet = EditProfileBottomSheet.newInstance(userID, firstName, middleName, lastName,
                    profilePictureFileName, specialty, email, licenseNo, professionalType, address, personnel_id, position_id);
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });
    }

    private void loadUserData() {
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String emailVal = sharedPreferences.getString("email", "");
        String profilePictureUrl = sharedPreferences.getString("profile_picture", "");


        // Set UI elements based on SharedPreferences data
        hiDoctor.setText(firstName + " " + middleName + " " + lastName);
        email.setText(emailVal);
    }

    private void setUpBottomNavigation() {

        nav = findViewById(R.id.nav);
        // Set the selected item to Settings to make it appear active
        nav.setSelectedItemId(R.id.settings);

        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.search) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.calendar) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.settings) {
                return true;
            }
            return false;
        });

    }

    private void setAppVersion() {
        appVersionTextView = findViewById(R.id.appVersionTextView);
        try {
            // Get the package manager and package info
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName; // Get the version name (e.g., "1.0")
            appVersionTextView.setText("App ver " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersionTextView.setText("App version not available");
        }

    }

    private void loadProfileIcon() {
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        if (profilePictureFileName != null && !profilePictureFileName.isEmpty()) {
            String profilePictureUrl = "http://192.168.1.2/websitedeployed/user_images/" + profilePictureFileName;
            Glide.with(this)
                    .load(profilePictureUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Prevent Glide from using cache
                    .skipMemoryCache(true)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .error(R.drawable.doctor)
                    .into(profileIcon);
        }
    }



    private void showChangePasswordDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userIDString = sharedPreferences.getString("userID", ""); // Fetch userID from SharedPreferences

        if (userIDString.isEmpty()) {
            Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert userID to integer
        int userID;
        try {
            userID = Integer.parseInt(userIDString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid User ID. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_changepassword, null);

        // Initialize the BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        // Reference EditTexts and Button
        EditText username = view.findViewById(R.id.username);
        EditText newPassword = view.findViewById(R.id.newPassword);
        EditText confirmPassword = view.findViewById(R.id.confirmPassword);
        Button changePasswordButton = view.findViewById(R.id.changePasswordButton);

        // Handle button click
        changePasswordButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();

            String newPass = newPassword.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();

            // Validation checks
            if (user.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isPasswordValid(newPass)) {
                newPassword.setError("Password must be at least 6 characters and include letters, numbers, and special characters (!$@%).");
                return;
            }

            if (!newPass.equals(confirm)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }

            // Call the API to update the password
            updatePassword(userID,user, newPass, confirm);

            // Dismiss the dialog
            bottomSheetDialog.dismiss();
        });

        // Show the BottomSheetDialog
        bottomSheetDialog.show();
    }

    // Helper function to validate password
    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!$@%])[A-Za-z\\d!$@%]{6,}$";
        return password.matches(passwordPattern);
    }


    public void updatePassword(int userID,String username, String newPassword, String confirmPassword) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<UpdatePassword> call = apiService.updatePassword(userID,username, newPassword, confirmPassword);
        call.enqueue(new Callback<UpdatePassword>() {
            @Override
            public void onResponse(Call<UpdatePassword> call, Response<UpdatePassword> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdatePassword result = response.body();
                    if (result.getSuccess()) {
                        Toast.makeText(getApplicationContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update password. Try again.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UpdatePassword> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void logout() {
        // Get the Retrofit instance and create the ApiService
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Get the userID from SharedPreferences to send to the server
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");

        if (userID.isEmpty()) {
            // Handle the case where userID is empty (session invalid, should not happen in most cases)
            Toast.makeText(SettingsActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
            Log.e("SharedPreferences Error", "User ID is not set in SharedPreferences" + userID );
            return;
        }

        // Make the API call to log out the user
        Call<LogoutResponse> call = apiService.logoutUser(userID);

        // Handle the API response asynchronously
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LogoutResponse logoutResponse = response.body();
                    if (logoutResponse.isSuccess()) {
                        // Successfully logged out, clear session data
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Clear all session data
                        editor.apply(); // Apply the changes

                        // Redirect to Login Activity
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Finish the current activity to prevent going back to it
                    } else {
                        // Handle the error in the API response
                        Toast.makeText(SettingsActivity.this, "Logout failed: " + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful API response
                    Toast.makeText(SettingsActivity.this, "Error: Could not log out", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                // Handle the failure case for the API request
                Toast.makeText(SettingsActivity.this, "Logout failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String emailVal = sharedPreferences.getString("email", "");
        String profilePictureUrl = sharedPreferences.getString("profile_picture", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String specialty = sharedPreferences.getString("Specialty", "");
        String licenseNo = sharedPreferences.getString("LicenseNo", "");
        String professionalType = sharedPreferences.getString("ProfessionalType", "");
        String address = sharedPreferences.getString("address", "");
        String personnel_id = sharedPreferences.getString("personnel_id", "");
        String position_id = sharedPreferences.getString("position_id", "");


        // Set UI elements based on SharedPreferences data
        hiDoctor.setText(firstName + " " + middleName + " " + lastName);
        email.setText(emailVal);


        // Update Profile Picture
        if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {

            String profilePictureUrlWithTimestamp = profilePictureUrl + "?timestamp=" + System.currentTimeMillis();
            Glide.with(this)
                    .load(profilePictureUrlWithTimestamp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .error(R.drawable.doctor)
                    .into(profileIcon);

        }
    }

}
