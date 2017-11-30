package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMembersActivity extends AppCompatActivity implements JSONAssetLoadListener {

    private RecyclerView recyclerView;
    private AboutMembersRecylerViewAdapter adapter;
    private List<MemberDetail> member = new ArrayList<>();
    private JSONAssetLoadTask jsonAssetLoadTask;

    public AboutMembersActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_board_members);

        initToolbar();

        setAboutMembersRecyclerView();

        jsonAssetLoadTask = new JSONAssetLoadTask(R.raw.meet_the_board, this,this);
        jsonAssetLoadTask.execute();

    }

    private void setAboutMembersRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvAboutBoardMembers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AboutMembersRecylerViewAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public List<MemberDetail> getMemberFromBelow() {
        member.add(new MemberDetail(
                "Ram",
                "Member",
                "Security Guard",
                "Thamel Bar",
                "To they four in love. Settling you has separate supplied bed. Concluded resembled suspected his resources curiosity joy. Led all cottage met enabled attempt through talking delight. Dare he feet my tell busy. Considered imprudence of he friendship boisterous. "));
        member.add(new MemberDetail(
                "Shyam",
                "General",
                "Manager",
                "Naxal",
                "To they four in love. Settling you has separate supplied bed. Concluded resembled suspected his resources curiosity joy. Led all cottage met enabled attempt through talking delight. Dare he feet my tell busy. Considered imprudence of he friendship boisterous. "));
        return member;
    }

    @Override
    public void onFileLoadComplete(String s) {

        Type listType = new TypeToken<List<MemberPojo>>() {
        }.getType();
        List<MemberPojo> members = new Gson().fromJson(s, listType);

        adapter.setMembers(members);

        Log.e("qqq", "This data is: " + s);
    }

    @Override
    public void onFileLoadError(String errorMsg) {
    }
}
