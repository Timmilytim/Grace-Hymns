package com.example.gracehyms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HymnNumberAdapter extends RecyclerView.Adapter<HymnNumberAdapter.HymnNumberViewHolder> {
    private Context context;
    private List<String> hymnNumbers;

    public HymnNumberAdapter(Context context, List<String> hymnNumbers) {
        this.context = context;
        this.hymnNumbers = hymnNumbers;
    }

    @NonNull
    @Override
    public HymnNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each hymn number item
        View view = LayoutInflater.from(context).inflate(R.layout.item_hymn_number, parent, false);
        return new HymnNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HymnNumberViewHolder holder, int position) {
        String number = hymnNumbers.get(position);

        // Set the hymn number
        holder.tvHymnNumber.setText(number);

        // Set click listener to pass hymn number to HymnDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HymnDetailsActivity.class);
            intent.putExtra("hymnId", number); // Pass the selected hymn number as ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hymnNumbers.size();
    }

    // ViewHolder class to hold the views for each hymn number item
    public static class HymnNumberViewHolder extends RecyclerView.ViewHolder {
        TextView tvHymnNumber;

        public HymnNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHymnNumber = itemView.findViewById(R.id.tvHymnNumber);
        }
    }
}