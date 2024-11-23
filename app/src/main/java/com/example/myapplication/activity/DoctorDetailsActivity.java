package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

public class DoctorDetailsActivity extends AppCompatActivity {
    private TextView nameTextView,middleNameView,lastNameView,emailTextView,specialtyTextView,license_no,proftype;
    private ImageView profileIcon;
    public static final String SHARED_PREFS = "UserPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_details);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());



        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Fetch data from SharedPreferences
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String email = sharedPreferences.getString("email", "");
        String specialty = sharedPreferences.getString("Specialty", "");
        String licenseNo = sharedPreferences.getString("LicenseNo", "");
        String professionalType = sharedPreferences.getString("ProfessionalType", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String address = sharedPreferences.getString("address", "");

        // Set up UI components
        TextView firstnameTextView = findViewById(R.id.firstNameEditText);
        TextView middlenameTextView = findViewById(R.id.middlenameText);
        TextView lastnameTextView = findViewById(R.id.lastNameEditText);
        TextView emailTextView = findViewById(R.id.emailText);
        TextView addressTextView = findViewById(R.id.Address);
        TextView specialtyTextView = findViewById(R.id.specialty);
        TextView licenseNoTextView = findViewById(R.id.licenseNo);
        TextView professionalTypeTextView = findViewById(R.id.profType);
        ImageView profileIcon = findViewById(R.id.doctorProfileImage);

        firstnameTextView.setText(firstName);
        middlenameTextView.setText(middleName);
        lastnameTextView.setText(lastName);
        emailTextView.setText(email);
        addressTextView.setText(address);
        specialtyTextView.setText(specialty);
        licenseNoTextView.setText(licenseNo);
        professionalTypeTextView.setText(professionalType);

        if (profilePictureFileName != null && !profilePictureFileName.isEmpty()) {
            String profilePictureUrl = "http://192.168.1.2/websitedeployed/user_images/" + profilePictureFileName;
            Glide.with(this)
                    .load(profilePictureUrl)
                    .apply(RequestOptions.circleCropTransform()) // This will crop the image into a circle
                    .error(R.drawable.doctor) // Set a fallback image in case of error
                    .into(profileIcon); // Set the image into the ImageView
        }





    }

}