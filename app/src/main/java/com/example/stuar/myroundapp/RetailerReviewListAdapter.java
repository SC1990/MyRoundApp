package com.example.stuar.myroundapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

public class RetailerReviewListAdapter extends RecyclerView.Adapter<RetailerReviewListAdapter.RetailerReviewViewHolder> {

    private Context context;
    private List<Review> reviewList;
    View view;

    public RetailerReviewListAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public RetailerReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.retailer_review_row, parent, false);
        return new RetailerReviewListAdapter.RetailerReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerReviewViewHolder retailerReviewViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class RetailerReviewViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parent1;

        public RetailerReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            parent1 = itemView.findViewById(R.id.parent);


        }
    }

}
