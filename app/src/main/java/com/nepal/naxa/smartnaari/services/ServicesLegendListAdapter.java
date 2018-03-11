package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.graphics.PorterDuff;
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
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_police);
                break;

            case "mowcsw":
                contactViewHolder.tvTypeID.setText("MoWCsW");
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_mowcsw);
                break;

            case "gov":
                contactViewHolder.tvTypeID.setText("Gov");
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_gov);
                break;

            case "ktm ngo":
                contactViewHolder.tvTypeID.setText("KTM NGO");
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ngo);
                break;

            case "ngo":
                contactViewHolder.tvTypeID.setText("NGO");
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ngo);
                break;

            case "ocmc":
                contactViewHolder.tvTypeID.setText("OCMC");
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ocmc);
                break;

            default:
                contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker);
                int color = ColorList.COLORFUL_COLORS[i];
                contactViewHolder.ivMarkerLegend.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        }
    }

//    private void SetTextViewDrawableColor(TextView textView, int color){
//
//        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
//        holder.markerTitle.getCompoundDrawables()[0].setColorFilter(colorFilter);
//        Log.e("qwert",color+ " ");
//    }

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
