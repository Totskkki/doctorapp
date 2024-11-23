package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.GridItemBinding;
import com.example.myapplication.viewmodel.GridItem;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private final Context context;
    private final List<GridItem> itemList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GridItem item); // Define a method to handle item clicks
    }

    public GridAdapter(Context context, List<GridItem> itemList, OnItemClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GridItemBinding binding = GridItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GridItem item = itemList.get(position);

        // Bind the data to the views
        holder.binding.title.setText(item.getTitle());
        holder.binding.icon.setImageResource(item.getIcon());
        holder.binding.countTextView.setText(String.valueOf(item.getCount()));

        // Set click listener
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final GridItemBinding binding;

        public ViewHolder(@NonNull GridItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
