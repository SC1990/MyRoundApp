package com.example.stuar.myroundapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.RetailerViewHolder> {

    private Context mCtx;
    private List<Retailer> retailerList;
    View view;
    DatabaseReference dbRef;

    public RetailerListAdapter(Context mCtx, List<Retailer> retailerList) {
        this.mCtx = mCtx;
        this.retailerList = retailerList;
    }

    @NonNull
    @Override
    public RetailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.retailer_row, parent, false);
        return new RetailerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RetailerViewHolder holder, final int position) {

        //Log.d(TAG, "onClick: clicked on: " + retailerList.get(position));
        //Retailer retailer = retailerList.get(position);
        //holder.textViewName.setText(retailer.getName());
        ;


        holder.textName = (TextView) view.findViewById(R.id.r_name);
            /*this.textStyle = (TextView) row.findViewById(R.id.tvCategory);
            this.textMin = (TextView) row.findViewById(R.id.tvTown);
            this.textDelFee = (TextView) row.findViewById(R.id.tvHours);*/

        //current retailer
        Retailer retailer = retailerList.get(position);

        //set retailer details
        holder.textName.setText(retailer.getName());


    }

    @Override
    public int getItemCount() {
        return retailerList.size();
    }




    class RetailerViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textStyle, textMin, textDelFee;
        LinearLayout parent;

        public RetailerViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            //textViewName = itemView.findViewById(R.id.tvName);
            parent = itemView.findViewById(R.id.parent);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(mCtx, RetailerProfile.class);
                    i.putExtra("name", retailerList.get(getAdapterPosition()).getName());
                    mCtx.startActivity(i);

                }
            });



        }
    }
}
