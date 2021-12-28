package com.example.mobexam.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class Car implements Parcelable {
    private int _id;
    private String mark;
    private int motorvolume;
    private String uniquenumber;
    private Owner owner;

    protected Car(Parcel in) {
        _id = in.readInt();
        mark = in.readString();
        motorvolume = in.readInt();
        uniquenumber = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getMotorvolume() {
        return motorvolume;
    }

    public void setMotorvolume(int motorvolume) {
        this.motorvolume = motorvolume;
    }

    public String getUniquenumber() {
        return uniquenumber;
    }

    public void setUniquenumber(String uniquenumber) {
        this.uniquenumber = uniquenumber;
    }

    public Car(int _id, String mark, String uniqueNumber, Owner owner, int motorvolume) {
        this._id = _id;
        this.mark = mark;
        this.motorvolume = motorvolume;
        this.uniquenumber = uniqueNumber;
        this.owner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(mark);
        dest.writeInt(motorvolume);
        dest.writeString(uniquenumber);
        dest.writeParcelable(owner, flags);
    }

    public static class CarEntry implements BaseColumns {
        public static final String TABLE_NAME = "cars";
        public static final String COLUMN_MARK = "mark";
        public static final String COLUMN_MOTORVOLUME = "motorvolume";
        public static final String COLUMN_UNIQUENUMBER = "uniquenumber";
        public static final String COLUMN_OWNERID = "ownerid";
    }

    @Override
    public String toString() {
        return uniquenumber + " " + mark;
    }
}
