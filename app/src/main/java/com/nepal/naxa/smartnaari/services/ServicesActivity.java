package com.nepal.naxa.smartnaari.services;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServicesActivity extends BaseActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{

    private static final String TAG = "ServicesActivity";


    @BindView(R.id.act_services_recycler_map_legend)
    RecyclerView recyclerMapLegend;
    AppDataManager appDataManager ;
    
    List<ServicesData> servicesData ;
    public static List<ServicesLegendListModel> resultCur = new ArrayList<>();
    public static List<ServicesLegendListModel> filteredList = new ArrayList<>();
    ServicesLegendListAdapter ca;

    Marker amarker;

    private GoogleMap map;
    private List<Marker> markersPresentOnMap;

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

            for(int i = 0 ; i< appDataManager.getAllUniqueServicesType().size(); i++){

                ServicesLegendListModel newData = new ServicesLegendListModel();
                newData.serviceTypeID = appDataManager.getAllUniqueServicesType().get(i);

                resultCur.add(newData);

            }



        }catch (Exception e){
            e.printStackTrace();
        }
        ca = new ServicesLegendListAdapter(this, resultCur);
        recyclerMapLegend.setAdapter(ca);
        appDataManager.getAllUniqueServicesType();

        Log.d(TAG, "initMapLegend: "+appDataManager.getAllUniqueServicesType().get(1));




//        LegendRecyclerAdapter adapter = new LegendRecyclerAdapter(ViewModel.getServicesList());
//        recyclerMapLegend.setAdapter(adapter);
        recyclerMapLegend.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location;
//        this.map = googleMap ;

        location = new LatLng(27.7172, 85.3240);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Marker Default "));


        getServicesData();
        removeMarkersIfPresent();
        addMarker(googleMap);
//        List<ServicesData> servicesData = appDataManager.getAllServicesdata();
//        placeMarkersOnMap(servicesData);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15.0f));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void getServicesData(){
       servicesData  = appDataManager.getAllServicesdata();
        Log.d(TAG, "getServicesData: "+servicesData.size());


    }


    public void addMarker (final GoogleMap googleMap){

        googleMap.setOnMarkerClickListener(this);

        this.map = googleMap ;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    LatLng location;


        for (int i = 0 ; i< servicesData.size() ; i++){

            Double lat = Double.parseDouble(servicesData.get(i).getServiceLat());
            Double lon = Double.parseDouble(servicesData.get(i).getServiceLon());

            Log.d(TAG, "run addMarker: " + "Lat"+lat + "  , Longt  "+lon);

            location = new LatLng(lat, lon);

                    amarker = map.addMarker(new MarkerOptions().position(location).title(servicesData.get(i).getServiceName()));
            amarker.setTag(servicesData.get(i));
            markersPresentOnMap.add(amarker);

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
        } catch (Exception e){
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
}
