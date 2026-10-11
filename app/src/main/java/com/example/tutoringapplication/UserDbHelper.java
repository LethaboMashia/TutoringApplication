package com.example.tutoringapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

/** Local SQLite store for registered users. Only salted password hashes are stored. */
public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tutoring.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "full_name";
    private static final String COL_EMAIL = "email";
    private static final String COL_HASH = "password_hash";
    private static final String COL_SALT = "salt";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COL_HASH + " TEXT NOT NULL, "
                + COL_SALT + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private static String normalise(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    public boolean emailExists(String email) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query(TABLE_USERS, new String[]{COL_ID}, COL_EMAIL + " = ?",
                new String[]{normalise(email)}, null, null, null)) {
            return c.moveToFirst();
        }
    }

    /** @return true if the account was created, false if the email is already registered */
    public boolean registerUser(String fullName, String email, String password) {
        if (emailExists(email)) {
            return false;
        }
        String salt = PasswordHasher.generateSalt();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, fullName.trim());
        values.put(COL_EMAIL, normalise(email));
        values.put(COL_SALT, salt);
        values.put(COL_HASH, PasswordHasher.hash(password, salt));
        return getWritableDatabase().insert(TABLE_USERS, null, values) != -1;
    }

    /** @return the user's full name if the credentials are correct, otherwise null */
    public String authenticate(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query(TABLE_USERS, new String[]{COL_NAME, COL_HASH, COL_SALT},
                COL_EMAIL + " = ?", new String[]{normalise(email)}, null, null, null)) {
            if (!c.moveToFirst()) {
                return null;
            }
            String name = c.getString(0);
            String hash = c.getString(1);
            String salt = c.getString(2);
            return PasswordHasher.verify(password, salt, hash) ? name : null;
        }
    }
}
