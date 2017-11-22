package com.nepal.naxa.smartnaari.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created on 10/12/17
 * by nishon.tan@gmail.com
 */

@Entity(nameInDb = "yuwa_question")
public class YuwaQuestion implements Parcelable {

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("yuwa_tbl_id")
    @Index(unique = true)
    @Property(nameInDb = "id_string")
    private String idString;

    @SerializedName("owl_id")
    @Property(nameInDb = "owl_id")
    private String owlId;

    @SerializedName("qstn")
    @Property(nameInDb = "question")
    private String question;

    @SerializedName("ans")
    @Property(nameInDb = "answer")
    private String answer;


    @SerializedName("user_id")
    @Property(nameInDb = "user_id")
    private String userId;

    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;

    @Property(nameInDb = "last_sync_date_time")
    @SerializedName("last_sync_date_time")
    private String last_sync_date_time;

    @Transient
    private Boolean isFooter;

    protected YuwaQuestion(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        idString = in.readString();
        owlId = in.readString();
        question = in.readString();
        answer = in.readString();
        userId = in.readString();
        if (in.readByte() == 0) {
            isDeleted = null;
        } else {
            isDeleted = in.readInt();
        }
        last_sync_date_time = in.readString();
        byte tmpIsFooter = in.readByte();
        isFooter = tmpIsFooter == 0 ? null : tmpIsFooter == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(idString);
        dest.writeString(owlId);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(userId);
        if (isDeleted == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDeleted);
        }
        dest.writeString(last_sync_date_time);
        dest.writeByte((byte) (isFooter == null ? 0 : isFooter ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<YuwaQuestion> CREATOR = new Creator<YuwaQuestion>() {
        @Override
        public YuwaQuestion createFromParcel(Parcel in) {
            return new YuwaQuestion(in);
        }

        @Override
        public YuwaQuestion[] newArray(int size) {
            return new YuwaQuestion[size];
        }
    };

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }



    public Boolean getIsFooter() {
        return isFooter;
    }

    public void setIsFooter(Boolean isFooter) {
        this.isFooter = isFooter;
    }

    @Generated(hash = 1609754814)
    public YuwaQuestion(Long id, String idString, String owlId, String question,
            String answer, String userId, Integer isDeleted,
            String last_sync_date_time) {
        this.id = id;
        this.idString = idString;
        this.owlId = owlId;
        this.question = question;
        this.answer = answer;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.last_sync_date_time = last_sync_date_time;
    }

    @Generated(hash = 2033614305)
    public YuwaQuestion() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwlId() {
        return this.owlId;
    }

    public void setOwlId(String owlId) {
        this.owlId = owlId;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLast_sync_date_time() {
        return this.last_sync_date_time;
    }

    public void setLast_sync_date_time(String last_sync_date_time) {
        this.last_sync_date_time = last_sync_date_time;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdString() {
        return this.idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }


}
