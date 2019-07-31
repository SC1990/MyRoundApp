package com.example.stuar.myroundapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.stuar.myroundapp.Models.Review;

import java.util.List;

public class RetailerReviewListAdapter extends RecyclerView.Adapter<RetailerReviewListAdapter.RetailerReviewViewHolder> {

    private Context mCtx;
    private List<Review> reviewList;
    View view;

    public RetailerReviewListAdapter(Context mCtx, List<Review> reviewList) {
        this.mCtx = mCtx;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public RetailerReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.retailer_review_row, parent, false);
        return new RetailerReviewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RetailerReviewViewHolder holder, final int position) {

        holder.userEmail = (TextView) view.findViewById(R.id.u_email);
        holder.dateTV = (TextView) view.findViewById(R.id.t_date);
        holder.reviewTxt = (TextView) view.findViewById(R.id.review_text);
        holder.stars = view.findViewById(R.id.review_stars);

        holder.stars.setBackgroundColor(Color.RED);

        //current retailer
        Review review = reviewList.get(position);

        //set retailer details
        holder.userEmail.setText(review.getName());
        holder.dateTV.setText(review.getTime_date());
        holder.reviewTxt.setText(review.getText());
        holder.stars.setNumStars(review.getStars());


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    public class RetailerReviewViewHolder extends RecyclerView.ViewHolder {
        TextView userEmail, dateTV, reviewTxt;
        RatingBar stars;
        LinearLayout parent;

        public RetailerReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            parent = itemView.findViewById(R.id.parent);

        }
    }
}
