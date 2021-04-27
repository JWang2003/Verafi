package com.example.misinformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizPage extends AppCompatActivity {

    TextView lessonName;
    TextView words;
    TextView website;
    TextView s_text;
    TextView description;
    ArrayList<Button> buttonChoices = new ArrayList<>();
    Button continue_button;

    String currentLessonID;
    int currentSectionProgress;
    int lessonSize;
    DataAccess dataAccess;
    DatabaseAccess db;
    ArrayList<Section> allSections;
    ArrayList<QuizOption> options = new ArrayList<>();
    Section currentSection;

    String lesson_content = null;
    String website_link = null;
    String description_text = null;

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

        try {
            setUpQuiz();
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
        description = findViewById(R.id.description);
        description.setText(currentSection.content.getString("text"));
        buttonChoices.add(findViewById(R.id.option1));
        buttonChoices.add(findViewById(R.id.option2));
        buttonChoices.add(findViewById(R.id.option3));
        buttonChoices.add(findViewById(R.id.option4));
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
            } else if (key.equals("description")) {
                description_text = currentSection.content.getString(key);
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
        }
        if (description_text != null) {
            description.setText(description_text);
            description.setVisibility(View.VISIBLE);
        }
        System.out.println(currentSection.choices);
        System.out.println(currentSection.choices.getString(1));
        // SET UP QUESTIONS
        for (int i = 0; i < currentSection.choices.length(); i++) {
            options.add(new QuizOption(currentSection.choices.getString(i)));
        }
        for (int i = 0; i < currentSection.correct.length(); i++) {
            options.get(currentSection.correct.getInt(i) - 1).isCorrect = true;
        }

        for (int i = 0; i < options.size(); i++) {
            buttonChoices.get(i).setText(options.get(i).text);
            buttonChoices.get(i).setVisibility(View.VISIBLE);
        }

        for (int i = 0 ; i < buttonChoices.size(); i++) {
            buttonChoices.get(i).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    int buttonIndex = buttonChoices.indexOf(v);
                    if (options.get(buttonIndex).isCorrect) {
                        buttonChoices.get(buttonIndex).setBackgroundTintList(QuizPage.this.getResources().getColorStateList(R.color.blue));
                        Toast.makeText(QuizPage.this, "Correct!", Toast.LENGTH_SHORT).show();
                        continue_button.setEnabled(true);
                        continue_button.setBackgroundTintList(QuizPage.this.getResources().getColorStateList(R.color.green));
                    } else {
                        buttonChoices.get(buttonIndex).setBackgroundTintList(QuizPage.this.getResources().getColorStateList(R.color.red));
                        Toast.makeText(QuizPage.this, "Incorrect! Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizPage.this, WebPage.class);
                intent.putExtra("url", website_link);
                startActivity(intent);
            }
        });
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

    public void setUpQuiz() throws JSONException {
        Iterator<String> keys = currentSection.content.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (key.equals("text")) {
                lesson_content = currentSection.content.getString(key);
            } else if (key.equals("website")) {
                website_link = currentSection.content.getString(key);
            } else if (key.equals("description")) {
                description_text = currentSection.content.getString(key);
            } else {
                System.out.println("Key not found");
            }
        }
    }
}