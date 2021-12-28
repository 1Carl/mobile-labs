package com.example.mobexam.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Owner implements Parcelable {
    private int _id;
    private String  fname, lname, rnumber;

    public Owner(int _id, String fname, String lname, String rnumber) {
        this._id = _id;
        this.fname = fname;
        this.lname = lname;
        this.rnumber = rnumber;
    }

    protected Owner(Parcel in) {
        _id = in.readInt();
        fname = in.readString();
        lname = in.readString();
        rnumber = in.readString();
    }

    public static class OwnerEntry implements BaseColumns {
        public static final String TABLE_NAME = "owners";
        public static final String COLUMN_FNAME = "fname";
        public static final String COLUMN_LNAME = "lname";
        public static final String COLUMN_RNUMBER = "rnumber";
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRnumber() {
        return rnumber;
    }

    public void setRnumber(String rnumber) {
        this.rnumber = rnumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(rnumber);
    }

    @Override
    public String toString() {
        return fname + " " + lname;
    }
}
