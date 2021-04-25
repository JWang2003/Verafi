package com.example.misinformation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseAccess {

    Context context;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.example.misinformation.DatabaseAccess instance;
    Cursor c = null;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public static com.example.misinformation.DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new com.example.misinformation.DatabaseAccess(context);
        }
        return instance;
    }

    // To open the database
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    // To close the database connection
    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

}
