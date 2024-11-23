package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.LoginResponse;

import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginButton;
    private ProgressBar progressBar;
    private CheckBox rememberMeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        togglePassword();


        // Initialize views
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        rememberMeCheckbox = findViewById(R.id.checkbox);

        // Check if user is already logged in
        checkLoginStatus();

        // Load saved credentials if "Remember Me" was checked
        loadCredentials();

        // Set login button click listener
        loginButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            // Call the login function
            loginUser(username, password);
        });
    }

    // Method to check if user is already logged in
    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("remember_me", false);

        if (isLoggedIn) {
            // If user is already logged in, go to DashboardActivity
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Finish LoginActivity
        }
    }


    private void loadCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        usernameField.setText(savedUsername);
        passwordField.setText(savedPassword);
    }

    private void loginUser(String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar and hide the login button
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        // Call the API using Retrofit
        LoginResponse.ApiService apiService = RetrofitClient.getRetrofitInstance().create(LoginResponse.ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        // Save user details in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        // Save "Remember Me" status
                        editor.putBoolean("remember_me", rememberMeCheckbox.isChecked());

                        // Save credentials if "Remember Me" is checked
                        if (rememberMeCheckbox.isChecked()) {
                            editor.putString("username", username);
                            editor.putString("password", password);
                        } else {
                            // Clear saved credentials if not checked
                            editor.remove("username");
                            editor.remove("password");
                        }

                        // Save additional user details
                        editor.putString("userID", loginResponse.getUserID());
                        editor.putString("first_name", loginResponse.getFirst_name());
                        editor.putString("middlename", loginResponse.getMiddle_name());
                        editor.putString("lastname", loginResponse.getLast_name());
                        editor.putString("profile_picture", loginResponse.getProfilePicture());
                        editor.putString("Specialty", loginResponse.getSpecialty());
                        editor.putString("LicenseNo", loginResponse.getLicenseno());
                        editor.putString("email", loginResponse.getEmail());
                        editor.putString("ProfessionalType",loginResponse.getProfessionalType());
                        editor.putString("address",loginResponse.getAddress());
                        editor.putString("personnel_id",loginResponse.getPersonnel_id());
                        editor.putString("position_id",loginResponse.getPosition_id());
                        editor.apply();

                        // Start DashboardActivity
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: Unable to connect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void togglePassword() {
        EditText passwordEditText = findViewById(R.id.password);
        passwordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = passwordEditText.getCompoundDrawables()[2]; // Get the end drawable (eye icon)
                if (drawableEnd != null) {
                    int drawableWidth = drawableEnd.getBounds().width();
                    // Check if the user tapped on the eye icon
                    if (event.getRawX() >= (passwordEditText.getRight() - drawableWidth - passwordEditText.getPaddingEnd())) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0); // Change to eye icon
                            } else {
                                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eyeoff, 0); // Change to eye off icon
                            }
                            passwordEditText.setSelection(passwordEditText.getText().length()); // Keep the cursor at the end
                        }
                        return true;
                    }
                }
            }
            return false; // Otherwise, let the event propagate
        });
    }

    private Drawable resizeDrawable(int drawableId, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height); // Set bounds for the drawable
        }
        return drawable;
    }
}
