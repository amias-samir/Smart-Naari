package com.nepal.naxa.smartnaari.data.local.model;

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
public class YuwaQuestion {

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

    @Property(nameInDb = "last_sync_date_time")
    private String last_sync_date_time;

    @Transient
    private Boolean isFooter;

    public Boolean getIsFooter() {
        return isFooter;
    }

    public void setIsFooter(Boolean isFooter) {
        this.isFooter = isFooter;
    }

    @Generated(hash = 1657222644)
    public YuwaQuestion(Long id, String idString, String owlId, String question,
            String answer, String userId, String last_sync_date_time) {
        this.id = id;
        this.idString = idString;
        this.owlId = owlId;
        this.question = question;
        this.answer = answer;
        this.userId = userId;
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
