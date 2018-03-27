package com.nepal.naxa.smartnaari.services;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesListActivity extends BaseActivity {

    @BindView(R.id.services_list_recyclerView)
    RecyclerView servicesListRecyclerView;

    AppDataManager appDataManager ;
    List<ServicesData> servicesData ;

    public static List<ServicesData> resultCur = new ArrayList<>();
    public static List<ServicesData> filteredList = new ArrayList<>();
    ServicesListAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        ButterKnife.bind(this);

        initToolbar();

        appDataManager = new AppDataManager(this);

        getServicesDataFromDatabase();

        initServicesList();




    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Services");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem mapViewServices = menu.findItem(R.id.item_map_menu);
        mapViewServices.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.item_map_menu:
                Intent mapIntent = new Intent(ServicesListActivity.this, ServicesActivity.class);
                startActivity(mapIntent);
                break;

            case R.id.item_call:
                Intent callIntent = new Intent(ServicesListActivity.this, TapItStopItActivity.class);
                startActivity(callIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getServicesDataFromDatabase (){
        servicesData = appDataManager.getAllServicesdata();
    }

    private void initServicesList() {

        try {
            resultCur.clear();
            for (int i = 0; i < servicesData.size(); i++) {

                resultCur.add(servicesData.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ca = new ServicesListAdapter(this, resultCur);
        ca.notifyDataSetChanged();
        servicesListRecyclerView.setAdapter(ca);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        servicesListRecyclerView.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xxsmall);
        servicesListRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, spacingInPixels, true, 0));

    }



//    public static List<ServicesData> sortServiceData(List<ServicesData> servicesData, final double myLatitude,final double myLongitude) {
//
//        Comparator comp = new Comparator<Location>() {
//            @Override
//            public int compare(Location o, Location o2) {
//
//
//
//                float[] result1 = new float[3];
//                android.location.Location.distanceBetween(myLatitude, myLongitude, o.Lat, o.Long, result1);
//                Float distance1 = result1[0];
//
//                float[] result2 = new float[3];
//                android.location.Location.distanceBetween(myLatitude, myLongitude, o2.Lat, o2.Long, result2);
//                Float distance2 = result2[0];
//
//                return distance1.compareTo(distance2);
//            }
//        };
//
//        Collections.sort(servicesData, comp);
//        return servicesData;
//    }

}
