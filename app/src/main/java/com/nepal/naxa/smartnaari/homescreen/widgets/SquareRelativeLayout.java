package com.nepal.naxa.smartnaari.homescreen.widgets;

/**
 * Created on 9/5/17
 * by nishon.tan@gmail.com
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * A RelativeLayout that will always be square -- same width and height,
 * where the height is based off the width.
 */
public class SquareRelativeLayout extends CardView {

    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
