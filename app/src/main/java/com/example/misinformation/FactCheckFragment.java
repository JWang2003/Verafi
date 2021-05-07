package com.example.misinformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


public class FactCheckFragment extends Fragment {

    //Properties
    FactCheckAPI factCheckAPI;
    FactCheckAdapter factCheckAdapter;
    private static FactCheckFragment instance; //The static has to stay for the whole thing to work
    boolean lock = false;
    String prevClaimString = "";
    String claimString = "";

    //XML Views
    RecyclerView factCheckRecyclerView;
    EditText claimSearch;
    ImageButton searchButton;
    ProgressBar progressBar;

    // TODO: Add loading circle
    public FactCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_fact_check, container, false);
        instance = this;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectXML();
        onClickSetup();
        recyclerViewInit();
//        System.out.println("going to start search");
//        startSearch();
    }

    public static FactCheckFragment getInstance() {
        return instance;
    }

    // Handle
    public synchronized void didReceivedNewSearchResult(ArrayList<Claim> claimsList) {
        System.out.println("# > didReceivedNewSearchResult new size: "+ claimsList.size());

        //TODO: update ui
    }

    private void startSearch() {
        System.out.println("Start search");
        searchClaim();
    }

    private synchronized void searchClaim() {
        prevClaimString = claimString;
        claimString = claimSearch.getText().toString();
        if (claimString.equals(prevClaimString)) {
            return;
        }
        factCheckRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.invalidate();
        System.out.println("new factcheck obj created");
        // What user searched
        factCheckAPI = new FactCheckAPI(claimString, this, getActivity().getApplicationContext());
    }

    private synchronized void recyclerViewInit() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        factCheckRecyclerView = getView().findViewById(R.id.claims_recycle);
        factCheckRecyclerView.setLayoutManager(linearLayoutManager);
    }


    public synchronized void recyclerViewSetup() {
        System.out.println(factCheckAPI.claimsList.size());
        factCheckAdapter = new FactCheckAdapter(getActivity().getApplicationContext(), factCheckAPI.claimsList);
        factCheckRecyclerView.setAdapter(factCheckAdapter);
        factCheckAdapter.notifyDataSetChanged();
        factCheckRecyclerView.setHasFixedSize(false);
        factCheckAdapter.setOnItemClickListener(new FactCheckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WebPage.class);
                intent.putExtra("url", factCheckAPI.claimsList.get(position).url);
                startActivity(intent);
            }
        });
        factCheckRecyclerView.setVisibility(View.VISIBLE);
        System.out.println("view is gone");
        progressBar.setVisibility(View.GONE);
    }

    private void connectXML() {
        claimSearch = getView().findViewById(R.id.claimInput);
        searchButton = (ImageButton)getView().findViewById(R.id.fce_searchButton);
        progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
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