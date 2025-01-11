package com.example.gracehyms;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "HymnBook.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}