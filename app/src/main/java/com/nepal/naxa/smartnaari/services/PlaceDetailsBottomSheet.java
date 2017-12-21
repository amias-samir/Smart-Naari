package com.nepal.naxa.smartnaari.services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.ServicesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PlaceDetailsBottomSheet extends BottomSheetDialogFragment {
    ServicesData servicesData;
    @BindView(R.id.office_name)
    TextView tvOfficeName;
    @BindView(R.id.office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.office_type)
    TextView tvOfficeType;
    @BindView(R.id.office_contact_duty_person)
    TextView tvOfficeContactDutyPerson;
    @BindView(R.id.office_landline)
    TextView tvOfficeLandline;
    Unbinder unbinder;


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


        try {


            tvOfficeName.setText("Office Name : "+servicesData.getOfficeName());
            tvOfficeType.setText("Office Type : "+servicesData.getOfficeType());
            tvOfficeAddress.setText("Office Address : "+servicesData.getDistrict());
            tvOfficeContactDutyPerson.setText("Contact Duty Person's no. : "+servicesData.getContactDutyPersonContactNumber());
            tvOfficeLandline.setText("Office Landline no : "+servicesData.getOfficeLandline());
//        Glide.with(this).load(servicesData.getPhotoPath()).into(backdrop);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }
}
