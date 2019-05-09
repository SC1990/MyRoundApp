package com.example.stuar.myroundapp.CustomerActivities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
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

import com.example.stuar.myroundapp.LogIn;
import com.example.stuar.myroundapp.MainPageActivity;
import com.example.stuar.myroundapp.MyDetails;
import com.example.stuar.myroundapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new ImageAdapterGridView(this));


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



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


        //userNameTextView.setText(firebaseUser.getUid());

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
            finish();
            startActivity(new Intent(CustomerHome.this, MainPageActivity.class));
        }
    }

    private void retrieveUserInfo() {
        startActivity(new Intent(CustomerHome.this, MyDetails.class));
    }

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
