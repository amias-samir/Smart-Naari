package com.nepal.naxa.smartnaari.common;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.utils.ui.DialogFactory;
import com.nepal.naxa.smartnaari.utils.NetworkUtils;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import es.dmoral.toasty.Toasty;

/**
 * Created on 10/9/17
 * by nishon.tan@gmail.com
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ToastUtils toastUtils;
    private Toasty progess;
    protected AppDataManager appDataManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastUtils = new ToastUtils();

        appDataManager = new AppDataManager(getApplicationContext());
    }

    public void showErrorToast(String message) {
        if (message != null) {
            toastUtils.error(this, message);
        } else {
            toastUtils.error(this, "Some Error Occurred!");
        }
    }

    public void showInfoToast(String message) {
        if (message != null) {
            toastUtils.info(this, message);
        }

    }

    public void showLoading(String msg) {

        progressDialog = DialogFactory.createProgressDialog(this, msg);
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }



    public boolean isNetworkDisconnected() {
        return NetworkUtils.isNetworkDisconnected(getApplicationContext());
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }




}
