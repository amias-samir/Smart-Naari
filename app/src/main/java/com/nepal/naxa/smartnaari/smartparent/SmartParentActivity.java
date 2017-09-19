package com.nepal.naxa.smartnaari.smartparent;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ChartColor;

import java.util.ArrayList;

public class SmartParentActivity extends Activity {

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    Float val1,val2,val3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_parent);


        val1 = Float.parseFloat("10");
        val2 = Float.parseFloat("40");
        val3 = Float.parseFloat("60");

        chart = (BarChart) findViewById(R.id.chart);
        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(val1, 0));
        BARENTRY.add(new BarEntry(val2, 1));
        BARENTRY.add(new BarEntry(val3, 2));

        BarEntryLabels = new ArrayList<String>();
        BarEntryLabels.add("2-12 Year's Old");
        BarEntryLabels.add("Early Teen's");
        BarEntryLabels.add("Mid Teen's");

        Bardataset = new BarDataSet(BARENTRY, "Yuwa Pusta");

        Bardataset.setColors(ChartColor.COLORFUL_COLORS);

        BARDATA = new BarData(BarEntryLabels, Bardataset);


        chart.setData(BARDATA);
        chart.setDescription("Desc");
        chart.animateY(3000);

        chart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image





    }
}
