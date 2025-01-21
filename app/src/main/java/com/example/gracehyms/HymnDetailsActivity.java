package com.example.gracehyms;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HymnDetailsActivity extends AppCompatActivity {

    private TextView tvHymnNumber, tvHymnTitleEnglish, tvHymnTitleYoruba;
    private ImageButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hymn_details);

        // Initialize Views
        tvHymnNumber = findViewById(R.id.tvHymnNumber);
        tvHymnTitleEnglish = findViewById(R.id.tvHymnTitleEnglish);
        tvHymnTitleYoruba = findViewById(R.id.tvHymnTitleYoruba);

        btnFavorite = findViewById(R.id.btnFavorite);

        // Get the hymn ID from the Intent
        String hymnId = getIntent().getStringExtra("hymnId");

        if (hymnId != null) {
            fetchHymnDetails(hymnId);
        }

        // Handle Favorite button click
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the logic for marking/unmarking as favorite
            }
        });
    }

    // Method to fetch hymn details based on hymn ID
    private void fetchHymnDetails(String hymnId) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        List<Hymn> hymnVerses = dbHelper.getHymnVersesById(hymnId);

        if (hymnVerses.isEmpty()) {
            tvHymnNumber.setText("Hymn not found");
            return;
        }

        Hymn firstVerse = hymnVerses.get(0);
        tvHymnNumber.setText(firstVerse.getNumber());
        tvHymnTitleEnglish.setText(firstVerse.getEnglishTitle());
        tvHymnTitleYoruba.setText(firstVerse.getYorubaTitle());

        LinearLayout englishLyricsContainer = findViewById(R.id.tvEnglishLyrics);
        LinearLayout yorubaLyricsContainer = findViewById(R.id.tvYorubaLyrics);

        englishLyricsContainer.removeAllViews();
        yorubaLyricsContainer.removeAllViews();

        Hymn chorus = null;
        List<Hymn> otherVerses = new ArrayList<>();
        for (Hymn verse : hymnVerses) {
            if ("0".equals(verse.getVerseNumber())) {
                chorus = verse;
            } else {
                otherVerses.add(verse);
            }
        }

        for (Hymn verse : otherVerses) {
            addVerseToContainer(verse, englishLyricsContainer, yorubaLyricsContainer);
            if ("1".equals(verse.getVerseNumber()) && chorus != null) {
                addChorusToContainer(chorus, englishLyricsContainer, yorubaLyricsContainer);
                chorus = null;
            }
        }

        if (chorus != null) {
            addChorusToContainer(chorus, englishLyricsContainer, yorubaLyricsContainer);
        }
    }

    // Helper method to add a regular verse
    private void addVerseToContainer(Hymn verse, LinearLayout englishContainer, LinearLayout yorubaContainer) {
        String verseNumber = verse.getVerseNumber();
        String englishLyrics = verse.getEnglishLyrics();
        String yorubaLyrics = verse.getYorubaLyrics();

        TextView tvEnglishVerse = new TextView(this);
        tvEnglishVerse.setTextSize(22);
        tvEnglishVerse.setTextColor(getResources().getColor(R.color.custom));
        tvEnglishVerse.setPadding(0, 8, 0, 8);
        tvEnglishVerse.setText(getStyledVerseText("Verse " + verseNumber + ":", englishLyrics));
        englishContainer.addView(tvEnglishVerse);

        TextView tvYorubaVerse = new TextView(this);
        tvYorubaVerse.setTextSize(22);
        tvYorubaVerse.setTextColor(getResources().getColor(R.color.custom));
        tvYorubaVerse.setPadding(0, 8, 0, 8);
        tvYorubaVerse.setText(getStyledVerseText("Verse " + verseNumber + ":", yorubaLyrics));
        yorubaContainer.addView(tvYorubaVerse);
    }

    // Helper method to add the chorus
    private void addChorusToContainer(Hymn chorus, LinearLayout englishContainer, LinearLayout yorubaContainer) {
        String englishChorus = chorus.getEnglishLyrics();
        String yorubaChorus = chorus.getYorubaLyrics();

        TextView tvEnglishChorus = new TextView(this);
        tvEnglishChorus.setTextSize(22);
        tvEnglishChorus.setTextColor(getResources().getColor(R.color.custom));
        tvEnglishChorus.setPadding(0, 8, 0, 8);
        tvEnglishChorus.setText(getStyledVerseText("Chorus:", englishChorus));
        englishContainer.addView(tvEnglishChorus);

        TextView tvYorubaChorus = new TextView(this);
        tvYorubaChorus.setTextSize(22);
        tvYorubaChorus.setTextColor(getResources().getColor(R.color.custom));
        tvYorubaChorus.setPadding(0, 8, 0, 8);
        tvYorubaChorus.setText(getStyledVerseText("Chorus:", yorubaChorus));
        yorubaContainer.addView(tvYorubaChorus);
    }

    // Method to create styled text with bold label
    private SpannableString getStyledVerseText(String label, String lyrics) {
        String fullText = label + "\n" + lyrics + "\n";

        SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                0, label.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
