//package api;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class DoctorSchedule {
//    private String day_of_week;
//    private String start_time;
//    private String end_time;
//    private int is_available;  // Change from boolean to int
//    private String location ,scheduled_date,doctors_name;
//
//    public String getDoctors_name() {
//        return doctors_name;
//    }
//
//    public void setDoctors_name(String doctors_name) {
//        this.doctors_name = doctors_name;
//    }
//
//    public String getScheduled_date() {
//        return scheduled_date;
//    }
//
//    public void setScheduled_date(String scheduled_date) {
//        this.scheduled_date = scheduled_date;
//    }
//    public String getFormattedScheduledDate() {
//        // Define the input and output date formats
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
//
//        try {
//            // Parse the input date string into a Date object
//            Date date = inputFormat.parse(scheduled_date);
//            // Format the Date object to the desired output string
//            return outputFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace(); // Handle the exception according to your needs
//            return null; // Return null or an appropriate message
//        }
//    }
//
//    // Getters and Setters
//    public String getDayOfWeek() {
//        return day_of_week;
//    }
//
//    public void setDayOfWeek(String day_of_week) {
//        this.day_of_week = day_of_week;
//    }
//
//    public String getStartTime() {
//        return start_time;
//    }
//
//    public void setStartTime(String start_time) {
//        this.start_time = start_time;
//    }
//
//    public String getEndTime() {
//        return end_time;
//    }
//
//    public void setEndTime(String end_time) {
//        this.end_time = end_time;
//    }
//
//    public int getIsAvailable() {
//        return is_available;
//    }
//
//    public void setIsAvailable(int is_available) {
//        this.is_available = is_available;
//    }
//
//    // Helper method to check availability
//    public boolean isAvailable() {
//        return is_available == 1;
//    }
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//}
//
//
