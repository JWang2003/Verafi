package com.example.misinformation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FactCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Properties
    View itemView;
    TextView claimTitle;
    TextView claimAuthor;
    TextView claimSource;
    TextView claimReviewDate;
    TextView claimRating;
    TextView publisherName;
    TextView publishersite;
    OnClickListener onClickListener;


    public FactCheckViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
        super(itemView);
        this.itemView = itemView;
//        nameView = itemView.findViewById(R.id.category_name);
//        imageView = itemView.findViewById(R.id.piclol);


        // Make item clickable
        this.onClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(getAdapterPosition());
    }


    public interface OnClickListener {
        void onClick(int position);
    }
}
