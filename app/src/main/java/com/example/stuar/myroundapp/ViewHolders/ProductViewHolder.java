package com.example.stuar.myroundapp.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuar.myroundapp.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public CircleImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (CircleImageView) itemView.findViewById(R.id.cv_product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.cv_product_name);
        txtProductPrice = (TextView) itemView.findViewById(R.id.cv_product_price);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
