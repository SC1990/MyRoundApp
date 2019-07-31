package com.example.stuar.myroundapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.stuar.myroundapp.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewBeerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView bName, bStyle, bABV;
    public CircleImageView bImage;
    public ItemClickListener listener;

    public NewBeerViewHolder(@NonNull View itemView) {
        super(itemView);

        bImage = (CircleImageView) itemView.findViewById(R.id.new_beer_img);
        bName = (TextView) itemView.findViewById(R.id.product_name);
        bStyle = (TextView) itemView.findViewById(R.id.prod_desc);
        bABV = (TextView) itemView.findViewById(R.id.abv);
    }

    @Override
    public void onClick(View v) {

    }
}
