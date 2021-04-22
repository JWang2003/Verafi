package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class QuizPage extends AppCompatActivity {

    String currentLesson;
    String currentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
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