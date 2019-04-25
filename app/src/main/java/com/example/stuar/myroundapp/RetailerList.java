package com.example.stuar.myroundapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetailerList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private RetailerListAdapter adapter;
    private ArrayList<Retailer> retailers;
    private ArrayList<String> retailerImages;

    DatabaseReference dbRets;
    DrawerLayout drawerLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_list);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading..");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retailers = new ArrayList<>();
        retailerImages = new ArrayList<>();
        adapter = new RetailerListAdapter(this, retailers);
        recyclerView.setAdapter(adapter);

        dbRets = FirebaseDatabase.getInstance().getReference("users/retailers");
        dbRets.addListenerForSingleValueEvent(valueEventListener);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


       /* adapter.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "click " , Toast.LENGTH_SHORT).show();
            }
        });*/

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
            progressDialog.dismiss();
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };







    //drawer

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //String itemName = (String)item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_login:
                break;

            case R.id.item_b:
                break;
        }

        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_user){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
