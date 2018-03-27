package com.nepal.naxa.smartnaari.services;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesListActivity extends BaseActivity {

    @BindView(R.id.services_list_recyclerView)
    RecyclerView servicesListRecyclerView;

    AppDataManager appDataManager;
    List<ServicesData> servicesData;

    Map<ServicesData, Float> hashMapWithDistance;
    List<ServicesData> sortedServicesList;
    List<Float> sortedServicesDistanceList;

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

        sortingServiceData();

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
                finish();
                break;

            case R.id.item_call:
                Intent callIntent = new Intent(ServicesListActivity.this, TapItStopItActivity.class);
                startActivity(callIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getServicesDataFromDatabase() {
        servicesData = appDataManager.getAllServicesdata();

    }

    private void initServicesList(List<ServicesData> servicesData, List<Float> servicesDistance) {

        try {
            resultCur.clear();
          resultCur.addAll(servicesData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (servicesData == null) {
            return;
        }
        ca = new ServicesListAdapter(this, resultCur, servicesDistance);
        ca.notifyDataSetChanged();
        servicesListRecyclerView.setAdapter(ca);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        servicesListRecyclerView.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xxsmall);
        servicesListRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, spacingInPixels, true, 0));

    }


    public void sortingServiceData() {

        hashMapWithDistance = new HashMap<ServicesData, Float>();

        final double myLat = 27.700769;
        final double myLon = 85.300140;

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (servicesData.size() > 1) {
//                    sortServiceData(servicesData, myLat, myLon);
                    for (int i = 0; i < servicesData.size(); i++) {
                        double latfirst = Double.parseDouble(servicesData.get(i).getServiceLat());
                        double longfirst = Double.parseDouble(servicesData.get(i).getServiceLon());

                        float[] result1 = new float[3];
                        Location.distanceBetween(myLat, myLon, latfirst, longfirst, result1);
                        Float distance1 = result1[0];

                        hashMapWithDistance.put(servicesData.get(i), distance1);
                    }
                    sortMapByValuesWithDuplicates(hashMapWithDistance);
                }
            }
        }).start();
    }

    private void sortMapByValuesWithDuplicates(Map passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
//        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                Object comp1 = passedMap.get(key);
                Float comp2 = Float.parseFloat(val.toString());

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((ServicesData) key, (Float) val);
                    break;
                }
            }
        }
        //Getting Set of keys from HashMap
        Set<ServicesData> keySet = sortedMap.keySet();
        //Creating an ArrayList of keys by passing the keySet
        sortedServicesList = new ArrayList<ServicesData>(keySet);


        //Getting Collection of values from HashMap
        Collection<Float> values = sortedMap.values();
        //Creating an ArrayList of values
        sortedServicesDistanceList = new ArrayList<Float>(values);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initServicesList(sortedServicesList, sortedServicesDistanceList);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent mapIntent = new Intent(ServicesListActivity.this, ServicesActivity.class);
        startActivity(mapIntent);
        finish();
    }
}
