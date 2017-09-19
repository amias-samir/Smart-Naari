package com.nepal.naxa.smartnaari.smartparent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ChartColor;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class SmartParentActivity extends Activity {

    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    Float val1,val2,val3;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smart_parenting_lets_have_talkLBL)
    TextView tvHeader;
    @BindView(R.id.chart)
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_parent);

        ButterKnife.bind(this);

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        SpanUtils.setColor(tvHeader, "Lets Have the ", "Talk", color);

        val1 = Float.parseFloat("10");
        val2 = Float.parseFloat("40");
        val3 = Float.parseFloat("60");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
