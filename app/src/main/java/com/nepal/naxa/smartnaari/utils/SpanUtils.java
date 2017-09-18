package com.nepal.naxa.smartnaari.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.util.Locale;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

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

    public static SpannableStringBuilder makeSectionOfTextBigger(String text, int textSize, String... textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem :
                textToBold) {
            if (textItem != null && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {


                    builder.setSpan(new AbsoluteSizeSpan(textSize), startingIndex, endingIndex, SPAN_INCLUSIVE_INCLUSIVE);


                }
            }
        }

        return builder;
    }


    public static SpannableStringBuilder makeSectionOfTextBold(String text, String... textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem :
                textToBold) {
            if (textItem != null && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                }
            }
        }

        return builder;
    }
}
