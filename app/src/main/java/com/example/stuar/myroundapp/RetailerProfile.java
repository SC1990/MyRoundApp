package com.example.stuar.myroundapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RetailerProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;
    Integer imgIds[] = {R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle,
            R.drawable.bbottle, R.drawable.bbottle, R.drawable.bbottle};

    final Context context = this;

    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_profile);

        tvName = (TextView)findViewById(R.id.prof_name);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        if(b!=null)
        {
            String name =(String) b.get("name");
            tvName.setText(name);
        }

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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    startActivity(new Intent(RetailerProfile.this, RetailerReviewList.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        gridView = findViewById(R.id.gridview_android_example);
        gridView.setAdapter(new ImageAdapterGridView(this));


        //product view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // custom dialog
                final AlertDialog dialog = new AlertDialog.Builder(context)
                        //.setTitle("Goose IPA 355ml")
                        .setView(R.layout.prod_popup)
                        .create();

                dialog.show();

                ImageButton closeDialog = (ImageButton) dialog.findViewById(R.id.x_butt);
                Button increment = (Button) dialog.findViewById(R.id.incrementQuantity);
                Button decrement = (Button) dialog.findViewById(R.id.decrementQuantity);
                Button addToCart = (Button) dialog.findViewById(R.id.add_to_cart_button);

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                increment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                decrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart();
                    }
                });

                dialog.show();
            }
        });



    }

    private void addToCart(){
        //new table in db
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartHmap = new HashMap<>();
        //cartHmap.put("")
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

    //cart icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cart_icon:
                startActivity(new Intent(getApplicationContext(), Cart.class));
                break;

        /*    case R.id.search_products_icon:
                startActivity(new Intent(getApplicationContext(), LogIn.class));
                break;*/

        }

        return true;
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
