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

        contactViewHolder.tvTypeID.setText(ci.serviceTypeID);


        if(ci.serviceTypeID.trim().equals("police")){
          contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_police);
        }
        else if(ci.serviceTypeID.trim().equals("mowcsw")){
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_mowcsw);

        }else if(ci.serviceTypeID.trim().equals("gov")){
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_government);

        }else if(ci.serviceTypeID.trim().equals("ktm ngo")){
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ktm_ngo);

        }else if(ci.serviceTypeID.trim().equals("ngo")){
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ngo);

        }else if(ci.serviceTypeID.trim().equals("ocmc")){
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker_ocmc);

        }else {
//        int color = ContextCompat.getColor(context, viewModel.getImage());
            contactViewHolder.ivMarkerLegend.setBackgroundResource(R.drawable.ic_marker);
            int color = ColorList.COLORFUL_COLORS[i];
//        contactViewHolder.ivMarkerLegend.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            contactViewHolder.ivMarkerLegend.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
//        contactViewHolder.tvTypeID.getCompoundDrawables()[0].getCurrent().setColorFilter(colorFilter);
            Log.d(TAG, "onBindViewHolder: " + i + "  ::: " + ColorList.COLORFUL_COLORS[i] + "  ::: " + color);
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
        protected TextView tvTypeID ;
        protected ImageView ivMarkerLegend ;

        public ContactViewHolder(View v) {
            super(v);
            tvTypeID = (TextView) v.findViewById(R.id.list_item_tv_title);
            ivMarkerLegend = (ImageView) v.findViewById(R.id.iv_services_legend_marker);
        }
    }

}
