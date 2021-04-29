package com.example.misinformation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class FactCheckFragment extends Fragment {

    public FactCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_fact_check, container, false);

        return rootView;
    }

//    //Properties
//    FactCheckAPI factCheckAPI;
//    FactCheckAdapter factCheckAdapter;
//
//
//    //XML Views
//    RecyclerView factCheckRecyclerView;
//    EditText claimSearch;
//    Button searchButton;
//    String passedClaim;
////    RecyclerView recyclerView;
//
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        System.out.println("fce connect xml");
//        connectXML();
//        System.out.println("fce onclick setup");
//        onClickSetup();
//        System.out.println("fce get intent");
//        getIntents();
//
//
//        startSearch();
//    }
//
//    private void startSearch() {
//        searchClaim();
//        recyclerViewSetup();
//    }
//
//
//    private synchronized void searchClaim() {
//        System.out.println("new factcheck obj created");
//        factCheckAPI = new FactCheckAPI(claimSearch.getText().toString());
//    }
//
//    private synchronized void recyclerViewSetup() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        factCheckRecyclerView = getView().findViewById(R.id.claims_recycle);
//        factCheckRecyclerView.setLayoutManager(linearLayoutManager);
//
//        System.out.println(factCheckAPI.claimsList.size());
//        factCheckAdapter = new FactCheckAdapter(this, factCheckAPI.claimsList, this);
//        factCheckRecyclerView.setAdapter(factCheckAdapter);
//        factCheckAdapter.notifyDataSetChanged();
//        factCheckRecyclerView.setHasFixedSize(false);
//    }
//
//
//
//    private void connectXML() {
//        claimSearch = getView().findViewById(R.id.claimInput);
//        searchButton = getView().findViewById(R.id.fce_searchButton);
//    }
//
//    private void onClickSetup() {
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (claimSearch.getText().toString() != null && claimSearch.getText().toString() != "") {
//                    startSearch();
//                }
//            }
//        });
//    }
//
//    private void getIntents() { ;
//        //
//    }
//
//
//    public void rvOnClick(int position) {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(factCheckAPI.claimsList.get(position).getUrl()));
//        startActivity(browserIntent);
//    }


}