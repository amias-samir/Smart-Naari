package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import at.blogc.android.views.ExpandableTextView;


/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMemberRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView memberImage;
    private TextView memberName, memberSmartNaariPost,memberDescription;
    private ExpandableTextView memberExternalPostOffice;
    private ImageButton memberDescriptionToogle;

    public AboutMemberRecyclerViewHolder(View itemView) {
        super(itemView);

        memberImage = (ImageView) itemView.findViewById(R.id.iv_member_image);
        memberName = (TextView) itemView.findViewById(R.id.tv_member_name);
        memberSmartNaariPost = (TextView) itemView.findViewById(R.id.tv_smart_naari_post);
        memberExternalPostOffice = (ExpandableTextView) itemView.findViewById(R.id.etv_external_post);
        memberDescription = (TextView) itemView.findViewById(R.id.tv_member_description);
        memberDescriptionToogle = (ImageButton) itemView.findViewById(R.id.btn_toggle);
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

    public TextView getMemberDescription() {
        return memberDescription;
    }

    public ExpandableTextView getMemberExternalPostOffice() {
        return memberExternalPostOffice;
    }

    public ImageButton getMemberDescriptionToogle() {
        return memberDescriptionToogle;
    }
}
