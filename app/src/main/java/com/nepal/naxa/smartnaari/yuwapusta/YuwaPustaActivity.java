package com.nepal.naxa.smartnaari.yuwapusta;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.LinePagerIndicatorDecoration;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YuwaPustaActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, YuwaQuestionAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.yuwa_pusta_iv_header)
    ImageView yuwaPustaIvHeader;
    @BindView(R.id.yuwa_pusta_act_tv_header)
    TextView yuwaPustaActTvHeader;
    @BindView(R.id.yuwa_pusta_act_rv_owlslist)
    RecyclerView yuwaPustaActRvOwlslist;
    @BindView(R.id.yuwa_pusta_act_rv_reviewslist)
    RecyclerView questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwa_pust);
        ButterKnife.bind(this);
        initToolbar();
        initHorizontalRecyclerView();
        initQuestionsRecyclerView();
    }


    private void initHorizontalRecyclerView() {
        List<Owl> owlslist = Owl.getOwlsList();
        RecyclerViewAdapter horizontalRecyclerViewAdapter = new RecyclerViewAdapter(owlslist);

        yuwaPustaActRvOwlslist.setAdapter(horizontalRecyclerViewAdapter);
        yuwaPustaActRvOwlslist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerViewAdapter.setOnItemClickListener(this);

        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(yuwaPustaActRvOwlslist);

        // pager indicator
        yuwaPustaActRvOwlslist.addItemDecoration(new RedLinePagerIndicatorDecoration());
        yuwaPustaActRvOwlslist.setNestedScrollingEnabled(false);
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onItemClick(View view, Owl owl) {

    }

    @Override
    public void onItemClick(View view, YuwaQuery yuwaQuery) {

    }
}
