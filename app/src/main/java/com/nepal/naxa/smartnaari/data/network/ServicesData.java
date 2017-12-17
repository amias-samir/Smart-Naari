
package com.nepal.naxa.smartnaari.data.network;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity(nameInDb = "services_data")
public class ServicesData implements ClusterItem {

    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("office_name")
    @Expose
    private String officeName;
    @SerializedName("office_landline")
    @Expose
    private String officeLandline;
    @SerializedName("office_email")
    @Expose
    private String officeEmail;
    @SerializedName("office_type")
    @Expose
    private String officeType;
    @SerializedName("contact_duty_person_name")
    @Expose
    private String contactDutyPersonName;
    @SerializedName("contact_duty_person_contact_number")
    @Expose
    private String contactDutyPersonContactNumber;
    @SerializedName("office_cheifname")
    @Expose
    private String officeCheifname;
    @SerializedName("office_cheif_mobile_number")
    @Expose
    private String officeCheifMobileNumber;
    @SerializedName("office_cheif_landline")
    @Expose
    private String officeCheifLandline;
    @SerializedName("service_lat")
    @Expose
    private String serviceLat;
    @SerializedName("service_lon")
    @Expose
    private String serviceLon;
    @SerializedName("addtional_description")
    @Expose
    private String addtionalDescription;
    @SerializedName("last_sync_date_time")
    @Expose
    private String lastSyncDateTime;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;

    private transient LatLng mPosition ;
    private transient String mTitle;
    private transient String mSnippet;



    @Keep
    public ServicesData(String serviceId, String zone, String district, String officeName,
            String officeLandline, String officeEmail, String officeType,
            String contactDutyPersonName, String contactDutyPersonContactNumber,
            String officeCheifname, String officeCheifMobileNumber,
            String officeCheifLandline, String serviceLat, String serviceLon,
            String addtionalDescription, String lastSyncDateTime, String isDelete) {
        this.serviceId = serviceId;
        this.zone = zone;
        this.district = district;
        this.officeName = officeName;
        this.officeLandline = officeLandline;
        this.officeEmail = officeEmail;
        this.officeType = officeType;
        this.contactDutyPersonName = contactDutyPersonName;
        this.contactDutyPersonContactNumber = contactDutyPersonContactNumber;
        this.officeCheifname = officeCheifname;
        this.officeCheifMobileNumber = officeCheifMobileNumber;
        this.officeCheifLandline = officeCheifLandline;
        this.serviceLat = serviceLat;
        this.serviceLon = serviceLon;
        this.addtionalDescription = addtionalDescription;
        this.lastSyncDateTime = lastSyncDateTime;
        this.isDelete = isDelete;

        this.mPosition = position(serviceLat, serviceLon);


    }

    @Generated(hash = 1853525144)
    public ServicesData() {
    }



    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeLandline() {
        return officeLandline;
    }

    public void setOfficeLandline(String officeLandline) {
        this.officeLandline = officeLandline;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }

    public String getContactDutyPersonName() {
        return contactDutyPersonName;
    }

    public void setContactDutyPersonName(String contactDutyPersonName) {
        this.contactDutyPersonName = contactDutyPersonName;
    }

    public String getContactDutyPersonContactNumber() {
        return contactDutyPersonContactNumber;
    }

    public void setContactDutyPersonContactNumber(String contactDutyPersonContactNumber) {
        this.contactDutyPersonContactNumber = contactDutyPersonContactNumber;
    }

    public String getOfficeCheifname() {
        return officeCheifname;
    }

    public void setOfficeCheifname(String officeCheifname) {
        this.officeCheifname = officeCheifname;
    }

    public String getOfficeCheifMobileNumber() {
        return officeCheifMobileNumber;
    }

    public void setOfficeCheifMobileNumber(String officeCheifMobileNumber) {
        this.officeCheifMobileNumber = officeCheifMobileNumber;
    }

    public String getOfficeCheifLandline() {
        return officeCheifLandline;
    }

    public void setOfficeCheifLandline(String officeCheifLandline) {
        this.officeCheifLandline = officeCheifLandline;
    }

    public String getServiceLat() {
        return serviceLat;
    }

    public void setServiceLat(String serviceLat) {
        this.serviceLat = serviceLat;
    }

    public String getServiceLon() {
        return serviceLon;
    }

    public void setServiceLon(String serviceLon) {
        this.serviceLon = serviceLon;
    }

    public String getAddtionalDescription() {
        return addtionalDescription;
    }

    public void setAddtionalDescription(String addtionalDescription) {
        this.addtionalDescription = addtionalDescription;
    }

    public String getLastSyncDateTime() {
        return lastSyncDateTime;
    }

    public void setLastSyncDateTime(String lastSyncDateTime) {
        this.lastSyncDateTime = lastSyncDateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }



    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }



//    avoid null or empty servive (Lat, Lon)
    public LatLng position (String serviceLat, String serviceLon){

        LatLng position = null ;
        Double Lat ;
        Double Lon ;

        if(TextUtils.isEmpty(serviceLat)){
            Lat = 0.0 ;
        }else {
            Lat = Double.parseDouble(serviceLat) ;

        }

        if(TextUtils.isEmpty(serviceLon)){
            Lon = 0.0 ;
        }
        else {
             Lon = Double.parseDouble(serviceLon) ;
        }



        position = new LatLng(Lat,Lon);
        return position ;
    }

}
