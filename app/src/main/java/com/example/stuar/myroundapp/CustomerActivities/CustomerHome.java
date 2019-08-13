package com.example.stuar.myroundapp.CustomerActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.stuar.myroundapp.MyBeersActivities.BeerLeaderboardActivity;
import com.example.stuar.myroundapp.MyBeersActivities.MyBeersActivity;
import com.example.stuar.myroundapp.OrderActivities.CartActivity;
import com.example.stuar.myroundapp.SignUpAndLogInActivities.LogIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Other.MainPageActivity;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.Other.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nex3z.notificationbadge.NotificationBadge;
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
            R.drawable.icons8_beer_40, R.drawable.beer_lboard, R.drawable.shop,
            R.drawable.form
          };
    NotificationBadge badge;

    CircleImageView findBeer;
    CircleImageView topBeers;
    CircleImageView myBeers;
    CircleImageView myDeets;

    private Button myBeersBtn;
    private Button lBoardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        myBeersBtn = findViewById(R.id.mybeers_btn);

        myBeersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHome.this, MyBeersActivity.class));
            }
        });


        findBeer = findViewById(R.id.shop);
        topBeers = findViewById(R.id.bl);
        myBeers = findViewById(R.id.mb);
        myDeets = findViewById(R.id.deets);

        findBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchRetailers();
            }
        });

        topBeers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), BeerLeaderboardActivity.class));
            }
        });

        myBeers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CustomerHome.this, MyBeersActivity.class));
            }
        });

        myDeets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrieveUserInfo();
            }
        });

        lBoardBtn = findViewById(R.id.leaderboard_btn);
        lBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), BeerLeaderboardActivity.class));
            }
        });

        Paper.init(this);

        /*gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new ImageAdapterGridView(this));*/


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
                if(RememberMe.cartCount != 0){
                    Intent intent = new Intent(CustomerHome.this, CartActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_SHORT).show();

                }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_cart, menu);
        View view = menu.findItem(R.id.action_cart).getActionView();
        badge = view.findViewById(R.id.badge);
        updateCartCounter();
        return super.onCreateOptionsMenu(menu);
    }

    private void updateCartCounter() {


        if(badge == null) return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(RememberMe.cartCount));

            }
        });

    }

    @Override
    protected void onResume() {
        updateCartCounter();
        super.onResume();
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
            Intent intent = new Intent(CustomerHome.this, LogIn.class);
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

        if (id == R.id.find_beer) {
            Intent intent = new Intent(CustomerHome.this, RetailerList.class);
            startActivity(intent);
        }

        if (id == R.id.nav_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerHome.this);
            builder.setTitle("Log out");

            builder.setMessage("Are you sure?");

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // User clicked the Yes button

                            Paper.book().destroy();

                            Intent intent = new Intent(CustomerHome.this, LogIn.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // User clicked the cancel button
                            break;
                    }
                }
            };

            // add the buttons
            builder.setPositiveButton("Yes", dialogClickListener);
            builder.setNegativeButton("Cancel", dialogClickListener);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

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
