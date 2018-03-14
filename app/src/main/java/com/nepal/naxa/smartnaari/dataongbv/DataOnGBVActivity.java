package com.nepal.naxa.smartnaari.dataongbv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataOnGBVActivity extends BaseActivity {

    @BindView(R.id.iv_data_nepal)
    ImageView ivDataNepal;
    @BindView(R.id.iv_nepal_monitor)
    ImageView ivNepalMonitor;
    @BindView(R.id.tv_data_nepal)
    TextView tvDataNepal;
    @BindView(R.id.tv_nepal_monitor)
    TextView tvNepalMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_on_gbv);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        initToolbar();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(500);
        // This view will not be affected by enter transition animation
//        enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Visibility buildReturnTransition() {
        Visibility enterTransition = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Slide();
        }
        enterTransition.setDuration(500);
        return enterTransition;
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Data");
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.item_call:
                Intent intent = new Intent(DataOnGBVActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_data_nepal, R.id.iv_nepal_monitor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_data_nepal:
                Intent dataNepalIntent = new Intent(DataOnGBVActivity.this, DefaultWebpageLoadActivity.class);
                dataNepalIntent.putExtra("toolbar_title", "Data Nepal");
                dataNepalIntent.putExtra("url", "https://www.datanepal.com/");
                startActivity(dataNepalIntent);
                break;

            case R.id.iv_nepal_monitor:
                Intent nepalMonitorIntent = new Intent(DataOnGBVActivity.this, DefaultWebpageLoadActivity.class);
                nepalMonitorIntent.putExtra("toolbar_title", "Nepal Monitor");
                nepalMonitorIntent.putExtra("url", "https://nepalmonitor.org/");
                startActivity(nepalMonitorIntent);
                break;
        }
    }

    @OnClick({R.id.tv_data_nepal, R.id.tv_nepal_monitor})
    public void onTextViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data_nepal:
                Intent dataNepalIntent = new Intent(DataOnGBVActivity.this, DefaultWebpageLoadActivity.class);
                dataNepalIntent.putExtra("toolbar_title", "Data Nepal");
                dataNepalIntent.putExtra("url", "https://www.datanepal.com/");
                startActivity(dataNepalIntent);
                break;
            case R.id.tv_nepal_monitor:
                Intent nepalMonitorIntent = new Intent(DataOnGBVActivity.this, DefaultWebpageLoadActivity.class);
                nepalMonitorIntent.putExtra("toolbar_title", "Nepal Monitor");
                nepalMonitorIntent.putExtra("url", "https://nepalmonitor.org/");
                startActivity(nepalMonitorIntent);
                break;
        }
    }
}
