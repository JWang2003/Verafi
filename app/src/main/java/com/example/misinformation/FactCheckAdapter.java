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

    public FactCheckAdapter(Context context, ArrayList<Claim> claimsList, FactCheckViewHolder.OnClickListener onClickListener) {
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

        holder.claimTitleV.setText(claim.getTitle());
        holder.claimDateV.setText(claim.getClaimDate());
        holder.claimClaimantV.setText(claim.getClaimant());
        holder.claimSourceV.setText(claim.getSource());
        holder.claimReviewDateV.setText(claim.getReviewDate());
        holder.claimRatingV.setText(claim.getClaimRating());
        holder.publisherNameV.setText(claim.getPublisherName());
        holder.publisherSiteV.setText(claim.getPublisherSite());

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
