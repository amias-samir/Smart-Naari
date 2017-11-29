package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Majestic on 11/29/2017.
 */

public class MemberDetail {

    private Image memberImage=null;
    private String name, smartNaariPost, externalPost, externalOffice, memberDescription;


    public MemberDetail(String name, String smartNaariPost, String externalPost, String externalOffice, String memberDescription) {
        //this.memberImage = memberImage;
        this.name = name;
        this.smartNaariPost = smartNaariPost;
        this.externalPost = externalPost;
        this.externalOffice = externalOffice;
        this.memberDescription = memberDescription;
    }

    public Image getMemberImage() {
        return memberImage;
    }

    public String getName() {
        return name;
    }

    public String getSmartNaariPost() {
        return smartNaariPost;
    }

    public String getExternalPost() {
        return externalPost;
    }

    public String getExternalOffice() {
        return externalOffice;
    }

    public String getMemberDescription() {
        return memberDescription;
    }
}
