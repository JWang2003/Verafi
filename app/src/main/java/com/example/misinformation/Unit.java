package com.example.misinformation;

import org.json.JSONArray;

public class Unit {
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

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", progress=" + progress +
                ", lessons=" + lessons +
                '}';
    }
}
