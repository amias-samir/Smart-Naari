package com.nepal.naxa.smartnaari.utils.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.nepal.naxa.smartnaari.BaseActivity;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.service.DownloadResultReceiver;
import com.nepal.naxa.smartnaari.data.network.service.DownloadService;
import com.nepal.naxa.smartnaari.homescreen.ArrowSliderView;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.homescreen.HorizontalRecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.LinePagerIndicatorDecoration;
import com.nepal.naxa.smartnaari.homescreen.RecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.masakchamchu.IAmAmazingActivity;
import com.nepal.naxa.smartnaari.masakchamchu.MaSakchamChuMainActivity;
import com.nepal.naxa.smartnaari.passion_of_life.ComplexListActivity;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.smartparent.SmartParentActivity;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_ERROR;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_FINISHED;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_RUNNING;

public class BeautifulMainActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener, RecyclerViewAdapter.OnItemClickListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    @BindView(R.id.toolbar_image_switcher)
    ViewSwitcher toolbarImageSwitcher;
    @BindView(R.id.main_act_recycler_hori)
    RecyclerView horizontalRecyclerView;


    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    SliderLayout slider;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    private long timeStampWhenBackWasPressed;


    ImageView image1,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beutiful_main);

        bindActivity();
        ButterKnife.bind(this);
        syncAllData();

        image1=(ImageView) findViewById(R.id.main_imageview_placeholder);
        image2=(ImageView) findViewById(R.id.main_imageview_placeholder2);

        Glide.with(this)
                .load(R.drawable.food_1).into(image1);
        Glide.with(this)
                .load(R.drawable.food_2).into(image2);

        mAppBarLayout.addOnOffsetChangedListener(this);

        //  mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        initGridRecyclerView();
        setRecyclerAdapter(recyclerView);
        initHorizontalRecyclerView();
        scrollToolBarImages();

        ;
    }

    public void scrollToolBarImages() {
        final int speedScroll = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                toolbarImageSwitcher.showNext();
                handler.postDelayed(this, speedScroll);

            }
        };
        handler.postDelayed(runnable, speedScroll);
    }


    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        slider = (SliderLayout) findViewById(R.id.slider);
    }


    private void initGridRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_large);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        recyclerView.setNestedScrollingEnabled(false);


    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {

        List<ViewModel> items = ViewModel.getGridItems();
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initHorizontalRecyclerView() {
        List<ViewModel> framelist = ViewModel.getHorizontalViewItems();
        HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(framelist);

        horizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));


        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(horizontalRecyclerView);

        // pager indicator
        horizontalRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        horizontalRecyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //   getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    @Override
    public void onBackPressed() {
        doubleTapToExit();
    }

    private void doubleTapToExit() {
        long timeRangeForDoubleTap = 3000;
        long totalAcceptedDelay = timeStampWhenBackWasPressed + timeRangeForDoubleTap;
        long currentTime = System.currentTimeMillis();
        if (totalAcceptedDelay > currentTime) {
            finish();
            return;
        }

        showInfoToast(getString(R.string.app_exit_msg));
        timeStampWhenBackWasPressed = System.currentTimeMillis();
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void setupTopSlider(SliderLayout sliderLayout) {

        HashMap<String, Integer> file_maps = getTopSliderImages();

        for (String name : file_maps.keySet()) {
            ArrowSliderView textSliderView = new ArrowSliderView(this);
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            startActivity(new Intent(BeautifulMainActivity.this, ComplexListActivity.class));
                        }
                    });


            sliderLayout.stopAutoCycle();

            sliderLayout.addSlider(textSliderView);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setDuration(10000);


        }
    }

    private HashMap<String, Integer> getTopSliderImages() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("First", R.drawable.food_1);
        file_maps.put("Second", R.drawable.food_2);

        return file_maps;
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
                        break;
                }
            }
        });

        Log.d("DownloadService", "Hello");

        Intent toDownloadService = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        toDownloadService.putExtra("receiver", mReceiver);

        this.startService(toDownloadService);
    }


    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        Log.e("MainActivity", "onItemClick: " + viewModel.getText());

        if (viewModel.getText().equals("Report a case")) {
            Intent maChupBasdinaIntent = new Intent(this, MaChupBasdinaActivity.class);
            startActivity(maChupBasdinaIntent);
        } else if (viewModel.getText().equals("Time is of the essence")) {
            startActivity(new Intent(this, ServicesActivity.class));
        } else if (viewModel.getText().equals("I am Amazing")) {
            startActivity(new Intent(this, IAmAmazingActivity.class));
        } else if (viewModel.getText().equals("Saksham Chu")) {
            startActivity(new Intent(this, MaSakchamChuMainActivity.class));
        } else if (viewModel.getText().equals("Yuwa पुस्ता")) {
            startActivity(new Intent(this, YuwaPustaActivity.class));
        } else if ((viewModel.getText().equals("Smart Parenting"))) {

            startActivity(new Intent(this, SmartParentActivity.class));
        }

    }
}
