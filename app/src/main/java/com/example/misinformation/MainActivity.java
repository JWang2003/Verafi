package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button nextPage;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextPage = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnitPage.class);
                startActivity(intent);
            }
        });

        DataAccess dataAccess = new DataAccess(MainActivity.this);
        ArrayList<Unit> units = dataAccess.getUnits();
        System.out.println(units);
        System.out.println("Success!");

        Unit firstUnit = units.get(0);
        // Get name of the unit
        System.out.println(firstUnit.name);


        System.out.println(firstUnit.lessons);

        // Get the names of all the lessons in side the unit
        for (int i = 0 ; i < firstUnit.lessons.length(); i++) {
            try {
                JSONObject currentUnit = firstUnit.lessons.getJSONObject(i);
                String lessonName = currentUnit.getString("lesson");
                System.out.println(lessonName);
                String lessonId = currentUnit.getString("id");
                ArrayList<Section> sections = dataAccess.getLessonSections(lessonId);

                // Now create a for loop to get all the sections of the lesson
                System.out.println(sections.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




//        ArrayList<Section> sections = dataAccess.getLessonSections("Introduction");
//        System.out.println(sections.get(5));
//        System.out.println("Printed choices");
//
//        Unit unit = units.get(0);
//        for (int i = 0; i < unit.lessons.length(); i++) {
//            try {
//                System.out.println(unit.lessons.getJSONObject(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }


    }

}
