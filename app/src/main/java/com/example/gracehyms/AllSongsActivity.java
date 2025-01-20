package com.example.gracehyms;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllSongsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HymnAdapter hymnAdapter;
    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);

        recyclerView = findViewById(R.id.hymn_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new MyDatabaseHelper(this);

        // Fetch data from the database
        List<Hymn> hymnList = databaseHelper.getAllHymns();

        // Set up the adapter
        hymnAdapter = new HymnAdapter(this, hymnList);
        recyclerView.setAdapter(hymnAdapter);
    }
}
