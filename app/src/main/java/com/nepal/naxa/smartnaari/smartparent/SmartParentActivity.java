package com.nepal.naxa.smartnaari.smartparent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartParentActivity extends Activity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smart_parenting_lets_have_talkLBL)
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_parent);
        ButterKnife.bind(this);

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        SpanUtils.setColor(tvHeader, "Lets Have the ", "Talk", color);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
