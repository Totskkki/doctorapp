package api;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogUtils {
    // Method to show a dialog with a title and message
    public static void showResultDialog(Activity activity, String title, String message, boolean isSuccess) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return; // Avoid showing the dialog if the Activity is not in a valid state
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Define colors for success and error
        int textColor;
        if (isSuccess) {
            textColor = activity.getResources().getColor(R.color.successColor); // Define `successColor` in `res/colors.xml`
        } else {
            textColor = activity.getResources().getColor(R.color.errorColor); // Define `errorColor` in `res/colors.xml`
        }

        // Set title color
        int titleId = activity.getResources().getIdentifier("alertTitle", "id", "android");
        TextView dialogTitle = dialog.findViewById(titleId);
        if (dialogTitle != null) {
            dialogTitle.setTextColor(textColor);
        }

        // Set message color
        TextView messageView = dialog.findViewById(android.R.id.message);
        if (messageView != null) {
            messageView.setTextColor(textColor);
        }
    }

}



