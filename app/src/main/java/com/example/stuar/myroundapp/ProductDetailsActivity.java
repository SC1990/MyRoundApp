package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.stuar.myroundapp.CustomerActivities.RetailerProfileCustView;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.Models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button cartButton;
    private CircleImageView prodImg;
    private ElegantNumberButton counterBtn;
    private TextView pName, pDesc, pPrice;

    private String pId = "";
    private String rId = "";
    private String image = "";
    private String saveCurrentDate;
    private String saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        pId = getIntent().getStringExtra("pId");
        rId = getIntent().getStringExtra("rId");
        image = getIntent().getStringExtra("image");

        cartButton = findViewById(R.id.cart_btn);
        prodImg = findViewById(R.id.product_details_img);
        counterBtn = findViewById(R.id.product_counter);
        pName = findViewById(R.id.product_name);
        pDesc = findViewById(R.id.prod_desc);
        pPrice = findViewById(R.id.prod_price);

        getProductDetails(pId);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

    }

    private void addToCart() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-mm-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm-ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        RetailerDetails.retailerId = rId;

        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pId", pId);
        cartMap.put("rId", rId);
        cartMap.put("cartImage", image);
        cartMap.put("pName", pName.getText().toString());
        cartMap.put("price", pPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", counterBtn.getNumber());

        cartRef.child("Customer view").child(RememberMe.currentOnlineUser.getPhone())
                .child("Products").child(pId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            cartRef.child("Retailer view").child(RememberMe.currentOnlineUser.getPhone())
                                    .child("Products").child(pId)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailsActivity.this, RetailerProfileCustView.class);
                                                intent.putExtra("id", rId);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });

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
