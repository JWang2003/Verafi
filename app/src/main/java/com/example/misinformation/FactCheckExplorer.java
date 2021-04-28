package com.example.misinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FactCheckExplorer extends AppCompatActivity implements FactCheckViewHolder.OnClickListener {


    //Properties
    FactCheckAPI factCheckAPI;
    FactCheckAdapter factCheckAdapter;


    //XML Views
    RecyclerView factCheckRecyclerView;
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
        startSearch();


//        displayResultInfo();

        /*
            BUG: RecyclerView content won't appear unless EditText is pressed again; Suspected that this is multithreading error but even after
            applying async to the function calls it still won't work (see code below). I've exhausted all options at this point.
            This is a huge issue that needs mentor attention ASAP.
         */
//        //Call async bs
//        try {
//            async();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    private void startSearch() {
        searchClaim();
        recyclerViewSetup();
    }


    private synchronized void searchClaim() {
         System.out.println("new factcheck obj created");
         factCheckAPI = new FactCheckAPI(claimSearch.getText().toString());
    }


//    Doesn't work
//    private void async() throws InterruptedException {

//        Thread t1 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                System.out.println("thread start");
//            }
//        });
//
//        t1.start();
//        t1.join();
//
//
//        recyclerViewSetup();
//        System.out.println("thread end");
//        displayResultInfo();
//    }

    //Can't use right now due to bug
//    private void displayResultInfo() {
//        TextView result = findViewById(R.id.fceResults_text);
//        String resultString = factCheckAPI.claimsList.size() + " results found for '" + claimSearch.getText().toString() + "'";
//        result.setText(resultString);
//    }

    private synchronized void recyclerViewSetup() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        factCheckRecyclerView = findViewById(R.id.claims_recycle);
        factCheckRecyclerView.setLayoutManager(linearLayoutManager);

        System.out.println(factCheckAPI.claimsList.size());
        factCheckAdapter = new FactCheckAdapter(this, factCheckAPI.claimsList, this);
        factCheckRecyclerView.setAdapter(factCheckAdapter);
        factCheckAdapter.notifyDataSetChanged();
        factCheckRecyclerView.setHasFixedSize(false);
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
                    startSearch();
                }
            }
        });
    }

    private void getIntents() { ;
        passedClaim = getIntent().getStringExtra("firstClaim");
    }

    @Override
    public void rvOnClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(factCheckAPI.claimsList.get(position).getUrl()));
        startActivity(browserIntent);
    }
}