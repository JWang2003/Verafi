package com.example.misinformation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {

    DatabaseAccess db;
    FactCheckAPI factCheck;
    Button submitButton;
    EditText claim;
    DataAccess dataAccess;
    ArrayList<Unit> units;
    Button nextPage;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseAccess.getInstance(getApplicationContext());
//
//        db.updateProgress("Introduction", 0);
//        db.updateProgress("LateralReading", 0);
        connectXML();
        connectButtons();

        dataAccess = new DataAccess(MainActivity.this);
        units = dataAccess.getUnits();
        System.out.println(units);
    }

    private void connectXML() {

        nextPage = findViewById(R.id.go_unit);
//        text = findViewById(R.id.textView);
        claim = findViewById(R.id.input);
        submitButton = findViewById(R.id.submit);
    }

    private void connectButtons() {

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnitPage.class);
                Unit selectedUnit = units.get(0);
                intent.putExtra("unit", selectedUnit);
                intent.putExtra("jsonArray", selectedUnit.lessons.toString());
                startActivity(intent);
            }
        });
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                search();
////                openResultFragment();
//            }
//        });
    }

    private void search() {
        factCheck = new FactCheckAPI(claim.getText().toString());
        factCheck.search();
//        System.out.println(factCheck.claimsList.get(0).getTitle());
    }

    //    private void openResultFragment() {
//        Intent intent = new Intent();
//    }

    // TODO: Remove this function when done with code
    private void WilliamDataCode() {
        DataAccess dataAccess = new DataAccess(MainActivity.this);
        ArrayList<Unit> units = dataAccess.getUnits();
        System.out.println(units);
        System.out.println("Success!");

        Unit firstUnit = units.get(0);
        // Get name of the unit
        System.out.println(firstUnit.name);


        System.out.println(firstUnit.lessons);

        // Get the names of all the lessons in side the unit
        for (int i = 0; i < firstUnit.lessons.length(); i++) {
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

// TODO: Remove this code by Jimmy once done

//        System.out.println(searchQuery);
//        AndroidNetworking.get("https://factchecktools.googleapis.com/v1alpha1/claims:search")
//                .addQueryParameter("key", "AIzaSyAMJ7rwRaewe6rhqmY1CHc0yP4HkO-jZc8")
//                .addQueryParameter("languageCode", "en-US")
//                .addQueryParameter("query", searchQuery)
//                .setTag(this)
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("success");
//                        try {
//                            try {
//                                nextPageToken = response.getString("nextPageToken");
//                                nextPage = true;
//                            } catch (JSONException e) {
//                                nextPage = false;
//                            }
//                            JSONArray claims = response.getJSONArray("claims");
//                            for (int i = 0; i < claims.length() - 1; i++) {
//                                String title = claims.getJSONObject(i).getString("text");
//                                String claimant = claims.getJSONObject(i).getString("claimant");
//                                String claimDate = claims.getJSONObject(i).getString("claimDate");
//                                String source = claims.getJSONObject(i).getJSONObject("claimReview").getString("url");
//                                String reviewDate = claims.getJSONObject(i).getJSONObject("claimReview").getString("reviewDate");
//                                String claimRating = claims.getJSONObject(i).getJSONObject("claimReview").getString("textualRating");
//                                String publisherName = claims.getJSONObject(i).getJSONObject("claimReview").getJSONObject("publisher").getString("name");
//                                String publisherSite = claims.getJSONObject(i).getJSONObject("claimReview").getJSONObject("publisher").getString("site");
//                                claimsList.add(new Claim(title, claimant, claimDate, source, reviewDate, claimRating, publisherName, publisherSite));
//                            }
//                            System.out.println(claimsList.get(0).getTitle());
//                            if (nextPage) {
//                                consecutiveSearch();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        System.out.println("Im going to kill myself");
//                    }
//                });
//    }

//    private void consecutiveSearch() {
//        currentPageToken = nextPageToken;
//        String searchQuery = claim.getText().toString();
//        System.out.println(searchQuery);
//        AndroidNetworking.get("https://factchecktools.googleapis.com/v1alpha1/claims:search")
//                .addQueryParameter("key", "AIzaSyAMJ7rwRaewe6rhqmY1CHc0yP4HkO-jZc8")
//                .addQueryParameter("pageToken", currentPageToken)
//                .addQueryParameter("languageCode", "en-US")
//                .addQueryParameter("query", searchQuery)
//                .setTag(this)
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("success");
//                        try {
//                            try {
//                                nextPageToken = response.getString("nextPageToken");
//                                nextPage = true;
//                            } catch (JSONException e) {
//                                nextPage = false;
//                            }
//                            JSONArray claims = response.getJSONArray("claims");
//                            for (int i = 0; i < claims.length() - 1; i++) {
//                                String title = claims.getJSONObject(i).getString("text");
//                                String claimant = claims.getJSONObject(i).getString("claimant");
//                                String claimDate = claims.getJSONObject(i).getString("claimDate");
//                                String source = claims.getJSONObject(i).getJSONObject("claimReview").getString("url");
//                                String reviewDate = claims.getJSONObject(i).getJSONObject("claimReview").getString("reviewDate");
//                                String claimRating = claims.getJSONObject(i).getJSONObject("claimReview").getString("textualRating");
//                                String publisherName = claims.getJSONObject(i).getJSONObject("claimReview").getJSONObject("publisher").getString("name");
//                                String publisherSite = claims.getJSONObject(i).getJSONObject("claimReview").getJSONObject("publisher").getString("site");
//                                claimsList.add(new Claim(title, claimant, claimDate, source, reviewDate, claimRating, publisherName, publisherSite));
//                            }
//                            if (nextPage) {
//                                consecutiveSearch();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        System.out.println("Im going to kill myself");
//                    }
//                });
//    }
