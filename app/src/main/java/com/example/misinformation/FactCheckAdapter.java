package com.example.misinformation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        TextView claimTitleV;
        TextView claimReviewDateV;
        TextView claimRatingV;
        TextView publisherNameV;
        LinearLayout colourIndicator;

        public FactCheckViewHolder(View itemView, final OnItemClickListener listener) {
            //Usual setup
            super(itemView);

            //Connecting item XML elements to variables
            claimTitleV = itemView.findViewById(R.id.fact_check_title);
            claimReviewDateV = itemView.findViewById(R.id.fact_check_reviewDate);
            claimRatingV = itemView.findViewById(R.id.fact_check_rating);
            publisherNameV = itemView.findViewById(R.id.fact_check_publisherName);
            colourIndicator = itemView.findViewById(R.id.colorIndicator);

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
        holder.claimReviewDateV.setText(claim.reviewDate);
        holder.claimRatingV.setText(claim.claimRating);

        // TODO: CHANGE COLOURS
        // Change the colour of the card depending on if it's true/false
        if (claim.claimRating.toLowerCase().contains("true")) {
            holder.colourIndicator.setBackgroundColor(Color.parseColor("#4ABD80"));
            holder.claimRatingV.setTextColor(Color.parseColor("#4ABD80"));
        } else if (claim.claimRating.toLowerCase().contains("false") || claim.claimRating.toLowerCase().contains("fire") || claim.claimRating.toLowerCase().contains("incorrect") || claim.claimRating.toLowerCase().contains("misleading")) {
            holder.colourIndicator.setBackgroundColor(Color.parseColor("#DE1F1F"));
            holder.claimRatingV.setTextColor(Color.parseColor("#DE1F1F"));
        } else if (claim.claimRating.toLowerCase().contains("half") || claim.claimRating.toLowerCase().contains("partly")){
            holder.colourIndicator.setBackgroundColor(Color.parseColor("#FFCD0B"));
            holder.claimRatingV.setTextColor(Color.parseColor("#FFCD0B"));
        } else {
            holder.colourIndicator.setBackgroundColor(Color.parseColor("#FFCD0B"));
            holder.claimRatingV.setTextColor(Color.parseColor("#FFCD0B"));
        }

        holder.claimTitleV.setText(claim.title);
        String Text = claim.publisherName + " rating:";
        holder.publisherNameV.setText(Text);

    }

    @Override
    public int getItemCount() {
        return claimsList.size();
    }

}
