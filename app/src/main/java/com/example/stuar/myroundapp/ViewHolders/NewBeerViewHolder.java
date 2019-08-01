package com.example.stuar.myroundapp.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stuar.myroundapp.Interfaces.ItemClickListener;
import com.example.stuar.myroundapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewBeerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView bName, bStyle, bABV, moreInfo;
    public ImageButton addToFavs;
    public CircleImageView bImage;
    public ItemClickListener listener;

    public NewBeerViewHolder(@NonNull View itemView) {
        super(itemView);

        bImage = (CircleImageView) itemView.findViewById(R.id.new_beer_img);
        bName = (TextView) itemView.findViewById(R.id.b_name);
        bStyle = (TextView) itemView.findViewById(R.id.b_style);
        bABV = (TextView) itemView.findViewById(R.id.b_abv);
        moreInfo = (TextView) itemView.findViewById(R.id.more_info);
        addToFavs = itemView.findViewById(R.id.add_to_favs_btn);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {

    }
}
