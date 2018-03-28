package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ColorList;

import java.util.List;


/**
 * Created by Samir on 11/14/2017.
 */

public class ServicesLegendListAdapter extends RecyclerView.Adapter<ServicesLegendListAdapter.ContactViewHolder> {

    private static final String TAG = "ServicesLegendAdapter";
    private List<ServicesLegendListModel> servicesTypeList;
    Context context;

    public ServicesLegendListAdapter(Context context, List<ServicesLegendListModel> cList) {
        this.servicesTypeList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return servicesTypeList.size();
    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ServicesLegendListModel ci = servicesTypeList.get(i);
        String serviceType = ci.getServiceTypeID();
        switch (serviceType) {
            case "police":
                contactViewHolder.tvTypeID.setText("Police");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_police);
                int color1 = ColorList.policeMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color1, PorterDuff.Mode.SRC_ATOP);
                break;

            case "mowcsw":
                contactViewHolder.tvTypeID.setText("MoWCsW");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_mowcsw);
                int color2 = ColorList.MoWCsWMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
                break;

            case "gov":
                contactViewHolder.tvTypeID.setText("Gov");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_gov);
                int color3 = ColorList.GOVMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color3, PorterDuff.Mode.SRC_ATOP);
                break;

            case "ktm ngo":
                contactViewHolder.tvTypeID.setText("KTM NGO");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ngo);
                int color4 = ColorList.NGOMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color4, PorterDuff.Mode.SRC_ATOP);
                break;

            case "ngo":
                contactViewHolder.tvTypeID.setText("NGO");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ngo);
                int color5 = ColorList.NGOMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color5, PorterDuff.Mode.SRC_ATOP);
                break;

            case "ocmc":
                contactViewHolder.tvTypeID.setText("OCMC");
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ocmc);
                int color6 = ColorList.OCMCMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color6, PorterDuff.Mode.SRC_ATOP);
                break;

            default:
//                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker);
                int color7 = ColorList.DefaultMarkerLegend;
                contactViewHolder.ivMarkerLegend.setColorFilter(color7, PorterDuff.Mode.SRC_ATOP);

        }
    }


    @Override
    public ServicesLegendListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_item_map_ledgend, viewGroup, false);

        return new ServicesLegendListAdapter.ContactViewHolder(itemView);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvTypeID;
        protected ImageView ivMarkerLegend;

        public ContactViewHolder(View v) {
            super(v);
            tvTypeID = (TextView) v.findViewById(R.id.list_item_tv_title);
            ivMarkerLegend = (ImageView) v.findViewById(R.id.iv_services_legend_marker);
        }
    }

}
