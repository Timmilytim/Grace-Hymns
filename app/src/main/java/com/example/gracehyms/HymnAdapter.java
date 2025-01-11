package com.example.gracehyms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HymnAdapter extends RecyclerView.Adapter<HymnAdapter.HymnViewHolder> {

    private Context context;
    private List<Hymn> hymnList;

    public HymnAdapter(Context context, List<Hymn> hymnList) {
        this.context = context;
        this.hymnList = hymnList;
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
        // Set the hymn title
        holder.tvEnglishTitle.setText(hymn.getEnglishTitle());

        // Check if categories is null before calling toString()
        if (hymn.getCategories() != null) {
            holder.tvCategories.setText(hymn.getCategories().toString());
        } else {
            holder.tvCategories.setText("No categories available");
        }
    }


    @Override
    public int getItemCount() {
        return hymnList.size();
    }

    // ViewHolder class to hold the views for each hymn item
    public static class HymnViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglishTitle;
        TextView tvCategories;

        public HymnViewHolder(View itemView) {
            super(itemView);
            tvEnglishTitle = itemView.findViewById(R.id.tvHymnTitle);
            tvCategories = itemView.findViewById(R.id.tvCategories);
        }
    }
}
