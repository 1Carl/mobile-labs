package com.example.mobexam.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mobexam.model.Car;

import java.util.ArrayList;

public class CarDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Car.CarEntry.TABLE_NAME + " (" +
                    Car.CarEntry._ID + " INTEGER PRIMARY KEY," +
                    Car.CarEntry.COLUMN_MARK + " TEXT," +
                    Car.CarEntry.COLUMN_UNIQUENUMBER + " TEXT," +
                    Car.CarEntry.COLUMN_OWNERID + " INTEGER," +
                    Car.CarEntry.COLUMN_MOTORVOLUME + " INTEGER)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cars.db";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Car.CarEntry.TABLE_NAME;
    private String[] projection = {
            Car.CarEntry._ID,
            Car.CarEntry.COLUMN_MARK,
            Car.CarEntry.COLUMN_UNIQUENUMBER,
            Car.CarEntry.COLUMN_OWNERID,
            Car.CarEntry.COLUMN_MOTORVOLUME,
    };

    public CarDBHelper(Context context) {
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

    public static Car getCar(int id, Context c) throws Resources.NotFoundException {
        CarDBHelper cdbh = new CarDBHelper(c);
        SQLiteDatabase db = cdbh.getReadableDatabase();
        String selection = Car.CarEntry._ID + " = ?";
        String[] selargs = new String[]{String.valueOf(id)};

        Cursor cur = db.query(Car.CarEntry.TABLE_NAME, cdbh.projection, selection, selargs, null, null, null);
        int rowc = cur.getCount();
        if(rowc > 0) {
            cur.moveToFirst();
            Car car = new Car(cur.getInt(0), cur.getString(1), cur.getString(2), OwnerDBHelper.getOwner(cur.getInt(3), c), cur.getInt(4));
            return car;
        }
        throw new Resources.NotFoundException();
    }

    public static ArrayList<Car> getcars(Context c) {
        CarDBHelper cdbh = new CarDBHelper(c);
        SQLiteDatabase db = cdbh.getReadableDatabase();
        Cursor cur = db.query(Car.CarEntry.TABLE_NAME, cdbh.projection, null, null, null, null, null);
        ArrayList<Car> cars = new ArrayList<>();

        while (cur.moveToNext()) {
            cars.add(new Car(cur.getInt(0), cur.getString(1), cur.getString(2), OwnerDBHelper.getOwner(cur.getInt(3), c), cur.getInt(4)));
        }

        cur.close();

        return cars;
    }

    public static Boolean postCar(String mark, int motorvolume, String uniquenum, int ownerID, Context c) {
        CarDBHelper cdbh = new CarDBHelper(c);
        SQLiteDatabase db = cdbh.getWritableDatabase();
        String selection = Car.CarEntry.COLUMN_UNIQUENUMBER + " = ?";
        String[] selargs = new String[]{uniquenum};

        Cursor cur = db.query(Car.CarEntry.TABLE_NAME, cdbh.projection, selection, selargs, null, null, null);
        int rowc = cur.getCount();
        if(rowc > 0) {
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(Car.CarEntry.COLUMN_MARK, mark);
        cv.put(Car.CarEntry.COLUMN_MOTORVOLUME, motorvolume);
        cv.put(Car.CarEntry.COLUMN_UNIQUENUMBER, uniquenum);
        cv.put(Car.CarEntry.COLUMN_OWNERID, ownerID);
        long newRowID = db.insert(Car.CarEntry.TABLE_NAME, null, cv);
        if(newRowID == -1) {
            return false;
        }
        return true;
    }

    public static boolean deleteCar(int id, Context c) {
        CarDBHelper cdbh = new CarDBHelper(c);
        SQLiteDatabase db = cdbh.getWritableDatabase();
        String selection = Car.CarEntry._ID + " = ?";
        String[] selargs = {"" + id};

        int deletedRows = db.delete(Car.CarEntry.TABLE_NAME, selection, selargs);
        if (deletedRows == 1) {
            return true;
        }

        else return false;
    }

}
