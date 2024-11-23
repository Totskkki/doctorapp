package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class DetailActivity extends AppCompatActivity {
    private ImageView closebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_announcementslist);

        // Get data from Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");

        // Set data to views
        TextView titleTextView = findViewById(R.id.textView8);
        TextView descriptionTextView = findViewById(R.id.textView9);
        TextView dateTextView = findViewById(R.id.textView10);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        dateTextView.setText(date);



        // Initialize the button and set an OnClickListener with the activity type as a parameter
        closebtn = findViewById(R.id.closeButton);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closebtn("dashboard");
                closebtn("announcement");
            }
        });

    }
    private void closebtn(String activityType) {
        Intent intent;

        // Check the activity type and redirect accordingly
        if ("announcement".equals(activityType)) {
            intent = new Intent(this, AllAnnouncementsActivity.class);
        } else if ("dashboard".equals(activityType)) {
            intent = new Intent(this, DashboardActivity.class);
        } else {
            // Default to DashboardActivity if type is unknown
            intent = new Intent(this, DashboardActivity.class);
        }

        finish();
    }

}

