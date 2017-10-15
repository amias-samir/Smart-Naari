package com.nepal.naxa.smartnaari.services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServicesActivity extends AppCompatActivity implements OnMapReadyCallback {


    @BindView(R.id.act_services_recycler_map_legend)
    RecyclerView recyclerMapLegend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

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
        LegendRecyclerAdapter adapter = new LegendRecyclerAdapter(ViewModel.getServicesList());
        recyclerMapLegend.setAdapter(adapter);
        recyclerMapLegend.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(27.7172, 85.3240);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Marker "));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
