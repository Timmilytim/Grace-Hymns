package com.example.gracehyms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AllSongsActivity extends AppCompatActivity {

    private ListView listViewSongs;
    private ArrayList<Hymn> hymnList;
    private ArrayList<String> songTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);

        // Initialize UI components
        listViewSongs = findViewById(R.id.listViewSongs);

        // Load hymns from the CSV file
        loadHymnsFromCSV();

        // Extract song titles for display
        songTitles = new ArrayList<>();
        for (Hymn hymn : hymnList) {
            songTitles.add(hymn.getEnglishTitle() + " - " + hymn.getYorubaTitle());
        }

        // Set up the adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                songTitles
        );
        listViewSongs.setAdapter(adapter);

        // Handle click events on the song list
        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hymn selectedHymn = hymnList.get(position);
                Toast.makeText(AllSongsActivity.this, "Selected: " + selectedHymn.getEnglishTitle(), Toast.LENGTH_SHORT).show();

                // Pass selected hymn details to another activity if needed
//                Intent intent = new Intent(AllSongsActivity.this, HymnDetailActivity.class);
//                intent.putExtra("hymn", selectedHymn); // You need to make Hymn implement Serializable or Parcelable
//                startActivity(intent);
            }
        });
    }

    private void loadHymnsFromCSV() {
        // Use the CSVReader utility to load hymns
        hymnList = CSVReader.readHymnsFromCSV(this, "Hymns.csv");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AllSongsActivity.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
