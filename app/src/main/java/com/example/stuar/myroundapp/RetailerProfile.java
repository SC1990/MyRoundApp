package com.example.stuar.myroundapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class RetailerProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;
    Integer imgIds[] = {R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer2);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        gridView = findViewById(R.id.gridview_android_example);
        gridView.setAdapter(new ImageAdapterGridView(this));





    }

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
                mImageView.setLayoutParams(new GridView.LayoutParams(130, 280));
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
