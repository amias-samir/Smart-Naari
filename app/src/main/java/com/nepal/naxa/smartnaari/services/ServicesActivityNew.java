package com.nepal.naxa.smartnaari.services;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;


public class ServicesActivityNew extends BaseActivity {
    AppDataManager dataManager;
    GeoJsonLayer districtLayer;
    GoogleMap googleMap;
    private final LatLng centerNepallatLong = new LatLng(27.9713, 84.5956);


    @BindView(R.id.layout_services)
    RelativeLayout layoutServices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_new);
        setupToolbar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        servicesCardVisiblity(true);
        setupGoogleMap();
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Services");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupGoogleMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragMap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                servicesCardVisiblity(false);

                ServicesActivityNew.this.googleMap = googleMap;
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                moveCamera(googleMap,centerNepallatLong,5.6f);

                try {
                    setDistrictGeoJSON(googleMap);
                    setOnLayerClickListener(districtLayer);
                    setupShowAndHideLayer(googleMap,districtLayer);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    showErrorToast("Bad json");
                }

            }
        });
    }

    private void setupShowAndHideLayer(GoogleMap googleMap,final GeoJsonLayer districtLayer) {
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    districtLayer.removeLayerFromMap();
                }
            },TimeUnit.SECONDS.toMillis(3));
//
            }
        });


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if(!districtLayer.isLayerOnMap()){
                    districtLayer.addLayerToMap();
                }

            }
        });
    }

    private void servicesCardVisiblity(boolean show){
        showInfoToast(show+"");
        //layoutServices.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setOnLayerClickListener(GeoJsonLayer districtLayer) {
        districtLayer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {

                String selectedDistrict = feature.getProperty("DISTRICT").toLowerCase().trim();

                double midLat = Double.parseDouble(feature.getProperty("centroid_2"));
                double midlong = Double.parseDouble(feature.getProperty("centroid_1"));

                double minLat = Double.parseDouble(feature.getProperty("y_min"));
                double minLong = Double.parseDouble(feature.getProperty("x_min"));

                double maxLat = Double.parseDouble(feature.getProperty("y_max"));
                double maxLong = Double.parseDouble(feature.getProperty("x_max"));

                final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat,maxLong));
                moveCameraToBounds(googleMap,DISTRICT);
            }
        });
    }

    private void setDistrictGeoJSON(GoogleMap map ) throws IOException,JSONException {

            districtLayer = new GeoJsonLayer(map, R.raw.district_bounds_centroid,
                    getApplicationContext());
            districtLayer.getDefaultPolygonStyle()
                    .setStrokeWidth(4);
            districtLayer.addLayerToMap();

    }

    private void moveCamera(GoogleMap googleMap, LatLng latLng,float zoom) {



        CameraPosition cameraPositon = CameraPosition.builder()
                .target(latLng)
                .zoom(zoom)
                .bearing(googleMap.getCameraPosition().bearing)
                .tilt(googleMap.getCameraPosition().tilt)
                .build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPositon), null);

    }


    private void moveCameraToBounds(GoogleMap googleMap, LatLngBounds bounds){

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,0);
        googleMap.animateCamera(cameraUpdate);

    }
}
