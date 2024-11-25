package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;
import com.example.myapplication.viewmodel.Checkup;
import com.example.myapplication.viewmodel.animalbite.Bite;
import com.example.myapplication.viewmodel.prenatal.Prenatal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        String recordType = intent.getStringExtra("recordType");


        if ("Checkup".equals(recordType)) {
            ArrayList<Checkup> presentRecords = intent.getParcelableArrayListExtra("present_records");
            ArrayList<Checkup> pastRecords = intent.getParcelableArrayListExtra("past_records");
            populateRecords(presentRecords, pastRecords);

        } else if ("Animal Bite".equals(recordType)) {
            ArrayList<Bite> presentRecords = intent.getParcelableArrayListExtra("present_records");
            ArrayList<Bite> pastRecords = intent.getParcelableArrayListExtra("past_records");
            populateBiteRecords(presentRecords, pastRecords);
        } else if ("Prenatal".equals(recordType)) {
            ArrayList<Prenatal> presentRecords = intent.getParcelableArrayListExtra("present_records");
            ArrayList<Prenatal> pastRecords = intent.getParcelableArrayListExtra("past_records");
            populatePrenatalRecords(presentRecords, pastRecords);
        }


        // Set patient info
        patientNameTextView.setText(name);
        patientDetailsTextView.setText(gender + " - " + age);
        patientStatusTextView.setText(formatDate(admittedDate));


    }

    private void populatePrenatalRecords(ArrayList<Prenatal> presentRecords, ArrayList<Prenatal> pastRecords) {
        populateRecordsLayout(presentRecordsLayout, presentRecords, "No present prenatal records found.");
        populateRecordsLayout(pastRecordsLayout, pastRecords, "No past prenatal records found.");
    }

    private void populateRecords(ArrayList<Checkup> presentRecords, ArrayList<Checkup> pastRecords) {
        populateRecordsLayout(presentRecordsLayout, presentRecords, "No present records found.");
        populateRecordsLayout(pastRecordsLayout, pastRecords, "No past records found.");

    }

    private void populateBiteRecords(ArrayList<Bite> presentRecords, ArrayList<Bite> pastRecords) {
        populateRecordsLayout(presentRecordsLayout, presentRecords, "No present bite records found.");
        populateRecordsLayout(pastRecordsLayout, pastRecords, "No past bite records found.");
    }

    private <T> void populateRecordsLayout(LinearLayout layout, ArrayList<T> records, String noRecordsMessage) {
        layout.removeAllViews();
        if (records != null && !records.isEmpty()) {
            for (T record : records) {
                View recordView = createRecordView(record);
                layout.addView(recordView);
            }
        } else {
            TextView noRecordText = new TextView(this);
            noRecordText.setText(noRecordsMessage);
            noRecordText.setTextSize(16);
            noRecordText.setTextColor(getResources().getColor(R.color.gray));
            layout.addView(noRecordText);
        }
    }

    // Dynamic record view creation
    private <T> View createRecordView(T record) {
        LinearLayout recordLayout = new LinearLayout(this);
        recordLayout.setOrientation(LinearLayout.VERTICAL);
        recordLayout.setPadding(16, 16, 16, 16);

        if (record instanceof Prenatal) {
            Prenatal prenatal = (Prenatal) record;

            TextView prenatalDateView = new TextView(this);
            prenatalDateView.setText(String.format("Prenatal Date: %s", formatDate(prenatal.getDate())));
            prenatalDateView.setTextSize(16);
            prenatalDateView.setTextColor(getResources().getColor(R.color.black));

            TextView prenatalRemarksView = new TextView(this);
            prenatalRemarksView.setText(String.format("Comments: %s", prenatal.getComments()));
            prenatalRemarksView.setTextSize(14);
            prenatalRemarksView.setTextColor(getResources().getColor(R.color.gray));

            recordLayout.addView(prenatalDateView);
            recordLayout.addView(prenatalRemarksView);
        } else if (record instanceof Checkup) {
            Checkup checkup = (Checkup) record;

            TextView dateView = new TextView(this);
            dateView.setText(String.format("Date: %s", formatDate(checkup.getAdmitted())));
            dateView.setTextSize(16);
            dateView.setTextColor(getResources().getColor(R.color.black));

            TextView notesView = new TextView(this);
            notesView.setText(String.format("Notes: %s", checkup.getDoctor_order()));
            notesView.setTextSize(14);
            notesView.setTextColor(getResources().getColor(R.color.gray));

            recordLayout.addView(dateView);
            recordLayout.addView(notesView);
        } else if (record instanceof Bite) {
            Bite bite = (Bite) record;

            TextView biteDateView = new TextView(this);
            biteDateView.setText(String.format("Bite Date: %s", formatDate(bite.getVaccination_date())));
            biteDateView.setTextSize(16);
            biteDateView.setTextColor(getResources().getColor(R.color.black));

            TextView biteRemarksView = new TextView(this);
            biteRemarksView.setText(String.format("Remarks: %s", bite.getRemarks()));
            biteRemarksView.setTextSize(14);
            biteRemarksView.setTextColor(getResources().getColor(R.color.gray));

            recordLayout.addView(biteDateView);
            recordLayout.addView(biteRemarksView);
        }

        return recordLayout;
    }


    private String formatDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return "N/A";
        }
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date date = originalFormat.parse(dateString);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }

    }
}





