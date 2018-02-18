package com.nepal.naxa.smartnaari.calendraevent;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.date.NepaliDate;
import com.nepal.naxa.smartnaari.utils.date.NepaliDateException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 1/4/18
 * by nishon.tan@gmail.com
 */

public class PopupActivity extends AppCompatActivity {


    @BindView(R.id.root_layout)
    ConstraintLayout rootLayout;
    @BindView(R.id.title)
    TextView tvNepaliDate;
    @BindView(R.id.english_date)
    TextView tvEnglishDate;
    private AnimationDrawable animationDrawable;

    public static void start(Context context) {
        Intent intent = new Intent(context, PopupActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_detail);
        ButterKnife.bind(this);
        resizeWindow();
        setupGradientAnimation();

        tvEnglishDate.setText(NepaliDate.getCurrentEngDate());

        try {
            tvNepaliDate.setText(NepaliDate.getCurrentNepaliDate());
        } catch (NepaliDateException e) {
            e.printStackTrace();
        }
    }

    private void resizeWindow(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void setupGradientAnimation(){

        animationDrawable = (AnimationDrawable) rootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
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
        constraintSet.clone((Context) this, R.layout.summary);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator((TimeInterpolator) (new AnticipateOvershootInterpolator(1.0F)));
        transition.setDuration(1200L);
        TransitionManager.beginDelayedTransition((ConstraintLayout) findViewById(R.id.constraint), (Transition) transition);
        constraintSet.applyTo((ConstraintLayout) findViewById(R.id.constraint));
    }
}
