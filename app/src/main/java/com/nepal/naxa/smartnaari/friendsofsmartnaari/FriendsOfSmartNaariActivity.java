package com.nepal.naxa.smartnaari.friendsofsmartnaari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.dataongbv.DataOnGBVActivity;
import com.nepal.naxa.smartnaari.dataongbv.DefaultWebpageLoadActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsOfSmartNaariActivity extends BaseActivity {

    @BindView(R.id.iv_naxa)
    ImageView ivNaxa;
    @BindView(R.id.iv_ark_ventures)
    ImageView ivArkVentures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_of_smart_naari);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Friends of Smart नारी");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @OnClick({R.id.iv_naxa, R.id.iv_ark_ventures})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_naxa:
                Intent naxaIntent = new Intent(FriendsOfSmartNaariActivity.this, DefaultWebpageLoadActivity.class);
                naxaIntent.putExtra("toolbar_title", "NAXA Location Matters");
                naxaIntent.putExtra("url", "http://naxa.com.np/");
                startActivity(naxaIntent);
                break;
            case R.id.iv_ark_ventures:
                Intent arkVenturesIntent = new Intent(FriendsOfSmartNaariActivity.this, DefaultWebpageLoadActivity.class);
                arkVenturesIntent.putExtra("toolbar_title", "ARK VENTURES PVT LTD");
                arkVenturesIntent.putExtra("url", "https://www.arkventures.com.np/");
                startActivity(arkVenturesIntent);
                break;
        }
    }
}
