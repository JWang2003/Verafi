package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataAccess dataAccess = new DataAccess(MainActivity.this);
        ArrayList<Unit> units = dataAccess.getUnits();
        System.out.println(units);
        System.out.println("Success!");

        ArrayList<Section> sections = dataAccess.getLessonSections("Introduction");
        System.out.println(sections.get(5));
        System.out.println("Printed choices");

        Unit unit = units.get(0);
        for (int i = 0; i < unit.lessons.length(); i++) {
            try {
                System.out.println(unit.lessons.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


//        System.out.println(sections);
//        System.out.println("PRINTED ALL");
    }

}
