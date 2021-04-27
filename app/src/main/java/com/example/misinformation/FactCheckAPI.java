package com.example.misinformation;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FactCheckAPI {
    public ArrayList<Claim> claimsList;
    private String searchQuery;
    private Boolean nextPage;
    private String nextPageToken = "";
    private String currentPageToken = "";


    public FactCheckAPI(String searchQuery) {
        this.searchQuery = searchQuery;
        claimsList = new ArrayList<>();
        nextPage = true;
        search();
    }

    public void search() {
        System.out.println("Initial Search");
        apiCall();
    }

    private void consecutiveSearch() {
        currentPageToken = nextPageToken;
        apiCall();
    }


    private void apiCall() {
        AndroidNetworking.get("https://factchecktools.googleapis.com/v1alpha1/claims:search")
                .addQueryParameter("key", "AIzaSyAMJ7rwRaewe6rhqmY1CHc0yP4HkO-jZc8")
                .addQueryParameter("languageCode", "en-US")
                .addQueryParameter("query", searchQuery)
                .addQueryParameter("pageToken", currentPageToken)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("success");
                        try {
                            //Look for nextPageToken
                            try {
                                System.out.println("init yes nextPageToken");
                                nextPageToken = response.getString("nextPageToken");
                            } catch (JSONException e) {
                                System.out.println("init no nextPageToken");
                                nextPage = false;
                            }

                            JSONArray claims = response.getJSONArray("claims");
                            System.out.println("running for loop");
                            for (int i = 0; i < claims.length() - 1; i++) {
                                System.out.println("for loop running " + i);
                                JSONObject currentClaim = claims.getJSONObject(i);
                                String title;
                                String claimant;
                                String claimDate;
                                String source;

                                try {
                                    title = currentClaim.getString("text");
                                } catch (Exception e) {
                                    title = "no title";
                                }
                                System.out.println("title: " + title);

                                try {
                                    claimant = currentClaim.getString("claimant");
                                } catch (Exception e) {
                                    claimant = "no claimant";
                                }
                                System.out.println("claimant: " + claimant);

                                try {
                                    claimDate = currentClaim.getString("claimDate");
                                } catch (Exception e) {
                                    claimDate = "no claimDate";
                                }
                                System.out.println("claimDate: " + claimDate);

                                try {
                                    source = currentClaim.getString("url");
                                } catch(Exception e) {
                                    source = "no source";
                                }
                                System.out.println("source: " + source);


                                JSONObject currentClaimReview = currentClaim.getJSONArray("claimReview").getJSONObject(0);
                                String reviewDate;
                                String claimRating;

                                try {
                                    reviewDate = currentClaimReview.getString("reviewDate");
                                } catch (Exception e) {
                                    reviewDate = "no reviewDate";
                                }
                                System.out.println("reviewDate: " + reviewDate);

                                try {
                                    claimRating = currentClaimReview.getString("textualRating");
                                } catch (Exception e) {
                                    claimRating = "no claimRating";
                                }
                                System.out.println("claimRating: " + claimRating);


                                JSONObject currentClaimPublisher = currentClaimReview.getJSONObject("publisher");
                                String publisherName;
                                String publisherSite;
                                try {
                                    publisherName = currentClaimPublisher.getString("name");
                                } catch (Exception e) {
                                    publisherName = "no publisherName";
                                }
                                System.out.println("publisherName: ");


                                try {
                                    publisherSite = currentClaimPublisher.getString("site");
                                } catch (Exception e) {
                                    publisherSite = "no publisherSite";
                                }
                                System.out.println("publisherSite: " + publisherSite);

                                claimsList.add(new Claim(title, claimant, claimDate, source, reviewDate, claimRating, publisherName, publisherSite));
                        }
                            if (nextPage) {
                                consecutiveSearch();
                                return;
                            }
                        } catch (JSONException e) {
                            System.out.println("Bad error has occurred");
                            e.printStackTrace();
                        }

//                        test();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        System.out.println("ANE ERROR :CCCCCCCCCCCCCCC");
                    }
                });
    }

//    private void test() {
//       for (int i = 0; i < claimsList.size(); i++) {
//           System.out.println(claimsList.get(i).getTitle());
//       }
//    }
}

