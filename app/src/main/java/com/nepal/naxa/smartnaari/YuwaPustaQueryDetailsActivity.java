package com.nepal.naxa.smartnaari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YuwaPustaQueryDetailsActivity extends BaseActivity {

    @BindView(R.id.yuwa_pusta_query_details_tv_question)
    HtmlTextView tvQuestionDetails;
    @BindView(R.id.yuwa_pusta_query_details_tv_answer)
    HtmlTextView tvAnswerDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwa_pusta_query_details);
        ButterKnife.bind(this);

        initToolbar();

        Intent intent = getIntent();

//        String A = intent.getStringExtra("query");
//        String B = intent.getStringExtra("answer");

        Bundle data = getIntent().getExtras();
        YuwaQuestion yuwaQuestion = (YuwaQuestion) data.getParcelable("yuwaQuestions");


//        tvQuestionDetails.setHtml(intent.getStringExtra("query"));
//        tvAnswerDetails.setHtml(intent.getStringExtra("answer"));

        tvQuestionDetails.setHtml(yuwaQuestion.getQuestion());
        tvAnswerDetails.setHtml(yuwaQuestion.getAnswer());
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Yuwa Pusta");
        setSupportActionBar(toolbar);



        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
