package com.example.stuar.myroundapp.RetailerActivities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.Models.ImageUpload;
import com.example.stuar.myroundapp.Models.Product;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.ViewHolders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RetailerProductsActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    ListView listView;
    String retId;

    FirebaseAuth firebaseAuth;

    ArrayList<ImageUpload> rProds;

    DatabaseReference databaseReference;

    RetailerProdListAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_products);


     /*   rProds = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            retId = firebaseUser.getName();
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



     /*   drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer5);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.ret_prodlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();



        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(databaseReference.orderByChild("retId").equalTo(RetailerDetails.retailerId), Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Product model) {

                        holder.txtProductName.setText(model.getpName());
                        //holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("€" + model.getPrice());
                        Picasso.get().load(model.getpImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(RetailerProductsActivity.this, MaintainProductsActivity.class);
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



}
