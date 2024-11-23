package api;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class ResultDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_IS_SUCCESS = "isSuccess";

    public static ResultDialogFragment newInstance(String title, String message, boolean isSuccess) {
        ResultDialogFragment fragment = new ResultDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putBoolean(ARG_IS_SUCCESS, isSuccess);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString(ARG_TITLE);
        String message = getArguments().getString(ARG_MESSAGE);
        boolean isSuccess = getArguments().getBoolean(ARG_IS_SUCCESS);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d -> {
            // Set colors based on success or failure
            int color;
            if (isSuccess) {
                color = ContextCompat.getColor(requireContext(), R.color.successColor);
            } else {
                color = ContextCompat.getColor(requireContext(), R.color.errorColor);
            }

            // Change title and message text color
            TextView titleView = dialog.findViewById(requireActivity().getResources().getIdentifier("alertTitle", "id", "android"));
            if (titleView != null) {
                titleView.setTextColor(color);
            }

            TextView messageView = dialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setTextColor(color);
            }
        });

        return dialog;
    }
}

