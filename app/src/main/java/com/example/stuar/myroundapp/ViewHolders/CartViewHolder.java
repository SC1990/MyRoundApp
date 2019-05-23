package com.example.stuar.myroundapp.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.stuar.myroundapp.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pName, quantity, pPrice;
    public CircleImageView imageView;
    public ItemClickListener listener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.p_img);
        pName = (TextView) itemView.findViewById(R.id.prod_name);
        quantity = (TextView) itemView.findViewById(R.id.prod_quantity);
        pPrice = (TextView) itemView.findViewById(R.id.price);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}