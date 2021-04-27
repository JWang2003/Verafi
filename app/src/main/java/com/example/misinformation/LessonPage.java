package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class LessonPage extends AppCompatActivity {

    TextView lessonName;
    TextView words;
    TextView website;
    TextView sources;
    TextView s_text;
    Button continue_button;

    String currentLessonID;
    int currentSectionIndex;
    int lessonSize;
    DataAccess dataAccess;
    DatabaseAccess db;
    ArrayList<Section> allSections;
    ArrayList<ImageButton> mImageButtonArray = new ArrayList<>();
    Section currentSection;

    int totalProgress;

    String lesson_content = null;
    String website_link = null;
    String text_sources = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_page);

        dataAccess = new DataAccess(LessonPage.this);
        db = DatabaseAccess.getInstance(getApplicationContext());
        processIntents();
        handle_top_bar();
        try {
            connectXML();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // TODO: DELETE THIS

    }

    private void processIntents() {
        // TODO: Make navigate to last section if progress = la
        Intent intent = getIntent();

        currentLessonID = intent.getStringExtra("lessonID");
        currentSectionIndex = intent.getIntExtra("progress", 0);
        lessonSize = intent.getIntExtra("sizeOfLesson", 0);
        allSections = dataAccess.getLessonSections(currentLessonID);
        currentSection = allSections.get(currentSectionIndex);
        totalProgress = db.getProgress(currentLessonID);
    }

    private void connectXML() throws JSONException {
        lessonName = findViewById(R.id.lesson_name);
        lessonName.setText(currentSection.name);
        words = findViewById(R.id.text);
        website = findViewById(R.id.website);
        sources = findViewById(R.id.sources);
        s_text = findViewById(R.id.s_text);
        continue_button = findViewById(R.id.continue_button);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_quiz();
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
            } else if (key.equals("sources")) {
                text_sources = currentSection.content.getString(key);
            } else {
                System.out.println("Key not found");
            }
        }

        if (lesson_content != null) {
            words.setText(lesson_content);
            words.setVisibility(View.VISIBLE);
        } if (website_link != null) {
            website.setText(website_link);
            website.setVisibility(View.VISIBLE);
        } if (text_sources != null) {
            s_text.setVisibility(View.VISIBLE);
            sources.setText(text_sources);
            sources.setVisibility(View.VISIBLE);
        }
    }

    public void go_to_quiz() {
        System.out.println("Progress is:" + currentSectionIndex);
        // Handle the case where user is redoing old lessons, don't change the progress
        if (lessonSize >= currentSectionIndex) {
            Intent intent = new Intent(LessonPage.this, QuizPage.class);
            intent.putExtra("progress", currentSectionIndex + 1);
            intent.putExtra("sizeOfLesson", lessonSize);
            intent.putExtra("lessonID", currentLessonID);
            startActivity(intent);
        }

        if (currentSectionIndex + 1 != lessonSize) {
            // Set the progress equal to the index of this lesson
            db.updateProgress(currentLessonID, currentSectionIndex + 1);
            Intent intent = new Intent(LessonPage.this, QuizPage.class);
            intent.putExtra("progress", currentSectionIndex + 1);
            intent.putExtra("sizeOfLesson", lessonSize);
            intent.putExtra("lessonID", currentLessonID);
            startActivity(intent);

        } else if (currentSectionIndex + 1 == lessonSize){
            db.updateProgress(currentLessonID, currentSectionIndex + 1);
            Intent intent = new Intent(LessonPage.this, UnitPage.class);
        }

    }

    public void handle_top_bar() {

        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_0));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_1));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_2));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_3));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_4));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_5));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_6));
        mImageButtonArray.add(findViewById(R.id.lesson_top_icon_7));

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
                            intent = new Intent(LessonPage.this, LessonPage.class);
                        } else {
                            intent = new Intent(LessonPage.this, QuizPage.class);
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