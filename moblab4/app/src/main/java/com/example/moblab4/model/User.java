package com.example.moblab4.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class User implements Parcelable {
    public int _id;
    public String username;
    public String password;
    public int age;
    public String phoneNo;
    public String gender;
    public String dob;

    public User(int id, String username, String password, int age, String phoneNo, String gender, String dob) {
        this._id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.dob = dob;
    }

    protected User(Parcel in) {
        _id = in.readInt();
        username = in.readString();
        password = in.readString();
        age = in.readInt();
        phoneNo = in.readString();
        gender = in.readString();
        dob = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(age);
        dest.writeString(dob);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phoneNo);
        dest.writeString(gender);
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHONENO = "phoneNo";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_DOB = "dob";
    }
}
