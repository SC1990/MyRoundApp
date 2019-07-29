package com.example.stuar.myroundapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.stuar.myroundapp.CustomerActivities.RetailerList;
import com.example.stuar.myroundapp.CustomerActivities.RetailerProfileCustView;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.Models.Review;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RetailerReviewList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RetailerReviewListAdapter adapter;
    private ArrayList<Review> reviews;
    private Button button;
    private float stars;

    DatabaseReference dbRets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailer_reviews);

        reviews = new ArrayList<>();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer2);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {

                    startActivity(new Intent(RetailerReviewList.this, RetailerProfileCustView.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recyclerView = findViewById(R.id.review_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviews = new ArrayList<>();
        adapter = new RetailerReviewListAdapter(this, reviews);
        recyclerView.setAdapter(adapter);

        dbRets = FirebaseDatabase.getInstance().getReference("retailer_reviews/" + RetailerDetails.retailerName);
        dbRets.addListenerForSingleValueEvent(valueEventListener);


        button = findViewById(R.id.add_review_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialogBuilder = new AlertDialog.Builder(RetailerReviewList.this).create();
                LayoutInflater inflater = RetailerReviewList.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.new_review, null);

                final EditText reviewET = dialogView.findViewById(R.id.edt_comment);
                Button button1 = dialogView.findViewById(R.id.buttonSubmit);
                Button button2 = dialogView.findViewById(R.id.buttonCancel);

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());
                        String uniqueReviewKey = currentDateandTime + "_" + RememberMe.currentOnlineUser.getPhone();

                        String review = String.valueOf(reviewET.getText());

                        final RatingBar ratingBar = findViewById(R.id.ratingBar);
                        stars = ratingBar.getRating();

                        final DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("retailer_reviews");
                        final HashMap<String, Object> reviewMap = new HashMap<>();
                        reviewMap.put("name", RememberMe.currentOnlineUser.getName());
                        reviewMap.put("text", review);
                        reviewMap.put("rId", RetailerDetails.retailerId);
                        reviewMap.put("ret_name", RetailerDetails.retailerName);
                        reviewMap.put("time_date", currentDateandTime);
                        reviewMap.put("stars", stars);

                        reviewRef.child(RetailerDetails.retailerName).child(uniqueReviewKey).updateChildren(reviewMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Intent intent = new Intent(RetailerReviewList.this, RetailerList.class);
                                            startActivity(intent);
                                            Toast.makeText(RetailerReviewList.this, "Review submitted successfully..", Toast.LENGTH_SHORT).show();
                                        }
                                        else{

                                            String message = task.getException().toString();
                                            Toast.makeText(RetailerReviewList.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });


    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            reviews.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Review review = dataSnapshot1.getValue(Review.class);
                    reviews.add(review);
                }
                adapter.notifyDataSetChanged();
            }

        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu_retailer_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
