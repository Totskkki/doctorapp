    package com.example.myapplication.adapter;

    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.List;


    import com.example.myapplication.R;
    import com.example.myapplication.activity.ViewDoctorScheduleActivity;
    import com.example.myapplication.viewmodel.DoctorSchedule;

    import api.TimeUtils;

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
        private List<DoctorSchedule.Schedule> events;
        private Context context;
        private String selectedDate;

        public EventAdapter(Context context, List<DoctorSchedule.Schedule> events, String selectedDate) {
            this.context = context;
            this.events = events;
            this.selectedDate = selectedDate;
        }

        public void updateEvents(List<DoctorSchedule.Schedule> newEvents, String newSelectedDate) {
            this.events = newEvents;
            this.selectedDate = newSelectedDate;  // Update selectedDate as well
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            DoctorSchedule.Schedule event = events.get(position);


            String fromTimeAmPm = TimeUtils.convertToAmPm(event.getFromtime());
            String toTimeAmPm = TimeUtils.convertToAmPm(event.getTotime());
            String worklength = event.getWorklength();


            holder.fromTimeTextView.setText(fromTimeAmPm);
            holder.toTimeTextView.setText(toTimeAmPm);
            holder.worklength.setText(worklength);


            holder.itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context, ViewDoctorScheduleActivity.class);



                // Pass data to the new activity

                intent.putExtra("from_time", fromTimeAmPm);
                intent.putExtra("to_time", toTimeAmPm);
                intent.putExtra("work_length", worklength);
                intent.putExtra("selected_date", selectedDate);

                // Start the activity
                context.startActivity(intent);

            });

        }


        @Override
        public int getItemCount() {
            return events.size();
        }

        public static class EventViewHolder extends RecyclerView.ViewHolder {

            TextView fromTimeTextView;
            TextView toTimeTextView;
            TextView worklength;

            public EventViewHolder(View itemView) {
                super(itemView);
                fromTimeTextView = itemView.findViewById(R.id.fromTimeTextView);
                toTimeTextView = itemView.findViewById(R.id.toTimeTextView);
                worklength = itemView.findViewById(R.id.worklength);

            }
        }


    }



