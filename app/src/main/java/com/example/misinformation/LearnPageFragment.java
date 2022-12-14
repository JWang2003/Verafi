package com.example.misinformation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class LearnPageFragment extends Fragment {

    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    LearnFragmentRecyclerAdapter mAdapter;

    ArrayList<Unit> mUnitList;
    DataAccess dataAccess;


    public LearnPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_learn_page, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataAccess = new DataAccess(getActivity());
        mUnitList = dataAccess.getUnits();
        System.out.println(mUnitList);

        connectXML();
    }

    private void connectXML() {
        mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView = getView().findViewById(R.id.lesson_frag_recycler_view);
        mAdapter = new LearnFragmentRecyclerAdapter(getActivity(), mUnitList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new LearnFragmentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Open if available
                if (mUnitList.get(position).progress != -1) {
                    openUnit(position);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry, this topic is not available currently", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onScrollViewClick(int position) {
                if (mUnitList.get(position).progress != -1) {
                    openUnit(position);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry, this topic is not available currently", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openUnit(int position) {

        Unit selectedUnit = dataAccess.getUnits().get(position);
        Intent intent = new Intent(getActivity(), UnitPage.class);
        intent.putExtra("unit", selectedUnit);
        intent.putExtra("jsonArray", selectedUnit.lessons.toString());
        startActivity(intent);
    }

}