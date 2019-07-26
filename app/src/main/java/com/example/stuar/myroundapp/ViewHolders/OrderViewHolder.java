package com.example.stuar.myroundapp.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuar.myroundapp.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView rname, timeDate, discount, subtotal, dFee, total;
    public CircleImageView imageView;
    public ItemClickListener listener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        //imageView = (CircleImageView) itemView.findViewById(R.id.cv_product_image);
        rname = itemView.findViewById(R.id.rname);
        timeDate = itemView.findViewById(R.id.time_date);
        discount = itemView.findViewById(R.id.discount2);
        subtotal = itemView.findViewById(R.id.subtotal2);
        dFee = itemView.findViewById(R.id.delivery_fee2);
        total = itemView.findViewById(R.id.order_total2);

    }


}
