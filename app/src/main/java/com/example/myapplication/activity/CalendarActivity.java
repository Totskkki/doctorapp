package com.example.myapplication.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import api.DialogUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.DoctorSchedule;
import com.example.myapplication.viewmodel.applyforleave;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.example.myapplication.adapter.EventAdapter;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import api.ApiService;
import api.DialogUtils;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends Activity {

    private BottomNavigationView nav;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private TextView daysweek;

    private Color color;
    private MaterialCalendarView calendarView;
    private Button toggleButton ;
    private boolean isWeekView = false;
    private ImageView closebtn;
    public static final String SHARED_PREFS = "UserPrefs";

    Map<CalendarDay, Integer> scheduleCountMap = new HashMap<>();
    private Map<CalendarDay, List<DoctorSchedule.Schedule>> scheduleMap;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

//===========================ddiaglog=========================
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaveDialog();
            }
        });
//===========================ddiaglog=========================






        // Bottom navigation functionality
        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.calendar);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.search) {
                    startActivity(new Intent(getApplicationContext(), SearchResultsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.calendar) {
                    return true;
                } else if (item.getItemId() == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });


        
        scheduleMap = new HashMap<>();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        String firstName = sharedPreferences.getString("first_name", "");
        String middleName = sharedPreferences.getString("middlename", "");
        String lastName = sharedPreferences.getString("lastname", "");
        String profilePictureFileName = sharedPreferences.getString("profile_picture", "");
        String specialty = sharedPreferences.getString("Specialty", "");



            fetchDoctorSchedules(Integer.parseInt(userID));





        closebtn = findViewById(R.id.closeButton);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closebtn();
            }
        });

        // Initialize views
        daysweek = findViewById(R.id.yourschedule);
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.doctorschedules);
        toggleButton = findViewById(R.id.toggleButton);

        setTodayDay();


        // Set the calendar to show month and year in full format
        calendarView.setTitleFormatter(new DateFormatTitleFormatter(
                new SimpleDateFormat("MMMM yyyy", Locale.getDefault())));


        calendarView.setTopbarVisible(false);
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.state().edit().setMinimumDate(CalendarDay.today()).commit();



        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                // Correctly format the month and year
                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
                String monthYearText = monthYearFormat.format(date.getDate());
                TextView dateRangeText = findViewById(R.id.dateRangeText);
                dateRangeText.setText(monthYearText);
            }
        });



        // Toggle button functionality (week/month view)
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWeekView) {
                    toggleButton.setText("Month");
                    calendarView.state().edit()
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit();
                } else {
                    toggleButton.setText("Week");
                    calendarView.state().edit()
                            .setCalendarDisplayMode(CalendarMode.WEEKS)
                            .commit();
                }
                isWeekView = !isWeekView;  // Toggle the state
            }
        });

        final TextView dateRangeText = findViewById(R.id.dateRangeText);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                // Update the TextView with the full month and year
                String monthYear = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(date.getDate());
                dateRangeText.setText(monthYear);
            }
        });


        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            // Log the selected date for debugging
            Log.d("date selected", "User selected date: " + date); // Always log the date
            int year = date.getYear();
            int month = date.getMonth(); // This is 0-based, so 0 = January, 11 = December
            int day = date.getDay();

            // Create a Calendar instance and set the date
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            // Get the day of the week (1 = Sunday, 7 = Saturday)
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String dayOfWeekString = getDayOfWeekString(dayOfWeek);

            // Format the month to its full name (e.g., "November")
            String[] months = new DateFormatSymbols().getMonths();
            String monthName = months[month]; // Get the full name of the month
            // Create a formatted string with the full date
            String formattedDate = monthName + " " + day + ", " + year;
            // Update TextView with formatted date
            daysweek.setText(formattedDate);
            // Pass the formatted date to load events
            loadEventsForSelectedDate(date, formattedDate);
        });




    }
    private void openLeaveDialog() {
        // Create a dialog instance using AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_apply_leave, null);
        builder.setView(view);

        // Find the EditTexts and Button from the layout
        final EditText startDate = view.findViewById(R.id.startDate);
        final EditText endDate = view.findViewById(R.id.endDate);
        final EditText reason = view.findViewById(R.id.reason);
        Button submitLeave = view.findViewById(R.id.submitLeave);

        // Store selected start date for validation
        final Calendar selectedStartDate = Calendar.getInstance();
        selectedStartDate.set(0, 0, 0);  // Clear the time portion for date comparison

        // Date picker for the start date
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDate, selectedStartDate); // Pass selectedStartDate to track the start date
            }
        });

        // Date picker for the end date
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogForEndDate(endDate, selectedStartDate); // Use selectedStartDate to validate the end date
            }
        });

        final AlertDialog dialog = builder.create();
        // Set up the submit button's click listener
        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get shared preferences to fetch userID
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String userID = sharedPreferences.getString("userID", "");

                // Get the text input from the fields in the dialog
                String startDateStr = startDate.getText().toString();
                String endDateStr = endDate.getText().toString();
                String reasonStr = reason.getText().toString();

                // Ensure the input fields are not empty before proceeding
                if (startDateStr.isEmpty() || endDateStr.isEmpty() || reasonStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with applying for leave
                    applyForLeave(userID, startDateStr, endDateStr, reasonStr,dialog);
                }
            }
        });


        dialog.show();
    }

    private void showDatePickerDialog(final EditText editText, final Calendar selectedDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update selected date
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Format the date as YYYY-MM-DD
                        String selectedDateStr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        editText.setText(selectedDateStr); // Update the text of the EditText
                    }
                },
                year, month, day
        );

        // Disable past dates by setting the minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void showDatePickerDialogForEndDate(final EditText editText, final Calendar startDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the end date
                        Calendar selectedEndDate = Calendar.getInstance();
                        selectedEndDate.set(year, monthOfYear, dayOfMonth);

                        // Validate that the end date is not earlier than the start date
                        if (selectedEndDate.before(startDate)) {
                            Toast.makeText(getApplicationContext(), "End date cannot be before the start date", Toast.LENGTH_SHORT).show();
                        } else {
                            // Format the date as YYYY-MM-DD
                            String selectedDateStr = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            editText.setText(selectedDateStr); // Update the text of the EditText
                        }
                    }
                },
                year, month, day
        );

        // Disable past dates by setting the minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    private void applyForLeave(String userID, String startDate, String endDate, String reason, final AlertDialog dialog) {
        // Create the ApiService using Retrofit
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Prepare the API call to apply for leave
        Call<applyforleave> call = apiService.Applyforleave(
                Integer.parseInt(userID),  // userID
                startDate,  // start date
                endDate,    // end date
                reason      // reason for leave
        );

        // Execute the API call asynchronously
        call.enqueue(new Callback<applyforleave>() {
            @Override
            public void onResponse(Call<applyforleave> call, Response<applyforleave> response) {
                if (response.isSuccessful()) {
                    // Check if the response has a message indicating success or error
                    if (response.body() != null && "error".equalsIgnoreCase(response.body().getStatus())) {
                        // Handle the case where the leave already exists or some other error occurred
                        DialogUtils.showResultDialog(CalendarActivity.this, "Failed", response.body().getMessage() ,true);

                    } else {
                        // Dismiss the leave dialog and show success message
                        dialog.dismiss();
                        DialogUtils.showResultDialog(CalendarActivity.this, "Success", "Leave applied successfully!",true);

                    }
                } else {
                    // If the response is not successful, show an error dialog

                    DialogUtils.showResultDialog(CalendarActivity.this, "Failed", "Leave request could not be processed.",true);

                }
            }

            @Override
            public void onFailure(Call<applyforleave> call, Throwable t) {
                // Show error dialog for failure cases (e.g., no internet)
                DialogUtils.showResultDialog(CalendarActivity.this, "Error", "Error: " + t.getMessage() ,true);
            }
        });
    }





    private void setTodayDay() {
        // Get today's date
        Calendar calendar = Calendar.getInstance();

        // Extract day of the week, day of the month, and year
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH); // 0-based month (0 = January, 11 = December)
        int year = calendar.get(Calendar.YEAR);   // Get the current year

        // Get the day of the week string (e.g., "Monday")
        String dayOfWeekString = getDayOfWeekString(dayOfWeek);

        // Format the month to its full name (e.g., "November")
        String[] months = new DateFormatSymbols().getMonths();
        String monthName = months[month]; // Get the full name of the month

        // Create a formatted string with the full date
        String formattedDate = monthName + " " + dayOfMonth + ", " + year;

        // Update the TextView with the formatted date
        daysweek.setText(formattedDate);
    }

    private String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "";
        }
    }

    private void fetchDoctorSchedules(int userID) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.getDoctorSchedule(userID).enqueue(new Callback<List<DoctorSchedule>>() {
            @Override
            public void onResponse(Call<List<DoctorSchedule>> call, Response<List<DoctorSchedule>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DoctorSchedule> schedules = response.body();
                    scheduleMap = new HashMap<>();

                    for (DoctorSchedule doctorSchedule : schedules) {
                        Map<String, List<DoctorSchedule.Schedule>> daySchedules = doctorSchedule.getSchedules();
                        String repeatType = doctorSchedule.getReapet();

                        if (daySchedules != null) {
                            for (Map.Entry<String, List<DoctorSchedule.Schedule>> entry : daySchedules.entrySet()) {
                                String dayOfWeek = entry.getKey();
                                List<DoctorSchedule.Schedule> scheduleList = entry.getValue();
                                String baseDate = getDateForWeekday(dayOfWeek);

                                for (DoctorSchedule.Schedule schedule : scheduleList) {
                                    if ("Weekly".equalsIgnoreCase(repeatType)) {
                                        for (int i = 0; i < 12; i++) {
                                            String weeklyDate = getDateForNextWeek(baseDate, i);
                                            addScheduleToMap(weeklyDate, schedule, scheduleMap);
                                        }
                                    } else if ("Monthly".equalsIgnoreCase(repeatType)) {
                                        for (int i = 0; i < 12; i++) {
                                            String nextMonthDate = getNextMonthDate(baseDate, i);
                                            addScheduleToMap(nextMonthDate, schedule, scheduleMap);
                                        }
                                    } else if ("Yearly".equalsIgnoreCase(repeatType)) {
                                        for (int i = 0; i < 5; i++) {
                                            String nextYearDate = getNextYearDate(baseDate, i);
                                            addScheduleToMap(nextYearDate, schedule, scheduleMap);
                                        }
                                    }

                                    // Set the leave dates and schedule date if available
                                    schedule.setLeaveStartDate(schedule.getLeaveStartDate());
                                    schedule.setLeaveEndDate(schedule.getLeaveEndDate());
                                    schedule.setScheduleDate(baseDate);
                                }
                            }
                        }
                    }

                    // Decorate calendar with dates that have schedules
                    Set<CalendarDay> datesWithSchedules = scheduleMap.keySet();
                    EventDecorator eventDecorator = new EventDecorator(Color.BLUE, datesWithSchedules);
                    calendarView.addDecorator(eventDecorator);
                } else {
                    Log.e("API Error", "No schedules found or error in response");
                }
            }

            @Override
            public void onFailure(Call<List<DoctorSchedule>> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }


    private void addScheduleToMap(String date, DoctorSchedule.Schedule schedule, Map<CalendarDay, List<DoctorSchedule.Schedule>> scheduleMap) {
        CalendarDay calendarDay = parseDateToCalendarDay(date);
        if (calendarDay != null) {
            if (!scheduleMap.containsKey(calendarDay)) {
                scheduleMap.put(calendarDay, new ArrayList<>());
            }
            scheduleMap.get(calendarDay).add(schedule);
        }
    }


    private CalendarDay parseDateToCalendarDay(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            CalendarDay calendarDay = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            Log.d("Parsed Date", "Parsed Date: " + calendarDay);
            return calendarDay;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getDateForWeekday(String dayOfWeek) {
        try {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeekCalendar = getDayOfWeekConstant(dayOfWeek);

            if (dayOfWeekCalendar == -1) {
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
            }

            int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int daysToAdd = (dayOfWeekCalendar - currentDayOfWeek + 7) % 7;
            if (daysToAdd == 0) daysToAdd = 7;  // Always return the next occurrence

            calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(calendar.getTime());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getDayOfWeekConstant(String dayOfWeek) {
        switch (dayOfWeek.toUpperCase()) {
            case "SUNDAY":
                return Calendar.SUNDAY;
            case "MONDAY":
                return Calendar.MONDAY;
            case "TUESDAY":
                return Calendar.TUESDAY;
            case "WEDNESDAY":
                return Calendar.WEDNESDAY;
            case "THURSDAY":
                return Calendar.THURSDAY;
            case "FRIDAY":
                return Calendar.FRIDAY;
            case "SATURDAY":
                return Calendar.SATURDAY;
            default:
                return -1;  // Invalid day of the week
        }
    }

    private String getDateForNextWeek(String baseDate, int weekOffset) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(baseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.WEEK_OF_YEAR, weekOffset);  // Add weeks
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return baseDate;  // Return the base date if parsing fails
        }
    }

    private String getNextMonthDate(String baseDate, int monthOffset) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(baseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, monthOffset);  // Add months
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return baseDate;  // Return the base date if parsing fails
        }
    }

    private String getNextYearDate(String baseDate, int yearOffset) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(baseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, yearOffset);  // Add years
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return baseDate;  // Return the base date if parsing fails
        }
    }


    private void loadEventsForSelectedDate(CalendarDay date, String formattedDate) {
        List<DoctorSchedule.Schedule> eventsForSelectedDate = scheduleMap.getOrDefault(date, new ArrayList<>());

        // You can handle the leave dates here and highlight them
        for (DoctorSchedule.Schedule event : eventsForSelectedDate) {
            if (event.getLeaveStartDate() != null && event.getLeaveEndDate() != null) {
                // Decorate or highlight the leave dates on the calendar
                decorateLeaveDates(event.getLeaveStartDate(), event.getLeaveEndDate());
            }
        }

        updateRecyclerView(eventsForSelectedDate, formattedDate);  // Pass formattedDate
    }
    private void decorateLeaveDates(String leaveStartDate, String leaveEndDate) {
        // Convert the leave dates to CalendarDay
        CalendarDay startDate = parseDateToCalendarDay(leaveStartDate);
        CalendarDay endDate = parseDateToCalendarDay(leaveEndDate);

        if (startDate == null || endDate == null) {
            Log.e("Decoration Error", "Invalid leave start or end date");
            return;
        }

        // Create a Set of CalendarDays from startDate to endDate
        Set<CalendarDay> leaveDates = new HashSet<>();

        // Use Calendar to increment the date
        Calendar calendar = Calendar.getInstance();
        calendar.set(startDate.getYear(), startDate.getMonth() - 1, startDate.getDay()); // Adjusting month since Calendar uses 0-based months

        // Add all dates between startDate and endDate to the Set
        while (!calendar.getTime().after(endDate.getDate())) {
            leaveDates.add(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.DAY_OF_YEAR, 1);  // Increment the date by one day
        }

        Log.d("Leave Dates", "Leave Dates to be decorated: " + leaveDates);

        // Create the EventDecorator with the set of leave dates
        EventDecorator leaveDecorator = new EventDecorator(Color.RED, leaveDates);
        calendarView.addDecorator(leaveDecorator);
    }


    private void updateRecyclerView(List<DoctorSchedule.Schedule> eventsForSelectedDate, String selectedDate) {
        if (recyclerView.getAdapter() == null) {
            EventAdapter adapter = new EventAdapter(this, eventsForSelectedDate, selectedDate);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            EventAdapter adapter = (EventAdapter) recyclerView.getAdapter();
            adapter.updateEvents(eventsForSelectedDate, selectedDate);
        }
    }

    public class EventDecorator implements DayViewDecorator {
        private final Set<CalendarDay> dates;
        private final int color;

        public EventDecorator(int color, Set<CalendarDay> dates) {
            this.dates = dates;
            this.color = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {

            view.addSpan(new DotSpan(10, color));
        }
    }



    private void closebtn() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

        // Optionally, close the current activity
        finish();
    }

}
