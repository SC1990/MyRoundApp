package com.example.stuar.myroundapp.RetailerActivities;

import android.content.Intent;

import com.example.stuar.myroundapp.Other.MainPageActivity;
import com.example.stuar.myroundapp.Other.MyDetails;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.Other.RetailerSalesActivities.PreSalesAnalysis;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class RetailerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView retEmailTV;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);

        Paper.init(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer_customer_home);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        retEmailTV = findViewById(R.id.retEmailTV);
        retEmailTV.setText(RememberMe.currentOnlineUser.getName());


    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //String itemName = (String)item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_login:
                break;

            case R.id.find_beer:
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



    public void onDetailsBtnClick1(View view) {
        if(view.getId() == R.id.myDetailsBtn1){
            retrieveUserInfo();
        }
    }

    private void retrieveUserInfo() {
        startActivity(new Intent(RetailerHome.this, MyDetails.class));
    }

    public void onRetOrdersBtnClick(View view) {
        if(view.getId() == R.id.retOrdersBtn){
            Intent intent = new Intent(RetailerHome.this, RetailerNewOrderActivity.class);
            startActivity(intent);
        }
    }

    public void onProductsClick(View view) {
        if(view.getId() == R.id.myProdBtn){
            startActivity(new Intent(getApplicationContext(), RetailerProductsActivity.class));
        }
    }

    public void onLogoutClick(View view) {
        if(view.getId() == R.id.loBtn){
            Paper.book().destroy();
            //firebaseAuth.signOut();
            Intent intent = new Intent(RetailerHome.this, MainPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void onSalesBtnClick(View view) {
        if(view.getId() == R.id.sales_btn){

            startActivity(new Intent(RetailerHome.this, PreSalesAnalysis.class));

        }
    }
}

