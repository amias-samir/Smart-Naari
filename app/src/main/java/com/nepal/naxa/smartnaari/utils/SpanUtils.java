package com.nepal.naxa.smartnaari.utils;

import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created on 9/13/17
 * by nishon.tan@gmail.com
 */

public class SpanUtils {

    public static void setColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


}
