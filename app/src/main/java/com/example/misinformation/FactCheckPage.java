package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FactCheckPage extends AppCompatActivity {

    FactCheckAPI factCheck;
    Button submitButton;
    EditText claim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_check_page);

        claim = findViewById(R.id.input);
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    private void search() {
        factCheck = new FactCheckAPI(claim.getText().toString());
        factCheck.search();
    }
}