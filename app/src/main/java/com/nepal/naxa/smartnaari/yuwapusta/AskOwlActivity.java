package com.nepal.naxa.smartnaari.yuwapusta;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AskOwlActivity extends AppCompatActivity implements YuwaQuestionAdapter.OnItemClickListener {




    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_user_birth_place_input_id)
    Spinner spinnerChooseOwn;
    @BindView(R.id.btn_ask_a_owl)
    Button btnAskAOwl;
    @BindView(R.id.yuwa_pusta_act_rv_questionlist)
    RecyclerView questionList;

    @BindView(R.id.yuwa_pusta_act_tv_header)
    TextView tvHeader;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_owl);
        ButterKnife.bind(this);

        initToolbar();
        initQuestionsRecyclerView();


        int color = ContextCompat.getColor(getApplicationContext(),R.color.colorAccent);
        SpanUtils.setColor(tvHeader,"Yuwa Pusta","Yuwa",color);

    }


    private void initQuestionsRecyclerView() {
        List<YuwaQuery> yuwaQueries = YuwaQuery.getDemoQueries();

        YuwaQuestionAdapter yuwaQuestionAdapter = new YuwaQuestionAdapter(yuwaQueries);
        questionList.setAdapter(yuwaQuestionAdapter);

        questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        yuwaQuestionAdapter.setOnItemClickListener(this);

        questionList.setNestedScrollingEnabled(false);


    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
    public void onItemClick(View view, YuwaQuery yuwaQuery) {
        Toast.makeText(this, "SOmething", Toast.LENGTH_SHORT).show();
    }
}
