package com.example.stuar.myroundapp.MyBeersActivities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.stuar.myroundapp.Models.NewBeer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.stuar.myroundapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class BeerLeaderboardActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TableLayout beerTable;
    LinearLayout layout;
    ArrayList<NewBeer> topBeers;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_beer_leaderboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        topBeers = new ArrayList<>();

        layout = findViewById(R.id.ll);

        beerTable = findViewById(R.id.beer_table);

        databaseReference = FirebaseDatabase.getInstance().getReference("new_beers");

        counter = 1;

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
                    newBeer.setNotes(dataSnapshot1.child("notes").getValue().toString());

                    //arraylist
                    topBeers.add(newBeer);

                    TableRow tr = new TableRow(BeerLeaderboardActivity.this);
                    tr.setBackgroundResource(R.color.grey2);

                    tr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog dialog = new AlertDialog.Builder(BeerLeaderboardActivity.this)
                                    .setTitle(newBeer.getName() + "      style: " + newBeer.getStyle())
                                    .setMessage("ABV: " + newBeer.getAbv())
                                    .setView(R.layout.prod_popup)
                                    .create();

                            dialog.show();

                            LinearLayout linearLayout = dialog.findViewById(R.id.ll2);

                            CircleImageView image = dialog.findViewById(R.id.image__);
                            Picasso.get().load(newBeer.getImage()).into(image);
                            TextView textView = dialog.findViewById(R.id.rev);
                            textView.setText(newBeer.getNotes());
                            RatingBar ratingBar = dialog.findViewById(R.id.rb);
                            ratingBar.setRating(Integer.valueOf(newBeer.getRating()));

                        }
                    });


                    TextView name = new TextView(BeerLeaderboardActivity.this);
                    name.setText((counter + " ") + newBeer.getName());
                    name.setPadding(0, 10, 5, 18);
                    name.setTextColor(Color.BLACK);
                    name.setTypeface(Typeface.MONOSPACE);

                    TextView style = new TextView(BeerLeaderboardActivity.this);
                    style.setText(newBeer.getStyle());
                    style.setPadding(55, 10, 5, 18);
                    style.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
                    style.setTextColor(Color.BLACK);

                    TextView rating = new TextView(BeerLeaderboardActivity.this);
                    rating.setText(newBeer.getRating() + "/5");
                    rating.setPadding(115, 10, 5, 18);
                    rating.setTextColor(Color.BLACK);

                    tr.addView(name);
                    tr.addView(style);
                    tr.addView(rating);
                    beerTable.addView(tr);

                    counter++;


                }



                    /*Map<String, Integer> nameCounts = new HashMap<>();

                    //check most common names
                    for (NewBeer newBeer1 : topBeers) {
                        if (nameCounts.containsKey(newBeer1.getName())) {
                            nameCounts.put(newBeer1.getName(), nameCounts.get(newBeer1.getName()) + 1);
                        } else {
                            nameCounts.put(newBeer1.getName(), 1);
                        }
                    }

                    Map<String, Integer> ratings = new HashMap<>();

                    //get highest rated
                    for (NewBeer newBeer2 : topBeers) {
                        if (ratings.containsKey(newBeer2.getRating())) {
                            ratings.put(newBeer2.getRating(), nameCounts.get(newBeer2.getRating()) + 1);
                        } else {
                            ratings.put(newBeer2.getRating(), 1);
                        }
                    }*/

                    /*TableLayout topBeers = (TableLayout)findViewById(R.id.table2);
                    topBeers.setStretchAllColumns(true);
                    topBeers.bringToFront();

                    //top 10 beers
                    for(int i = 0; i < 10; i++){

                        TableRow tr =  new TableRow(BeerLeaderboardActivity.this);
                        tr.setBackgroundColor(Color.WHITE);



                        TextView name = new TextView(BeerLeaderboardActivity.this);
                        name.setText((i + 1) + " " + newBeer.getName());

                        TextView style = new TextView(BeerLeaderboardActivity.this);
                        style.setText(newBeer.getStyle());
                        style.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);

                        *//*TextView abv = new TextView(BeerLeaderboardActivity.this);
                        abv.setText(newBeer.getAbv());*//*

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
                    }*/






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}





