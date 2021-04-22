package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

public class LessonPage extends AppCompatActivity {

    String currentLesson;
    String currentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_page);
        processIntents();
    }

    private void processIntents() {
        Intent intent = getIntent();

        currentLesson = intent.getStringExtra("lessonName");
        currentSection = intent.getStringExtra("sectionName");
        System.out.println(currentSection);
        System.out.println(currentLesson);

    }
}