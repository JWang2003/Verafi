package com.example.misinformation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {

    DatabaseAccess db;
    FactCheckAPI factCheckAPI;
    Button submitButton;
    EditText claim;
    DataAccess dataAccess;
    ArrayList<Unit> units;
    Button factCheckPage;
    Button learnPage;
    Button nextPage;
    TextView text;
    RadioGroup radioGroup;
    TextView topText;

    private static final int NUM_PAGES = 2;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseAccess.getInstance(getApplicationContext());
        // TODO: Delete this debugging stuff
       //db.updateProgress("Introduction", 0);
        //db.updateProgress("LateralReading", 0);

        connectXML();
        onClickSetup();


        if (viewPager!=null) {
            MainPagePagerAdapter adapter = new MainPagePagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }


        dataAccess = new DataAccess(MainActivity.this);
        units = dataAccess.getUnits();
        System.out.println(units);
    }



    public class MainPagePagerAdapter extends FragmentPagerAdapter {

        // Constructor
        public MainPagePagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        // Overridden FragmentPagerAdapter methods
        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 1:
                    fragment = new FactCheckFragment();
                    break;
                case 0:
                    fragment = new LearnPageFragment();
                    break;
            }

            return fragment;

        }

        @Override
        public int getCount() {
            return numPages;
        }

        // Private properties
        final private int numPages = 2;
    }


    private void connectXML() {
        viewPager = findViewById(R.id.viewPager);
        factCheckPage = findViewById(R.id.go_factCheck);
        learnPage = findViewById(R.id.Learn);
        radioGroup = findViewById(R.id.toggle);
        topText = findViewById(R.id.top_text);
//        text = findViewById(R.id.textView);
    }

    private void onClickSetup() {

        factCheckPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);
//                Intent intent = new Intent(MainActivity.this, FactCheckPage.class);
//                startActivity(intent);
            }
        });

        learnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0, true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //changing stuff depending on scroll
                switch (position) {
                    case 1:
                        radioGroup.check(R.id.go_factCheck);
                        topText.setText("Fact Check");
                        break;
                    default:
                        radioGroup.check(R.id.Learn);
                        topText.setText("Learn");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        factCheckAPI = new FactCheckAPI(claim.getText().toString());
        factCheckAPI.search();
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
