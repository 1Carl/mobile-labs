package com.example.moblab4a.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TranslationDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Translation.TranslationEntry.TABLE_NAME + " (" +
                    Translation.TranslationEntry._ID + " INTEGER PRIMARY KEY," +
                    Translation.TranslationEntry.COLUMN_MONGOLIAN + " TEXT," +
                    Translation.TranslationEntry.COLUMN_FOREIGN + " TEXT)";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "translations.db";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Translation.TranslationEntry.TABLE_NAME;
    private String[] projection = {
            Translation.TranslationEntry._ID,
            Translation.TranslationEntry.COLUMN_MONGOLIAN,
            Translation.TranslationEntry.COLUMN_FOREIGN,
    };
    private TranslationDbHelper(Context context) {
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
    public static ArrayList<Translation> getAllTranslations(Context c) throws Resources.NotFoundException {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getReadableDatabase();
        String order = null;
        Cursor cur = db.query(Translation.TranslationEntry.TABLE_NAME, udbh.projection, null, null, null, null, order);
        ArrayList<Translation> ts = new ArrayList<>();
        while (cur.moveToNext()) {
                ts.add(new Translation(cur.getInt(0),cur.getString(1), cur.getString(2)));
        }
        cur.close();
        return ts;
    }

    public static long postTranslation(String mongolian, String foreign, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Translation.TranslationEntry.COLUMN_MONGOLIAN, mongolian);
        cv.put(Translation.TranslationEntry.COLUMN_FOREIGN, foreign);
        long newRowID = db.insert(Translation.TranslationEntry.TABLE_NAME, null, cv);
        return newRowID;
    }

    public static boolean putTranslation(Translation u, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        String selection = Translation.TranslationEntry._ID + " = ?";
        String[] selectionArgs = {"" + u._id};
        ContentValues cv = new ContentValues();
        cv.put(Translation.TranslationEntry.COLUMN_MONGOLIAN, u.mongolian);
        cv.put(Translation.TranslationEntry.COLUMN_FOREIGN, u.foreign);
        int count = db.update(Translation.TranslationEntry.TABLE_NAME, cv, selection, selectionArgs);
        if(count < 1) {
            return false;
        }
        return true;
    }

    se
}
