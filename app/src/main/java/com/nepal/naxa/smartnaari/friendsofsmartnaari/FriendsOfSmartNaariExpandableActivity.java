package com.nepal.naxa.smartnaari.friendsofsmartnaari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsOfSmartNaariExpandableActivity extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    List<String> technicalSupport = new ArrayList<String>();
    List<String> designResearch = new ArrayList<String>();
    List<String> reportingMediaHouse = new ArrayList<String>();
    List<String> donation = new ArrayList<String>();
    List<String> safetyOfClients = new ArrayList<String>();
    List<String> hostAtFractionOfCost = new ArrayList<String>();
    List<String> NGO_INGO = new ArrayList<String>();
    List<String> linkPhotosVideosBlog = new ArrayList<String>();
    List<String> volunteers = new ArrayList<String>();

    @BindView(R.id.lvExp)
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_of_smart_naari_expandable);
        ButterKnife.bind(this);

        initToolbar();

        initFriendExpListView();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Acknowledging Friends");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initFriendExpListView(){

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.item_call:
                Intent intent = new Intent(FriendsOfSmartNaariExpandableActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("For the technical support and continuing to power this App");
        listDataHeader.add("For the designs and market research");
        listDataHeader.add("For reporting on the activities of this App as a media house");
        listDataHeader.add("For the donations and grants that allows our clients to use the App for free");
        listDataHeader.add("For the in-kind support so that safety of our joint clients is enhanced");
        listDataHeader.add("For helping us to host different events at a fraction of your cost");
        listDataHeader.add("For disseminating the information contained in this App and encouraging its use");
        listDataHeader.add("For allowing us to utilise your links photos, videos, blogs and much more");
        listDataHeader.add("Last but not least, for your commitment to volunteerism, giving the people of Nepal your valuable time and expertise");


        technicalSupport.add("Naxa Private Ltd ");
        designResearch.add("Ark Ventures");
        donation.add("Bank");
        safetyOfClients.add("NCell");
        hostAtFractionOfCost.add("Embassy Restaurant");
        NGO_INGO.add("NGOs");

        linkPhotosVideosBlog.add("Data Nepal");
        linkPhotosVideosBlog.add("Nepal Monitor");

        volunteers.add("Adrian White");
        volunteers.add("Paige Sinkler");
        volunteers.add("Sadhana Adhikari");
        volunteers.add("Rachel Ward ");


        listDataChild.put(listDataHeader.get(0), technicalSupport); // Header, Child data
        listDataChild.put(listDataHeader.get(1), designResearch);
        listDataChild.put(listDataHeader.get(2), reportingMediaHouse);
        listDataChild.put(listDataHeader.get(3), donation);
        listDataChild.put(listDataHeader.get(4), safetyOfClients); // Header, Child data
        listDataChild.put(listDataHeader.get(5), hostAtFractionOfCost);
        listDataChild.put(listDataHeader.get(6), NGO_INGO);
        listDataChild.put(listDataHeader.get(7), linkPhotosVideosBlog);
        listDataChild.put(listDataHeader.get(8), volunteers);


    }
}
