package com.example.misinformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class FactCheckFragment extends Fragment {

    //Properties
    FactCheckAPI factCheckAPI;
    FactCheckAdapter factCheckAdapter;


    //XML Views
    RecyclerView factCheckRecyclerView;
    EditText claimSearch;
    Button searchButton;
    String passedClaim;

    // TODO: Fix bug where you have to click on editText to open
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectXML();
        onClickSetup();
        startSearch();

    }

    // Handle
    public synchronized void didReceivedNewSearchResult(ArrayList<Claim> claimsList) {
        System.out.println("# > didReceivedNewSearchResult new size: "+ claimsList.size());
        //TODO: update ui
    }

    private void startSearch() {
        searchClaim();
        recyclerViewSetup();
    }


    private synchronized void searchClaim() {
        System.out.println("new factcheck obj created");
        // What user searched
        factCheckAPI = new FactCheckAPI(claimSearch.getText().toString(), this);
    }


    private synchronized void recyclerViewSetup() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        factCheckRecyclerView = getView().findViewById(R.id.claims_recycle);
        factCheckRecyclerView.setLayoutManager(linearLayoutManager);

        System.out.println(factCheckAPI.claimsList.size());
        factCheckAdapter = new FactCheckAdapter(getActivity().getApplicationContext(), factCheckAPI.claimsList);
        factCheckRecyclerView.setAdapter(factCheckAdapter);
        factCheckAdapter.notifyDataSetChanged();
        factCheckRecyclerView.setHasFixedSize(false);

    }

    private void connectXML() {
        claimSearch = getView().findViewById(R.id.claimInput);
        searchButton = getView().findViewById(R.id.fce_searchButton);
    }

    private void onClickSetup() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICKED");
                if (claimSearch.getText().toString() != null && claimSearch.getText().toString() != "") {
                    startSearch();
                    hideKeyboardFrom(getActivity().getApplicationContext(), getView());
                }
            }
        });
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}