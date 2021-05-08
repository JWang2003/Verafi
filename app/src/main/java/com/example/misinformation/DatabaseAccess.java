package com.example.misinformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.example.misinformation.DatabaseAccess instance;
    Cursor c = null;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
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

    public int getProgress(String id) {
        int progress = -1;
        open();
        c = db.rawQuery("select * from Progress where ID = '" + id + "'", new String[]{});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                progress = c.getInt(c.getColumnIndex("CurrentProgress"));
            }
        }
         else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
         close();
        return progress;
    }

    public void updateProgress(String id, int prog) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CurrentProgress", prog);
        db.update("Progress", contentValues, "ID = ?", new String[]{String.valueOf(id)});
        System.out.println("UPDATED");
        close();
    }

}
