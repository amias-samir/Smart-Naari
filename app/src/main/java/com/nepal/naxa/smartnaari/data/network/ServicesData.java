
package com.nepal.naxa.smartnaari.data.network;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;

@Entity (nameInDb = "services_data")
public class ServicesData implements ClusterItem{

    @SerializedName("service_id")
    @Unique
    @Expose
    private String serviceId;

    @SerializedName("service_phone_number")
    @Expose
    private String servicePhoneNumber;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("service_district_name")
    @Expose
    private String serviceDistrictName;
    @SerializedName("service_district_type")
    @Expose
    private String serviceDistrictType;
    @SerializedName("last_sync_date_time")
    @Expose
    private String lastSyncDateTime;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("service_lat")
    @Expose
    private String serviceLat;
    @SerializedName("service_lon")
    @Expose
    private String serviceLon;

    private transient LatLng mPosition ;
    private transient String mTitle;
    private transient String mSnippet;

    @Keep
    public ServicesData(String serviceId, String servicePhoneNumber, String serviceName, String serviceTypeId,
            String serviceDistrictName, String serviceDistrictType, String lastSyncDateTime, Integer isDeleted,
            String serviceLat, String serviceLon) {
        this.serviceId = serviceId;
        this.servicePhoneNumber = servicePhoneNumber;
        this.serviceName = serviceName;
        this.serviceTypeId = serviceTypeId;
        this.serviceDistrictName = serviceDistrictName;
        this.serviceDistrictType = serviceDistrictType;
        this.lastSyncDateTime = lastSyncDateTime;
        this.isDeleted = isDeleted;
        this.serviceLat = serviceLat;
        this.serviceLon = serviceLon;

        this.mPosition = new LatLng(Double.parseDouble(serviceLat), Double.parseDouble(serviceLon));
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

    public String getServicePhoneNumber() {
        return servicePhoneNumber;
    }

    public void setServicePhoneNumber(String servicePhoneNumber) {
        this.servicePhoneNumber = servicePhoneNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceDistrictName() {
        return serviceDistrictName;
    }

    public void setServiceDistrictName(String serviceDistrictName) {
        this.serviceDistrictName = serviceDistrictName;
    }

    public String getServiceDistrictType() {
        return serviceDistrictType;
    }

    public void setServiceDistrictType(String serviceDistrictType) {
        this.serviceDistrictType = serviceDistrictType;
    }

    public String getLastSyncDateTime() {
        return lastSyncDateTime;
    }

    public void setLastSyncDateTime(String lastSyncDateTime) {
        this.lastSyncDateTime = lastSyncDateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

}
