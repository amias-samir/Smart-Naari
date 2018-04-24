package com.nepal.naxa.smartnaari.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.data.network.UserData;
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
    private boolean isActivityFirstTimeLoad = true;


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

    ArrayAdapter distArrayAdpt;

    String selectedDistrict;
    double minLat, minLong, midLat, midlong, maxLat, maxLong;
    LatLng startingLatLong, midLatLong;

    private GoogleMap map;
    private List<Marker> markersPresentOnMap;


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


        initToolbar();
        initDistrictSpinner();


//        if from MaChupBasdina Activity
        if (ConstantData.isFromMaChupBasdina) {
            Intent intent = getIntent();
            selectedDistrict = intent.getStringExtra(ConstantData.KEY_DISTRICT);
        } else {
            getUserCurrentDistrict();
        }


    }

    private void getUserCurrentDistrict() {
        SessionManager sessionManager;
        UserData userData;
        sessionManager = new SessionManager(this);
        userData = sessionManager.getUser();
        String District = userData.getCurrentDistrict().trim().toLowerCase();

        Log.d(TAG, "getUserCurrentDistrict: " + selectedDistrict);
        setSpinnerOnfeatureSelection(District);
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
        distArrayAdpt = new ArrayAdapter<String>(this, R.layout.services_spinner_item_layout, ConstantData.districtListServices);
        distArrayAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictListServices.setAdapter(distArrayAdpt);
    }

    @OnItemSelected(R.id.spinner_district_list_services)
    public void onDistrictListServicesClicked() {

        if (spinnerDistrictListServices.getSelectedItem().toString().equals("All District")) {

            if (isActivityFirstTimeLoad) {
                return;
            } else {
                removeMarkersIfPresent();
                startCluster();
                setNepalMapCamera();
            }


        } else {
            selectedDistrict = spinnerDistrictListServices.getSelectedItem().toString().trim().toLowerCase();
            removeMarkersIfPresent();
            new FilterFromGeoJson().execute();
            try {
                addMarker(selectedDistrict);
            } catch (Exception e) {
                Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
            }

        }


        isActivityFirstTimeLoad = false;

    }

    private void setNepalMapCamera() {
        try {
            final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(26.3484, 80.0509), new LatLng(30.4458, 88.2047));

            map.setLatLngBoundsForCameraTarget(DISTRICT);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(DISTRICT, 2);
//            map.moveCamera(cu);
            map.animateCamera(cu);


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
            final LatLngBounds DISTRICT = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));

//            map.setLatLngBoundsForCameraTarget(DISTRICT);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(DISTRICT, 0);
//            map.moveCamera(cu);
            map.animateCamera(cu);

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);

            setSpinnerOnfeatureSelection(selectedDistrict);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setDistrictMapCamera: ERROR");
        }
    }

    public void setSpinnerOnfeatureSelection(String district) {

        //set spinner
        final int districtPos = distArrayAdpt.getPosition(district.substring(0, 1).toUpperCase() + district.substring(1));
        spinnerDistrictListServices.setSelection(districtPos);
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
        ca.notifyDataSetChanged();
        recyclerMapLegend.setAdapter(ca);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerMapLegend.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xxsmall);
        recyclerMapLegend.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, true, 0));

    }


    public boolean isGPSOn() {
        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        initMapLegend();
        LatLng centerNepallatLong = new LatLng(27.9713, 84.5956);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerNepallatLong, 5.6f));

        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (!isGPSOn()) {
                        showInfoToast("Turn on GPS");
                    }
                    return false;
                }
            });
            googleMap.setMyLocationEnabled(true);
        } else {
            requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub
                LatLng location = new LatLng(arg0.getLatitude(), arg0.getLongitude());
            }
        });


        getServicesData();
        removeMarkersIfPresent();

//        cluster testing
        if (map != null) {
            return;
        }
        map = googleMap;
        loadDistrictGeoJSON();

        if (ConstantData.isFromMaChupBasdina) {

            removeMarkersIfPresent();
//            loadDistrictGeoJSON();
            new FilterFromGeoJson().execute();
            try {
                addMarker(selectedDistrict);
            } catch (Exception e) {
                Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
            }
        } else {

//            startCluster();

        }
