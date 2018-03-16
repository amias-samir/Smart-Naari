package com.nepal.naxa.smartnaari.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.XandYDetailsActivity;
import com.nepal.naxa.smartnaari.aboutboardmembers.AboutMembersActivity;
import com.nepal.naxa.smartnaari.aboutsmartnaari.AboutSmartNaariActivity;
import com.nepal.naxa.smartnaari.calendraevent.EventShowcaseActivity;
import com.nepal.naxa.smartnaari.celebratingprofessional.CelebratingProfessionalActivity;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.copyrightandprivacypolicy.PrivacyPolicyActivity;
import com.nepal.naxa.smartnaari.copyrightandprivacypolicy.SmartNariCopyrightActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.service.DownloadResultReceiver;
import com.nepal.naxa.smartnaari.data.network.service.DownloadService;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.GlossaryListActivity;
import com.nepal.naxa.smartnaari.dataongbv.DataOnGBVActivity;
import com.nepal.naxa.smartnaari.donate.DonateActivity;
import com.nepal.naxa.smartnaari.friendsofsmartnaari.FriendsOfSmartNaariActivity;
import com.nepal.naxa.smartnaari.friendsofsmartnaari.FriendsOfSmartNaariSmallBusinessActivity;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.homescreen.HorizontalRecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.LinePagerIndicatorDecoration;
import com.nepal.naxa.smartnaari.homescreen.RecyclerViewAdapter;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.masakchamchu.IAmAmazingActivity;
import com.nepal.naxa.smartnaari.masakchamchu.MaSakchamChuMainActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleProtectorActivity;
import com.nepal.naxa.smartnaari.passion_of_life.heteregenouscomplexrecycler.HotPotComplexRecyclerViewActivity;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.setingschange.SettingsChangeActivity;
import com.nepal.naxa.smartnaari.smartparent.SmartParentActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.tutorials.TutorialsActivity;
import com.nepal.naxa.smartnaari.userprofileupdate.UserProfileUpdateActivity;
import com.nepal.naxa.smartnaari.utils.date.NepaliDate;
import com.nepal.naxa.smartnaari.utils.date.NepaliDateException;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_ERROR;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_FINISHED;
import static com.nepal.naxa.smartnaari.data.network.service.DownloadService.STATUS_RUNNING;

