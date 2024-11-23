package api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtils {


    public static String convertToAmPm(String time) {
        try {
            // Input time format (24-hour format)
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");

            // Output time format (12-hour format with AM/PM)
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");

            // Parse the input time
            Date date = inputFormat.parse(time);

            // Format the time to AM/PM format
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return time; // Return original time if error occurs
        }
    }


}
