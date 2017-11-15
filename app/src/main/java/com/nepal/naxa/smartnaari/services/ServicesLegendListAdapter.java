package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ColorList;

import java.util.List;

import static java.lang.System.in;


/**
 * Created by Samir on 11/14/2017.
 */

public class ServicesLegendListAdapter extends RecyclerView.Adapter<ServicesLegendListAdapter.ContactViewHolder> {

    private static final String TAG = "ServicesLegendListAdapter";
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

//        int color = ContextCompat.getColor(context, viewModel.getImage());
        int color = ColorList.COLORFUL_COLORS[i];
        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        contactViewHolder.tvTypeID.getCompoundDrawables()[0].getCurrent().setColorFilter(colorFilter);

        Log.d(TAG, "onBindViewHolder: "+i + "  ::: "+ ColorList.COLORFUL_COLORS[i] +"  ::: "+ color );


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

        public ContactViewHolder(View v) {
            super(v);
            tvTypeID = (TextView) v.findViewById(R.id.list_item_tv_title);
        }
    }

}
