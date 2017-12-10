
package com.nepal.naxa.smartnaari.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity (nameInDb = "hot_pot_of_passion_data")
public class HotPotOfPassionData {

    @SerializedName("cnt_id")
    @Expose
    private String cntId;
    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uploaded_by")
    @Expose
    private String uploadedBy;
    @SerializedName("last_sync_date_time")
    @Expose
    private String lastSyncDateTime;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;

    @Generated(hash = 1329274117)
    public HotPotOfPassionData(String cntId, String head, String body, String photo, String type,
            String uploadedBy, String lastSyncDateTime, String isDelete) {
        this.cntId = cntId;
        this.head = head;
        this.body = body;
        this.photo = photo;
        this.type = type;
        this.uploadedBy = uploadedBy;
        this.lastSyncDateTime = lastSyncDateTime;
        this.isDelete = isDelete;
    }

    @Generated(hash = 1472774195)
    public HotPotOfPassionData() {
    }

    public String getCntId() {
        return cntId;
    }

    public void setCntId(String cntId) {
        this.cntId = cntId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
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

}
