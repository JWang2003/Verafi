package com.example.misinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FactCheckAdapter extends RecyclerView.Adapter<FactCheckViewHolder> {

    private FactCheckViewHolder.OnClickListener onClickListener;

    //Properties
    Context context;
    ArrayList<Claim> claimsList;

    public FactCheckAdapter(Context context, ArrayList<Claim> claimsList, FactCheckViewHolder.OnClickListener onClickListener) {
        this.context = context;
        this.claimsList = claimsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FactCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(com.example.food_order.R.layout.item_category, parent, false);
        return
    }

    @Override
    public void onBindViewHolder(@NonNull FactCheckViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return claimsList.size();
    }
}
