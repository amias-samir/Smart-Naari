package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.ServicesData;

import java.util.List;

/**
 * Created by user on 3/27/2018.
 */

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.ContactViewHolder> {

    private static final String TAG = "ServicesLegendAdapter";
    private List<ServicesData> servicesList;
    Context context;

    public ServicesListAdapter(Context context, List<ServicesData> cList) {
        this.servicesList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }




    @Override
    public ServicesListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.services_recycler_list_item_layout, viewGroup, false);

        return new ServicesListAdapter.ContactViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ServicesData ci = servicesList.get(i);
        contactViewHolder.tvServicesName.setText(ci.getOfficeName().trim());
        contactViewHolder.tvServicesAddress.setText(ci.getDistrict().trim());
        contactViewHolder.tvServicesDistance.setText("102.3 Km.");
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvServicesName, tvServicesAddress, tvServicesDistance;

        public ContactViewHolder(View v) {
            super(v);
            tvServicesName = (TextView) v.findViewById(R.id.tv_services_name);
            tvServicesAddress = (TextView) v.findViewById(R.id.tv_services_address);
            tvServicesDistance = (TextView) v.findViewById(R.id.tv_services_distance);
        }
    }

}
