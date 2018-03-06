package com.nepal.naxa.smartnaari.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ColorList;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static java.lang.StrictMath.abs;


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
    @BindView(R.id.spinner_district_list_services)
    Spinner spinnerDistrictListServices;
    String selectedDistrict;
    double minLat, minLong, midLat, midlong, maxLat, maxLong;
    LatLng startingLatLong, midLatLong;

    private GoogleMap map;
    private List<Marker> markersPresentOnMap;

    BitmapDescriptor markerPolice, markerOCMC, markerKTMNGO, markerNGO, markerGOV, markerMoWCsW;
    Bitmap markerPolice1;

    //cluster testing
    private ClusterManager<ServicesData> mClusterManager;
    GeoJsonLayer districtLayer;
//    ==============

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

//        markerPolice1 = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_mowcsw);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragMap);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);

        appDataManager = new AppDataManager(getApplicationContext());
        markersPresentOnMap = new ArrayList<>();

        markerPolice = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_police);
        markerOCMC = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_ocmc);
        markerKTMNGO = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_ktm_ngo);
        markerNGO = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_ngo);
        markerGOV = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_government);
        markerMoWCsW = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_mowcsw);

        initToolbar();
        initDistrictSpinner();
        initMapLegend();

//        if from MaChupBasdina Activity
        if (ConstantData.isFromMaChupBasdina) {
            Intent intent = getIntent();
            selectedDistrict = intent.getStringExtra(ConstantData.KEY_DISTRICT);
        }
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


    private void initDistrictSpinner() {
        ArrayAdapter<String> distArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.districtListServices);
        distArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictListServices.setAdapter(distArray);
    }

    @OnItemSelected(R.id.spinner_district_list_services)
    public void onDistrictListServicesClicked() {

        if (spinnerDistrictListServices.getSelectedItem().toString().equals("All District")) {

            removeMarkersIfPresent();
            setDistrictGeoJSON();
            startCluster();
            setNepalMapCamera();


        } else {
            selectedDistrict = spinnerDistrictListServices.getSelectedItem().toString().trim().toLowerCase();
            removeMarkersIfPresent();
            setDistrictGeoJSON();
            new FilterFromGeoJson().execute();
            try {
                addMarker(selectedDistrict);
            } catch (Exception e) {
                Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
            }

        }


    }

    private void setNepalMapCamera(){
        try {
            final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(26.3484, 80.0509), new LatLng(30.4458,88.2047));

            map.setLatLngBoundsForCameraTarget(DISTRICT);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(DISTRICT, 0);
            map.moveCamera(cu);

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setNepalMapCamera: ERROR");
        }
    }


    private void setDistrictMapCamera() {

        try {
            final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat,maxLong));

            map.setLatLngBoundsForCameraTarget(DISTRICT);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(DISTRICT, 0);
            map.moveCamera(cu);

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setDistrictMapCamera: ERROR");
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

//        recyclerMapLegend.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.HORIZONTAL, false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerMapLegend.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xxsmall);
        recyclerMapLegend.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, true, 0));

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLng centerNepallatLong = new LatLng(27.9713, 84.5956);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerNepallatLong, 5.6f));

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
        setDistrictGeoJSON();

        if (ConstantData.isFromMaChupBasdina) {
            removeMarkersIfPresent();
            setDistrictGeoJSON();
            new FilterFromGeoJson().execute();
            try {
                addMarker(selectedDistrict);
            } catch (Exception e) {
                Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
            }
        } else {
            startCluster();

        }
