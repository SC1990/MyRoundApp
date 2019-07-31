package com.example.stuar.myroundapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView rname, timeDate, discount, subtotal, dFee, total;
    private int orderTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);

        recyclerView = findViewById(R.id.order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rname = findViewById(R.id.rname);
        timeDate = findViewById(R.id.time_date);
        discount = findViewById(R.id.discount2);
        subtotal = findViewById(R.id.subtotal2);
        dFee = findViewById(R.id.delivery_fee2);
        total = findViewById(R.id.order_total2);

        rname.setText(RememberMe.rName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        timeDate.setText(currentDateandTime);

        discount.setText("€0");

        total.setText(String.valueOf(RememberMe.total));
        subtotal.setText(String.valueOf(RememberMe.total - 2.50));
        dFee.setText("€2.50");

    }


    /*@Override
    protected void onStart() {
               super.onStart();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(databaseReference.child("Customer view")
                                .child(RememberMe.currentOnlineUser.getPhone()).child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                        Picasso.get().load(model.getCartImage()).into(holder.imageView);
                        holder.pName.setText(model.getpName());
                        holder.pPrice.setText("Price: " + model.getPrice() + "€");
                        holder.quantity.setText("Quantity: " + model.getQuantity());

                        int prodTotal=(Integer.parseInt(model.getPrice().replaceAll("\\D+",""))) * Integer.parseInt(model.getQuantity());
                        orderTotal = orderTotal + prodTotal;


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



    }*/
}
