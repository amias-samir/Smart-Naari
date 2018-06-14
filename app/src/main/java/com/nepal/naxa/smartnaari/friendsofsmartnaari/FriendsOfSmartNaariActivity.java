package com.nepal.naxa.smartnaari.friendsofsmartnaari;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.AboutMembersActivity;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FriendsOfSmartNaariActivity extends BaseActivity {

    @BindView(R.id.tvVolunteers)
    TextView tvVolunteers;
    @BindView(R.id.tvSmallBusiness)
    TextView tvSmallBusiness;
    @BindView(R.id.tvNgo)
    TextView tvNGO;
    @BindView(R.id.tvCorporate)
    TextView tvCorporate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_of_smart_naari);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Friends of Smart नारी");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
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
                Intent intent = new Intent(FriendsOfSmartNaariActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tvVolunteers, R.id.tvSmallBusiness, R.id.tvNgo, R.id.tvCorporate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvVolunteers:
                Intent volunteerIntent = new Intent(FriendsOfSmartNaariActivity.this, FriendsOfSmartNaariVolunteersActivity.class);
                startActivity(volunteerIntent);
                break;
            case R.id.tvSmallBusiness:
                Intent smallBusinessIntent = new Intent(FriendsOfSmartNaariActivity.this, FriendsOfSmartNaariSmallBusinessActivity.class);
                startActivity(smallBusinessIntent);
                break;
            case R.id.tvNgo:
                break;
            case R.id.tvCorporate:
                break;
        }
    }


}
