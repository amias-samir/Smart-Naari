package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private WordsWithDetailsModel wordsWithDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_glossary_word_details);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        wordsWithDetailsModel = (WordsWithDetailsModel) data.getParcelable("wordsWithDetails");

        initToolbar();

        tvWordTitle.setText(wordsWithDetailsModel.getTitle().trim());

        setSpannableTextDescription();


        //tvWordDesc.setText(wordsWithDetailsModel.getDesc().trim());
    }
    

    private void setSpannableTextDescription() {
        String toBeSpannedText = wordsWithDetailsModel.getDesc().trim();

        SpannableString spannableString = new SpannableString(toBeSpannedText);


        String[] tester = getResources().getStringArray(R.array.checker_for_spannable_text);
        for (int i = 0; i < tester.length; i++) {
            int start = toBeSpannedText.indexOf(tester[i]);
            int end = start + tester[i].length();
            spannableString.setSpan(new OnSpannedTextClicked(tester[i]), start, end, 0);

            tvWordDesc.setText(spannableString);

        }
        tvWordDesc.setMovementMethod(new LinkMovementMethod());
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(wordsWithDetailsModel != null ? wordsWithDetailsModel.getTitle() : "Failed to load");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class OnSpannedTextClicked extends ClickableSpan {

        String selectedString;

        public OnSpannedTextClicked(String s) {
            selectedString = s;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "You selected: " + selectedString, Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(view.getContext(),WordsWithDetailsActivity.class));
        }
    }
}
