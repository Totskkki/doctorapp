package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


import java.util.Calendar;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us2);

        // Create a custom element for ads or any other additional info

        ImageView headerImage = findViewById(R.id.headerImage);
        headerImage.setImageResource(R.drawable.ic_doctor); // Replace with your image resource

        Element adsElement = new Element();
        adsElement.setTitle("Doctor Scheduling");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("We are dedicated to providing exceptional healthcare services with compassion and care.")
                .addGroup("CONNECT WITH US!")
                .addEmail("floresjovenrey26@gmail.com")
                .addWebsite("https://lutayanrhu.site/")
                .addYoutube("UCD0x85yzI1t9_aIWqZ5_tiw")
                .addItem(createCopyright())
                .create();

        // Add the AboutPage to the FrameLayout
        FrameLayout container = findViewById(R.id.aboutPageContainer);
        container.addView(aboutPage);
    }

    private Element createCopyright() {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString =
                String.format("Copyright © %d by Joven Rey V. Flores", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}
