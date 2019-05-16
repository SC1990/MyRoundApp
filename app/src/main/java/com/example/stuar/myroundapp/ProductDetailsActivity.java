package com.example.stuar.myroundapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.stuar.myroundapp.Models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {

    private FloatingActionButton cartButton;
    private CircleImageView prodImg;
    private ElegantNumberButton counterBtn;
    private TextView pName, pDesc, pPrice;

    private String pId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        pId = getIntent().getStringExtra("pId");

        cartButton = findViewById(R.id.cart_btn);
        prodImg = findViewById(R.id.product_details_img);
        counterBtn = findViewById(R.id.product_counter);
        pName = findViewById(R.id.product_name);
        pDesc = findViewById(R.id.prod_desc);
        pPrice = findViewById(R.id.prod_price);

        getProductDetails(pId);

    }

    private void getProductDetails(String pId) {
        final DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(pId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    Product product = dataSnapshot.getValue(Product.class);
                    pName.setText(product.getpName());
                    pDesc.setText(product.getDescription());
                    pPrice.setText(product.getPrice());
                    Picasso.get().load(product.getpImage()).into(prodImg);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
