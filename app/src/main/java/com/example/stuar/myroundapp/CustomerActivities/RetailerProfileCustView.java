package com.example.stuar.myroundapp.CustomerActivities;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stuar.myroundapp.CartActivity;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.ImageUpload;
import com.example.stuar.myroundapp.Models.Product;
import com.example.stuar.myroundapp.ProductDetailsActivity;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.RetailerReviewList;
import com.example.stuar.myroundapp.SearchProductsActivity;
import com.example.stuar.myroundapp.ViewHolders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;


public class RetailerProfileCustView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    final Context context = this;
    private TextView tvName;

    ArrayList<ImageUpload> rProds;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    //ProdListCustViewAdapter adapter;

    String rID;
    String name;
    String rIdCheck = "";

    FirebaseDatabase database;
    NotificationBadge badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_profile);

        Paper.init(this);

        tvName = findViewById(R.id.prof_name);


        Intent i = getIntent();
        Bundle b = i.getExtras();

        /*rID = (String) b.get("id");*/
        rID = RetailerDetails.retailerId;
        //name = (String) b.get("name");
        name = RetailerDetails.retailerName;

        RememberMe.rName = name;
        tvName.setText(RememberMe.rName);



        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Products");




      /*  Retailer retailer = new Retailer();
        retailer.setName(name);


        if(rID == null){
            Toast.makeText(RetailerProfileCustView.this, "nope", Toast.LENGTH_SHORT).show();
        }

        rProds = new ArrayList<>();

        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);*/


       /* databaseReference = FirebaseDatabase.getInstance().getReference("prod_image");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    ImageUpload imageUpload = dataSnapshot1.getValue(ImageUpload.class);

                    if(imageUpload.getRetId().equalsIgnoreCase(rID)){
                        rProds.add(imageUpload);
                   }else{
                        Toast.makeText(RetailerProfileCustView.this, "nope", Toast.LENGTH_SHORT).show();
                    }



                }
                adapter = new ProdListCustViewAdapter(RetailerProfileCustView.this, R.layout.grid_img, rProds );
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*NavigationView navigationView = (NavigationView)findViewById(R.id.drawer2);
        navigationView.setNavigationItemSelectedListener(this);*/


        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    startActivity(new Intent(RetailerProfileCustView.this, RetailerReviewList.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




       /* //product view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(databaseReference.orderByChild("retId").equalTo(rID), Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Product model) {
                        rIdCheck = model.getRetId();

                        if(model.getRetId().equals(rID)){
                            holder.txtProductName.setText(model.getpName());
                            //holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText("€" + model.getPrice());
                            Picasso.get().load(model.getpImage()).into(holder.imageView);
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(RetailerProfileCustView.this, ProductDetailsActivity.class);
                                intent.putExtra("rId", model.getRetId());
                                intent.putExtra("pId", model.getpId());
                                intent.putExtra("image", model.getpImage());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
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

    //cart icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                break;

            case R.id.search_products_icon:
                startActivity(new Intent(getApplicationContext(), SearchProductsActivity.class));
                break;

        }

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    /*public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context context) {
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
