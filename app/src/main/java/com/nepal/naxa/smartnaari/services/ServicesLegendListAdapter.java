package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import java.util.List;


/**
 * Created by Samir on 11/14/2017.
 */

public class ServicesLegendListAdapter extends RecyclerView.Adapter<ServicesLegendListAdapter.ContactViewHolder> {

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

    }

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
