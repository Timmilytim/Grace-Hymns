package com.example.gracehyms;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HymnNumbersActivity extends AppCompatActivity {
    private RecyclerView rvHymnNumbers;
    private HymnNumberAdapter hymnNumberAdapter;
    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.hymn_numbers);

        // Handle system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        rvHymnNumbers = findViewById(R.id.rvHymnNumbers);

        // Use GridLayoutManager with 5 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5); // 5 columns
        rvHymnNumbers.setLayoutManager(gridLayoutManager);

        // Add spacing between grid items
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        rvHymnNumbers.addItemDecoration(new GridSpacingItemDecoration(5, spacingInPixels, true));

        // Initialize database helper
        databaseHelper = new MyDatabaseHelper(this);

        // Fetch hymns from the database
        List<Hymn> hymns = databaseHelper.getAllHymns();

        // Extract unique hymn numbers and sort them
        List<String> hymnNumbers = getUniqueHymnNumbers(hymns);

        // Set up the adapter
        hymnNumberAdapter = new HymnNumberAdapter(this, hymnNumbers);
        rvHymnNumbers.setAdapter(hymnNumberAdapter);
    }

    // Method to extract unique hymn numbers and sort them
    private List<String> getUniqueHymnNumbers(List<Hymn> hymns) {
        HashSet<String> uniqueNumbers = new HashSet<>();
        for (Hymn hymn : hymns) {
            uniqueNumbers.add(hymn.getNumber());
        }

        // Convert the set to a list and filter out invalid hymn numbers
        List<String> validNumbers = new ArrayList<>();
        for (String number : uniqueNumbers) {
            try {
                Integer.parseInt(number); // Check if the number is valid
                validNumbers.add(number);
            } catch (NumberFormatException e) {
                // Skip invalid hymn numbers
            }
        }

        // Sort the list in ascending order
        validNumbers.sort((num1, num2) -> Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2)));

        return validNumbers;
    }
}