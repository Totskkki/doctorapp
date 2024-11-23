package com.example.myapplication.activity;

import static android.text.format.DateUtils.formatDateTime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;
import com.example.myapplication.viewmodel.Checkup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import api.TimeUtils;
public class PatientDetailsActivity extends AppCompatActivity {
    private TextView patientNameTextView;
    private TextView patientDetailsTextView;
    private TextView patientStatusTextView;
    private LinearLayout presentRecordsLayout;
    private LinearLayout pastRecordsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_information);

        // Initialize views
        patientNameTextView = findViewById(R.id.patientNameTextView);
        patientDetailsTextView = findViewById(R.id.patientDetailsTextView);
        patientStatusTextView = findViewById(R.id.patientStatusTextView);
        presentRecordsLayout = findViewById(R.id.presentRecordsLayout);
        pastRecordsLayout = findViewById(R.id.pastRecordsLayout);

        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> onBackPressed());

        // Get data from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        String age = intent.getStringExtra("age");
        String admittedDate = intent.getStringExtra("admitted");
        ArrayList<Checkup> presentRecords = intent.getParcelableArrayListExtra("present_records");
        Log.d("PatientDetails", "Present records size: " + presentRecords.size());

        ArrayList<Checkup> pastRecords = intent.getParcelableArrayListExtra("past_records");

        // Set patient info
        patientNameTextView.setText(name);
        patientDetailsTextView.setText(gender + " - " + age);
        patientStatusTextView.setText(admittedDate);

        // Populate Present Records
        if (presentRecords != null && !presentRecords.isEmpty()) {
            for (Checkup record : presentRecords) {
                View recordView = createRecordView(record);
                presentRecordsLayout.addView(recordView);
            }
        } else {
            TextView noRecordText = new TextView(this);
            noRecordText.setText("No present records found.");
            presentRecordsLayout.addView(noRecordText);
        }


        // Populate Past Records
        if (pastRecords != null && !pastRecords.isEmpty()) {
            for (Checkup record : pastRecords) {
                View recordView = createRecordView(record);
                pastRecordsLayout.addView(recordView);
            }
        } else {
            TextView noRecordText = new TextView(this);
            noRecordText.setText("No past records found.");
            pastRecordsLayout.addView(noRecordText);
        }
    }

    // Helper method to create a view for each record
    private View createRecordView(Checkup record) {
        LinearLayout recordLayout = new LinearLayout(this);
        recordLayout.setOrientation(LinearLayout.VERTICAL);
        recordLayout.setPadding(16, 16, 16, 16);

        TextView dateView = new TextView(this);
        dateView.setText(String.format("Date: %s", formatDate(record.getAdmitted())));
        dateView.setTextSize(16);
        dateView.setTextColor(getResources().getColor(R.color.black));

        TextView notesView = new TextView(this);
        notesView.setText(String.format("Notes: %s", record.getDoctor_order()));
        notesView.setTextSize(14);
        notesView.setTextColor(getResources().getColor(R.color.gray));

        recordLayout.addView(dateView);
        recordLayout.addView(notesView);

        return recordLayout;
    }
    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;  // Return original if error occurs
        }
    }
}






