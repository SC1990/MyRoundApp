package com.example.stuar.myroundapp.Other.RetailerSalesActivities;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RetailerSalesOverviewActivity extends AppCompatActivity {

    PieChartView pieChartView;

    DatabaseReference databaseReference;
    private int orderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailer_sales_overview);

        pieChartView = findViewById(R.id.chart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        calculateTotalOrders();

        //orders > orders containing this retailer's id > get child total > add to total sales
        //orders > orders containing this retailer's id > get customer userID > add to list of unique customers
        //orders > orders containing this retailer's id > increment ordersAmount

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Total sales: €8,300.60"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Gross profit: €6,100.40"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Total orders: " + orderCount));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Unique customers: 178"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Last 12 months").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);

    }


    private void calculateTotalGross(){

    }

    private void calculateTotalSales(){



    }

    private void calculateTotalOrders(){

        databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Toast.makeText(RetailerSalesOverviewActivity.this, String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();

                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if(dataSnapshot1.child("rId").equals(RetailerDetails.retailerId)){
                                orderCount++;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //Toast.makeText(this, String.valueOf(orderCount), Toast.LENGTH_SHORT).show();



    }

    private void calculateUniqueCustomers(){

    }


}
