package com.nepal.naxa.smartnaari.services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.ServicesData;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceDetailsBottomSheet extends BottomSheetDialogFragment {
    ServicesData servicesData;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.backdrop)
    ImageView backdrop;


    public static PlaceDetailsBottomSheet getInstance(ServicesData servicesData) {

        PlaceDetailsBottomSheet placeDetailsBottomSheet = new PlaceDetailsBottomSheet();
        placeDetailsBottomSheet.setObject(servicesData);
        return placeDetailsBottomSheet;
    }

    private void setObject(ServicesData servicesData) {
        this.servicesData = servicesData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);
        ButterKnife.bind(this, rootView);

        title.setText(servicesData.getServiceName());
        address.setText(servicesData.getServiceDistrictName());
        desc.setText(servicesData.getServicePhoneNumber());
//        Glide.with(this).load(servicesData.getPhotoPath()).into(backdrop);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
