package com.example.misinformation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Lesson implements Parcelable {
    String id;
    int lessonProgress;
    String name;
    ArrayList<String> sectionNames;
    ArrayList<Integer> sectionProgress;

    public Lesson(String id, int lessonProgress, String name, ArrayList<String> sectionNames, ArrayList<Integer> sectionProgress) {
        this.id = id;
        this.lessonProgress = lessonProgress;
        this.name = name;
        this.sectionNames = sectionNames;
        this.sectionProgress = sectionProgress;
    }

    protected Lesson(Parcel in) {
        id = in.readString();
        lessonProgress = in.readInt();
        name = in.readString();
        sectionNames = in.createStringArrayList();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(lessonProgress);
        dest.writeString(name);
        dest.writeStringList(sectionNames);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", lessonProgress=" + lessonProgress +
                ", name='" + name + '\'' +
                ", sectionNames=" + sectionNames +
                ", sectionProgress=" + sectionProgress +
                '}';
    }
}

