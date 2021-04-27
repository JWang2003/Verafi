package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.ArrayList;

public class UnitPage extends AppCompatActivity {

    JSONArray lessons;
    String jsonArray;
    // TODO: Generate all section names for each lesson
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    UnitPageRecyclerAdapter mAdapter;
    ArrayList<Lesson> mLessonList;
    Unit unit;
    Button nextPage;
    DataAccess dataAccess;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_page);
        dataAccess = new DataAccess(UnitPage.this);
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        processIntents();

        mLessonList = getLessonsArray();
        connectXML();
    }

    private ArrayList<Lesson> getLessonsArray() {
        ArrayList<Lesson> theLessons = new ArrayList<Lesson>();
        try {
            lessons = new JSONArray(jsonArray);
            System.out.println(lessons.toString());
            System.out.println(lessons.get(2));
            for (int i = 0; i < lessons.length(); i++) {
                String id = lessons.getJSONObject(i).getString("id");
                String name = lessons.getJSONObject(i).getString("lesson");
                int progress = 0;
                ArrayList<String> secNames = dataAccess.getSectionNames(id);
                // TODO: Generate Progress
                ArrayList<Integer> secProgress = new ArrayList<Integer>();
                theLessons.add(new Lesson(id, progress, name, secNames, secProgress));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return theLessons;

    }
    private void processIntents() {
        Intent intent = getIntent();
        unit = intent.getParcelableExtra("unit");
        System.out.println(unit);
        System.out.println("PRINTED UNIT");
        jsonArray = intent.getStringExtra("jsonArray");
    }

    private void connectXML() {
        nextPage = findViewById(R.id.go_lesson);

        //TODO: RECYCLER VIEW PORTION
        mRecyclerView = findViewById(R.id.unit_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new UnitPageRecyclerAdapter(this, mLessonList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new UnitPageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openLesson(position);
                System.out.println("Lesson clicked at " + position);
            }
        });
        // TODO: Remove this button and have page navigation from clicking on recycler view
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLesson(0);
            }
        });
    }

    private void openLesson(int selectedLesson) {
        Lesson mLesson = mLessonList.get(selectedLesson);
        int progress = databaseAccess.getProgress(mLesson.id);
        //TODO: Be able to tell if we must open a quiz or lesson page based on progress, determine what section we need to get
        if (progress == mLesson.sectionNames.size()) {
            progress = progress - 1;
        }
        if (progress % 2 == 0) {
            Intent intent = new Intent(UnitPage.this, LessonPage.class);
            System.out.println(progress);
            System.out.println(mLesson.sectionNames.size());
            System.out.println(mLesson.id);
            System.out.println("PROPERTIES");
            intent.putExtra("progress", progress);
            intent.putExtra("sizeOfLesson", mLesson.sectionNames.size());
            intent.putExtra("lessonID", mLesson.id);
            startActivity(intent);
        } else if (progress % 2 != 0) {
            Intent intent = new Intent(UnitPage.this, QuizPage.class);
            intent.putExtra("progress", progress);
            intent.putExtra("sizeOfLesson", mLesson.sectionNames.size());
            intent.putExtra("lessonID", mLesson.id);
            startActivity(intent);
        }

        else {
            Toast.makeText(UnitPage.this, "Something went wrong in UnitPage.java", Toast.LENGTH_LONG).show();
        }
    }
}