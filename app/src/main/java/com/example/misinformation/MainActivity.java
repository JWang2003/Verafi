package com.example.misinformation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {
    FactCheckAPI factCheck;
    Button submitButton;
    EditText claim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        claim = findViewById(R.id.input);
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
//                openResultFragment();
            }
        });
    }

//    private void openResultFragment() {
//        Intent intent = new Intent();
//    }

    private void search() {
        factCheck = new FactCheckAPI(claim.getText().toString());
        factCheck.search();
//        System.out.println(factCheck.claimsList.get(0).getTitle());
    }




}










































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