package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FactCheckPage extends AppCompatActivity {
    Button toFactCheckExplorer;
    EditText claim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_check_page);
//        System.out.println("connecting xml");
        connectXML();
//        System.out.println("setup onclick");
        onClickSetup();
    }

    private void connectXML() {
        claim = findViewById(R.id.input);
        toFactCheckExplorer = findViewById(R.id.go_factCheckExplorer);
    }

    private void onClickSetup() {
        toFactCheckExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFactCheckExplorer();
            }
        });
    }


    private void toFactCheckExplorer() {
        Intent intent = new Intent(FactCheckPage.this, FactCheckExplorer.class);
        intent.putExtra("firstClaim", claim.getText().toString());
        startActivity(intent);
    }
}