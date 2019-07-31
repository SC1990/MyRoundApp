package com.example.stuar.myroundapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stuar.myroundapp.Models.Cart;
import com.example.stuar.myroundapp.ViewHolders.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OrderProductDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference prodsRef;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_prod_details);

        uid = getIntent().getStringExtra("uid");

        prodsRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart")
                .child("Retailer view")
                .child(uid)
                .child("Products");


        recyclerView = findViewById(R.id.orders_prodlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(prodsRef, Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                        Picasso.get().load(model.getCartImage()).into(holder.imageView);
                        holder.pName.setText(model.getpName());
                        holder.pPrice.setText("Price: " + model.getPrice() + "€");
                        holder.quantity.setText("Quantity: " + model.getQuantity());
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items, viewGroup, false);
                        CartViewHolder cartViewHolder = new CartViewHolder(view);
                        return  cartViewHolder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
