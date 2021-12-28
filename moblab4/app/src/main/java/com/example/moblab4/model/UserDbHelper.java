package com.example.moblab4.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + User.UserEntry.TABLE_NAME + " (" +
                    User.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    User.UserEntry.COLUMN_USERNAME + " TEXT," +
                    User.UserEntry.COLUMN_AGE + " INTEGER," +
                    User.UserEntry.COLUMN_DOB + " INTEGER," +
                    User.UserEntry.COLUMN_GENDER + " TEXT," +
                    User.UserEntry.COLUMN_PHONENO + " TEXT," +
                    User.UserEntry.COLUMN_PASSWORD + " TEXT)";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + User.UserEntry.TABLE_NAME;
    private String[] projection = {
            User.UserEntry._ID,
            User.UserEntry.COLUMN_USERNAME,
            User.UserEntry.COLUMN_PASSWORD,
            User.UserEntry.COLUMN_AGE,
            User.UserEntry.COLUMN_PHONENO,
            User.UserEntry.COLUMN_GENDER,
            User.UserEntry.COLUMN_DOB,
    };
    private UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public static User getUser(String username, String password, Context c) throws Resources.NotFoundException {
        UserDbHelper udbh = new UserDbHelper(c);
        SQLiteDatabase db = udbh.getReadableDatabase();
        String selection = User.UserEntry.COLUMN_USERNAME + " = ? and " + User.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cur = db.query(User.UserEntry.TABLE_NAME, udbh.projection, selection, selectionArgs, null, null, null);
        int rowCount = cur.getCount();
        if (rowCount > 0) {
            cur.moveToFirst();
            User u = new User(cur.getInt(0),cur.getString(1), cur.getString(2), cur.getInt(3), cur.getString(4), cur.getString(5), cur.getString(6));
            return u;
        }
        throw new Resources.NotFoundException();
    }

    public static boolean postUser(String username, String password, Context c) {
        UserDbHelper udbh = new UserDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        String selection = User.UserEntry.COLUMN_USERNAME + " = ? and " + User.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cur = db.query(User.UserEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int rowCount = cur.getCount();
        if (rowCount > 0) {
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(User.UserEntry.COLUMN_USERNAME, username);
        cv.put(User.UserEntry.COLUMN_PASSWORD, password);
        cv.put(User.UserEntry.COLUMN_AGE, 0);
        cv.put(User.UserEntry.COLUMN_PHONENO, "");
        cv.put(User.UserEntry.COLUMN_GENDER, "");
        cv.put(User.UserEntry.COLUMN_DOB, "1970/01/01");
        long newRowID = db.insert(User.UserEntry.TABLE_NAME, null, cv);
        if(newRowID == -1) {
            return false;
        }
        return true;
    }

    public static boolean putUser(User u, Context c) {
        UserDbHelper udbh = new UserDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        String selection = User.UserEntry._ID + " = ?";
        String[] selectionArgs = {"" + u._id};
        ContentValues cv = new ContentValues();
        cv.put(User.UserEntry.COLUMN_USERNAME, u.username);
        cv.put(User.UserEntry.COLUMN_PASSWORD, u.password);
        cv.put(User.UserEntry.COLUMN_AGE, u.age);
        cv.put(User.UserEntry.COLUMN_PHONENO, u.phoneNo);
        cv.put(User.UserEntry.COLUMN_GENDER, u.gender);
        cv.put(User.UserEntry.COLUMN_DOB, u.dob);
        int count = db.update(User.UserEntry.TABLE_NAME, cv, selection, selectionArgs);
        if(count < 1) {
            return false;
        }
        return true;
    }

    public static boolean usernameAvailable(String username, Context c) {
        UserDbHelper udbh = new UserDbHelper(c);
        SQLiteDatabase db = udbh.getReadableDatabase();
        String selection = User.UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cur = db.query(User.UserEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int rowCount = cur.getCount();
        if (rowCount > 0) {
            return false;
        }
        return true;
    }
}
