package com.example.mobexam.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mobexam.model.Owner;

import java.util.ArrayList;

public class OwnerDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Owner.OwnerEntry.TABLE_NAME + " (" +
                    Owner.OwnerEntry._ID + " INTEGER PRIMARY KEY," +
                    Owner.OwnerEntry.COLUMN_FNAME + " TEXT," +
                    Owner.OwnerEntry.COLUMN_LNAME + " TEXT," +
                    Owner.OwnerEntry.COLUMN_RNUMBER + " TEXT)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "owners.db";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Owner.OwnerEntry.TABLE_NAME;
    private String[] projection = {
            Owner.OwnerEntry._ID,
            Owner.OwnerEntry.COLUMN_FNAME,
            Owner.OwnerEntry.COLUMN_LNAME,
            Owner.OwnerEntry.COLUMN_RNUMBER
    };

    public OwnerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Owner getOwner(int id, Context c) throws Resources.NotFoundException {
        OwnerDBHelper helper = new OwnerDBHelper(c);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = Owner.OwnerEntry._ID + " = ?";
        String[] selargs = new String[]{String.valueOf(id)};

        Cursor cur = db.query(Owner.OwnerEntry.TABLE_NAME, helper.projection, selection, selargs, null, null, null);
        int rowc = cur.getCount();
        if(rowc > 0) {
            cur.moveToFirst();

            Owner owner = new Owner(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3));
            return owner;
        }
        throw new Resources.NotFoundException();
    }

    public static boolean postOwner(String fname, String lname, String rnumber, Context c) {
        OwnerDBHelper helper = new OwnerDBHelper(c);
        SQLiteDatabase db = helper.getWritableDatabase();
        String selection = Owner.OwnerEntry.COLUMN_RNUMBER + " = ?";
        String[] selargs = new String[]{rnumber};

        Cursor cur = db.query(Owner.OwnerEntry.TABLE_NAME, helper.projection, selection, selargs, null, null, null);
        int rowc = cur.getCount();
        if(rowc > 0) {
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(Owner.OwnerEntry.COLUMN_FNAME, fname);
        cv.put(Owner.OwnerEntry.COLUMN_LNAME, lname);
        cv.put(Owner.OwnerEntry.COLUMN_RNUMBER, rnumber);
        long nrid = db.insert(Owner.OwnerEntry.TABLE_NAME, null, cv);
        if(nrid == -1) {
            return false;
        }
        return true;
    }

    public static ArrayList<Owner> getowners(Context c) {
        OwnerDBHelper helper = new OwnerDBHelper(c);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cur = db.query(Owner.OwnerEntry.TABLE_NAME, helper.projection, null, null, null, null, null);
        ArrayList<Owner> owners = new ArrayList<>();
        while (cur.moveToNext()) {
            owners.add(new Owner(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3)));
        }
        cur.close();
        return owners;
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
}
