package com.nepal.naxa.smartnaari.utils.ui;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created on 10/9/17
 * by nishon.tan@gmail.com
 */

public class ToastUtils {

    public void error(Context context, String msg) {
        Toasty.error(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void info(Context context, String msg) {
        Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();
    }


}
