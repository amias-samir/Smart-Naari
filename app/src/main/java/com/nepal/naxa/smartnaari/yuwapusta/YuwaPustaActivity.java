package com.nepal.naxa.smartnaari.yuwapusta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.network.service.DownloadResultReceiver;
import com.nepal.naxa.smartnaari.data.network.service.DownloadService;
import com.nepal.naxa.smartnaari.debug.AppLogger;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_ERROR;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_FINISHED;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_RUNNING;

public class YuwaPustaActivity extends BaseActivity implements RecyclerViewAdapter.OnItemClickListener, YuwaQuestionAdapter.OnItemClickListener {

    private static final String TAG = "YuwaPustaActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.yuwa_pusta_iv_header)
    ImageView yuwaPustaIvHeader;
    @BindView(R.id.yuwa_pusta_act_tv_header)
    TextView header;
    @BindView(R.id.yuwa_pusta_act_rv_owlslist)
    RecyclerView yuwaPustaActRvOwlslist;
    @BindView(R.id.yuwa_pusta_act_rv_reviewslist)
    RecyclerView questionList;

    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwa_pust);
        ButterKnife.bind(this);
        initToolbar();
        initHorizontalRecyclerView();

        initQuestionsRecyclerView();


        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        SpanUtils.setColor(header, "Yuwa Pusta", "Yuwa", color);

        //Swipe Refresh Action
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        // Setup refresh listener which triggers new data loading
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

//                        DownloadService downloadService = new DownloadService();
//                        downloadService.getYuwaPustaPosts();
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
//                        myUpdateOperation();

                        syncAllData();


                        initQuestionsRecyclerView();

                    }

                }
        );
        // Configure the refreshing colors
        swiperefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

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
        List<YuwaQuestion> yuwaQuestions = appDataManager.getAllYuwaQuestions();

        YuwaQuestionAdapter yuwaQuestionAdapter = new YuwaQuestionAdapter(yuwaQuestions);
        questionList.setAdapter(yuwaQuestionAdapter);

        questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        yuwaQuestionAdapter.setOnItemClickListener(this);

        questionList.setNestedScrollingEnabled(false);

        if (swiperefresh!= null && swiperefresh.isRefreshing()){
            swiperefresh.setRefreshing(false);
        }




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
    public void onItemClick(View view, Owl owl) {

    }

    @Override
    public void onItemClick(View view, YuwaQuestion yuwaQuestion) {

    }


    @OnClick(R.id.btn_ask_a_owl)
    public void toAskAOwnActivity() {

        Intent toAskOwlActivity = new Intent(this, AskOwlActivity.class);
        startActivity(toAskOwlActivity);

    }


    private void syncAllData() {


        DownloadResultReceiver mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(new DownloadResultReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                switch (resultCode) {
                    case STATUS_RUNNING:
                        showInfoToast("Syncing Data");
                        break;
                    case STATUS_ERROR:
                        break;
                    case STATUS_FINISHED:
                        showInfoToast("Successfully updated ");
                        AppLogger.d("Last Sync Date Time for Yuwa Pusta Posts is %s",appDataManager.getLastSyncDateTime(YuwaQuestion.class));
                        break;
                }
            }
        });


        Intent toDownloadService = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        toDownloadService.putExtra("receiver", mReceiver);

        this.startService(toDownloadService);
    }
}