//        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
//        ==========================
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_call:
                Intent intent = new Intent(ServicesActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
//                    mClusterManager.addItems(servicesData);
                    Double lat, lon;
                    String serviceOfficeType;
                    for (int i = 0; i < servicesData.size(); i++) {
                        serviceOfficeType = servicesData.get(i).getOfficeType();
                        if (TextUtils.isEmpty(servicesData.get(i).getServiceLat()) || TextUtils.isEmpty(servicesData.get(i).getServiceLon())) {
                            lat = 0.0;
                            lon = 0.0;
                        } else {
                            lat = abs(Double.parseDouble(servicesData.get(i).getServiceLat()));
                            lon = abs(Double.parseDouble(servicesData.get(i).getServiceLon()));
                        }

                        location = new LatLng(lat, lon);

                        switch (serviceOfficeType.trim()){
                            case "police":
                                policeAddMarker(location, servicesData.get(i));
                                break;
                            case "MoWCsW":
                                MoWCsWAddMarker(location, servicesData.get(i));
                                break;
                            case "GOV":
                                GovAddMarker(location, servicesData.get(i));
                                break;
                            case "KTM NGO":
                                KtmNGOAddMarker(location, servicesData.get(i));
                                break;
                            case "NGO":
                                NGOAddMarker(location, servicesData.get(i));
                                break;
                            case "OCMC":
                                OCMCAddMarker(location, servicesData.get(i));
                                break;

                            default:
                                addDefaultmarker(location,servicesData.get(i));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                Log.e(TAG, "runDelayedBeforeSheet: SAMIR" + servicesData.getOfficeName());
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

//        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
//        getMap().setOnCameraIdleListener(mClusterManager);

        try {
            addMarker();
        } catch (Exception e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }
//    ==========================

    private void setDistrictGeoJSON() {
        //set district layer
        try {
            try {
                districtLayer = new GeoJsonLayer(map, R.raw.district_bounds_centroid,
                        getApplicationContext());
            } catch (JSONException e) {
                Log.d(TAG, "Error ! while parsing GeoJSON raw file /n" + e);

                e.printStackTrace();
            }
            districtLayer.addLayerToMap();
        } catch (IOException e) {
            Log.d(TAG, "Error ! While Applying GeoJSON to map /n" + e);
            e.printStackTrace();

        }

    }

    private class FilterFromGeoJson extends AsyncTask<Void, Void, List<LatLng>> {
        @Override
        protected List<LatLng> doInBackground(Void... voids) {

            ArrayList<LatLng> points = new ArrayList<>();

            try {
                // Load GeoJSON file
                InputStream inputStream = getResources().openRawResource(R.raw.district_bounds_centroid);
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();

                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }

                inputStream.close();
                // Parse JSON
                JSONObject json = new JSONObject(sb.toString());
                JSONArray featuresJarray = json.getJSONArray("features");
                int loopCounterMonitor = featuresJarray.length();

                for (int i = 0; i < loopCounterMonitor; i++) {
                    JSONObject jobj = featuresJarray.getJSONObject(i);
                    JSONObject properties = jobj.getJSONObject("properties");
                    if (properties != null) {

                        String district = properties.getString("DISTRICT").toLowerCase();
                        if (district.equalsIgnoreCase(selectedDistrict)) {
//                            Log.e(TAG, "doInBackground : district " + district);
                            loopCounterMonitor = featuresJarray.length();

                            midLat = Double.parseDouble(properties.getString("centroid_2"));
                            midlong = Double.parseDouble(properties.getString("centroid_1"));

                            JSONArray boundryJarray = jobj.getJSONArray("bbox");
                            Log.d(TAG, "doInBackground: boundry"+boundryJarray.toString());
                            minLat = boundryJarray.getDouble(1);
                            minLong = boundryJarray.getDouble(0);
                            maxLat = boundryJarray.getDouble(3);
                            maxLong = boundryJarray.getDouble(2);
                        }
                    }
                }


            } catch (Exception exception) {
                Log.e(TAG, "Exception Loading GeoJSON: " + exception.toString());
            }

            return points;
        }

        @Override
        protected void onPostExecute(List<LatLng> points) {
            super.onPostExecute(points);
//            if (points.size() > 0) {
//
//            }
            setDistrictMapCamera();
            ConstantData.isFromMaChupBasdina = false;
        }
    }

    public void addMarker(final String districtFilter) {

        getMap().setOnMarkerClickListener(this);

        this.map = getMap();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LatLng location;
//                    mClusterManager.addItems(servicesData);
                    Double lat, lon;
                    String serviceOfficeType;

                    for (int i = 0; i < servicesData.size(); i++) {

                        if (servicesData.get(i).getDistrict().toLowerCase().equals(districtFilter)) {
                            serviceOfficeType = servicesData.get(i).getOfficeType().trim();
                            if (TextUtils.isEmpty(servicesData.get(i).getServiceLat()) || TextUtils.isEmpty(servicesData.get(i).getServiceLon())) {
                                lat = 0.0;
                                lon = 0.0;
                            } else {
                                lat = abs(Double.parseDouble(servicesData.get(i).getServiceLat()));
                                lon = abs(Double.parseDouble(servicesData.get(i).getServiceLon()));
                            }

                            location = new LatLng(lat, lon);

                            switch (serviceOfficeType.trim()){
                                case "police":
                                    policeAddMarker(location, servicesData.get(i));
                                    break;
                                case "MoWCsW":
                                    MoWCsWAddMarker(location, servicesData.get(i));
                                    break;
                                case "GOV":
                                    GovAddMarker(location, servicesData.get(i));
                                    break;
                                case "KTM NGO":
                                    KtmNGOAddMarker(location, servicesData.get(i));
                                    break;
                                case "NGO":
                                    NGOAddMarker(location, servicesData.get(i));
                                    break;
                                case "OCMC":
                                    OCMCAddMarker(location, servicesData.get(i));
                                    break;
                                    default:

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorToast("Server sent bad data");
                }
            }
        }).run();
    }


    public void policeAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerPolice));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void MoWCsWAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerMoWCsW));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void KtmNGOAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerKTMNGO));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void NGOAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerNGO));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void GovAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerGOV));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void OCMCAddMarker (LatLng location, ServicesData servicesData){
        amarker = map.addMarker(new MarkerOptions().position(location)
                .title(servicesData.getOfficeName())
                .icon(markerOCMC));
        amarker.setTag(servicesData);
        markersPresentOnMap.add(amarker);
    }

    public void addDefaultmarker(LatLng location, ServicesData servicesData){
        for (int j = 0; j < appDataManager.getAllUniqueServicesType().size(); j++) {
//                            Log.e(TAG, "runAddMarkerSAMIR: " + "services type loop count -->"+ j);
            if (appDataManager.getAllUniqueServicesType().get(j).equals(servicesData.getOfficeType())) {

                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(BitmapDescriptorFactory.defaultMarker(ColorList.MarkerColorList[j])));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }

        }
    }
}
