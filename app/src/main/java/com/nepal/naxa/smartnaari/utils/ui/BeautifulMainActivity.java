package com.nepal.naxa.smartnaari.utils.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.nepal.naxa.smartnaari.data.local.SessionManager;

import com.nepal.naxa.smartnaari.data.network.service.DownloadResultReceiver;
import com.nepal.naxa.smartnaari.data.network.service.DownloadService;
import com.nepal.naxa.smartnaari.homescreen.ArrowSliderView;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.homescreen.HorizontalRecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.LinePagerIndicatorDecoration;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.homescreen.RecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.masakchamchu.IAmAmazingActivity;
import com.nepal.naxa.smartnaari.masakchamchu.MaSakchamChuMainActivity;

import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;

import com.nepal.naxa.smartnaari.passion_of_life.ComplexListActivity;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.smartparent.SmartParentActivity;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.iv_logo)
    CircleImageView ivLogo;
    @BindView(R.id.main_imageview_placeholder)
    ImageView placeholder;
    @BindView(R.id.main_imageview_placeholder2)
    ImageView placeholder2;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


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


    ImageView image1, image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beutiful_main);

        bindActivity();
        ButterKnife.bind(this);
        syncAllData();


        Glide.with(this)
                .load(R.drawable.food_1).into(placeholder);
        Glide.with(this)
                .load(R.drawable.food_2).into(placeholder2);


        mAppBarLayout.addOnOffsetChangedListener(this);
        setupActionBar();
        setupDrawerLayout();


        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        initGridRecyclerView();
        setRecyclerAdapter(recyclerView);
        initHorizontalRecyclerView();
        scrollToolBarImages();


        boolean isFirstTimeLoad = new SessionManager(getApplicationContext()).isFirstTimeLoad();
        if (isFirstTimeLoad) {
            //drawerLayout.openDrawer(GravityCompat.START);
        }


    }

    public int getImage(String imageName) {

        return this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
    }

    private void setupActionBar() {
        //  mToolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_ic_menuitem_option);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setupDrawerLayout() {

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                if (menuItem.getTitle().equals("My Circle")) {
                    Intent intent = new Intent(BeautifulMainActivity.this, MyCircleActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }


    public void scrollToolBarImages() {

        final int scrollSpeed = 3000;


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                toolbarImageSwitcher.showNext();
                handler.postDelayed(this, scrollSpeed);

            }
        };
        handler.postDelayed(runnable, scrollSpeed);
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
        getMenuInflater().inflate(R.menu.menu_toolbar_red, menu);

        MenuItem tapItStopIt = menu.findItem(R.id.item_call);
        MenuItem notification = menu.findItem(R.id.item_notification);


        tapItStopIt.setVisible(mIsTheTitleVisible);
        notification.setVisible(mIsTheTitleVisible);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);

        boolean showToolbarIcons = shouldShowToolbarItems(percentage);
        if (showToolbarIcons) {
            ivLogo.setVisibility(View.VISIBLE);
        } else {
            ivLogo.setVisibility(View.VISIBLE);
        }
    }

    private boolean shouldShowToolbarItems(float offsetPrecentage) {
        return offsetPrecentage != 1.0f;
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
                invalidateOptionsMenu();
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                invalidateOptionsMenu();
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


        Intent toDownloadService = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        toDownloadService.putExtra("receiver", mReceiver);

        this.startService(toDownloadService);
    }

    @OnClick(R.id.btn_open_hotpot)
    public void toHotPotOfPassion() {
        startActivity(new Intent(this, ComplexListActivity.class));
    }

    @OnClick(R.id.iv_logo)
    public void onHomeClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        Log.e("MainActivity", "onItemClick: " + viewModel.getText());

        if (viewModel.getText().equals("Report a case")) {
            Intent maChupBasdinaIntent = new Intent(this, MaChupBasdinaActivity.class);
            startActivity(maChupBasdinaIntent);
        } else if (viewModel.getText().equals("\t")) {
            startActivity(new Intent(this, ServicesActivity.class));
        } else if (viewModel.getText().equals("\t\t\t")) {
            startActivity(new Intent(this, IAmAmazingActivity.class));
        } else if (viewModel.getText().equals("\t\t")) {
            startActivity(new Intent(this, MaSakchamChuMainActivity.class));
        } else if (viewModel.getText().equals("Yuwa पुस्ता")) {
            startActivity(new Intent(this, YuwaPustaActivity.class));
        } else if ((viewModel.getText().equals("Smart Parenting"))) {

            startActivity(new Intent(this, SmartParentActivity.class));
        }

    }
}
