package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

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
    private String status = "";
    NotificationBadge badge;
    private int cartCount;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        cartCount = RememberMe.cartCount;
        Paper.init(this);


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

               /* if(status.equals("CustomerOrder Placed") *//*|| status.equals("Out for delivery")*//*){
                    Toast.makeText(getApplicationContext(), "Please wait for order confirmation", Toast.LENGTH_SHORT).show();
                }
                else{*/


                    addToCart();
                    RememberMe.cartCount++;

                }
           // }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_cart, menu);
        View view = menu.findItem(R.id.action_cart).getActionView();
        badge = view.findViewById(R.id.badge);
        updateCartCounter();

        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_cart){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        updateCartCounter();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkOrderStatus();

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
        cartMap.put("total", Integer.valueOf(counterBtn.getNumber()) * price);

        RememberMe.total += Integer.valueOf(counterBtn.getNumber()) * price;

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

                    price = Integer.valueOf(product.getPrice());

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


    private void checkOrderStatus(){
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(RememberMe.currentOnlineUser.getPhone());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String orderStatus = dataSnapshot.child("status").getValue().toString();

                    if(orderStatus.equals("Out for Delivery")){
                        status = "Out for delivery";

                    }
                    else if(orderStatus.equals("CustomerOrder Placed")){
                        status = "CustomerOrder Placed";

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
