package com.nepal.naxa.smartnaari.masakchamchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nepal.naxa.smartnaari.R;

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

    ExpandableTextView expandableTextView;

    String personOfTheMonthDetail = "Smart Naari presents ordinary Nepali Nepali folks who grew up under some kind of heavy boots and whom may not make it into the history books, but they have a story to tell and we beleive these stories must be heard";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_am_amazing);
        ButterKnife.bind(this);
        initToolbar();

        expandableTextView = (ExpandableTextView) findViewById(R.id.expandable_textview);
        expandableTextView.setText(personOfTheMonthDetail);
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
}