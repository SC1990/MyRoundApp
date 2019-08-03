package com.example.stuar.myroundapp.RetailerActivities;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.stuar.myroundapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RetailerSalesOverviewActivity extends AppCompatActivity {

    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailer_sales_overview);

        pieChartView = findViewById(R.id.chart);

        //orders > orders containing this retailer's id > get child total > add to total sales
        //orders > orders containing this retailer's id > get customer userID > add to list of unique customers
        //orders > orders containing this retailer's id > increment ordersAmount

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Total sales: €8,300.60"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Gross profit: €6,100.40"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Total orders: 560"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Unique customers: 178"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Last 12 months").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);

    }


}
