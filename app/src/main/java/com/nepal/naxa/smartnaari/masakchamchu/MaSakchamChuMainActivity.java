package com.nepal.naxa.smartnaari.masakchamchu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.nepal.naxa.smartnaari.MaSakchamChuCoachActivity;
import com.nepal.naxa.smartnaari.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Majestic on 9/13/2017.
 */

public class MaSakchamChuMainActivity extends AppCompatActivity {

    @BindView(R.id.rb_main_activitylifecoaching)
    RadioButton rbMainActivitylifecoaching;
    @BindView(R.id.rb_main_activity_angermanagement)
    RadioButton rbMainActivityAngermanagement;
    @BindView(R.id.bt_main_activity_takeatest)
    Button btnTakeATest;
    @BindView(R.id.bt_main_activity_freeonlinecource)
    Button btMainActivityFreeonlinecource;
    @BindView(R.id.bt_main_activity_bookanappointment)
    Button btMainActivityBookanappointment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_sakcham_chu_main);
        ButterKnife.bind(this);
        initToolbar();
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
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.bt_main_activity_takeatest)
    public void toTakeATest(){
        startActivity(new Intent(this, LifeCoachingActivity.class));
    }

}
