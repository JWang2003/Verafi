package com.example.misinformation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FactCheckAdapter extends RecyclerView.Adapter<FactCheckAdapter.FactCheckViewHolder> {

    //Properties
    Context context;
    ArrayList<Claim> claimsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
}

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FactCheckViewHolder extends RecyclerView.ViewHolder {

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
        CardView card;

        public FactCheckViewHolder(View itemView, final OnItemClickListener listener) {
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
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public FactCheckAdapter(Context context, ArrayList<Claim> claimsList) {
        this.context = context;
        this.claimsList = claimsList;
    }

    @NonNull
    @Override
    public FactCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(com.example.misinformation.R.layout.item_fact_check, parent, false);
        FactCheckViewHolder viewHolder = new FactCheckViewHolder(itemView, mListener);
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

        // TODO: CHANGE COLOURS
        // Change the colour of the card depending on if it's true/false
        if (claim.claimRating.toLowerCase().contains("true")) {
            holder.card.setCardBackgroundColor(Color.parseColor("#0978BE"));
        } else if (claim.claimRating.toLowerCase().contains("false") || claim.claimRating.toLowerCase().contains("fire") || claim.claimRating.toLowerCase().contains("incorrect")) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor("#C4C4C4"));
        }

        holder.publisherNameV.setText(claim.publisherName);
        holder.publisherSiteV.setText(claim.publisherSite);

    }

    @Override
    public int getItemCount() {
        return claimsList.size();
    }

}
