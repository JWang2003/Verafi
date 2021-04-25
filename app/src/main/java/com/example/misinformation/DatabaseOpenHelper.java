package com.example.misinformation;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

// https://www.youtube.com/watch?v=rziyVBKEU50 Set up database

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "progress.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("DATABASE CREATED");
    }
}
