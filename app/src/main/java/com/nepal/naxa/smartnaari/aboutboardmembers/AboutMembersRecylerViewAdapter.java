package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.nepal.naxa.smartnaari.R;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMembersRecylerViewAdapter extends RecyclerView.Adapter<AboutMemberRecyclerViewHolder> {

    private Context mContext;
    private List<MemberPojo> members = new ArrayList<>();


    public AboutMembersRecylerViewAdapter(Context mContext, List<MemberPojo> members) {
        this.members = members;
        this.mContext = mContext;
    }

    public AboutMembersRecylerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setMembers(List<MemberPojo> members) {
        this.members = members;
    }

    @Override
    public AboutMemberRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_board_members, parent, false);
        return new AboutMemberRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AboutMemberRecyclerViewHolder holder, int position) {
        MemberPojo cPerson = members.get(position);
        Glide.with(mContext)
                .load(R.drawable.grid_i_am_amazing)
                .into(holder.getMemberImage());
        holder.getMemberName().setText(cPerson.getPersonName());
        holder.getMemberSmartNaariPost().setText(cPerson.getSmartNaariPost());
        holder.getMemberExternalPostOffice().setText(cPerson.getExternalPost() + ", " + cPerson.getExternalOffice());


        final ExpandableTextView expandableTextView = holder.getMemberDescription();
        final Button button = holder.getMemberDescriptionToogle();
        setExpandableText(expandableTextView, button);
        expandableTextView.setText(cPerson.getPersonDescription());

    }

    private void setExpandableText(final ExpandableTextView expandableTextView, final Button buttonToggle) {


        expandableTextView.setAnimationDuration(750L);

        expandableTextView.setInterpolator(new OvershootInterpolator());

        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttonToggle.setText(expandableTextView.isExpanded() ? "More" : "Less");
                expandableTextView.toggle();
            }
        });

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (expandableTextView.isExpanded()) {
                    expandableTextView.collapse();
                    buttonToggle.setText("More");
                } else {
                    expandableTextView.expand();
                    buttonToggle.setText("Less");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
