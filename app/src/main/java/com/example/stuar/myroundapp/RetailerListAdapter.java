package com.example.stuar.myroundapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.RetailerViewHolder> {

    private Context mCtx;
    private List<Retailer> retailerList;

    public RetailerListAdapter(Context mCtx, List<Retailer> artistList) {
        this.mCtx = mCtx;
        this.retailerList = artistList;
    }

    @NonNull
    @Override
    public RetailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.retailer_row, parent, false);
        return new RetailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerViewHolder holder, int position) {
        Log.d(TAG, "onClick: clicked on: " + retailerList.get(position));
        Retailer retailer = retailerList.get(position);
        holder.textViewName.setText(retailer.getName());
    }

    @Override
    public int getItemCount() {
        return retailerList.size();
    }

    class RetailerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge, textViewCountry;
        RelativeLayout parent;

        public RetailerViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.retname);
            parent = itemView.findViewById(R.id.parent);


        }
    }
}
