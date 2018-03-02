package com.nepal.naxa.smartnaari;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Majestic on 3/2/2018.
 */

public class YoutubeWebViewActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.image_btn_close)
    ImageButton imageBtnClose;
    @BindView(R.id.wv_youtube)
    WebView wvYoutube;

    String URL = "https://www.youtube.com/embed/47yJ2XCRLZs";


//    public class startYoutubeWebViewActivity(Context context, String URL) {
//        startActivity(new Intent(context, YoutubeWebViewActivity.class));
//        getIntent().putExtra("url", URL);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_web_view);
        ButterKnife.bind(this);

        URL = getIntent().getStringExtra("url");

        setUpWebView();

        imageBtnClose.setOnClickListener(this);

    }

    private void setUpWebView() {
        wvYoutube.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wvYoutube.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        wvYoutube.loadUrl(URL);

    }

    @Override
    public void onClick(View v) {

    }
}
