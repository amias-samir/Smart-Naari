package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nepal.naxa.smartnaari.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMembersRecylerViewAdapter extends RecyclerView.Adapter<AboutMemberRecyclerViewHolder> {

    private Context mContext;
    private List<MemberDetail> members = new ArrayList<>();

    public AboutMembersRecylerViewAdapter(Context mContext,List<MemberDetail> members) {
        this.members=members;
        this.mContext = mContext;
    }

    public void setMembers(List<MemberDetail> person) {
        this.members = person;
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
        MemberDetail cPerson = members.get(position);
        Glide.with(mContext)
                .load(R.drawable.grid_i_am_amazing)
                .into(holder.getMemberImage());
        holder.getMemberName().setText(cPerson.getName());
        holder.getMemberSmartNaariPost().setText(cPerson.getSmartNaariPost());
        holder.getMemberExternalPost().setText(cPerson.getExternalPost());
        holder.getMemberExternalOffice().setText(cPerson.getExternalOffice());
        holder.getMemberDescription().setText(cPerson.getMemberDescription());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
