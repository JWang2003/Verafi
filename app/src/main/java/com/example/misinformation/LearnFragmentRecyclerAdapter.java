package com.example.misinformation;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LearnFragmentRecyclerAdapter extends RecyclerView.Adapter<LearnFragmentRecyclerAdapter.LearnFragmentViewHolder> {

    private ArrayList<Unit> mUnitList;
    private OnItemClickListener mListener;
    Context context;
    DatabaseAccess databaseAccess;
    DataAccess dataAccess;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onScrollViewClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class LearnFragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView mUnitName;
        public LinearLayout mScrollViewLinearLayout;
        public ArrayList<LinearLayout> mLinearLayoutArray = new ArrayList<>();
        public ArrayList<TextView> mTextViewArray = new ArrayList<>();
        public ArrayList<ImageView> mImageViewArray = new ArrayList<>();
        public CardView unitCard;
        public ArrayList<CircularProgressIndicator> mProgBarArray = new ArrayList<>();

        public LearnFragmentViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mUnitName = itemView.findViewById(R.id.unit_name);
            unitCard = itemView.findViewById(R.id.unit_card);
            mScrollViewLinearLayout = itemView.findViewById(R.id.scrollview_layout);
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_0));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_1));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_2));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_3));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_4));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_5));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_6));
            mLinearLayoutArray.add(itemView.findViewById(R.id.main_unit_sub_linear_7));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_0));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_1));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_2));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_3));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_4));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_5));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_6));
            mImageViewArray.add(itemView.findViewById(R.id.main_unit_sub_circle_7));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_0));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_1));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_2));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_3));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_4));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_5));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_6));
            mTextViewArray.add(itemView.findViewById(R.id.main_unit_sub_title_7));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_0));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_1));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_2));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_3));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_4));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_5));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_6));
            mProgBarArray.add(itemView.findViewById(R.id.main_unit_sub_prog_7));

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
            //intended to stop scrollview from eating touch events
            mScrollViewLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("registered click on scrollview");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onScrollViewClick(position);
                        }
                    }
                }
            });
        }
    }

    public LearnFragmentRecyclerAdapter(Context context, ArrayList<Unit> unitList) {
        mUnitList = unitList;
        this.context = context;
    }

    @Override
    public LearnFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_container, parent, false);
        LearnFragmentViewHolder evh = new LearnFragmentViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull LearnFragmentViewHolder holder, int position) {
        databaseAccess = DatabaseAccess.getInstance(context.getApplicationContext());
        dataAccess = new DataAccess(context);
        Unit currentUnit = mUnitList.get(position);
        holder.setIsRecyclable(false);
        holder.mUnitName.setText(currentUnit.name);
        holder.setIsRecyclable(false);

        //TODO: DYNAMICALLY SHOWING SECTIONS
        for (int i = 0; i < currentUnit.lessons.length(); i++) {

            ArrayList<String> sectionNames = new ArrayList<>();
            int progress = 0;
            int total;

            holder.mLinearLayoutArray.get(i).setVisibility(View.VISIBLE);

            try {
                holder.mTextViewArray.get(i).setText(currentUnit.lessons.getJSONObject(i).getString("lesson"));
                progress = databaseAccess.getProgress(currentUnit.lessons.getJSONObject(i).getString("id"));
                sectionNames = dataAccess.getSectionNames(currentUnit.lessons.getJSONObject(i).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            total = sectionNames.size();
            if (total == 0) {
                progress = 0;
            } else {
                progress = (progress * 100) / total;
            }

            holder.mProgBarArray.get(i).setProgress(progress);
        }
        // Make card grey to show it can't be clicked
        if (currentUnit.progress == -1) {
//            holder.unitCard.setCardBackgroundColor(Color.parseColor("#C4C4C4"));
        }
    }

    @Override
    public int getItemCount() {
        return mUnitList.size();
    }
}

