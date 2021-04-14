package com.example.misinformation;

import org.json.JSONArray;
import org.json.JSONObject;

public class Section {
    String type;
    String name;
    JSONObject content;
    JSONArray choices;
    JSONArray correct;

    // Constructor for lesson
    public Section(String type, String name, JSONObject content) {
        this.type = type;
        this.name = name;
        this.content = content;
    }

    // Constructor for quiz
    public Section(String type, String name, JSONObject content, JSONArray choices, JSONArray correct) {
        this.type = type;
        this.name = name;
        this.content = content;
        this.choices = choices;
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Section{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", content=" + content +
                ", choices=" + choices +
                ", correct=" + correct +
                '}';
    }
}
