package com.nepal.naxa.smartnaari.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLinkToSignup)
    Button btnLinkToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnLogin, R.id.btnLinkToSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnLinkToSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}
