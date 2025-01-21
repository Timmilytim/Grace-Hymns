package com.example.gracehyms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HymnAdapter extends RecyclerView.Adapter<HymnAdapter.HymnViewHolder> {

    private Context context;
    private List<Hymn> hymnList;

    public HymnAdapter(Context context, List<Hymn> hymnList) {
        this.context = context;
        // Remove duplicates based on hymn ID before passing it to the adapter
        this.hymnList = removeDuplicates(hymnList);
    }

    @Override
    public HymnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each hymn item
        View view = LayoutInflater.from(context).inflate(R.layout.item_hymn, parent, false);
        return new HymnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HymnViewHolder holder, int position) {
        Hymn hymn = hymnList.get(position);

        // Set the hymn English title, Yoruba title, and hymn number
        holder.tvEnglishTitle.setText(hymn.getEnglishTitle());
        holder.tvYorubaTitle.setText(hymn.getYorubaTitle());
        holder.tvHymnNumber.setText(String.valueOf(hymn.getNumber())); // Display hymn number

        // Set click listener to pass hymn ID to HymnDetailsActivity
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, HymnDetailsActivity.class);
            intent.putExtra("hymnId", hymn.getId()); // Pass the selected hymn ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hymnList.size();
    }

    // ViewHolder class to hold the views for each hymn item
    public static class HymnViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglishTitle;
        TextView tvYorubaTitle;
        TextView tvHymnNumber; // Add TextView for hymn number

        public HymnViewHolder(View itemView) {
            super(itemView);
            tvEnglishTitle = itemView.findViewById(R.id.tvHymnTitle);
            tvYorubaTitle = itemView.findViewById(R.id.yorubaTitle);
            tvHymnNumber = itemView.findViewById(R.id.tvHymnNumber); // Initialize hymn number TextView
        }
    }

    // Method to remove duplicates based on hymn ID
    private List<Hymn> removeDuplicates(List<Hymn> hymnList) {
        HashSet<String> seenIds = new HashSet<>();
        List<Hymn> uniqueHymns = new ArrayList<>();

        for (Hymn hymn : hymnList) {
            if (!seenIds.contains(hymn.getId())) {
                seenIds.add(hymn.getId());
                uniqueHymns.add(hymn);
            }
        }

        return uniqueHymns;
    }
}
