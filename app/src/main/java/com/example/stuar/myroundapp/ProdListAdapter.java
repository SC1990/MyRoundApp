package com.example.stuar.myroundapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProdListAdapter extends ArrayAdapter<ImageUpload> {

    private Activity context;
    private int resource;
    private List<ImageUpload> imgs;

    public ProdListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        imgs = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(resource, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.img);
        TextView textView = (TextView)view.findViewById(R.id.retName);


        Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/myroundapp-925fa.appspot.com/o/image%2F1556729995922.jpg?alt=media&token=dd8944c8-9588-4b61-8008-fd07b7236f7d").into(imageView);
        textView.setText(imgs.get(position).getRetId());


        return view;
    }
}
