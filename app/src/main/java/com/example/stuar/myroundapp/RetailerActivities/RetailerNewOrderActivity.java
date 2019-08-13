package com.example.stuar.myroundapp.RetailerActivities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stuar.myroundapp.OrderActivities.OrderProductDetailsActivity;
import com.example.stuar.myroundapp.Models.CustomerOrder;
import com.example.stuar.myroundapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetailerNewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.orders_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CustomerOrder> options =
                new FirebaseRecyclerOptions.Builder<CustomerOrder>()
                .setQuery(ordersRef, CustomerOrder.class)
                .build();

        FirebaseRecyclerAdapter<CustomerOrder, OrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<CustomerOrder, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, final int position, @NonNull CustomerOrder model) {

                        holder.name.setText("Name: " + model.getName());
                        holder.phone.setText("Number: " + model.getPhone());
                        holder.total.setText("Total: €" + model.getTotal());
                        holder.address.setText("Address: " + model.getAddress());
                        holder.date.setText("Date/time: " + model.getDate() + "/" + model.getTime());

                        holder.moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uid = getRef(position).getKey();

                                Intent intent = new Intent(RetailerNewOrderActivity.this, OrderProductDetailsActivity.class);
                                intent.putExtra("uid", uid);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_layout, viewGroup, false);
                        return new OrderViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        public TextView name, phone, total, address, date;
        public Button moreDetailsBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cname);
            phone = itemView.findViewById(R.id.phone);
            total = itemView.findViewById(R.id.order_total_price);
            address = itemView.findViewById(R.id.c_address);
            date = itemView.findViewById(R.id.date_time);

            moreDetailsBtn = itemView.findViewById(R.id.more_details_btn);

            moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
