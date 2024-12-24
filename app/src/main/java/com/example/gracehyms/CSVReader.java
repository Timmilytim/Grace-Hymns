package com.example.gracehyms;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVReader {

    public static ArrayList<Hymn> readHymnsFromCSV(Context context, String fileName) {
        ArrayList<Hymn> hymnList = new ArrayList<>();

        try {
            // Open the CSV file from assets
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split the line by commas
                String[] data = line.split(",");

                // Extract columns
                String id = data[0].trim();
                String englishTitle = data[1].trim();
                String yorubaTitle = data[2].trim();
                String number = data[3].trim();
                String verseNumber = data[4].trim();
                String englishLyrics = data[5].trim();
                String yorubaLyrics = data[6].trim();
                String type = data[7].trim();
                boolean isFavorite = data[8].trim().equalsIgnoreCase("true");

                // Create a Hymn object
                Hymn hymn = new Hymn(id, englishTitle, yorubaTitle, number, verseNumber, englishLyrics, yorubaLyrics, type, isFavorite);

                // Add to the list
                hymnList.add(hymn);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("CSVReader", "Error reading CSV file", e);
        }

        return hymnList;
    }
}
