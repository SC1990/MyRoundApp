package com.example.stuar.myroundapp.CustomerActivities;

import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuar.myroundapp.CartActivity;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.MainPageActivity;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CustomerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView userEmailET;

    DrawerLayout drawerLayout;

    GridView gridView;
    Integer imgIds[] =  {
            R.drawable.or, R.drawable.sett, R.drawable.or, R.drawable.sett, R.drawable.or, R.drawable.sett
          };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        Paper.init(this);

        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new ImageAdapterGridView(this));


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer_customer_home);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHome.this, CartActivity.class);
                startActivity(intent);
            }
        });



      /*  //userEmailET = findViewById(R.id.userEmailET);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
           finish();
           startActivity(new Intent(getApplicationContext(), LogIn.class));
        }

        else{
            firebaseUser = firebaseAuth.getCurrentUser();
            //userEmailET.setText("Welcome " + firebaseUser.getEmail());
        }*/

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.profile_user_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        if(RememberMe.currentOnlineUser.getImage() == "empty"){
            RememberMe.currentOnlineUser.setImage(String.valueOf(R.drawable.av));
        }else{
            Picasso.get().load(RememberMe.currentOnlineUser.getImage()).into(profileImageView);
        }

        userNameTextView.setText(RememberMe.currentOnlineUser.getName());

    }

    public void onDetailsBtnClick(View view){
        if(view.getId() == R.id.myDetailsBtn1){
            retrieveUserInfo();
        }
    }

    public void onLogOutBtnClick(View view){
        if(view.getId() == R.id.logOutBtn){
            Paper.book().destroy();
            //firebaseAuth.signOut();
            Intent intent = new Intent(CustomerHome.this, MainPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void retrieveUserInfo() {
        startActivity(new Intent(CustomerHome.this, SettingsActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(CustomerHome.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.item_cart){
            Intent intent = new Intent(CustomerHome.this, CartActivity.class);
            startActivity(intent);

        }

        closeDrawer();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.action_user){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onViewRetailersBtnClick(View view){
        if(view.getId() == R.id.viewRetailersBtn1){
            fetchRetailers();
        }
    }

    private void fetchRetailers() {
        startActivity(new Intent(getApplicationContext(), RetailerList.class));
    }


    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView =  new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(250, 280));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imgIds[position]);
            return mImageView;
        }

    }


}
