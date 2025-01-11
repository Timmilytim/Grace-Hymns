package com.example.gracehyms;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AllSongsActivity extends AppCompatActivity {

    private RecyclerView hymnTitles;
    private HymnAdapter hymnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_all_songs);

        // Reference the RecyclerView by its ID
        hymnTitles = findViewById(R.id.hymn_title);

        // Create a list of hymns (You can later replace this with real data from your database)
        List<Hymn> hymnList = new ArrayList<>();
        hymnList.add(new Hymn("1", "Amazing Grace", "Ire Olugbala", "1", "1", "Lyrics here", "Orin Yoruba", "Praise", false));
        hymnList.add(new Hymn("2", "How Great Thou Art", "Bawo ni o se nla", "2", "1", "Lyrics here", "Orin Yoruba", "Worship", false));

        // Initialize the adapter with the hymn list
        hymnAdapter = new HymnAdapter(this, hymnList);

        // Set up RecyclerView with the layout manager and adapter
        hymnTitles.setLayoutManager(new LinearLayoutManager(this));
        hymnTitles.setAdapter(hymnAdapter);
    }
}
