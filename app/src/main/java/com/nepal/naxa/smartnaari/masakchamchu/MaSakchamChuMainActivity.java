package com.nepal.naxa.smartnaari.masakchamchu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.LifeCoachingTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Majestic on 9/13/2017.
 */

public class MaSakchamChuMainActivity extends AppCompatActivity {


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
        toolbar.setTitle("Ma Sakshyam Chhu");
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
    public void toTakeATest() {
        Intent lifeCoachingIntent = new Intent(MaSakchamChuMainActivity.this, LifeCoachingTestActivity.class);
        startActivity(lifeCoachingIntent);
    }

    @OnClick({R.id.bt_main_activity_freeonlinecource, R.id.bt_main_activity_bookanappointment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_main_activity_freeonlinecource:
                Intent freeOnlineIntent = new Intent(MaSakchamChuMainActivity.this, FreeOnlineCourseActivity.class);
                startActivity(freeOnlineIntent);
                break;
            case R.id.bt_main_activity_bookanappointment:
                Intent bookAppointmentIntent = new Intent(MaSakchamChuMainActivity.this, BookAnAppointmentActivity.class);
                startActivity(bookAppointmentIntent);
                break;
        }
    }
}
