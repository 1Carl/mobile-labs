package com.example.moblab6.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

public class TranslationConsumer {
    public static String[] projection = {
            Translation.TranslationEntry._ID,
            Translation.TranslationEntry.COLUMN_MONGOLIAN,
            Translation.TranslationEntry.COLUMN_FOREIGN,
    };

    public static final String AUTHORITY = "com.example.moblab5.provider.TranslationProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/translations");


    public static ArrayList<Translation> getAllTranslations(Context c) throws Resources.NotFoundException {
        Cursor cur = c.getContentResolver().query(CONTENT_URI, projection, null, null, null);
        ArrayList<Translation> ts = new ArrayList<>();
        while (cur != null && cur.moveToNext()) {
                ts.add(new Translation(cur.getInt(0),cur.getString(1), cur.getString(2)));
        }
        if(cur != null)
        cur.close();
        return ts;
    }


    public static long postTranslation(String mongolian, String foreign, Context c) {
        ContentValues cv = new ContentValues();
        cv.put(Translation.TranslationEntry.COLUMN_MONGOLIAN, mongolian);
        cv.put(Translation.TranslationEntry.COLUMN_FOREIGN, foreign);
        c.getContentResolver().insert(CONTENT_URI, cv);
        return 0L;
    }


    public static boolean putTranslation(Translation u, Context c) {
        String selection = Translation.TranslationEntry._ID + " = ?";
        String[] selectionArgs = {"" + u._id};
        ContentValues cv = new ContentValues();
        cv.put(Translation.TranslationEntry.COLUMN_MONGOLIAN, u.mongolian);
        cv.put(Translation.TranslationEntry.COLUMN_FOREIGN, u.foreign);
        int count = c.getContentResolver().update(CONTENT_URI, cv, selection, selectionArgs);
        if(count < 1) {
            return false;
        }
        return true;
    }

    public static boolean deleteTranslation(int id, Context c) {
        String selection = Translation.TranslationEntry._ID + " = ?";
        String[] selectionArgs = {"" + id};
        int deletedRows = c.getContentResolver().delete(CONTENT_URI, selection, selectionArgs);
        if(deletedRows == 1) {
            return  true;
        }
        else return false;
    }
}
