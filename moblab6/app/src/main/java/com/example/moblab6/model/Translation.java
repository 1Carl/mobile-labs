package com.example.moblab6.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class Translation implements Parcelable {
    public int _id;
    public String mongolian;
    public String foreign;

    public Translation(int _id, String mongolian, String foreign) {
        this._id = _id;
        this.mongolian = mongolian;
        this.foreign = foreign;
    }

    protected Translation(Parcel in) {
        _id = in.readInt();
        mongolian = in.readString();
        foreign = in.readString();
    }

    public static final Creator<Translation> CREATOR = new Creator<Translation>() {
        @Override
        public Translation createFromParcel(Parcel in) {
            return new Translation(in);
        }

        @Override
        public Translation[] newArray(int size) {
            return new Translation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(mongolian);
        dest.writeString(foreign);
    }

    public static class TranslationEntry implements BaseColumns {
        public static final String TABLE_NAME = "translation";
        public static final String COLUMN_MONGOLIAN = "mongolian";
        public static final String COLUMN_FOREIGN = "fore";
    }
}
