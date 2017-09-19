package com.nepal.naxa.smartnaari.masakchamchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Majestic on 9/12/2017.
 */

public class LifeCoachingActivity extends AppCompatActivity {
    @BindView(R.id.btnLifeCoachingTakeaTest)
    Button btnLifeCoachingTakeaTest;
    @BindView(R.id.btnLifeCoachingFreeOnlineCourse)
    Button btnLifeCoachingFreeOnlineCourse;
    @BindView(R.id.tvLifeCoachingDetail)
    TextView tvLifeCoachingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_coaching);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
