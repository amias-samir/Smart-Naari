package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

    private final int TEXT_HEDER = 0, BOARD_MEMBER = 1;


    public AboutMembersRecylerViewAdapter(Context mContext, List<MemberPojo> members) {
        this.members = members;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    @Override
    public int getItemViewType(int position) {
        //More to come
        if (members.get(position).getType().equals("text_header")) {
            return TEXT_HEDER;
        } else if (members.get(position).getType().equals("board_member")) {
            return BOARD_MEMBER;
        }
        return -1;
    }

    public AboutMembersRecylerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setMembers(List<MemberPojo> members) {
        this.members = members;
    }

    @Override
    public AboutMemberRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View v = LayoutInflater
//                .from(mContext)
//                .inflate(R.layout.item_board_members, parent, false);
//        return new AboutMemberRecyclerViewHolder(v);

        AboutMemberRecyclerViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TEXT_HEDER:
                View v1 = inflater.inflate(R.layout.item_board_members, parent, false);
                viewHolder = new AboutMemberRecyclerViewHolder(v1);


                break;
            case BOARD_MEMBER:
                View v2 = inflater.inflate(R.layout.item_board_members, parent, false);
                viewHolder = new AboutMemberRecyclerViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AboutMemberRecyclerViewHolder holder, int position) {


        switch (holder.getItemViewType()) {
            case TEXT_HEDER:
                AboutMemberRecyclerViewHolder vh1 = (AboutMemberRecyclerViewHolder) holder;
                configureViewHolder1(vh1, position);
                break;
            case BOARD_MEMBER:
                AboutMemberRecyclerViewHolder vh2 = (AboutMemberRecyclerViewHolder) holder;
                configureViewHolder2(vh2, position);
                break;
        }



    }

    private void configureViewHolder1(final AboutMemberRecyclerViewHolder vh1, int position) {
        MemberPojo cPerson = members.get(position);


        vh1.getMemberDescription().setText(cPerson.getPersonName());

        vh1.getMemberImage().setVisibility(View.GONE);
        vh1.getMemberExternalPostOffice().setVisibility(View.GONE);
        vh1.getMemberDescriptionToogle().setVisibility(View.GONE);
        vh1.getMemberSmartNaariPost().setVisibility(View.GONE);
        vh1.getMemberName().setVisibility(View.GONE);
    }

    private void configureViewHolder2(AboutMemberRecyclerViewHolder vh2, int position) {
        MemberPojo cPerson = members.get(position);

        if (cPerson.getPhoto().equals("")){
            Glide.with(mContext)
                    .load(R.drawable.nav_user_avatar_default)
                    .into(vh2.getMemberImage());
        }else {
            Glide.with(mContext)
                    .load(cPerson.getPhoto())
                    .into(vh2.getMemberImage());
        }
        vh2.getMemberName().setText(cPerson.getPersonName());
        vh2.getMemberSmartNaariPost().setText(cPerson.getSmartNaariPost() + "");
        vh2.getMemberDescription().setText(cPerson.getPersonDescription());

        final ExpandableTextView expandableTextView = vh2.getMemberExternalPostOffice();
        final ImageButton button = vh2.getMemberDescriptionToogle();
        setExpandableText(expandableTextView, button);
        expandableTextView.setText(cPerson.getExternalPost() + "\n" + cPerson.getExternalOffice());
    }

    private void setExpandableText(final ExpandableTextView expandableTextView, final ImageButton buttonToggle) {


        expandableTextView.setAnimationDuration(750L);

        expandableTextView.setInterpolator(new OvershootInterpolator());

        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttonToggle.setBackgroundResource(expandableTextView.isExpanded() ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_up_black_24dp);
                expandableTextView.toggle();
            }
        });

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (expandableTextView.isExpanded()) {
                    expandableTextView.collapse();

                    buttonToggle.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    expandableTextView.expand();
                    buttonToggle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

                }
            }
        });

    }


}
