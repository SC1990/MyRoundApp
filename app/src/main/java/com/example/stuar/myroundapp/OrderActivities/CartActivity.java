package com.example.stuar.myroundapp.OrderActivities;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.Cart;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.ViewHolders.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextBtn;
    private TextView totalTv, orderMsg;
    private CircleImageView cart_prodImg;

    private int orderTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart3);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cart_prodImg = findViewById(R.id.p_img);


        nextBtn = findViewById(R.id.next_btn);
        totalTv = findViewById(R.id.total);
        orderMsg = findViewById(R.id.order_msg);


        totalTv.setText("Total: €" + String.valueOf(RememberMe.total));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this , ConfirmOrderActivity.class);
                intent.putExtra("total", String.valueOf(orderTotal));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkOrderStatus();

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

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final CharSequence options[] = new CharSequence[]{
                                        "Edit",
                                        "Remove"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(which == 0){

                                            Intent intent = new Intent(CartActivity.this , ProductDetailsActivity.class);
                                            intent.putExtra("pId", model.getpId());
                                            startActivity(intent);
                                        }
                                        if(which == 1){
                                            databaseReference.child("Customer view")
                                                    .child(RememberMe.currentOnlineUser.getPhone())
                                                    .child("Products")
                                                    .child(model.getpId())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){
                                                                Toast.makeText(getApplicationContext(), model.getpName() + " removed from cart", Toast.LENGTH_SHORT).show();
                                                                if(RememberMe.cartCount != 0){
                                                                    RememberMe.cartCount--;
                                                                }

                                                                Intent intent = new Intent(CartActivity.this , CartActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                });
                                builder.setCancelable(true);
                                builder.show();
                            }
                        });
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

    private void checkOrderStatus(){
        /*DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(RememberMe.currentOnlineUser.getPhone());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String orderStatus = dataSnapshot.child("status").getValue().toString();
                    String customerName = dataSnapshot.child("name").getValue().toString();

                    if(orderStatus.equals("Out for Delivery")){
                        totalTv.setText("Hi " + customerName + " your Total: €" + orderTotal);
                        recyclerView.setVisibility(View.GONE);
                        orderMsg.setVisibility(View.VISIBLE);
                        nextBtn.setVisibility(View.GONE);

                    }
                    else if(orderStatus.equals("CustomerOrder Placed")){
                        totalTv.setText("CustomerOrder status: " + orderStatus);
                        recyclerView.setVisibility(View.GONE);
                        orderMsg.setVisibility(View.VISIBLE);
                        nextBtn.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