//        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
//        ==========================
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem listViewServices = menu.findItem(R.id.item_list_menu);
        listViewServices.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_call:
                Intent intent = new Intent(ServicesActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                break;
            case R.id.item_list_menu:
                if (!isGPSOn()) {
                    showInfoToast("Turn on GPS");
                } else {
                    Intent intentListMenu = new Intent(ServicesActivity.this, ServicesListActivity.class);
                    startActivity(intentListMenu);
                    finish();
                }
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
                        switch (serviceOfficeType.trim()) {
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
                                addDefaultmarker(location, servicesData.get(i));
                        }
                    }
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            showErrorToast("Server sent bad data");
                        }
                    });

                }
            }
        }).start();
    }

    private void removeMarkersIfPresent() {

        for (Marker marker : markersPresentOnMap) {
            marker.remove();
        }
        markersPresentOnMap.clear();


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
//        mClusterManager = new ClusterManager<ServicesData>(this, getMap());
//        getMap().setOnCameraIdleListener(mClusterManager);

        try {
            addMarker();
        } catch (Exception e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }
//    ==========================

    private void loadDistrictGeoJSON() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    districtLayer = new GeoJsonLayer(map, R.raw.district_bounds_centroid,
                            getApplicationContext());
                    districtLayer.getDefaultPolygonStyle().setStrokeWidth(2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            districtLayer.addLayerToMap();
                            setOnDistictTapListener(districtLayer);
                        }
                    });

                } catch (IOException | JSONException e) {

                }
            }
        }).start();

    }


    private void setOnDistictTapListener(GeoJsonLayer geoJsonLayer) {
        geoJsonLayer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {

                selectedDistrict = feature.getProperty("DISTRICT").toLowerCase().trim();

                midLat = Double.parseDouble(feature.getProperty("centroid_2"));
                midlong = Double.parseDouble(feature.getProperty("centroid_1"));

                minLat = Double.parseDouble(feature.getProperty("y_min"));
                minLong = Double.parseDouble(feature.getProperty("x_min"));

                maxLat = Double.parseDouble(feature.getProperty("y_max"));
                maxLong = Double.parseDouble(feature.getProperty("x_max"));

                removeMarkersIfPresent();
                addMarker(selectedDistrict);
                setDistrictMapCamera();
                ConstantData.isFromMaChupBasdina = false;
                isActivityFirstTimeLoad = false;
//                Log.e(TAG, "onFeatureClick: " + feature.getProperty("DISTRICT").toLowerCase().trim());
            }
        });
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
                            minLat = Double.parseDouble(properties.getString("y_min"));
                            minLong = Double.parseDouble(properties.getString("x_min"));
                            maxLat = Double.parseDouble(properties.getString("y_max"));
                            maxLong = Double.parseDouble(properties.getString("x_max"));

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
                            switch (serviceOfficeType.trim()) {
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
                                    addDefaultmarker(location, servicesData.get(i));
                            }

                        }
                    }
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            showErrorToast("Server sent bad data");
                        }
                    });

                }
            }
        }).start();
    }


    public void policeAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.policeMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void MoWCsWAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.MoWCsWMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void KtmNGOAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.NGOMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void NGOAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.NGOMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void GovAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.GOVMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void OCMCAddMarker(final LatLng location, final ServicesData servicesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                amarker = map.addMarker(new MarkerOptions().position(location)
                        .title(servicesData.getOfficeName())
                        .icon(ColorList.getMarkerIcon(ColorList.OCMCMarker)));
                amarker.setTag(servicesData);
                markersPresentOnMap.add(amarker);
            }
        });

    }

    public void addDefaultmarker(final LatLng location, final ServicesData servicesData) {

        for (int j = 0; j < appDataManager.getAllUniqueServicesType().size(); j++) {
//                            Log.e(TAG, "runAddMarkerSAMIR: " + "services type loop count -->"+ j);
            if (appDataManager.getAllUniqueServicesType().get(j).equals(servicesData.getOfficeType())) {

                final int finalJ = j;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        amarker = map.addMarker(new MarkerOptions().position(location)
                                .title(servicesData.getOfficeName())
                                .icon(BitmapDescriptorFactory.defaultMarker()));
                        amarker.setTag(servicesData);
                        markersPresentOnMap.add(amarker);
                    }
                });

            }

        }
    }
}
