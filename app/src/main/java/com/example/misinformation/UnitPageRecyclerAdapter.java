package com.example.misinformation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UnitPageRecyclerAdapter extends RecyclerView.Adapter<UnitPageRecyclerAdapter.UnitPageViewHolder> {

    private ArrayList<Lesson> mLessonList;
    private OnItemClickListener mListener;
    Context context;
    DatabaseAccess databaseAccess;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UnitPageViewHolder extends RecyclerView.ViewHolder {
        public TextView mLessonName;
        public ProgressBar mProgressBar;
        public ArrayList<LinearLayout> mLinearLayoutArray = new ArrayList<>();
        public ArrayList<TextView> mTextViewArray = new ArrayList<>();
        public ArrayList<ImageView> mImageViewArray = new ArrayList<>();
        public CardView card;

        public UnitPageViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            card = itemView.findViewById(R.id.unit_lesson_card);
            mLessonName = itemView.findViewById(R.id.unit_lesson_name);
            mProgressBar = itemView.findViewById(R.id.unit_progress_bar);
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_0));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_1));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_2));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_3));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_4));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_5));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_6));
            mLinearLayoutArray.add(itemView.findViewById(R.id.unit_sub_linear_7));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_0_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_1_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_2_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_3_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_4_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_5_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_6_name));
            mTextViewArray.add(itemView.findViewById(R.id.unit_sub_linear_7_name));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_0_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_1_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_2_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_3_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_4_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_5_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_6_icon));
            mImageViewArray.add(itemView.findViewById(R.id.unit_sub_linear_7_icon));

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

    public UnitPageRecyclerAdapter(Context context, ArrayList<Lesson> lessonList) {
        mLessonList = lessonList;
        this.context = context;
    }

    @Override
    public UnitPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson_container, parent, false);
        UnitPageViewHolder evh = new UnitPageViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UnitPageViewHolder holder, int position) {
        databaseAccess = DatabaseAccess.getInstance(context.getApplicationContext());
        Lesson currentLesson = mLessonList.get(position);
        holder.setIsRecyclable(false);
        holder.mLessonName.setText(currentLesson.name);

        // Make card grey if not available
        if (currentLesson.lessonProgress == -1) {
//            holder.card.setCardBackgroundColor(Color.parseColor("#C4C4C4"));
        }

        int progress = databaseAccess.getProgress(currentLesson.id);
        int percentage = 0;
        if (currentLesson.sectionNames.size() > 0) {
            percentage = progress * 100 / currentLesson.sectionNames.size();
        }
        holder.mProgressBar.setProgress(percentage);

        for (int i = 0; i < currentLesson.sectionNames.size(); i++) {
            holder.mLinearLayoutArray.get(i).setVisibility(View.VISIBLE);
            holder.mTextViewArray.get(i).setText(currentLesson.sectionNames.get(i));
            if (i % 2 == 0) {
                holder.mImageViewArray.get(i).setImageResource(R.drawable.lesson_pic);
            } else {
                holder.mImageViewArray.get(i).setImageResource(R.drawable.quiz_pic);
            }
            if (i < progress) {
                holder.mImageViewArray.get(i).setColorFilter(0xff4ABD80);
            } else if (i == progress) {
                holder.mImageViewArray.get(i).setColorFilter(0xff0978BE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mLessonList.size();
    }
}

