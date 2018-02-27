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
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutsmartnaari.AboutSmartNaariActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    private void setExpandableText(final at.blogc.android.views.ExpandableTextView expandableTextView, final Button buttonToggle) {

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
}