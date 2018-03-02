package com.nepal.naxa.smartnaari.dataongbv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefaultWebpageLoadActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;
    private String postUrl = "";
    private String toolbarTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_default_webpage_load);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        toolbarTitle = intent.getStringExtra("toolbar_title");
        postUrl = intent.getStringExtra("url");



        initToolbar();
        loadWebURL();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

//    @SuppressLint("JavascriptInterface")
    public void loadWebURL (){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If Android 6.0+ i must add support for Third Party Cookies
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
//        webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUserAgentString("Android WebView");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(this, "Android");// add this only if required. Vulnerable to mitm attacks.
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new myWebClient());// use as above to handle ssl errors
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(postUrl);
        webView.setHorizontalScrollBarEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.item_call:
                Intent intent = new Intent(DefaultWebpageLoadActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
