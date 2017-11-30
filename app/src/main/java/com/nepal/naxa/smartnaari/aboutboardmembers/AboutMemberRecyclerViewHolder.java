package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;


/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMemberRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView memberImage;
    private TextView memberName, memberSmartNaariPost, memberExternalPost, memberExternalOffice, memberDescription;

    public AboutMemberRecyclerViewHolder(View itemView) {
        super(itemView);

        memberImage = (ImageView) itemView.findViewById(R.id.iv_member_image);
        memberName = (TextView) itemView.findViewById(R.id.tv_member_name);
        memberSmartNaariPost = (TextView) itemView.findViewById(R.id.tv_smart_naari_post);
        memberExternalPost = (TextView) itemView.findViewById(R.id.tv_external_post);
        memberExternalOffice = (TextView) itemView.findViewById(R.id.tv_external_office);
        memberDescription = (TextView) itemView.findViewById(R.id.expandable_text);
    }

    public ImageView getMemberImage() {
        return memberImage;
    }

    public TextView getMemberName() {
        return memberName;
    }

    public TextView getMemberSmartNaariPost() {
        return memberSmartNaariPost;
    }

    public TextView getMemberExternalPost() {
        return memberExternalPost;
    }

    public TextView getMemberExternalOffice() {
        return memberExternalOffice;
    }

    public TextView getMemberDescription() {
        return memberDescription;
    }
}
