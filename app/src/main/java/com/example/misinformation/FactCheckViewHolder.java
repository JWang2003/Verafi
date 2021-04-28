package com.example.misinformation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FactCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Properties
    View itemView;
    TextView claimTitleV;
    TextView claimDateV;
    TextView claimClaimantV;
    TextView claimSourceV;
    TextView claimReviewDateV;
    TextView claimRatingV;
    TextView publisherNameV;
    TextView publisherSiteV;
    OnClickListener onClickListener;


    public FactCheckViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
        //Usual setup
        super(itemView);
        this.itemView = itemView;

        //Connecting item XML elements to variables
        claimTitleV = itemView.findViewById(R.id.fact_check_title);
        claimDateV = itemView.findViewById(R.id.fact_check_date);
        claimClaimantV = itemView.findViewById(R.id.fact_check_claimant);
        claimSourceV = itemView.findViewById(R.id.fact_check_source);
        claimReviewDateV = itemView.findViewById(R.id.fact_check_reviewDate);
        claimRatingV = itemView.findViewById(R.id.fact_check_rating);
        publisherNameV = itemView.findViewById(R.id.fact_check_publisherName);
        publisherSiteV = itemView.findViewById(R.id.fact_check_publisherSite);

        // Make item clickable
        this.onClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickListener.rvOnClick(getAdapterPosition());
    }


    public interface OnClickListener {
        void rvOnClick(int position);
    }

}
