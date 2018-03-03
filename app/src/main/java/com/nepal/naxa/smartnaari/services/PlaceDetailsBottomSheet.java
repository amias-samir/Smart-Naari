package com.nepal.naxa.smartnaari.services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.ServicesData;



public class PlaceDetailsBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "PlaceDetailsBottomSheet";

    ServicesData servicesData;
//    @BindView(R.id.office_name)
    TextView tvOfficeName;
//    @BindView(R.id.office_address)
    TextView tvOfficeAddress;
//    @BindView(R.id.office_type)
    TextView tvOfficeType;
//    @BindView(R.id.office_contact_duty_person)
    TextView tvOfficeContactDutyPerson;
//    @BindView(R.id.office_landline)
    TextView tvOfficeLandline;
//    Unbinder unbinder;

TextView tvofficeEmail, tvOfficeCheifLandline, tvOfficeCheifMobileNo ;


    public static PlaceDetailsBottomSheet getInstance(ServicesData servicesData) {

        PlaceDetailsBottomSheet placeDetailsBottomSheet = new PlaceDetailsBottomSheet();
        placeDetailsBottomSheet.setObject(servicesData);
        return placeDetailsBottomSheet;
    }

    private void setObject(ServicesData servicesData) {
        this.servicesData = servicesData;

//        Log.e(TAG, "setObject: "+ servicesData.getOfficeName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);

        tvOfficeName = (TextView)rootView. findViewById(R.id.office_name);
        tvOfficeAddress = (TextView)rootView. findViewById(R.id.office_address);
        tvOfficeType = (TextView)rootView. findViewById(R.id.office_type);
        tvofficeEmail = (TextView)rootView.findViewById(R.id.office_email);
        tvOfficeCheifLandline = (TextView)rootView.findViewById(R.id.office_cheif_landline);
        tvOfficeCheifMobileNo = (TextView)rootView.findViewById(R.id.office_cheif_mobile);
        tvOfficeContactDutyPerson = (TextView)rootView. findViewById(R.id.office_contact_duty_person);
        tvOfficeLandline = (TextView)rootView. findViewById(R.id.office_landline);


        try {
            Log.e(TAG, "setObject: "+ servicesData.getOfficeName());
String district = servicesData.getDistrict();
            String officeDistrict = district.substring(0, 1).toUpperCase() + district.substring(1);

            tvOfficeName.setText("Office Name : "+servicesData.getOfficeName().trim());
            tvOfficeType.setText("Office Type : "+servicesData.getOfficeType().trim());
            tvOfficeAddress.setText("Office Address : "+ officeDistrict.trim());

            tvofficeEmail.setText("Email: "+servicesData.getOfficeEmail().trim());
            Linkify.addLinks(tvofficeEmail, Linkify.EMAIL_ADDRESSES);

            tvOfficeCheifLandline.setText("Office Cheif's Landline. : "+servicesData.getOfficeCheifLandline().trim());
            Linkify.addLinks(tvOfficeCheifLandline, Linkify.PHONE_NUMBERS);

            tvOfficeLandline.setText("Office Cheif's Mobile no. : "+servicesData.getOfficeCheifMobileNumber().trim());
            Linkify.addLinks(tvOfficeCheifMobileNo, Linkify.PHONE_NUMBERS);

            tvOfficeContactDutyPerson.setText("Contact Duty Person's no. : "+servicesData.getContactDutyPersonContactNumber().trim());
            Linkify.addLinks(tvOfficeContactDutyPerson, Linkify.PHONE_NUMBERS);

            tvOfficeLandline.setText("Office Landline no : "+servicesData.getOfficeLandline().trim());
            Linkify.addLinks(tvOfficeLandline, Linkify.PHONE_NUMBERS);
//        Glide.with(this).load(servicesData.getPhotoPath()).into(backdrop);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//
//
//    }
}
