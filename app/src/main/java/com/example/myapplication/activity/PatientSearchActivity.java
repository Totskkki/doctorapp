package com.example.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class PatientSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_search);

        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_button();
            }
        });

        String patientId = getIntent().getStringExtra("patientID");
        String patientName = getIntent().getStringExtra("patient_name");
        String middlename = getIntent().getStringExtra("middle_name");
        String lastname = getIntent().getStringExtra("last_name");
        String suffix = getIntent().getStringExtra("suffix");
        String patientGender = getIntent().getStringExtra("gender");
        String patientAge = getIntent().getStringExtra("age");
        String patientContact = getIntent().getStringExtra("phone_number");
        String address = getIntent().getStringExtra("address");
        String date_of_birth = getIntent().getStringExtra("date_of_birth");
        String patient_blood_type = getIntent().getStringExtra("patient_blood_type");
        String Height = getIntent().getStringExtra("Height");
        String rr = getIntent().getStringExtra("rr");
        String weight = getIntent().getStringExtra("weight");


        TextView nameView = findViewById(R.id.patient_name);
        TextView patientDetailsView = findViewById(R.id.patient_details);
        TextView patientbllodtype = findViewById(R.id.patient_blood_type);
        TextView height = findViewById(R.id.patient_height);
        TextView respiratory = findViewById(R.id.patient_bmi);
        TextView Weight = findViewById(R.id.patient_weight);




        String formattedDetails = String.format(
                "%s · %s years old\n%s\n%s",
                patientGender,
                patientAge,
                date_of_birth,
                address
        );
        height.setText(Height);
        respiratory.setText(rr);
        Weight.setText(weight);

        patientDetailsView.setText(formattedDetails);
        nameView.setText("Name: " + patientName + " " +middlename + " " + lastname + " " +suffix);
        patientbllodtype.setText(patient_blood_type);








    }

    private void back_button() {
        onBackPressed();
    }

}