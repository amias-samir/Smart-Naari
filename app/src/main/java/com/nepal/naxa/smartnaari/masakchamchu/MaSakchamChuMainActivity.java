package com.nepal.naxa.smartnaari.masakchamchu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.LifeCoachingTestActivity;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.smartparent.SmartParentActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

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
    @BindView(R.id.btn_go_to_ma_chup_basdina)
    ImageButton btnGoToMaChupBasdina;
    @BindView(R.id.btn_go_to_services)
    ImageButton btnGoToServices;
    @BindView(R.id.btn_go_to_yuwa_pusta)
    ImageButton btnGoToYuwaPusta;
    @BindView(R.id.btn_go_to_smart_parenting)
    ImageButton btnGoToSmartParenting;
    @BindView(R.id.btn_go_to_i_am_amazing)
    ImageButton btnGoToIAmAmazing;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_call:
                Intent intent = new Intent(MaSakchamChuMainActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    @OnClick({R.id.btn_go_to_ma_chup_basdina, R.id.btn_go_to_services, R.id.btn_go_to_yuwa_pusta, R.id.btn_go_to_smart_parenting, R.id.btn_go_to_i_am_amazing})
    public void onBottomViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go_to_ma_chup_basdina:
                Intent intent = new Intent(MaSakchamChuMainActivity.this, MaChupBasdinaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_go_to_services:
                Intent intent2 = new Intent(MaSakchamChuMainActivity.this, ServicesActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_go_to_yuwa_pusta:
                Intent intent3 = new Intent(MaSakchamChuMainActivity.this, YuwaPustaActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.btn_go_to_smart_parenting:
                Intent intent4 = new Intent(MaSakchamChuMainActivity.this, SmartParentActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.btn_go_to_i_am_amazing:
                Intent intent5 = new Intent(MaSakchamChuMainActivity.this, IAmAmazingActivity.class);
                startActivity(intent5);
                finish();
                break;
        }
    }
}
