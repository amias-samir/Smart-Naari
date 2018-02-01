package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataGlossaryWordDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_word_title)
    TextView tvWordTitle;
    @BindView(R.id.tv_word_desc)
    TextView tvWordDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_glossary_word_details);
        ButterKnife.bind(this);

        initToolbar();

        Intent intent = getIntent();

//        String A = intent.getStringExtra("query");
//        String B = intent.getStringExtra("answer");

        Bundle data = getIntent().getExtras();
        WordsWithDetailsModel wordsWithDetailsModel = (WordsWithDetailsModel) data.getParcelable("wordsWithDetails");


//        tvQuestionDetails.setHtml(intent.getStringExtra("query"));
//        tvAnswerDetails.setHtml(intent.getStringExtra("answer"));

        tvWordTitle.setText(wordsWithDetailsModel.getTitle().trim());
        tvWordDesc.setText(wordsWithDetailsModel.getDesc().trim());
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
