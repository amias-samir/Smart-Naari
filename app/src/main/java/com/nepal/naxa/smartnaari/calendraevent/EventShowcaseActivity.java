package com.nepal.naxa.smartnaari.calendraevent;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.date.NepaliDate;
import com.nepal.naxa.smartnaari.utils.date.NepaliDateException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;


public final class EventShowcaseActivity extends AppCompatActivity {


    private TextView tvNepaliDate;
    private TextView tvEnglishDate;
    private TextView tvSummary;
    private ImageView ivPhoto;
    private AnimationDrawable animationDrawable;
    private KonfettiView konfettiView;


    public static void start(Context context) {
        Intent intent = new Intent(context, EventShowcaseActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        bindUI();
        //resizeWindow();
        //setupFirework();
        setupGradientAnimation();

        try {
            tvNepaliDate.setText(NepaliDate.getCurrentNepaliDate());
        } catch (NepaliDateException e) {
            e.printStackTrace();
        }
        tvEnglishDate.setText(NepaliDate.getCurrentEngDate());
        tvSummary.setText(getString(R.string.default_calendra_event_msg,
                "Sambidan Diwas", getString(R.string.app_name)));

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                showComponents();
                setConfetti();
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    private void bindUI() {
        tvNepaliDate = (TextView) findViewById(R.id.title);
        tvEnglishDate = (TextView) findViewById(R.id.english_date);
        tvSummary = (TextView) findViewById(R.id.description);
        ivPhoto = (ImageView) findViewById(R.id.backgroundImage);
        konfettiView = (KonfettiView) findViewById(R.id.viewKonfetti);

    }

    private void setConfetti() {

        konfettiView.build()

                .addColors(Color.YELLOW, Color.GREEN, Color.BLUE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 4f)
                .setFadeOutEnabled(true)
                .setTimeToLive(TimeUnit.DAYS.toMillis(1))
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(40, TimeUnit.SECONDS.toMillis(2));
    }

    private void burstConfetti() {
        DisplayMetrics displaymatrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymatrics);
        Random R = new Random();
        float dx = R.nextFloat() * displaymatrics.widthPixels;
        float dy = R.nextFloat() * displaymatrics.heightPixels;


        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.BLUE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 4f)
                .setFadeOutEnabled(true)
                .setTimeToLive(TimeUnit.DAYS.toMillis(1))
                .addShapes(Shape.RECT, Shape.CIRCLE)
                // .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .setPosition(dx, dx, dy, dy).burst(100);
        //.stream(40, TimeUnit.MINUTES.toMillis(3));
    }

    private void resizeWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // getWindow().setLayout((int) (width * .8), (int) (height * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        //hideComponents();

        EventShowcaseActivity.super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showComponents() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((Context) this, R.layout.detail);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator((TimeInterpolator) (new AnticipateOvershootInterpolator(1.0F)));
        transition.setDuration(1200L);
        TransitionManager.beginDelayedTransition((ConstraintLayout) findViewById(R.id.constraint), (Transition) transition);
        constraintSet.applyTo((ConstraintLayout) this.findViewById(R.id.constraint));

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideComponents() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((Context) this, R.id.constraint);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator((TimeInterpolator) (new AnticipateOvershootInterpolator(1.0F)));
        transition.setDuration(1200L);
        TransitionManager.beginDelayedTransition((ConstraintLayout) findViewById(R.id.constraint), (Transition) transition);
        constraintSet.applyTo((ConstraintLayout) findViewById(R.id.constraint));
    }


    private void setupGradientAnimation() {

        animationDrawable = (AnimationDrawable) ivPhoto.getDrawable();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
    }

    //https://stackoverflow.com/a/14749916/4179914
    private void setupFirework() {
        FireworkLayout firework = new FireworkLayout(this);
        firework.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        ConstraintLayout surface = (ConstraintLayout) findViewById(R.id.constraint);
        surface.addView(firework);
    }
}
