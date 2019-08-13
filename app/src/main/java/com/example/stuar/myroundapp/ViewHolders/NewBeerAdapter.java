package com.example.stuar.myroundapp.ViewHolders;

import android.app.Activity;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stuar.myroundapp.Models.ImageUpload;
import com.example.stuar.myroundapp.R;

import java.util.List;

public class NewBeerAdapter extends ArrayAdapter<ImageUpload> {

    private Activity context;
    private int resource;
    private List<ImageUpload> imgs;

    public NewBeerAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
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

        ImageView imageView = (ImageView)view.findViewById(R.id.new_beer_img);
        //TextView textView = (TextView)view.findViewById(R.id.retName);


        Glide.with(context).load(imgs.get(position).getUrl()).into(imageView);
        Toast.makeText(context, imgs.get(position).getUrl(), Toast.LENGTH_SHORT).show();
        //textView.setText(imgs.get(position).getRetId());


        return view;
    }
}
