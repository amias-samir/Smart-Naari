package com.nepal.naxa.smartnaari.utils;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by Samir on 11/20/2016.
 */

public class ColorList {

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(255,0,255),
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),  Color.rgb(207, 248, 246),
            Color.rgb(148, 212, 212), Color.rgb(136, 180, 187), Color.rgb(118, 174, 175),
            Color.rgb(140, 220, 200), Color.rgb(126, 210, 117), Color.rgb(100, 134, 75)
    };



//    Marker Color List
    public static final float HUE_RED = 0.0F;
    public static final float HUE_ORANGE = 30.0F;
    public static final float HUE_YELLOW = 60.0F;
    public static final float HUE_GREEN = 120.0F;
    public static final float HUE_CYAN = 180.0F;
    public static final float HUE_AZURE = 210.0F;
    public static final float HUE_BLUE = 240.0F;
    public static final float HUE_VIOLET = 270.0F;
    public static final float HUE_MAGENTA = 300.0F;
    public static final float HUE_ROSE = 330.0F;

    public static final float [] MarkerColorListFloat = {
            HUE_RED, HUE_ORANGE, HUE_YELLOW, HUE_GREEN, HUE_CYAN, HUE_AZURE, HUE_BLUE, HUE_VIOLET, HUE_MAGENTA, HUE_ROSE
    };



    public static final  String policeMarker = "#3F51B5";
    public static final  String MoWCsWMarker = "#FF9800";
    public static final  String NGOMarker = "#8D6CA5";
    public static final  String GOVMarker = "#0A5C03";
    public static final  String OCMCMarker = "#0EB115";
    public static final  String DefaultMarker = "#FF00FF";

    public static final int policeMarkerLegend = Color.rgb(63,81,181);
    public static final int MoWCsWMarkerLegend = Color.rgb(255,152,0);
    public static final int NGOMarkerLegend = Color.rgb(141,108,165);
    public static final int GOVMarkerLegend = Color.rgb(10,92,3);
    public static final int OCMCMarkerLegend = Color.rgb(14,177,21);
    public static final int DefaultMarkerLegend = Color.rgb(255,0,255);

public static final int [] markerLegendColorList = {policeMarkerLegend, MoWCsWMarkerLegend, NGOMarkerLegend, GOVMarkerLegend,
        OCMCMarkerLegend, DefaultMarkerLegend};

    // method definition
//    dynamic HUE Generator
    public static BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

}
