package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnitPage extends AppCompatActivity {

    JSONArray lessons;
    // TODO: Generate all section names for each lesson
    ArrayList<String> sectionNames;
    Unit unit;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_page);

        processIntents();
        connectXML();
        connectButtons();
        DataAccess dataAccess = new DataAccess(UnitPage.this);

        // TODO: Currently we are just getting the first lesson -> introduction
        sectionNames = dataAccess.getSectionNames("Introduction");
        System.out.println(sectionNames);


    }
    private void processIntents() {
        Intent intent = getIntent();
        unit = intent.getParcelableExtra("unit");
        System.out.println(unit);
        System.out.println("PRINTED UNIT");
        String jsonArray = intent.getStringExtra("jsonArray");
        try {
            lessons = new JSONArray(jsonArray);
            System.out.println(lessons.toString());
            System.out.println(lessons.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void connectXML() {
        nextPage = findViewById(R.id.go_lesson);
    }

    private void connectButtons() {
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Be able to tell if we must open a quiz or lesson page based on progress, determine what section we need to get
                String typeOfLesson = "lesson";
                String lessonName = "Introduction";
                String sectionName = sectionNames.get(0);

                if (typeOfLesson.equals("lesson")) {
                    Intent intent = new Intent(UnitPage.this, LessonPage.class);
                    intent.putExtra("sectionName", sectionName);
                    intent.putExtra("lessonName", lessonName);
                    startActivity(intent);
                }

                else if (typeOfLesson.equals("quiz")) {
                    Intent intent = new Intent(UnitPage.this, QuizPage.class);
                    intent.putExtra("sectionName", sectionName);
                    intent.putExtra("lessonName", lessonName);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(UnitPage.this, "Something went wrong in UnitPage.java", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}