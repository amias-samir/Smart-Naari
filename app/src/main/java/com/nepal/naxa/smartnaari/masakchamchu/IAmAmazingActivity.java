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
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.smartparent.SmartParentActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Majestic on 9/12/2017.
 */

public class IAmAmazingActivity extends AppCompatActivity {
    @BindView(R.id.tvIAmAmazingDetail)
    TextView tvIAmAmazingDetail;
    @BindView(R.id.ivIAmAmazingPersonOfTheMonth)
    ImageView ivIAmAmazingPersonOfTheMonth;
    @BindView(R.id.etv_person_of_the_month_description)
    ExpandableTextView etvPersonOfTheMonthDescription;
    @BindView(R.id.btn_toggle_i_am_amazing)
    Button btnToggleIAmAmazing;
    @BindView(R.id.btn_go_to_ma_chup_basdina)
    ImageButton btnGoToMaChupBasdina;
    @BindView(R.id.btn_go_to_services)
    ImageButton btnGoToServices;
    @BindView(R.id.btn_go_to_ma_sakshyam_chhu)
    ImageButton btnGoToMaSakshyamChhu;
    @BindView(R.id.btn_go_to_yuwa_pusta)
    ImageButton btnGoToYuwaPusta;
    @BindView(R.id.btn_go_to_smart_parenting)
    ImageButton btnGoToSmartParenting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_am_amazing);
        ButterKnife.bind(this);
        initToolbar();

        setExpandableText(etvPersonOfTheMonthDescription, btnToggleIAmAmazing);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("I Am Amazing");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
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

            case R.id.item_call:
                Intent intent = new Intent(IAmAmazingActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setExpandableText(final ExpandableTextView expandableTextView, final Button buttonToggle) {

        expandableTextView.setInterpolator(new OvershootInterpolator());

        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttonToggle.setText(expandableTextView.isExpanded() ? "Read More >>" : "Read Less <<");
                expandableTextView.toggle();
            }
        });

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (expandableTextView.isExpanded()) {
                    expandableTextView.collapse();
                    buttonToggle.setText("Read More >>");
                } else {
                    expandableTextView.expand();
                    buttonToggle.setText("Read Less <<");
                }
            }
        });

    }

    @OnClick({R.id.btn_go_to_ma_chup_basdina, R.id.btn_go_to_services, R.id.btn_go_to_ma_sakshyam_chhu, R.id.btn_go_to_yuwa_pusta, R.id.btn_go_to_smart_parenting})
    public void onBottomViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go_to_ma_chup_basdina:
                Intent intent = new Intent(IAmAmazingActivity.this, MaChupBasdinaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_go_to_services:
                Intent intent2 = new Intent(IAmAmazingActivity.this, ServicesActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_go_to_ma_sakshyam_chhu:
                Intent intent3 = new Intent(IAmAmazingActivity.this, MaSakchamChuMainActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.btn_go_to_yuwa_pusta:
                Intent intent4 = new Intent(IAmAmazingActivity.this, YuwaPustaActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.btn_go_to_smart_parenting:
                Intent intent5 = new Intent(IAmAmazingActivity.this, SmartParentActivity.class);
                startActivity(intent5);
                finish();
                break;
        }
    }
}