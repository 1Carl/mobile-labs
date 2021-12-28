package com.example.moblab5.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public String[] projection = {
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

    public static Cursor query(Context c, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Resources.NotFoundException {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getReadableDatabase();
        Cursor cur = db.query(Translation.TranslationEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        ArrayList<Translation> ts = new ArrayList<>();
        return cur;
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

    public static long postTranslation(ContentValues cv, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
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

    public static boolean putTranslation(ContentValues cv, String selection, String[] selectionArgs, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        int count = db.update(Translation.TranslationEntry.TABLE_NAME, cv, selection, selectionArgs);
        if(count < 1) {
            return false;
        }
        return true;
    }

    public static boolean deleteTranslation(int id, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        String selection = Translation.TranslationEntry._ID + " = ?";
        String[] selectionArgs = {"" + id};
        int deletedRows = db.delete(Translation.TranslationEntry.TABLE_NAME, selection, selectionArgs);
        if(deletedRows == 1) {
            return  true;
        }
        else return false;
    }

    public static boolean deleteTranslation(String selection, String[] selectionArgs, Context c) {
        TranslationDbHelper udbh = new TranslationDbHelper(c);
        SQLiteDatabase db = udbh.getWritableDatabase();
        int deletedRows = db.delete(Translation.TranslationEntry.TABLE_NAME, selection, selectionArgs);
        if(deletedRows == 1) {
            return  true;
        }
        else return false;
    }
}
