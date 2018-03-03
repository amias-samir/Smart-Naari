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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
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
    double initLat, initLong, midLat, midlong;
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

    private void setDistrictMapCamera() {

        try {
            final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(midLat, midlong), new LatLng(initLat, initLong));
//        final LatLngBounds DISTRICT = new LatLngBounds(midLatLong,startingLatLong);

            map.setLatLngBoundsForCameraTarget(DISTRICT);
            map.setMinZoomPreference(9.8f);

            CameraPosition cameraPositon = CameraPosition.builder()
                    .target(centerPointOfCordinates(midLat, midlong, initLat, initLong))
                    .zoom(10f)
                    .bearing(0.0f)
                    .tilt(20f)
                    .build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPositon), null);

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setDistrictMapCamera: ERROR");
        }
    }

    private LatLng centerPointOfCordinates(double lat1, double lon1, double lat2, double lon2) {

        LatLng centerLatLong = new LatLng(0, 0);

//        double dLon = Math.toRadians(lon2 - lon1);
//
//        //convert to radians
//        lat1 = Math.toRadians(lat1);
//        lat2 = Math.toRadians(lat2);
//        lon1 = Math.toRadians(lon1);
//
//        double Bx = Math.cos(lat2) * Math.cos(dLon);
//        double By = Math.cos(lat2) * Math.sin(dLon);
//        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
//        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        double midLAT = (lat1 + lat2) / 2;
        double midLON = (lon1 + lon2) / 2;

        centerLatLong = new LatLng(midLAT, midLON);
        Log.e(TAG, "centerPointOfCordinates: " + centerLatLong);


        return centerLatLong;
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

//                    Log.e(TAG, "runAddMarkerSAMIR: " + "services table row count -->"+ servicesData.size());
                    Double lat, lon;
                    String serviceOfficeType;
                    for (int i = 0; i < servicesData.size(); i++) {
//                        Log.e(TAG, "runAddMarkerSAMIR: " + "services whole loop count -->"+ i);
                        serviceOfficeType = servicesData.get(i).getOfficeType();
                        if (TextUtils.isEmpty(servicesData.get(i).getServiceLat()) || TextUtils.isEmpty(servicesData.get(i).getServiceLon())) {
                            lat = 0.0;
                            lon = 0.0;
                        } else {
                            lat = abs(Double.parseDouble(servicesData.get(i).getServiceLat()));
                            lon = abs(Double.parseDouble(servicesData.get(i).getServiceLon()));
                        }

                        location = new LatLng(lat, lon);

                        if (serviceOfficeType.trim().equals("police")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerPolice));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else if (serviceOfficeType.trim().equals("MoWCsW")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerMoWCsW));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else if (serviceOfficeType.trim().equals("GOV")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerGOV));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else if (serviceOfficeType.trim().equals("KTM NGO")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerKTMNGO));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else if (serviceOfficeType.trim().equals("NGO")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerNGO));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else if (serviceOfficeType.trim().equals("OCMC")) {
                            amarker = map.addMarker(new MarkerOptions().position(location)
                                    .title(servicesData.get(i).getOfficeName())
                                    .icon(markerOCMC));
                            amarker.setTag(servicesData.get(i));
                            markersPresentOnMap.add(amarker);

                        } else {
                            for (int j = 0; j < appDataManager.getAllUniqueServicesType().size(); j++) {
//                            Log.e(TAG, "runAddMarkerSAMIR: " + "services type loop count -->"+ j);
                                if (appDataManager.getAllUniqueServicesType().get(j).equals(servicesData.get(i).getOfficeType())) {

                                    amarker = map.addMarker(new MarkerOptions().position(location)
                                            .title(servicesData.get(i).getOfficeName())
                                            .icon(BitmapDescriptorFactory.defaultMarker(ColorList.MarkerColorList[j])));
                                    amarker.setTag(servicesData.get(i));
                                    markersPresentOnMap.add(amarker);
                                }

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
                districtLayer = new GeoJsonLayer(map, R.raw.district,
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
                InputStream inputStream = getResources().openRawResource(R.raw.district);
                Log.e(TAG, "doInBackground SAMIR: " + inputStream.toString());
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();

                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }

                inputStream.close();
                Log.e(TAG, "doInBackground:SAMIR : " + sb.toString());

                // Parse JSON
                JSONObject json = new JSONObject(sb.toString());
                JSONArray featuresJarray = json.getJSONArray("features");
                Log.e(TAG, "doInBackground: feature " + featuresJarray);

                int loopCounterMonitor = featuresJarray.length();

                for (int i = 0; i < loopCounterMonitor; i++) {
                    JSONObject jobj = featuresJarray.getJSONObject(i);
                    JSONObject properties = jobj.getJSONObject("properties");
                    if (properties != null) {

                        String district = properties.getString("DISTRICT").toLowerCase();
                        // Our GeoJSON only has one feature: a line string
                        if (district.equalsIgnoreCase(selectedDistrict)) {
                            Log.e(TAG, "doInBackground : district " + district);
                            loopCounterMonitor = featuresJarray.length();
                            // Get the Coordinates
                            JSONObject geometry = jobj.getJSONObject("geometry");
                            Log.e(TAG, "doInBackground: geometry" + geometry.toString());

                            JSONArray coord = geometry.getJSONArray("coordinates");
                            JSONArray coords = coord.getJSONArray(0);
                            int coordsMax = coords.length();
                            Log.e(TAG, "doInBackground: coordLength" + coordsMax);
                            int midIndex;
                            if (coordsMax % 2 == 0) {
                                midIndex = coordsMax / 2;
                            } else {
                                midIndex = (coordsMax + 1) / 2;
                            }
                            Log.e(TAG, "doInBackground: midindex" + midIndex);

                            startingLatLong = new LatLng(coords.getJSONArray(0).getDouble(0), coords.getJSONArray(0).getDouble(1));
                            Log.e(TAG, "doInBackground: startLatLong" + startingLatLong);
                            midLatLong = new LatLng(coords.getJSONArray(midIndex).getDouble(0), coords.getJSONArray(midIndex).getDouble(1));
                            initLat = coords.getJSONArray(0).getDouble(1);
                            initLong = coords.getJSONArray(0).getDouble(0);
                            midLat = coords.getJSONArray(midIndex).getDouble(1);
                            midlong = coords.getJSONArray(midIndex).getDouble(0);

                            Log.e(TAG, "doInBackground: midLatLong" + midLatLong);
                            Log.e(TAG, "doInBackground: initLat" + initLat);
                            Log.e(TAG, "doInBackground: initLong" + initLong);
                            Log.e(TAG, "doInBackground: midLat" + midLat);
                            Log.e(TAG, "doInBackground: midLong" + midlong);

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

//                    Log.e(TAG, "runAddMarkerSAMIR: " + "services table row count -->"+ servicesData.size());
                    Double lat, lon;
                    String serviceOfficeType;

                    for (int i = 0; i < servicesData.size(); i++) {

                        if (servicesData.get(i).getDistrict().toLowerCase().equals(districtFilter)) {
//                        Log.e(TAG, "runAddMarkerSAMIR: " + "services whole loop count -->"+ i);
                            serviceOfficeType = servicesData.get(i).getOfficeType();
                            if (TextUtils.isEmpty(servicesData.get(i).getServiceLat()) || TextUtils.isEmpty(servicesData.get(i).getServiceLon())) {
                                lat = 0.0;
                                lon = 0.0;
                            } else {
                                lat = abs(Double.parseDouble(servicesData.get(i).getServiceLat()));
                                lon = abs(Double.parseDouble(servicesData.get(i).getServiceLon()));
                            }

                            location = new LatLng(lat, lon);

                            if (serviceOfficeType.trim().equals("police")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerPolice));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else if (serviceOfficeType.trim().equals("MoWCsW")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerMoWCsW));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else if (serviceOfficeType.trim().equals("GOV")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerGOV));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else if (serviceOfficeType.trim().equals("KTM NGO")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerKTMNGO));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else if (serviceOfficeType.trim().equals("NGO")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerNGO));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else if (serviceOfficeType.trim().equals("OCMC")) {
                                amarker = map.addMarker(new MarkerOptions().position(location)
                                        .title(servicesData.get(i).getOfficeName())
                                        .icon(markerOCMC));
                                amarker.setTag(servicesData.get(i));
                                markersPresentOnMap.add(amarker);

                            } else {
                                for (int j = 0; j < appDataManager.getAllUniqueServicesType().size(); j++) {
//                            Log.e(TAG, "runAddMarkerSAMIR: " + "services type loop count -->"+ j);
                                    if (appDataManager.getAllUniqueServicesType().get(j).equals(servicesData.get(i).getOfficeType())) {

                                        amarker = map.addMarker(new MarkerOptions().position(location)
                                                .title(servicesData.get(i).getOfficeName())
                                                .icon(BitmapDescriptorFactory.defaultMarker(ColorList.MarkerColorList[j])));
                                        amarker.setTag(servicesData.get(i));
                                        markersPresentOnMap.add(amarker);
                                    }

                                }
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
}
