package com.nepal.naxa.smartnaari.mycircle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.mycircle.common.BaseActivity;
import com.nepal.naxa.smartnaari.mycircle.powerbutton.PowerButtonService;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;


public class PermissionActivity extends BaseActivity implements VerticalStepperForm {

    private final String TAG = this.getClass().getSimpleName();
    private VerticalStepperFormLayout verticalStepperForm;
    final int CODE_REQUEST_PERMISSIONS = 7876778;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title_permission_activity);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(toolbar);


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            boolean canWriteSystemSetting = Settings.System.canWrite(getApplicationContext());
//            Log.i(TAG, "Can Write Setting " + canWriteSystemSetting);
//            Intent myIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//            startActivity(myIntent);
//        }

        String[] mySteps = {"Start Setup", "Allow SMS And Location Access ", "In Case of Emergency"};
        String[] subtitles = {"Follow 4 steps to configure MyCircle",
                "Smart नारी needs access to SMS and Location Services to send location data to the people in your MyCircle. ",
                "Allow smart नारी to overlay over other apps and notify your 'My Circle'"};
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            subtitles[2] = "Notification has been granted";
        }
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);


        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .stepsSubtitles(subtitles)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .displayBottomNavigation(false)
                .init();

    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;

        switch (stepNumber) {
            case 2:
                view = createInstructionView();
                break;

//            case 3:
//                Intent intent = new Intent(PermissionActivity.this, SplashScreenActivity.class);
//                startActivity(intent);
//                break;

            default:
                view = createDummyView();
                break;
        }

        return view;
    }

    private View createInstructionView() {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isSystemAlertPermissionGranted(this)) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());

            RelativeLayout instructionLayoutContent = (RelativeLayout) inflater.inflate(R.layout.permission_step_layout, null, false);
            TextView title = (TextView) instructionLayoutContent.findViewById(R.id.tv_title_permission_step);

            Button button = (Button) instructionLayoutContent.findViewById(R.id.btn_open_setup_permission_step);


            String msg;
            msg = "Read the following steps and tap 'START' when ready\n\n" +
                    "Tap 'Start' Button \n\n" +
                    "2.Choose " +
                    getString(R.string.app_name) +
                    " From The List." +
                    "\n\n" +
                    "3.Toggle \'Permit drawing over other app\' On";
            title.setText(msg);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(myIntent);
                }
            });
            return instructionLayoutContent;
        } else {

            return createDummyView();
        }


    }

    private View createDummyView() {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        return linearLayout;
    }


    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {

            case 0:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 1:
                handlePermissionOpen();
                break;
            case 2:
                handleOverlayPermissionOpen();
                break;

//            case 3:
//                Intent intent = new Intent(PermissionActivity.this, SplashScreenActivity.class);
//                startActivity(intent);
//                break;

        }
    }

    private void handleOverlayPermissionOpen() {
        try {
            if (isSystemAlertPermissionGranted(getApplicationContext())) {
                verticalStepperForm.setActiveStepAsCompleted();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePermissionOpen() {

        String[] requiredPermissions = new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WAKE_LOCK};

        if (hasPermission(Manifest.permission.SEND_SMS) && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            verticalStepperForm.setActiveStepAsCompleted();
        } else {
            requestPermissionsSafely(requiredPermissions, CODE_REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSIONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    verticalStepperForm.setStepAsCompleted(1);
                    verticalStepperForm.setStepSubtitle(1, "Press Continue");

                } else {
                    verticalStepperForm.setActiveStepAsUncompleted("Permission Denied");
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void sendData() {
        startService(new Intent(this, PowerButtonService.class));
        Intent intent = new Intent(PermissionActivity.this, BeautifulMainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPermission(Manifest.permission.SEND_SMS) && hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepSubtitle(1, "Press Continue");
        }

        if (isSystemAlertPermissionGranted(getApplicationContext())) {
            verticalStepperForm.setStepAsCompleted(2);
            verticalStepperForm.setStepSubtitle(2, "Press Continue");
        }
    }


}
