package com.example.gracehyms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HymnBook.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_HYMNS = "Hymns";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ENGLISH_TITLE = "EnglishTitle";
    public static final String COLUMN_YORUBA_TITLE = "YorubaTitle";
    public static final String COLUMN_NUMBER = "Number";
    public static final String COLUMN_VERSE_NUMBER = "VerseNumber";
    public static final String COLUMN_ENGLISH_LYRICS = "EnglishLyrics";
    public static final String COLUMN_YORUBA_LYRICS = "YorubaLyrics";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_IS_FAVORITE = "IsFavorite";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database", "Database Path: " + context.getDatabasePath(DATABASE_NAME).getAbsolutePath());
        try {
            copyDatabaseFromAssets(context);
            Log.d("Database", "Database copied successfully.");
        } catch (IOException e) {
            Log.e("Database", "Error copying database from assets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_HYMNS + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_ENGLISH_TITLE + " TEXT," +
                COLUMN_YORUBA_TITLE + " TEXT," +
                COLUMN_NUMBER + " TEXT," +
                COLUMN_VERSE_NUMBER + " TEXT," +
                COLUMN_ENGLISH_LYRICS + " TEXT," +
                COLUMN_YORUBA_LYRICS + " TEXT," +
                COLUMN_TYPE + " TEXT," +
                COLUMN_IS_FAVORITE + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
        Log.d("Database", "Hymns table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            if (!isTableExists(db, TABLE_HYMNS)) {
                onCreate(db);
                Log.d("Database", "Table " + TABLE_HYMNS + " created during upgrade.");
            } else {
                Log.d("Database", "Table " + TABLE_HYMNS + " already exists. No action taken.");
            }
        }
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = cursor.getCount() > 0;
        Log.d("Database", "Table " + tableName + " exists: " + exists);
        cursor.close();
        return exists;
    }

    public List<Hymn> getAllHymns() {
        List<Hymn> hymnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HYMNS;
        Cursor cursor = db.rawQuery(query, null);

        Log.d("Database", "Query executed: " + query);
        Log.d("Database", "Cursor count: " + (cursor != null ? cursor.getCount() : 0));

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String englishTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGLISH_TITLE));
                String yorubaTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YORUBA_TITLE));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));
                String verseNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VERSE_NUMBER));
                String englishLyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGLISH_LYRICS));
                String yorubaLyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YORUBA_LYRICS));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;

                Hymn hymn = new Hymn(id, englishTitle, yorubaTitle, number, verseNumber, englishLyrics, yorubaLyrics, type, isFavorite);
                hymnList.add(hymn);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return hymnList;
    }

    public List<Hymn> getHymnVersesById(String hymnId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Hymn> hymnVerses = new ArrayList<>();

        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_HYMNS + " WHERE " + COLUMN_ID + " = ? ORDER BY " + COLUMN_VERSE_NUMBER;
            cursor = db.rawQuery(query, new String[]{hymnId});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String englishTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGLISH_TITLE));
                    String yorubaTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YORUBA_TITLE));
                    String number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));
                    String verseNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VERSE_NUMBER));
                    String englishLyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGLISH_LYRICS));
                    String yorubaLyrics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YORUBA_LYRICS));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                    boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;

                    Hymn hymn = new Hymn(id, englishTitle, yorubaTitle, number, verseNumber, englishLyrics, yorubaLyrics, type, isFavorite);
                    hymnVerses.add(hymn);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Database", "Error fetching hymn verses by ID: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return hymnVerses;
    }



    private void copyDatabaseFromAssets(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("databases/HymnBook.db");
        File outFile = new File(context.getDatabasePath(DATABASE_NAME).getAbsolutePath());

        // Force replace the database
        if (outFile.exists()) {
            outFile.delete();
            Log.d("Database", "Database already exists. Overwriting.");
        }

        FileOutputStream outputStream = new FileOutputStream(outFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        Log.d("Database", "Database copied to " + outFile.getAbsolutePath());
    }

    public void testDatabaseAccess() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check table existence
        boolean exists = isTableExists(db, TABLE_HYMNS);
        Log.d("Database", "Hymns table existence check: " + exists);

        // Test query
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_HYMNS, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            Log.d("Database", "Hymns table row count: " + count);
        } else {
            Log.d("Database", "Hymns table query returned no rows.");
        }
        cursor.close();

        db.close();
    }
}
