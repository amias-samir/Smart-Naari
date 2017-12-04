package com.nepal.naxa.smartnaari.mycircle.shake;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;
import com.nepal.naxa.smartnaari.mycircle.setting.SettingsActivity;


public class ServiceActivity extends BaseActivity {

    public static boolean isServiceRunning = false;
    final int CODE_REQUEST_PERMISSIONS = 7876778;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] requiredPermissions = new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WAKE_LOCK};

        if (isSystemAlertPermissionGranted(getApplicationContext())) {
            startService(new Intent(this, PowerButtonService.class));
        } else {

            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);

            requestPermissionsSafely(requiredPermissions, CODE_REQUEST_PERMISSIONS);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSIONS:

                startService(new Intent(this, ShakeService.class));

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
