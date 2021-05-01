package com.example.misinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FactCheckAdapter extends RecyclerView.Adapter<FactCheckViewHolder> {

    private FactCheckViewHolder.OnClickListener onClickListener;

    //Properties
    Context context;
    ArrayList<Claim> claimsList;

    // TODO: Set up onClickListener
    public FactCheckAdapter(Context context, ArrayList<Claim> claimsList) {
        this.context = context;
        this.claimsList = claimsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FactCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(com.example.misinformation.R.layout.item_fact_check, parent, false);
        FactCheckViewHolder viewHolder = new FactCheckViewHolder(itemView, onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FactCheckViewHolder holder, int position) {
        Claim claim = claimsList.get(position);
        System.out.println(claim);
        holder.claimTitleV.setText(claim.title);
        holder.claimDateV.setText(claim.claimDate);
        holder.claimClaimantV.setText(claim.claimant);
        holder.claimSourceV.setText(claim.source);
        holder.claimReviewDateV.setText(claim.reviewDate);
        holder.claimRatingV.setText(claim.claimRating);
        holder.publisherNameV.setText(claim.publisherName);
        holder.publisherSiteV.setText(claim.publisherSite);

    }

    @Override
    public int getItemCount() {
        return claimsList.size();
    }

//    public String checkRatingSize(String rating) {
//        if (rating.length() <= 30) {
//            return rating;
//        } else {
//
//        }
//    }
}
