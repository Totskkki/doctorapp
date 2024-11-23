package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.ApplyLeaveBottomSheetDialog;
import com.example.myapplication.viewmodel.LeaveRequest;
import com.example.myapplication.viewmodel.LeaveResponse;
import com.example.myapplication.viewmodel.applyforleave;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import api.ApiService;
import api.DialogUtils;

import api.ResultDialogFragment;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDoctorScheduleActivity extends AppCompatActivity implements ApplyLeaveBottomSheetDialog.ApplyLeaveListener {

    private TextView fromTimeTextView, toTimeTextView, worklengthTextView, date;
    public static final String SHARED_PREFS = "UserPrefs";
    private Button applyLeaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleinfo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());



        fromTimeTextView = findViewById(R.id.fromTimeTextView);
        toTimeTextView = findViewById(R.id.toTimeTextView);
        worklengthTextView = findViewById(R.id.worklengthTextView);
        date = findViewById(R.id.datestring);


        // Set TextViews with schedule data
        String fromTime = getIntent().getStringExtra("from_time");
        String toTime = getIntent().getStringExtra("to_time");
        String workLength = getIntent().getStringExtra("work_length");
        String selectedDate = getIntent().getStringExtra("selected_date");


        fromTimeTextView.setText(fromTime);
        toTimeTextView.setText(toTime);
        worklengthTextView.setText(workLength);
        date.setText(selectedDate);



        applyLeaveButton = findViewById(R.id.applyLeaveButton);
        applyLeaveButton.setOnClickListener(v -> showApplyLeaveBottomSheet());
    }

    private void showApplyLeaveBottomSheet() {
        ApplyLeaveBottomSheetDialog bottomSheet = new ApplyLeaveBottomSheetDialog();

        // Pass the date and time values to the dialog
        Bundle args = new Bundle();
        args.putString("from_time", fromTimeTextView.getText().toString());
        args.putString("to_time", toTimeTextView.getText().toString());
        args.putString("leave_date", date.getText().toString());
        bottomSheet.setArguments(args);

        bottomSheet.show(getSupportFragmentManager(), "ApplyLeaveBottomSheetDialog");
    }

    @Override
    public void onLeaveApplied(String reason, String leaveDate, String startTime, String endTime) {
        String formattedDate = convertDateForBackend(leaveDate); // Format the date here

        if (formattedDate != null) {
            sendLeaveRequest(reason, formattedDate, startTime, endTime);
        } else {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendLeaveRequest(String reason, String leaveDate, String startTime, String endTime) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");

        if (userID.isEmpty()) {
            Log.e("SharedPreferences Error", "User ID is not set in SharedPreferences");
            return;
        }

        // Check for a duplicate request in SharedPreferences
        String lastLeaveDate = sharedPreferences.getString("lastLeaveDate", "");
        String lastStartTime = sharedPreferences.getString("lastStartTime", "");
        String lastEndTime = sharedPreferences.getString("lastEndTime", "");

        // If the last request matches the current one, show an error and return
        if (leaveDate.equals(lastLeaveDate) && startTime.equals(lastStartTime) && endTime.equals(lastEndTime)) {
            Toast.makeText(this, "Duplicate leave request. Please choose a different date or time.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update SharedPreferences with the new request
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastLeaveDate", leaveDate);
        editor.putString("lastStartTime", startTime);
        editor.putString("lastEndTime", endTime);
        editor.apply();

        // Continue with your existing code to make the API call
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LeaveResponse> call = apiService.applyLeave("applyForLeave", Integer.parseInt(userID), leaveDate, reason, startTime, endTime);

        call.enqueue(new Callback<LeaveResponse>() {
            @Override
            public void onResponse(Call<LeaveResponse> call, Response<LeaveResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    LeaveResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        String message = leaveResponse.getMessage();
                        if (leaveResponse.isSuccess()) {
                            if (!isFinishing() && !isDestroyed()) {
                                ResultDialogFragment dialog = ResultDialogFragment.newInstance("Success", "Leave applied successfully!", true);
                                dialog.show(getSupportFragmentManager(), "resultDialog");
                                Intent intent = new Intent(ViewDoctorScheduleActivity.this, CalendarActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorResponse = response.errorBody().string();
                            Log.e("LeaveRequestError", "Error body: " + errorResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ResultDialogFragment dialog = ResultDialogFragment.newInstance("Failed", "Failed to submit leave request. Please try again", true);
                    dialog.show(getSupportFragmentManager(), "resultDialog");
                }
            }

            @Override
            public void onFailure(Call<LeaveResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String convertDateForBackend(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);  // Returns "2024-11-27"
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("DateError", "Date format error: " + dateStr);
            return null;
        }
    }



}
