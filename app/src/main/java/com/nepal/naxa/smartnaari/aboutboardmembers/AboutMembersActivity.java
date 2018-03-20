package com.nepal.naxa.smartnaari.aboutboardmembers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Majestic on 11/29/2017.
 */

public class AboutMembersActivity extends BaseActivity implements JSONAssetLoadListener {

    private static final String TAG = "AboutMembersActivity";

    private RecyclerView recyclerView;
    private AboutMembersRecylerViewAdapter adapter;
    private JSONAssetLoadTask jsonAssetLoadTask;

    private int recyclerPosition;

    public AboutMembersActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_board_members);

        initToolbar();

        if(ConstantData.isFromVolunteerFriends){
            Intent intent = getIntent();
            recyclerPosition =Integer.parseInt( intent.getStringExtra(ConstantData.KEY_RECYCLER_POS));
            Log.d(TAG, "onCreate: "+recyclerPosition);
        }

        setAboutMembersRecyclerView();

        jsonAssetLoadTask = new JSONAssetLoadTask(R.raw.meet_the_board, this, this);
        jsonAssetLoadTask.execute();


    }

    private void setAboutMembersRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvAboutBoardMembers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setNestedScrollingEnabled(false);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.item_call:
                Intent intent = new Intent(AboutMembersActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFileLoadComplete(String s) {

        Type listType = new TypeToken<List<MemberPojo>>() {
        }.getType();
        List<MemberPojo> members = new Gson().fromJson(s, listType);

        adapter.setMembers(members);
        adapter.notifyDataSetChanged();
        Log.e("qqq", "This data is: " + s);


        if(ConstantData.isFromVolunteerFriends){


                    new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerPosition);
                Log.d(TAG, "run: recyclerPosition "+recyclerPosition);
            }
        }, 200);

            ConstantData.isFromVolunteerFriends = false;

        }


    }



    @Override
    public void onFileLoadError(String errorMsg) {
    }
}
