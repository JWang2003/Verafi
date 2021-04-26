package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizPage extends AppCompatActivity {

    TextView lessonName;
    TextView words;
    TextView website;
    TextView s_text;
    Button continue_button;

    String currentLessonID;
    int currentSectionProgress;
    int lessonSize;
    DataAccess dataAccess;
    DatabaseAccess db;
    ArrayList<Section> allSections;
    Section currentSection;

    String lesson_content = null;
    String website_link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        dataAccess = new DataAccess(QuizPage.this);
        db = db.getInstance(getApplicationContext());

        processIntents();
        try {
            connectXML();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processIntents() {
        Intent intent = getIntent();
        currentLessonID = intent.getStringExtra("lessonID");
        currentSectionProgress = intent.getIntExtra("progress", 0);
        lessonSize = intent.getIntExtra("sizeOfLesson", 0);
        allSections = dataAccess.getLessonSections(currentLessonID);
        currentSection = allSections.get(currentSectionProgress);
        System.out.println(currentSection);
    }
    private void connectXML() throws JSONException {
        lessonName = findViewById(R.id.lesson_name);
        lessonName.setText(currentSection.name);
        words = findViewById(R.id.text);
        website = findViewById(R.id.website);
        s_text = findViewById(R.id.s_text);
        continue_button = findViewById(R.id.continue_button);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_lesson();
            }
        });

        // SET THE LESSON CONTENT
        Iterator<String> keys = currentSection.content.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (key.equals("text")) {
                lesson_content = currentSection.content.getString(key);
            } else if (key.equals("website")) {
                website_link = currentSection.content.getString(key);
            }  else {
                System.out.println("Key not found");
            }
        }

        if (lesson_content != null) {
            words.setText(lesson_content);
            words.setVisibility(View.VISIBLE);
        } if (website_link != null) {
            website.setText(website_link);
            website.setVisibility(View.VISIBLE);
        }
    }

    public void go_to_lesson() {
        System.out.println("Progress is:" + currentSectionProgress);
        // Handle the case where user is redoing old lessons, don't change the progress
//        if (lessonSize >= currentSectionProgress) {
//            Intent intent = new Intent(QuizPage.this, LessonPage.class);
//            intent.putExtra("progress", currentSectionProgress + 1);
//            intent.putExtra("sizeOfLesson", lessonSize);
//            intent.putExtra("lessonID", currentLessonID);
//            startActivity(intent);
//        }

        if (currentSectionProgress + 1 != lessonSize) {
            // Set the progress equal to the index of this lesson
            db.updateProgress(currentLessonID, currentSectionProgress + 1);
            Intent intent = new Intent(QuizPage.this, LessonPage.class);
            intent.putExtra("progress", currentSectionProgress + 1);
            intent.putExtra("sizeOfLesson", lessonSize);
            intent.putExtra("lessonID", currentLessonID);
            startActivity(intent);

        } else if (currentSectionProgress + 1 == lessonSize){
            db.updateProgress(currentLessonID, currentSectionProgress + 1);
            Intent intent = new Intent(QuizPage.this, UnitPage.class);
            startActivity(intent);
        }

    }
}