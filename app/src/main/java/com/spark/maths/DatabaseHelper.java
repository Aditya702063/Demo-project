package com.spark.maths;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create user table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "username TEXT, " +
                "gender TEXT, " +
                "image_path TEXT)";
        db.execSQL(CREATE_TABLE);

        // Insert one dummy user to start with
        ContentValues values = new ContentValues();
        values.put("name", "John Doe");
        values.put("username", "john123");
        values.put("gender", "Male");
        values.put("image_path", "");
        db.insert(TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Fetch user
    public Cursor getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " LIMIT 1", null);
    }

    // Update user
    public boolean updateUser(String name, String username, String gender, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("username", username);
        values.put("gender", gender);
        values.put("image_path", imagePath);

        // Always update the first row (id = 1 or use LIMIT 1)
        Cursor cursor = getUser();
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int rows = db.update(TABLE_USER, values, "id = ?", new String[]{String.valueOf(id)});
            cursor.close();
            return rows > 0;
        } else {
            cursor.close();
            return false;
        }
    }
}
