package com.example.gracehyms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Handling the insets to avoid system bar overlap
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Switching to the All Songs page
        LinearLayout btnAllSongs = findViewById(R.id.all_songs);

        btnAllSongs.setOnClickListener(v -> {
            // Create an Intent to open the AllSongsActivity
            Intent intent = new Intent(Dashboard.this, AllSongsActivity.class);
            startActivity(intent);
        });
    }
}
