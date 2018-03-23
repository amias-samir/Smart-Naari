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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.YuwaPustaQueryDetailsActivity;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.network.OwlData;
import com.nepal.naxa.smartnaari.data.network.service.DownloadResultReceiver;
import com.nepal.naxa.smartnaari.data.network.service.DownloadService;
import com.nepal.naxa.smartnaari.debug.AppLogger;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_ERROR;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_FINISHED;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_RUNNING;


public class YuwaPustaActivity extends BaseActivity {

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
    @BindView(R.id.btn_load_more)
    Button btnLoadMore;

    private int totalQuerySize, pageCounter = 1;

    private SwipeRefreshLayout swipeContainer;

    List<YuwaQuestion> yuwaQuestions = new ArrayList<YuwaQuestion>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwa_pust);
        ButterKnife.bind(this);
        initToolbar();

        if (ConstantData.isFromAskAnOwl) {
            syncAllData();

        } else {
            initHorizontalRecyclerView();
            initQuestionsRecyclerView(1);
        }


        totalQuerySize = appDataManager.getQeryrListSizeFr0mDatabase();

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        SpanUtils.setColor(header, "Yuwa Pusta", "Yuwa", color);

        //Swipe Refresh Action
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkConnected()){
                    syncAllData();
                }else {
                    swipeContainer.setRefreshing(false);
                    showErrorToast("No Internet Conection");
                    return;
                }


            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        questionList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
//                    Drawer.closeDrawers();
                    int position = recyclerView.getChildPosition(child);

                    Intent intent = new Intent(YuwaPustaActivity.this, YuwaPustaQueryDetailsActivity.class);
                    intent.putExtra("yuwaQuestions", yuwaQuestions.get(position));
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }


    private void initHorizontalRecyclerView() {
        List<OwlData> owlslist = appDataManager.getOwls();
        RecyclerViewAdapter horizontalRecyclerViewAdapter = new RecyclerViewAdapter(owlslist);

        yuwaPustaActRvOwlslist.setAdapter(horizontalRecyclerViewAdapter);
        yuwaPustaActRvOwlslist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, OwlData owl) {

            }
        });


        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(yuwaPustaActRvOwlslist);

        // pager indicator
        yuwaPustaActRvOwlslist.addItemDecoration(new RedLinePagerIndicatorDecoration());
        yuwaPustaActRvOwlslist.setNestedScrollingEnabled(false);

    }

    private void initQuestionsRecyclerView(int page) {
        yuwaQuestions.addAll(appDataManager.getAllYuwaQuestions(page));

        final YuwaQuestionAdapter yuwaQuestionAdapter = new YuwaQuestionAdapter(yuwaQuestions);
        yuwaQuestionAdapter.notifyDataSetChanged();
        questionList.setAdapter(yuwaQuestionAdapter);
        questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        questionList.setNestedScrollingEnabled(false);

        if (swipeContainer != null && swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
        if (ConstantData.isFromAskAnOwl) {
//            questionList.smoothScrollToPosition(yuwaQuestions.size()-1);
            ConstantData.isFromAskAnOwl = false;
        }


    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Yuwa Pusta");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.black);
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

            case R.id.item_call:
                Intent intent = new Intent(YuwaPustaActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                        swipeContainer.setRefreshing(true);
                        showInfoToast("Syncing Data");
                        break;
                    case STATUS_ERROR:
                        swipeContainer.setRefreshing(false);
                        showErrorToast("Unable to Sync Data, Please try again later");
                        break;
                    case STATUS_FINISHED:

                        pageCounter = 1 ;
                        yuwaQuestions.clear();
                        initQuestionsRecyclerView(1);
                        AppLogger.d("Last Sync Date Time for Yuwa Pusta Posts is %s", appDataManager.getLastSyncDateTime(YuwaQuestion.class));
                        break;

                }
            }
        });


        Intent toDownloadService = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        toDownloadService.putExtra("receiver", mReceiver);

        this.startService(toDownloadService);
    }

    @OnClick(R.id.btn_load_more)
    public void onViewClicked() {


        if (totalQuerySize == questionList.getChildCount()) {
            showInfoToast("No new data");
        } else {
            loadNextDataFromApi(pageCounter + 1);
            pageCounter++;
        }

    }

    private void loadNextDataFromApi(int page) {

        Log.d(TAG, "loadNextDataFromApi: " + page);
//        yuwaQuestions.clear();
        swipeContainer.setRefreshing(true);
        initQuestionsRecyclerView(page);
    }

}
