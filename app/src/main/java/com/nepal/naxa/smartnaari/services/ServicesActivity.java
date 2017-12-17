package com.nepal.naxa.smartnaari.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.utils.ColorList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServicesActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "ServicesActivity";


    @BindView(R.id.act_services_recycler_map_legend)
    RecyclerView recyclerMapLegend;
    AppDataManager appDataManager;

    List<ServicesData> servicesData;
    public static List<ServicesLegendListModel> resultCur = new ArrayList<>();
    public static List<ServicesLegendListModel> filteredList = new ArrayList<>();
    ServicesLegendListAdapter ca;

    Marker amarker;

    private GoogleMap map;
    private List<Marker> markersPresentOnMap;

    //cluster testing
    private ClusterManager<ServicesData> mClusterManager;
//    ==============

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        appDataManager = new AppDataManager(getApplicationContext());
        markersPresentOnMap = new ArrayList<>();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragMap);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        initToolbar();
        initMapLegend();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initMapLegend() {

        try {
            resultCur.clear();
            for (int i = 0; i < appDataManager.getAllUniqueServicesType().size(); i++) {

                ServicesLegendListModel newData = new ServicesLegendListModel();
                newData.serviceTypeID = appDataManager.getAllUniqueServicesType().get(i);

                resultCur.add(newData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ca = new ServicesLegendListAdapter(this, resultCur);
        recyclerMapLegend.setAdapter(ca);
        appDataManager.getAllUniqueServicesType();

        recyclerMapLegend.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            googleMap.setMyLocationEnabled(true);

        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub
                LatLng location = new LatLng(arg0.getLatitude(), arg0.getLongitude());

//                googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.5f));
            }
        });


        getServicesData();
        removeMarkersIfPresent();

//        cluster testing
        if (map != null) {
            return;
        }
        map = googleMap;
        startCluster();
        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
//        ==========================
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void getServicesData() {
        servicesData = appDataManager.getAllServicesdata();
        Log.d(TAG, "getServicesData: " + servicesData.size());
    }


    public void addMarker() {

        getMap().setOnMarkerClickListener(this);

        this.map = getMap();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LatLng location;
                    mClusterManager.addItems(servicesData);

                    for (int i = 0; i < servicesData.size(); i++) {

                        for (int j = 0; j < appDataManager.getAllUniqueServicesType().size(); j++) {

                            if (servicesData.get(i).getOfficeType().equals(appDataManager.getAllUniqueServicesType().get(j))) {

                                Double lat = Double.parseDouble(servicesData.get(i).getServiceLat());
                                Double lon = Double.parseDouble(servicesData.get(i).getServiceLon());

                                Log.d(TAG, "run addMarker: " + "Lat" + lat + "  , Longt  " + lon);

                                location = new LatLng(lat, lon);

                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.get(i).getOfficeName())
                        .icon(BitmapDescriptorFactory.defaultMarker(ColorList.MarkerColorList[j])));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    showErrorToast("Server sent bad data");
                }
            }
        }).run();
    }

    private void removeMarkersIfPresent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Marker marker : markersPresentOnMap) {
                    marker.remove();
                }
                markersPresentOnMap.clear();
            }
        }).run();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.e(TAG, "onMarkerClick: ");
        try {
            ServicesData servicesData = (ServicesData) marker.getTag();
            delayBeforeSheetOpen(servicesData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void delayBeforeSheetOpen(final ServicesData servicesData) {
        Log.e(TAG, "delayBeforeSheetOpen: ");

        int ANIMATE_DELAY = 250;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PlaceDetailsBottomSheet placeDetailsBottomSheet = PlaceDetailsBottomSheet.getInstance(servicesData);
                placeDetailsBottomSheet.show(getSupportFragmentManager(), "a");

            }
        }, ANIMATE_DELAY);
    }


    //    cluster testing
    protected GoogleMap getMap() {
        return map;
    }

    protected void startCluster() {
//        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
        getMap().setOnCameraIdleListener(mClusterManager);

        try {
            addMarker();
        } catch (Exception e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }
//    ==========================
}
