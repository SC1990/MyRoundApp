package com.example.stuar.myroundapp.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.stuar.myroundapp.OrderActivities.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView userEmail, dateTV, reviewTxt;
    public CircleImageView imageView;
    public ItemClickListener listener;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        userEmail =  itemView.findViewById(R.id.u_email);
        dateTV =  itemView.findViewById(R.id.t_date);
        reviewTxt =  itemView.findViewById(R.id.review_text);
    }


}

