package com.nepal.naxa.smartnaari.smartparent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmartParentActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smart_parenting_lets_have_talkLBL)
    TextView tvHeader;
    @BindView(R.id.btn2To12Years)
    Button btn2To12Years;
    @BindView(R.id.btnEarlyTeens)
    Button btnEarlyTeens;
    @BindView(R.id.btnMidTeens)
    Button btnMidTeens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_parent);

        ButterKnife.bind(this);
        initToolbar();


        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

        SpanUtils.setColor(tvHeader, "Lets Have the Talk", "Talk", color);


    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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


    @OnClick({R.id.btn2To12Years, R.id.btnEarlyTeens, R.id.btnMidTeens})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn2To12Years:
                Intent twoToTwelveIntent = new Intent(SmartParentActivity.this, TwoToTwelveYearsActivity.class);
                startActivity(twoToTwelveIntent);
                break;
            case R.id.btnEarlyTeens:
                Intent earlyTeensIntent = new Intent(SmartParentActivity.this, EarlyTeensActivity.class);
                startActivity(earlyTeensIntent);
                break;
            case R.id.btnMidTeens:
                Intent midTeensIntent = new Intent(SmartParentActivity.this, MidTeensActivity.class);
                startActivity(midTeensIntent);
                break;
        }
    }
}
