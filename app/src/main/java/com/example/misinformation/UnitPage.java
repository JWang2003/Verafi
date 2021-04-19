package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UnitPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_page);

        DataAccess dataAccess = new DataAccess(UnitPage.this);

    }
}