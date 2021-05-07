package com.example.misinformation;

import android.graphics.Color;
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


        connectXML();
        onClickSetup();


        if (viewPager!=null) {
            MainPagePagerAdapter adapter = new MainPagePagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }




        dataAccess = new DataAccess(MainActivity.this);
        units = dataAccess.getUnits();
        radioGroup.check(R.id.Learn);
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
    }

    private void onClickSetup() {



        factCheckPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);
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

            }

            @Override
            public void onPageSelected(int position) {

                System.out.println(position);


                if (position == 0){
                    radioGroup.check(R.id.Learn);
                    topText.setText("Learn");
                    System.out.println("Learn fragment");
                    learnPage.setTextColor(Color.parseColor("#FFFFFF"));
                    factCheckPage.setTextColor(Color.parseColor("#000000"));
                }
                else {
                    radioGroup.check(R.id.go_factCheck);
                    topText.setText("Fact Check");
                    System.out.println("Fact check fragment");
                    factCheckPage.setTextColor(Color.parseColor("#FFFFFF"));
                    learnPage.setTextColor(Color.parseColor("#000000"));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}

