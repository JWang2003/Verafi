package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FactCheckExplorer extends AppCompatActivity {
    FactCheckAPI factCheckAPI;
    EditText claimSearch;
    Button searchButton;
    String passedClaim;
//    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_check_explorer);
        System.out.println("fce connect xml");
        connectXML();
        System.out.println("fce onclick setup");
        onClickSetup();
        System.out.println("fce get intent");
        getIntents();

        claimSearch.setText(passedClaim);
        System.out.println(passedClaim);
        System.out.println("set claimSearch to" + claimSearch.getText().toString());

        System.out.println("searching claim");
        searchClaim();
    }

    private void searchClaim() {
        System.out.println("new factcheck obj created");
        factCheckAPI = new FactCheckAPI(claimSearch.getText().toString());
//        display();
    }

    private void display() {
//        refreshAdapters();

    }

    private void connectXML() {
        claimSearch = findViewById(R.id.claimInput);
        searchButton = findViewById(R.id.fce_searchButton);
    }

    private void onClickSetup() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (claimSearch.getText().toString() != null && claimSearch.getText().toString() != "") {
                    searchClaim();
                }
            }
        });
    }

    private void getIntents() { ;
        passedClaim = getIntent().getStringExtra("firstClaim");
    }
}