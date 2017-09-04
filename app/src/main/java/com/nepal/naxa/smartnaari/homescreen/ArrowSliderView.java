package com.nepal.naxa.smartnaari.homescreen;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.nepal.naxa.smartnaari.R;

/**
 * Created on 9/4/17
 * by nishon.tan@gmail.com
 */

public class ArrowSliderView extends BaseSliderView {
    public ArrowSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text,null);

        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);


        bindEventAndShow(v, target);
        return v;
    }

    @Override
    protected void bindEventAndShow(View v, ImageView targetImageView) {
        super.bindEventAndShow(v, targetImageView);
    }
}
