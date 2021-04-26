package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class LessonPage extends AppCompatActivity {

    String currentLessonID;
    int currentSectionIndex;
    int lessonSize;
    DataAccess dataAccess;
    ArrayList<Section> allSections;
    Section currentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_page);
        dataAccess = new DataAccess(LessonPage.this);
        processIntents();
    }

    private void processIntents() {
        // TODO: Make navigate to last section if progress = la
        Intent intent = getIntent();

        currentLessonID = intent.getStringExtra("lessonID");
        currentSectionIndex = intent.getIntExtra("progress", 0);
        lessonSize = intent.getIntExtra("sizeOfLesson", 0);
        System.out.println(currentSectionIndex);
        allSections = dataAccess.getLessonSections(currentLessonID);
        currentSection = allSections.get(currentSectionIndex);
        System.out.println(currentSection);
    }
}