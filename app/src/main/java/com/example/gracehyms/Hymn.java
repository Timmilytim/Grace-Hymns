package com.example.gracehyms;

public class Hymn {
    private String id;
    private String englishTitle;
    private String yorubaTitle;
    private String number;
    private String verseNumber;
    private String englishLyrics;
    private String yorubaLyrics;
    private String type;
    private boolean isFavorite;

    // Constructor
    public Hymn(String id, String englishTitle, String yorubaTitle, String number, String verseNumber,
                String englishLyrics, String yorubaLyrics, String type, boolean isFavorite) {
        this.id = id;
        this.englishTitle = englishTitle;
        this.yorubaTitle = yorubaTitle;
        this.number = number;
        this.verseNumber = verseNumber;
        this.englishLyrics = englishLyrics;
        this.yorubaLyrics = yorubaLyrics;
        this.type = type;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getYorubaTitle() {
        return yorubaTitle;
    }

    public String getNumber() {
        return number;
    }

    public String getVerseNumber() {
        return verseNumber;
    }

    public String getEnglishLyrics() {
        return englishLyrics;
    }

    public String getYorubaLyrics() {
        return yorubaLyrics;
    }

    public String getType() {
        return type;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
