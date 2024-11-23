//package com.example.myapplication.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.R;
//
//import java.util.List;
//
//import api.DoctorSchedule;
//
//public class DoctorScheduleAdapter extends RecyclerView.Adapter<DoctorScheduleAdapter.ViewHolder> {
//
//    private List<DoctorSchedule> scheduleList;
//    private Context context;
//
//    public DoctorScheduleAdapter(List<DoctorSchedule> scheduleList, Context context) {
//        this.scheduleList = scheduleList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        DoctorSchedule schedule = scheduleList.get(position);
//
//        holder.tvDayOfWeek.setText(schedule.getDayOfWeek());
//        holder.tvScheduledDate.setText(schedule.getFormattedScheduledDate());
//        holder.tvTime.setText(schedule.getStartTime() + " - " + schedule.getEndTime());
//        holder.tvLocation.setText("Location: " + schedule.getLocation());
//
//        if (schedule.isAvailable()) {
//            holder.tvAvailability.setText("Available");
//        } else {
//            holder.tvAvailability.setText("Not Available");
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return scheduleList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tvDayOfWeek, tvScheduledDate, tvTime, tvLocation, tvAvailability;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tvDayOfWeek = itemView.findViewById(R.id.doctorName);
//            tvScheduledDate = itemView.findViewById(R.id.scheduledate);
//            tvTime = itemView.findViewById(R.id.time);
//            tvAvailability = itemView.findViewById(R.id.availability);
//        }
//    }
//}
