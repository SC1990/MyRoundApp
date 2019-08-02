package com.example.stuar.myroundapp.CustomerActivities;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.NewBeer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;

import com.example.stuar.myroundapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class BeerLeaderboardActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TableLayout table;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_beer_leaderboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = findViewById(R.id.ll);

        table = findViewById(R.id.table2);

        databaseReference = FirebaseDatabase.getInstance().getReference("new_beers");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    NewBeer newBeer = dataSnapshot.getValue(NewBeer.class);

                    newBeer.setName(dataSnapshot1.child("name").getValue().toString());
                    newBeer.setStyle(dataSnapshot1.child("style").getValue().toString());
                    newBeer.setAbv(dataSnapshot1.child("abv").getValue().toString());
                    newBeer.setRating(dataSnapshot1.child("rating").getValue().toString());
                    newBeer.setImage(dataSnapshot1.child("image").getValue().toString());

                    TableLayout topBeers = (TableLayout)findViewById(R.id.table2);
                    topBeers.setStretchAllColumns(true);
                    topBeers.bringToFront();

                    //top 20 beers
                    for(int i = 0; i < 10; i++){

                        TableRow tr =  new TableRow(BeerLeaderboardActivity.this);
                        tr.setBackgroundColor(Color.WHITE);

                        tr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog dialog = new AlertDialog.Builder(BeerLeaderboardActivity.this)
                                        .setTitle(newBeer.getName())
                                        .setView(R.layout.prod_popup)
                                        .create();

                                dialog.show();

                                LinearLayout linearLayout = dialog.findViewById(R.id.ll2);

                                CircleImageView image = dialog.findViewById(R.id.image__);
                                Picasso.get().load(newBeer.getImage()).into(image);

                                RatingBar ratingBar = new RatingBar(getApplicationContext());
                                ratingBar.setNumStars(Integer.valueOf(newBeer.getRating()));
                                linearLayout.addView(ratingBar);




                            }
                        });

                        TextView name = new TextView(BeerLeaderboardActivity.this);
                        name.setText((i + 1) + " " + newBeer.getName());

                        TextView style = new TextView(BeerLeaderboardActivity.this);
                        style.setText(newBeer.getStyle());
                        style.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);

                        /*TextView abv = new TextView(BeerLeaderboardActivity.this);
                        abv.setText(newBeer.getAbv());*/

                        TextView rating = new TextView(BeerLeaderboardActivity.this);
                        rating.setText(newBeer.getRating() + "/5");

                        TextView votes = new TextView(BeerLeaderboardActivity.this);
                        votes.setText("86");

                        tr.setGravity(Gravity.CENTER);
                        tr.addView(name);
                        tr.addView(style);
                        //tr.addView(abv);
                        tr.addView(rating);
                        tr.addView(votes);

                        topBeers.addView(tr);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}