public class BeautifulMainActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener, RecyclerViewAdapter.OnItemClickListener, HorizontalRecyclerViewAdapter.OnItemClickListener, View.OnClickListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    //    Facebook Page URL
    public static String FACEBOOK_URL = "https://www.facebook.com/naxa.np";
    public static String FACEBOOK_PAGE_ID = "naxa.np";


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

    @BindView(R.id.beautiful_main_tv_eng_date)
    TextView tvEngDate;

    @BindView(R.id.btn_nav_drawer_toggle)
    ImageButton btnNavDrawer;

    @BindView(R.id.beautiful_main_tv_nep_date)
    TextView tvNepaliDate;
    @BindView(R.id.main_imageview_placeholder_tap_it_stop_it)
    ImageView imageviewPlaceholderTapItStopIt;
    @BindView(R.id.main_imageview_placeholder2_tap_it_stop_it)
    ImageView imageviewPlaceholder2TapItStopIt;
    @BindView(R.id.toolbar_image_switcher_tap_it_stop_it)
    ViewSwitcher imageSwitcherTapItStopIt;
    @BindView(R.id.btn_tap_it_stop_it)
    Button btnTapItStopIt;


    ImageButton btnNavMessage, btnNavUserProfileUpdate;
    CircleImageView ivNavUserAvatar;
    TextView tvNavUserName ;


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
    private boolean stopShakeAnimate;


    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beutiful_main);

        sessionManager = new SessionManager(this);

        bindActivity();
        ButterKnife.bind(this);

        syncAllData();
        setDateTimeInUI();


        mAppBarLayout.addOnOffsetChangedListener(this);
        setupActionBar();
        setupDrawerLayout();


        Glide.with(this)
                .load(R.drawable.food_1).into(placeholder);
        Glide.with(this)
                .load(R.drawable.food_2).into(placeholder2);


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

    private void stopShakeAnimation() {
        stopShakeAnimate = true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        stopShakeAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  animateIfSpecialDay();
            }
        }, TimeUnit.SECONDS.toMillis(3));


    }

    private void animateIfSpecialDay() {
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        animShake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                if (stopShakeAnimate) {
                    return;
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ivLogo.startAnimation(animShake);
                    }
                }, TimeUnit.SECONDS.toMillis(2));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivLogo.startAnimation(animShake);

    }

    private void setDateTimeInUI() {

        try {
            tvEngDate.setText(NepaliDate.getCurrentEngDate());
            tvNepaliDate.setText(NepaliDate.getCurrentNepaliDate());
        } catch (NepaliDateException e) {
            e.printStackTrace();
            //todo log error from frabric
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
        View headerview = navigationView.getHeaderView(0);
//        navigationView.setOnClickListener(this);
        btnNavMessage = (ImageButton) headerview.findViewById(R.id.btn_nav_user_message);
        btnNavUserProfileUpdate = (ImageButton) headerview.findViewById(R.id.btn_nav_user_profile_update);
        ivNavUserAvatar = (CircleImageView) headerview.findViewById(R.id.iv_nav_user_avatar);
        tvNavUserName = (TextView) headerview.findViewById(R.id.tv_nav_user_name);

        tvNavUserName.setText(sessionManager.getUser().getUsername());
        ivNavUserAvatar.setImageResource(R.drawable.default_avatar);

        btnNavMessage.setOnClickListener(this);
        btnNavUserProfileUpdate.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
//                drawerLayout.closeDrawers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleNavigation(menuItem);
                    }
                }, 250);


                return true;
            }
        });
    }

    private void handleNavigation(MenuItem menuItem) {

        if (menuItem.getTitle().equals("My Circle")) {
//            Intent intent = new Intent(BeautifulMainActivity.this, MyCircleActivity.class);
//            startActivity(intent);
            Intent intent = new Intent(BeautifulMainActivity.this, MyCircleProtectorActivity.class);
            startActivity(intent);

        }

        if (menuItem.getTitle().equals("About Smart नारी")) {
            Intent intent = new Intent(BeautifulMainActivity.this, AboutSmartNaariActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Meet The Team")) {
            Intent intent = new Intent(BeautifulMainActivity.this, AboutMembersActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Glossary")) {
            Intent intent = new Intent(BeautifulMainActivity.this, GlossaryListActivity.class);
            startActivity(intent);
        }
        if (menuItem.getTitle().equals("Acknowledging Friends")) {
            Intent intent = new Intent(BeautifulMainActivity.this, FriendsOfSmartNaariActivity.class);
            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }

        if (menuItem.getTitle().equals("Celebrating Professionals")) {
            Intent intent = new Intent(BeautifulMainActivity.this, CelebratingProfessionalActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Data")) {
            Intent intent = new Intent(BeautifulMainActivity.this, DataOnGBVActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Donate")) {
            Intent intent = new Intent(BeautifulMainActivity.this, DonateActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Tutorials")) {
            Intent intent = new Intent(BeautifulMainActivity.this, TutorialsActivity.class);
            startActivity(intent);
        }

        if (menuItem.getTitle().equals("Privacy Policy and Terms")) {
            Intent intent = new Intent(BeautifulMainActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        }
        if (menuItem.getTitle().equals("Copyright")) {
            Intent intent = new Intent(BeautifulMainActivity.this, SmartNariCopyrightActivity.class);
            startActivity(intent);
        }
        if (menuItem.getTitle().equals("Disclaimer")) {
            showDisclaimerDialog("Disclaimer");
        }
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

        horizontalRecyclerViewAdapter.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_red, menu);

        MenuItem tapItStopIt = menu.findItem(R.id.item_call);
        MenuItem notification = menu.findItem(R.id.item_notification);


        tapItStopIt.setVisible(mIsTheTitleVisible);
        notification.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // drawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.item_call:
                Intent intent = new Intent(BeautifulMainActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                break;

            case R.id.item_share:
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, "url link ");
                textShareIntent.setType("text/plain");
                startActivity(Intent.createChooser(textShareIntent, "Share Smart नारि App with your Friends"));
                break;

            case R.id.item_like_us_on_facebook:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
                break;

            case R.id.item_setting_change:
                Intent settingsIntent = new Intent(BeautifulMainActivity.this, SettingsChangeActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.item_report_bug:
                Intent reportBugMailIntent = new Intent(Intent.ACTION_SENDTO);
                reportBugMailIntent.setType("text/plain");
                reportBugMailIntent.setData(Uri.parse("mailto:mail.naxa@gmail.com"));
                reportBugMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Smart नारी issue");
                startActivity(Intent.createChooser(reportBugMailIntent, "Report Bug"));
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

            ivLogo.setVisibility(View.VISIBLE);
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

            finishAffinity();
            System.exit(0);
//            finish();
            return;
        }

        showInfoToast(getString(R.string.app_exit_msg));
        timeStampWhenBackWasPressed = System.currentTimeMillis();
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(btnNavDrawer,ALPHA_ANIMATIONS_DURATION,View.INVISIBLE);


                mIsTheTitleVisible = true;
                invalidateOptionsMenu();
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(btnNavDrawer,ALPHA_ANIMATIONS_DURATION,View.VISIBLE);

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
                        //showInfoToast("Syncing Data");
                        break;
                    case STATUS_ERROR:
                        break;
                    case STATUS_FINISHED:
                        // AppLogger.d("Last Sync Date Time for Yuwa Pusta Posts is %s", appDataManager.getLastSyncDateTime(YuwaQuestion.class));
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
//        startActivity(new Intent(this, ComplexListActivity.class));
        startActivity(new Intent(this, HotPotComplexRecyclerViewActivity.class));
    }

    @OnClick(R.id.iv_logo)
    public void onHomeClicked() {
        if (stopShakeAnimate) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            // stopShakeAnimation();
            EventShowcaseActivity.start(BeautifulMainActivity.this);
        }

    }


    @OnClick(R.id.btn_nav_drawer_toggle)
    public void onNavDrawerToggleClicked() {
        if (stopShakeAnimate) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            // stopShakeAnimation();
            EventShowcaseActivity.start(BeautifulMainActivity.this);
        }

    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        Log.e("MainActivity", "onItemClick: " + viewModel.getText());

        if (viewModel.getText().equals("Report")) {
            Intent maChupBasdinaIntent = new Intent(this, MaChupBasdinaActivity.class);
            startActivity(maChupBasdinaIntent);
        } else if (viewModel.getText().equals("\t")) {
            startActivity(new Intent(this, ServicesActivity.class));
        } else if (viewModel.getText().equals("\t\t\t")) {
            startActivity(new Intent(this, IAmAmazingActivity.class));
        } else if (viewModel.getText().equals("\t\t")) {
            startActivity(new Intent(this, MaSakchamChuMainActivity.class));
        } else if (viewModel.getText().equals("Yuwa Pusta")) {
            startActivity(new Intent(this, YuwaPustaActivity.class));
        } else if ((viewModel.getText().equals("Smart Parenting"))) {

            startActivity(new Intent(this, SmartParentActivity.class));
        }

    }


    @OnClick({R.id.main_imageview_placeholder_tap_it_stop_it, R.id.main_imageview_placeholder2_tap_it_stop_it, R.id.btn_tap_it_stop_it})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_imageview_placeholder_tap_it_stop_it:

                break;
            case R.id.main_imageview_placeholder2_tap_it_stop_it:
                break;
            case R.id.btn_tap_it_stop_it:
                Intent intent = new Intent(BeautifulMainActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                break;
        }
    }


    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    @Override
    public void onHorizontalItemClick(View view, ViewModel viewModel) {

        String title = viewModel.getText();

        Intent intent = new Intent(BeautifulMainActivity.this, XandYDetailsActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);

//        if (title.equals("X's and Y's")) {
//
//        } else if (title.equals("What's the Difference")) {
//
//        } else if (title.equals("Crime and Punishment")) {
//
//        } else if (title.equals("Myth Busters")) {
//
//        }

//        showInfoToast(viewModel.getText());
    }


    public void showDisclaimerDialog(String title) {

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(this);
        showDialog.setContentView(R.layout.disclaimer_dialog_layout);

//         initialize
        TextView tvDisclaimerDetails = (TextView) showDialog.findViewById(R.id.dialog_text_details);
        Button btnClose = (Button) showDialog.findViewById(R.id.btn_close_dialog);

        tvDisclaimerDetails.setText(R.string.disclaimer_details);

        showDialog.setTitle(title);
        showDialog.getActionBar();
        showDialog.show();
        showDialog.getWindow().setLayout((width), LinearLayout.LayoutParams.WRAP_CONTENT);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_nav_user_message:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:info@smartnaari.org"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "My Feedback To Smart नारी");
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;

            case R.id.btn_nav_user_profile_update:
                Intent userProfileIntent = new Intent(BeautifulMainActivity.this, UserProfileUpdateActivity.class);
                startActivity(userProfileIntent);
                break;

            default:
        }
    }
}
