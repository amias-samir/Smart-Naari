package com.nepal.naxa.smartnaari.mycircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.mycircle.mycirclecircularview.MyCircleCircularViewActivity;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCircleProtectorActivity extends BaseActivity {

    @BindView(R.id.dialog_text_details)
    TextView dialogTextDetails;
    @BindView(R.id.tv_my_circle_new_password)
    EditText tvMyCircleNewPassword;
    @BindView(R.id.tv_my_circle_confirm_password)
    EditText tvMyCircleConfirmPassword;
    @BindView(R.id.create_password_layout)
    LinearLayout createPasswordLayout;
    @BindView(R.id.tv_my_circle_enter_password)
    EditText tvMyCircleEnterPassword;
    @BindView(R.id.btn_change_password)
    TextView btnChangePassword;
    @BindView(R.id.enter_password_layout)
    LinearLayout enterPasswordLayout;
    @BindView(R.id.tv_my_circle_old_password)
    EditText tvMyCircleOldPassword;
    @BindView(R.id.tv_my_circle_change_new_password)
    EditText tvMyCircleChangeNewPassword;
    @BindView(R.id.tv_my_circle_change_confirm_new_password)
    EditText tvMyCircleChangeConfirmNewPassword;
    @BindView(R.id.change_password_layout)
    LinearLayout changePasswordLayout;
    @BindView(R.id.btn_close_dialog)
    Button btnCloseDialog;
    @BindView(R.id.btn_agree_dialog)
    Button btnAgreeDialog;
    @BindView(R.id.button_layout)
    LinearLayout buttonLayout;


    SessionManager sessionManager;
    private Boolean changePasswordBoolead = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle_protector);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        if(sessionManager.isMyCircleFirstTimeLoad()){
            createPasswordLayout.setVisibility(View.VISIBLE);
            enterPasswordLayout.setVisibility(View.GONE);
            changePasswordLayout.setVisibility(View.GONE);
        }else {
            createPasswordLayout.setVisibility(View.GONE);
            changePasswordLayout.setVisibility(View.GONE);
            enterPasswordLayout.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btn_change_password, R.id.btn_close_dialog, R.id.btn_agree_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_password:
                createPasswordLayout.setVisibility(View.GONE);
                enterPasswordLayout.setVisibility(View.GONE);
                changePasswordLayout.setVisibility(View.VISIBLE);
                changePasswordBoolead = true ;

//                changePassword();


                break;
            case R.id.btn_close_dialog:
                Intent intent = new Intent(MyCircleProtectorActivity.this, BeautifulMainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_agree_dialog:

                if(sessionManager.isMyCircleFirstTimeLoad()){
                    createPassword();


                }else if (changePasswordBoolead){
                    changePassword();

                }
                else {
                    loginToMyCircle();
                }

                break;
        }
    }


    public void changePassword(){
        if (tvMyCircleOldPassword.getText().toString().trim().equals(sessionManager.getUserMyCirclePassword())) {

            if(tvMyCircleChangeNewPassword.getText().toString().trim().isEmpty() ){
                tvMyCircleChangeNewPassword.setFocusable(true);
                showErrorToast("Empty Password Field");
                return;
            }

            if (tvMyCircleChangeNewPassword.getText().toString().trim().equals(tvMyCircleChangeConfirmNewPassword.getText().toString().trim())) {

                sessionManager.setUserMyCirclePassword(tvMyCircleChangeConfirmNewPassword.getText().toString().trim());
                sessionManager.setIsMyCircleFirstTimeLoad(false);

                showInfoToast("Password changed successfully");

                Intent intent = new Intent(MyCircleProtectorActivity.this, MyCircleCircularViewActivity.class);
                startActivity(intent);
                finish();
            }else {
                showErrorToast("Password did not match");
                tvMyCircleChangeConfirmNewPassword.setFocusable(true);
                return;
            }


        }else {
            showErrorToast("Incorrect Old Password");
            tvMyCircleOldPassword.setFocusable(true);
            return;
        }
    }

    public void loginToMyCircle(){
        if (tvMyCircleEnterPassword.getText().toString().trim().equals(sessionManager.getUserMyCirclePassword())) {

            Intent intent = new Intent(MyCircleProtectorActivity.this, MyCircleCircularViewActivity.class);
            startActivity(intent);
            finish();

        }else {
            showErrorToast("Password wrong");
            tvMyCircleEnterPassword.setFocusable(true);
            return;
        }
    }

    public void createPassword(){
        if (tvMyCircleNewPassword.getText().toString().trim().isEmpty()){
            tvMyCircleNewPassword.setFocusable(true);
            showErrorToast("Empty password field");
            return;
        }

        if(tvMyCircleNewPassword.getText().toString().trim().equals(tvMyCircleConfirmPassword.getText().toString().trim())){

            sessionManager.setIsMyCircleFirstTimeLoad(false);
            sessionManager.setUserMyCirclePassword(tvMyCircleConfirmPassword.getText().toString());
            showInfoToast("Password created successfully");
            Intent intent = new Intent(MyCircleProtectorActivity.this, MyCircleCircularViewActivity.class);
            startActivity(intent);
            finish();

        }else {
            showErrorToast("Password did not match");
            tvMyCircleConfirmPassword.setFocusable(true);
            return;
        }

    }

}
