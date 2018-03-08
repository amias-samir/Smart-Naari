package com.nepal.naxa.smartnaari.utils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.copyrightandprivacypolicy.PrivacyPolicyActivity;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.DataGlossaryWordDetailsActivity;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.WordsWithDetailsModel;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by nishon on 3/3/18.
 */

public class TextViewUtils {


    public static void highlightWordToBlue(List<String> wordlist, TextView textView) {
        String fullText = textView.getText().toString();
        for (String word : wordlist) {

            fullText = fullText.replaceAll(word, "<font color='blue'>" + word + "</font>");
            textView.setText(Html.fromHtml(fullText));
        }
    }

    public static void linkWordToPrivacyPolicy(String[] wordlist, TextView textView) {

        String fullText = textView.getText().toString();
        SpannableStringBuilder span = new SpannableStringBuilder(fullText);


        for (String word : wordlist) {

            String testText = fullText.toLowerCase(Locale.US);
            String testTextToHighlight = "\\b" + word.toLowerCase(Locale.US) + "\\b";

            int startingIndex = testText.indexOf(testTextToHighlight);
            int endingIndex = startingIndex + testTextToHighlight.length();

            span.setSpan(new GotoPrivacyPolicySpan(word), startingIndex, endingIndex, 0);

        }


        textView.setText(span);
        textView.setMovementMethod(new LinkMovementMethod());

    }

    public static void linkWordsToGlossary(List<String> wordlist, TextView textView) {
        String fullText = textView.getText().toString();
        textView.setText(fullText);

        SpannableStringBuilder span = new SpannableStringBuilder(fullText);

        for (String textItem : wordlist) {


            if (textItem != null && !textItem.trim().equals("") && !textItem.equalsIgnoreCase("other")) {
                //for counting start/end indexes
                String testText = fullText.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    // span.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                    span.setSpan(new GotoGlossarySpan(textItem), startingIndex, endingIndex, 0);

                }
            }
        }


        textView.setText(span);
        textView.setMovementMethod(new LinkMovementMethod());


    }

    private static SpannableStringBuilder makeSectionOfTextBold(String text, List<String> textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem : textToBold) {
            if (textItem != null && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    //builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                    builder.setSpan(new GotoGlossarySpan(textItem), startingIndex, endingIndex, 0);

                }
            }
        }
        return builder;
    }


    private static class GotoPrivacyPolicySpan extends ClickableSpan {

        private String text;

        public GotoPrivacyPolicySpan(String text) {
            Log.d("TextViewUtils", "GotoPrivacyPolicySpan: " + text);
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), PrivacyPolicyActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private static class GotoGlossarySpan extends ClickableSpan {

        String selectedString;

        public GotoGlossarySpan(String s) {
            selectedString = s;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(final View view) {

            new JSONAssetLoadTask(R.raw.data_glossary, new JSONAssetLoadListener() {
                @Override
                public void onFileLoadComplete(String fullText) {


                    new GlossaryDao().searchAndOpenDetail(fullText, selectedString)
                            .subscribe(new DisposableObserver<WordsWithDetailsModel>() {
                                @Override
                                public void onNext(WordsWithDetailsModel wordsWithDetailsModel) {
                                    if (!TextUtils.isEmpty(wordsWithDetailsModel.getError())) {
                                        //todo ask what to do
                                        Toast.makeText(view.getContext(),
                                                "Error", Toast.LENGTH_SHORT).show();
                                        return;
                                    }


                                    Intent intent = new Intent(view.getContext(), DataGlossaryWordDetailsActivity.class);
                                    intent.putExtra("wordsWithDetails", wordsWithDetailsModel);
                                    view.getContext().startActivity(intent);

                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }

                @Override
                public void onFileLoadError(String errorMsg) {

                }
            }).execute();
        }
    }
}
