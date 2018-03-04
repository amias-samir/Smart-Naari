package com.nepal.naxa.smartnaari.friendsofsmartnaari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.AboutMembersActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsOfSmartNaariVolunteersActivity extends AppCompatActivity {

    @BindView(R.id.tvAwantika)
    TextView tvAwantika;
    @BindView(R.id.tvKrishangi)
    TextView tvKrishangi;
    @BindView(R.id.tvMadhuri)
    TextView tvMadhuri;
    @BindView(R.id.tvPoonam)
    TextView tvPoonam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_of_smart_naari_volunteers);
        ButterKnife.bind(this);

        initToolbar();
    }



    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Volunteers");
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
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_call:
                Intent intent = new Intent(FriendsOfSmartNaariVolunteersActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tvAwantika, R.id.tvKrishangi, R.id.tvMadhuri, R.id.tvPoonam})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAwantika:
                ConstantData.isFromVolunteerFriends = true ;
                Intent awentikaIntent = new Intent(FriendsOfSmartNaariVolunteersActivity.this, AboutMembersActivity.class);
                awentikaIntent.putExtra(ConstantData.KEY_RECYCLER_POS, "2");
                startActivity(awentikaIntent);
                break;

            case R.id.tvKrishangi:
                break;

            case R.id.tvMadhuri:
                ConstantData.isFromVolunteerFriends = true ;
                Intent madhuriIntent = new Intent(FriendsOfSmartNaariVolunteersActivity.this, AboutMembersActivity.class);
                madhuriIntent.putExtra(ConstantData.KEY_RECYCLER_POS, "1");
                startActivity(madhuriIntent);
                break;

            case R.id.tvPoonam:
                ConstantData.isFromVolunteerFriends = true ;
                Intent poonamIntent = new Intent(FriendsOfSmartNaariVolunteersActivity.this, AboutMembersActivity.class);
                poonamIntent.putExtra(ConstantData.KEY_RECYCLER_POS, "0");
                startActivity(poonamIntent);
                break;
        }
    }
}
