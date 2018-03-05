package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data_glossary.JSONLoadImpl;
import com.nepal.naxa.smartnaari.utils.TextViewUtils;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

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
        tvWordDesc.setText(wordsWithDetailsModel.getDesc());

        JSONLoadImpl
                .cacheGlossaryObj()
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .flatMap(new Function<WordsWithDetailsModel, Observable<String>>() {
                    @Override
                    public Observable<String> apply(WordsWithDetailsModel wordsWithDetailsModel) throws Exception {
                        return Observable.just(wordsWithDetailsModel.getTitle());
                    }
                })
                .toList()
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        //TextViewUtils.highlightWordToBlue(strings, tvWordDesc);

                        TextViewUtils.linkWordsToGlossary(strings, tvWordDesc);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(wordsWithDetailsModel != null ? wordsWithDetailsModel.getTitle() : "Failed to load");
        toolbar.setTitle("");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onBackPressed();
                return true;
            }
        });
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

        @Override
    public void onBackPressed() {
        // close view on back button pressed
        super.onBackPressed();
    }

}
