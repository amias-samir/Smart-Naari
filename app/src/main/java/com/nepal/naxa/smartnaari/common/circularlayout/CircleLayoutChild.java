package com.nepal.naxa.smartnaari.common.circularlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.nepal.naxa.smartnaari.R;

/**
 * Created by samir on 3/29/2018.
 * * reference https://github.com/szugyi
 */

public class CircleLayoutChild extends RelativeLayout {
    // The name of the view
    private String name;

    public CircleLayoutChild(Context context) {
        this(context, null);
    }

    public CircleLayoutChild(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLayoutChild(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.LayoutViewAttachedToCircularlayout);

            this.name = array.getString(R.styleable.LayoutViewAttachedToCircularlayout_name);
            array.recycle();
        }
    }

    /**
     * Return the name of the view.
     * @return Returns the name of the view.
     */
    public String getName(){
        return name;
    }

    /**
     * Set the name of the view.
     * @param name The name to be set for the view.
     */
    public void setName(String name){
        this.name = name;
    }


}
