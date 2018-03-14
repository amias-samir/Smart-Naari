package com.nepal.naxa.smartnaari.aboutsmartnaari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutSmartNaariActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_smart_naari);
        ButterKnife.bind(this);


        initToolbar();

        stringToHTMLParser();

    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About Smart नारी");
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
                Intent intent = new Intent(AboutSmartNaariActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void stringToHTMLParser (){
        // get our html content
        String htmlAsString = getString(R.string.smart_naari_about_app_details);      // used by WebView
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView

        // set the html content on a TextView
//        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(htmlAsSpanned);
    }
}
