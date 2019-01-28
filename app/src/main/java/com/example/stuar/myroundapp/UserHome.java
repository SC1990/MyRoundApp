package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView userEmailET;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "Send message?", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, "Done!", Snackbar.LENGTH_SHORT).show();
                    }
                });

                snackbar.show();;
            }
        });

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        userEmailET = findViewById(R.id.userEmailET);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
           finish();
           startActivity(new Intent(getApplicationContext(), LogIn.class));
        }

        else{
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            userEmailET.setText("Welcome " + firebaseUser.getEmail());
        }

    }

    public void onDetailsBtnClick(View view){
        if(view.getId() == R.id.myDetailsBtn){
            retrieveUserInfo();
        }
    }

    private void retrieveUserInfo() {
        startActivity(new Intent(UserHome.this, MyDetails.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //String itemName = (String)item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_a:
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

    public void onViewRetailersBtnClick(View view){
        if(view.getId() == R.id.viewRetailersBtn){
            fetchRetailers();
        }
    }

    private void fetchRetailers() {
        startActivity(new Intent(getApplicationContext(), RetailerList.class));
    }


}
