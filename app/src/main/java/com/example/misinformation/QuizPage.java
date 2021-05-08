package com.example.misinformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizPage extends AppCompatActivity {
    TextView lessonName;
    TextView words;
    TextView website;
    TextView description;
    Button continue_button;
    ImageButton back_button;

    DataAccess dataAccess;
    DatabaseAccess db;

    String currentLessonID;
    int currentSectionIndex;
    int lessonSize;
    int totalProgress;

    Section currentSection;
    String lesson_content = null;
    String website_link = null;
    String description_text = null;
    ArrayList<Section> allSections;
    ArrayList<QuizOption> options = new ArrayList<>();
    ArrayList<Button> buttonChoices = new ArrayList<>();
    ArrayList<ImageButton> mImageButtonArray = new ArrayList<>();

    Unit unit;
    String jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        dataAccess = new DataAccess(QuizPage.this);
        db = DatabaseAccess.getInstance(getApplicationContext());
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

        handle_top_bar();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuizPage.this, UnitPage.class);
        intent.putExtra("unit", unit);
        intent.putExtra("jsonArray", jsonArray);
        startActivity(intent);
    }

    private void processIntents() {
        Intent intent = getIntent();
        currentLessonID = intent.getStringExtra("lessonID");
        currentSectionIndex = intent.getIntExtra("progress", 0);
        lessonSize = intent.getIntExtra("sizeOfLesson", 0);
        allSections = dataAccess.getLessonSections(currentLessonID);
        currentSection = allSections.get(currentSectionIndex);
        totalProgress = db.getProgress(currentLessonID);
        unit = intent.getParcelableExtra("unit");
        jsonArray = intent.getStringExtra("jsonArray");
    }

    private void connectXML() throws JSONException {
        lessonName = findViewById(R.id.lesson_name);
        lessonName.setText(currentSection.name);
        words = findViewById(R.id.text);
        website = findViewById(R.id.website);
        description = findViewById(R.id.description);
        description.setText(currentSection.content.getString("text"));
        buttonChoices.add(findViewById(R.id.option1));
        buttonChoices.add(findViewById(R.id.option2));
        buttonChoices.add(findViewById(R.id.option3));
        buttonChoices.add(findViewById(R.id.option4));
        continue_button = findViewById(R.id.continue_button);
        back_button = findViewById(R.id.quiz_page_back);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_lesson();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizPage.this, UnitPage.class);
                intent.putExtra("unit", unit);
                intent.putExtra("jsonArray", jsonArray);
                startActivity(intent);
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
        System.out.println(currentSection);
        System.out.println(currentSection.choices);
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
                    buttonChoices.get(buttonIndex).setTextColor(QuizPage.this.getResources().getColorStateList(R.color.white));
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

        Intent intent;
        // Update progress if index matches progress, check for bugs here
        // If index == 5, total Progress = 6, index should never reach 6
        if (currentSectionIndex == totalProgress) {
            db.updateProgress(currentLessonID, totalProgress + 1);
        }
        // If size = 6, and index + 1 == 6, that means we are currently on the last section
        if (currentSectionIndex + 1 >= lessonSize) {
            System.out.println("NAVIGATE TO UNIT PAGE");
            intent = new Intent(QuizPage.this, UnitPage.class);
            intent.putExtra("unit", unit);
            intent.putExtra("jsonArray", jsonArray);
            startActivity(intent);
        }
        else {

            intent = new Intent(QuizPage.this, LessonPage.class);

            intent.putExtra("progress", currentSectionIndex + 1);
            intent.putExtra("sizeOfLesson", lessonSize);
            intent.putExtra("lessonID", currentLessonID);
            intent.putExtra("unit", unit);
            intent.putExtra("jsonArray", jsonArray);
            startActivity(intent);
        }
        // Handle the case where user is redoing old lessons, don't change the progress
//        if (lessonSize >= currentSectionProgress) {
//            Intent intent = new Intent(QuizPage.this, LessonPage.class);
//            intent.putExtra("progress", currentSectionProgress + 1);
//            intent.putExtra("sizeOfLesson", lessonSize);
//            intent.putExtra("lessonID", currentLessonID);
//            startActivity(intent);
//        }

//        if (currentSectionProgress + 1 != lessonSize) {
//            // Set the progress equal to the index of this lesson
//            db.updateProgress(currentLessonID, currentSectionProgress + 1);
//            Intent intent = new Intent(QuizPage.this, LessonPage.class);
//            intent.putExtra("progress", currentSectionProgress + 1);
//            intent.putExtra("sizeOfLesson", lessonSize);
//            intent.putExtra("lessonID", currentLessonID);
//            startActivity(intent);
//            finish();
//
//        } else if (currentSectionProgress + 1 == lessonSize){
//            db.updateProgress(currentLessonID, currentSectionProgress + 1);
//            Intent intent = new Intent(QuizPage.this, UnitPage.class);
//            startActivity(intent);
//            finish();
//        }

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
                        intent.putExtra("unit", unit);
                        intent.putExtra("jsonArray", jsonArray);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}