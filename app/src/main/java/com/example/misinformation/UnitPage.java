package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.ArrayList;

public class UnitPage extends AppCompatActivity {

    JSONArray lessons;
    String jsonArray;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    UnitPageRecyclerAdapter mAdapter;
    ArrayList<Lesson> mLessonList;
    Unit unit;
    Button nextPage;
    DataAccess dataAccess;
    DatabaseAccess databaseAccess;
    TextView unitName;
    ImageButton back_button;

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UnitPage.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private ArrayList<Lesson> getLessonsArray() {
        ArrayList<Lesson> theLessons = new ArrayList<Lesson>();
        try {
            lessons = new JSONArray(jsonArray);
            for (int i = 0; i < lessons.length(); i++) {
                String id = lessons.getJSONObject(i).getString("id");
                String name = lessons.getJSONObject(i).getString("lesson");
                int progress = lessons.getJSONObject(i).getInt("progress");
                ArrayList<String> secNames = dataAccess.getSectionNames(id);
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
        jsonArray = intent.getStringExtra("jsonArray");
        System.out.println(unit);
        System.out.println("PRINTED UNIT");
    }

    private void connectXML() {
        unitName = findViewById(R.id.unit_name);
        unitName.setText(unit.name);
        back_button = findViewById(R.id.unit_back);
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.unit_recycler_view);
        mAdapter = new UnitPageRecyclerAdapter(this, mLessonList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new UnitPageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mLessonList.get(position).lessonProgress != - 1) {
                    openLesson(position);
                } else {
                    Toast.makeText(UnitPage.this, "Sorry, this lesson is not available currently", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnitPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openLesson(int selectedLesson) {
        Lesson mLesson = mLessonList.get(selectedLesson);
        int progress = databaseAccess.getProgress(mLesson.id);
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
            intent.putExtra("unit", unit);
            intent.putExtra("jsonArray", jsonArray);
            startActivity(intent);
        } else if (progress % 2 != 0) {
            Intent intent = new Intent(UnitPage.this, QuizPage.class);
            intent.putExtra("progress", progress);
            intent.putExtra("sizeOfLesson", mLesson.sectionNames.size());
            intent.putExtra("lessonID", mLesson.id);
            intent.putExtra("unit", unit);
            intent.putExtra("jsonArray", jsonArray);
            startActivity(intent);
        }

        else {
            Toast.makeText(UnitPage.this, "Something went wrong in UnitPage.java", Toast.LENGTH_LONG).show();
        }
    }
}