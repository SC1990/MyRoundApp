package com.example.stuar.myroundapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetailerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RetailerListAdapter adapter;
    private ArrayList<Retailer> retailers;
    private ArrayList<String> retailerImages;

    DatabaseReference dbRets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retailers = new ArrayList<>();
        retailerImages = new ArrayList<>();
        adapter = new RetailerListAdapter(this, retailers);
        recyclerView.setAdapter(adapter);


        dbRets = FirebaseDatabase.getInstance().getReference("users/retailers");
        dbRets.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            retailers.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Retailer retailer = dataSnapshot1.getValue(Retailer.class);
                    retailers.add(retailer);
                }
                    adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
