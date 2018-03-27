package com.nepal.naxa.smartnaari.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesListActivity extends BaseActivity {

    @BindView(R.id.services_list_recyclerView)
    RecyclerView servicesListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        ButterKnife.bind(this);

        initToolbar();

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Services");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem mapViewServices = menu.findItem(R.id.item_map_menu);
        mapViewServices.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.item_map_menu:
                Intent mapIntent = new Intent(ServicesListActivity.this, ServicesActivity.class);
                startActivity(mapIntent);
                break;

            case R.id.item_call:
                Intent callIntent = new Intent(ServicesListActivity.this, TapItStopItActivity.class);
                startActivity(callIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
