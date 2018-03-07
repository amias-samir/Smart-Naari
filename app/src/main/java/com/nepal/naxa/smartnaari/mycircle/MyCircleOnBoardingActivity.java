package com.nepal.naxa.smartnaari.mycircle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.LayoutParams;

import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.application.SmartNaari;
import com.nepal.naxa.smartnaari.mycircle.common.BaseActivity;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;
import com.nepal.naxa.smartnaari.utils.ui.DialogFactory;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import java.util.concurrent.TimeUnit;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class MyCircleOnBoardingActivity extends BaseActivity {

    private static final int CODE_REQUEST_PERMISSIONS = 777;
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 888;
    private CardView swipeCard, setupCard;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout layoutVideo, layoutConfigComplete, layoutThankYou;
    private ImageButton btnCloseVideoLayout;
    private Button btnOpenApp;
    private VerticalStepperFormLayout stepper;
    private Snackbar snackbar;
    private EasyVideoPlayer player;


    @RequiresApi(Build.VERSION_CODES.M)
    public static void start(Context context) {
        Intent intent = new Intent(context, MyCircleOnBoardingActivity.class);
        context.startActivity(intent);
    }

    public static void startSafe(Context context,boolean coldStart) {
        Boolean hasAllRequredPermission = false;
        if (hasAllRequredPermission) {
            //not implemeted


        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            showMyCircleActivatedToast();
            context.startService(new Intent(context, PowerButtonService.class));
            if(coldStart)context.startActivity(new Intent(context,BeautifulMainActivity.class));
        } else {
            start(context);
        }
    }

    private static void showMyCircleActivatedToast() {
        new ToastUtils().info(SmartNaari.getAppContext(), "My Circle Activated");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle_on_boaring);
        bindUI();
        setupSwipeCard();
        setupVideo();

        btnCloseVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoLayoutVisiblity(false);
            }
        });

        btnOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCircleOnBoardingActivity.this, BeautifulMainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void bindUI() {
        swipeCard = (CardView) findViewById(R.id.swype_card);
        setupCard = (CardView) findViewById(R.id.card_setup);
        stepper = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_my_circle_on_boarding);
        layoutVideo = (RelativeLayout) findViewById(R.id.layout_video);
        layoutConfigComplete = (RelativeLayout) findViewById(R.id.layout_config_complete);
        btnCloseVideoLayout = (ImageButton) findViewById(R.id.btn_close_video_layout);
        btnOpenApp = (Button) findViewById(R.id.btn_open_app);
        layoutThankYou = (RelativeLayout) findViewById(R.id.layout_thank_you);
        player = (EasyVideoPlayer) findViewById(R.id.player);
    }

    private void videoLayoutVisiblity(boolean show) {
        layoutVideo.setVisibility(show ? View.VISIBLE : View.GONE);
        setupCard.setVisibility(show ? View.GONE : View.VISIBLE);

        if (!show) {
            player.pause();
        }
    }

    private void configCompleteLayoutVisiblity(boolean show) {
        layoutConfigComplete.setVisibility(show ? View.VISIBLE : View.GONE);
        setupCard.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void showThankYouView() {
        layoutThankYou.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutThankYou.setVisibility(View.GONE);
            }
        }, TimeUnit.SECONDS.toMillis(3));
    }

    private void setupSwipeCard() {

        final SwipeDismissBehavior swipe = new SwipeDismissBehavior();
        final LayoutParams coordinatorParams = (LayoutParams) swipeCard.getLayoutParams();
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
        swipe.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                showSetupCard();
                showDelayedVideoSnackBar(TimeUnit.SECONDS.toMillis(1));
                coordinatorParams.setBehavior(null);
            }

            @Override
            public void onDragStateChanged(int state) {
            }
        });


        coordinatorParams.setBehavior(swipe);
    }

    private void showDelayedVideoSnackBar(long delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showVideoSnack();
            }
        }, delayTime);
    }


    private void showSetupCard() {
        setupCard.setVisibility(View.VISIBLE);

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        String[] mySteps = {"Hello",
                "An Explanation",
                "Allow SMS And Location Access",
                "Activate Shake SMS",
                "Allow " + getString(R.string.app_name) + " to overlay over other apps"};

        String overlayInstruction = "\n\nPress continue and perform the following steps\n\n" +
                "1. Turn on \'Permit drawing over other app\' item " +
                "\n\n" +
                "2. Press back button to return to Setup Screen";


        String[] subtitles = {"Press continue when you are ready",
                "In Case of Emergency, Smart नारी needs access to SMS and Location Services on your phone to send location data to the people in your MyCircle.",
                "",
                "You can hold the power button and shake however you need to allow smart नारी to overlay over other apps and notify your 'My Circle'" +
                        "\n" + overlayInstruction,};


        VerticalStepperFormLayout.Builder.newInstance(stepper, mySteps, new VerticalStepperForm() {
            @Override
            public View createStepContentView(int stepNumber) {
                return createDummyView();
            }

            @Override
            public void onStepOpening(int stepNumber) {
                switch (stepNumber) {
                    case 2:
                        showPermissionExplanationDialog();
                        break;
                    case 4:
                        @SuppressLint("InlinedApi")
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                        break;
                    case 5:
                        snackbar.dismiss();
                        break;
                    default:
                        stepper.setActiveStepAsCompleted();

                }
            }

            @Override
            public void sendData() {
                showMyCircleActivatedToast();
                configCompleteLayoutVisiblity(true);
                startService(new Intent(MyCircleOnBoardingActivity.this, PowerButtonService.class));

            }
        }, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .stepsSubtitles(subtitles)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .displayBottomNavigation(false)
                .init();
    }


    private void showPermissionExplanationDialog() {

        if (hasPermission(Manifest.permission.SEND_SMS) && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            stepper.setActiveStepAsCompleted();
            return;
        }

        DialogFactory.createActionDialog(MyCircleOnBoardingActivity.this, "Allow Permission", "Press allow on upcoming dialogs ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handlePermissionOpen();
                    }
                }).show();
    }


    private void setupVideo() {

        player.setCallback(new EasyVideoCallback() {
            @Override
            public void onStarted(EasyVideoPlayer player) {

            }

            @Override
            public void onPaused(EasyVideoPlayer player) {

            }

            @Override
            public void onPreparing(EasyVideoPlayer player) {

            }

            @Override
            public void onPrepared(EasyVideoPlayer player) {

            }

            @Override
            public void onBuffering(int percent) {

            }

            @Override
            public void onError(EasyVideoPlayer player, Exception e) {

            }

            @Override
            public void onCompletion(EasyVideoPlayer player) {

            }

            @Override
            public void onRetry(EasyVideoPlayer player, Uri source) {

            }

            @Override
            public void onSubmit(EasyVideoPlayer player, Uri source) {

            }
        });
        player.setSource(Uri.parse("http://naxa.com.np/smartnaari/android/smart_naari.mp4"));
    }

    private View createDummyView() {
        return new LinearLayout(getApplicationContext());
    }

    private void handlePermissionOpen() {

        String[] requiredPermissions = new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WAKE_LOCK};

        if (hasPermission(Manifest.permission.SEND_SMS) && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            stepper.setActiveStepAsCompleted();
        } else {
            requestPermissionsSafely(requiredPermissions, CODE_REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSIONS:

                if (allPermissionGranted(grantResults)) {

                    stepper.setStepAsCompleted(2);
                    stepper.setStepSubtitle(2, "This step has been completed. Press Continue");
                    stepper.goToNextStep();
                } else {
                    stepper.setActiveStepAsUncompleted("Sorry, cannot continue when permission is denied");
                    stepper.goToPreviousStep();
                }
                break;
        }
    }


    private boolean allPermissionGranted(int[] grantResults) {

//        for (int grantResult : grantResults) {
//            if (grantResult == PackageManager.PERMISSION_DENIED) return false;
//        }
//     return true;

        return hasPermission(Manifest.permission.SEND_SMS) && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                stepper.setActiveStepAsCompleted();
//              stepper.setStepSubtitle(4, "This step has been completed. Press Continue");
                stepper.goToNextStep();
            } else {
                stepper.setActiveStepAsUncompleted("Sorry, cannot continue when permission is denied");
                stepper.goToPreviousStep();
            }
        }
    }

    private void showVideoSnack() {


        snackbar = Snackbar.make(coordinatorLayout, "Confused? Or Want to know more? Watch this video", Snackbar.LENGTH_INDEFINITE);
        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

        snackbar.setAction("Play", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoLayoutVisiblity(true);
            }
        }).show();
    }
}
