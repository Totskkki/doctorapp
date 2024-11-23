package com.example.myapplication.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
public class ApplyLeaveBottomSheetDialog extends BottomSheetDialogFragment {

    private EditText reasonEditText, leaveDateEditText, startTimeEditText, endTimeEditText;
    private Button submitLeaveButton;
    private ApplyLeaveListener listener;

    public interface ApplyLeaveListener {
        void onLeaveApplied(String reason, String leaveDate, String startTime, String endTime);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ApplyLeaveListener) {
            listener = (ApplyLeaveListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ApplyLeaveListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_leave, container, false);

        reasonEditText = view.findViewById(R.id.reasonEditText);
        leaveDateEditText = view.findViewById(R.id.datestringstr);
        startTimeEditText = view.findViewById(R.id.fromTimeTextView);
        endTimeEditText = view.findViewById(R.id.toTimeTextView);
        submitLeaveButton = view.findViewById(R.id.submitLeaveButton);

        // Retrieve the arguments passed from the Activity
        if (getArguments() != null) {
            String fromTime = getArguments().getString("from_time");
            String toTime = getArguments().getString("to_time");
            String leaveDate = getArguments().getString("leave_date");

            // Set them to the respective EditTexts
            startTimeEditText.setText(fromTime);
            endTimeEditText.setText(toTime);
            leaveDateEditText.setText(leaveDate);
        }

        submitLeaveButton.setOnClickListener(v -> {
            String reason = reasonEditText.getText().toString().trim();
            String leaveDate = leaveDateEditText.getText().toString().trim();
            String startTime = startTimeEditText.getText().toString().trim();
            String endTime = endTimeEditText.getText().toString().trim();

            if (!reason.isEmpty() && !leaveDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                listener.onLeaveApplied(reason, leaveDate, startTime, endTime);  // Send all data back to activity
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

