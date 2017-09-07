package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends Activity {

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);



    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_sex_male:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_sex_female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    @OnClick(R.id.btnSignUp)
    public void SignUpBtnClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
