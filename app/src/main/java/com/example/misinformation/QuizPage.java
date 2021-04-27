package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;

import java.util.ArrayList;

public class QuizPage extends AppCompatActivity {

    String currentLessonID;
    int currentSectionIndex;
    int lessonSize;
    DataAccess dataAccess;
    DatabaseAccess db;
    ArrayList<Section> allSections;
    ArrayList<ImageButton> mImageButtonArray = new ArrayList<>();
    Section currentSection;

    int totalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        dataAccess = new DataAccess(QuizPage.this);
        db = DatabaseAccess.getInstance(getApplicationContext());
        processIntents();
        handle_top_bar();
//        try {
//            connectXML();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void processIntents() {
        Intent intent = getIntent();

        currentLessonID = intent.getStringExtra("lessonID");
        currentSectionIndex = intent.getIntExtra("progress", 0);
        lessonSize = intent.getIntExtra("sizeOfLesson", 0);
        allSections = dataAccess.getLessonSections(currentLessonID);
        currentSection = allSections.get(currentSectionIndex);
        totalProgress = db.getProgress(currentLessonID);
    }

    public void handle_top_bar() {

        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_0));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_1));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_2));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_3));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_4));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_5));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_6));
        mImageButtonArray.add(findViewById(R.id.quiz_top_icon_7));

        for (int i = 0; i < allSections.size(); i++) {
            mImageButtonArray.get(i).setVisibility(View.VISIBLE);
            if (i % 2 == 0) {
                mImageButtonArray.get(i).setImageResource(R.drawable.lesson_pic);
            } else {
                mImageButtonArray.get(i).setImageResource(R.drawable.quiz_pic);
            }
            if (i < totalProgress) {
                mImageButtonArray.get(i).setColorFilter(0xff4ABD80);
            }
            if (i == currentSectionIndex) {
                mImageButtonArray.get(i).setColorFilter(0xff0978BE);
            }
        }

        for (int i = 0; i < mImageButtonArray.size(); i++) {
            mImageButtonArray.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonIndex = mImageButtonArray.indexOf(v);
                    Intent intent;
                    if (buttonIndex <= totalProgress) {
                        if (buttonIndex % 2 == 0) {
                            intent = new Intent(QuizPage.this, LessonPage.class);
                        } else {
                            intent = new Intent(QuizPage.this, QuizPage.class);
                        }
                        intent.putExtra("progress", buttonIndex);
                        intent.putExtra("sizeOfLesson", lessonSize);
                        intent.putExtra("lessonID", currentLessonID);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}