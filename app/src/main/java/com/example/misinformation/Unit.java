package com.example.misinformation;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

public class Unit implements Parcelable {
    String id;
    String name;
    int progress;
    JSONArray lessons;

    public Unit(String id, String name, int progress, JSONArray lessons) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.lessons = lessons;
    }

    protected Unit(Parcel in) {
        id = in.readString();
        name = in.readString();
        progress = in.readInt();
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", progress=" + progress +
                ", lessons=" + lessons +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(progress);
    }
}
