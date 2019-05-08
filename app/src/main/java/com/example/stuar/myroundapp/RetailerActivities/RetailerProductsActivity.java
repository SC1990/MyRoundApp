package com.example.stuar.myroundapp.RetailerActivities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.stuar.myroundapp.ImageUpload;
import com.example.stuar.myroundapp.ImageUploader;
import com.example.stuar.myroundapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RetailerProductsActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    ListView listView;
    String retId;

    FirebaseAuth firebaseAuth;

    ArrayList<ImageUpload> rProds;

    DatabaseReference databaseReference;

    RetailerProdListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_products);

        listView = findViewById(R.id.listView);

     /*   rProds = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            retId = firebaseUser.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("prod_image");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ImageUpload imageUpload = dataSnapshot1.getValue(ImageUpload.class);
                    rProds.add(imageUpload);
                }

                adapter = new RetailerProdListAdapter(RetailerProductsActivity.this, R.layout.ret_prod_view, rProds );
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/




        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

     /*   drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer5);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/
    }


   /* @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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
    }*/

    public void onAddProdClick(View view) {
        if(view.getId() == R.id.floatingActionButton2){
            startActivity(new Intent(getApplicationContext(), AddNewProductActivity.class));
        }
    }


  /*  public class ProdImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ProdImageAdapterGridView(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return rProds.size();
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
                mImageView.setLayoutParams(new GridView.LayoutParams(130, 280));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imgIds[position]);
            return mImageView;
        }

    }*/
}
