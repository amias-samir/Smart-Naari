package com.nepal.naxa.smartnaari.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLinkToSignup)
    Button btnLinkToSignup;
    @BindView(R.id.user_name_input_id)
    EditText tvUserName;
    @BindView(R.id.user_password_input_id)
    EditText tvUserPassword;
    @BindView(R.id.btnDown)
    ImageButton btnDown;
    @BindView(R.id.registerLBL)
    TextView registerLBL;
    @BindView(R.id.registerBeTheOneLBL)
    TextView tvRegisterBeTheOneLBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        Be the one to say NO to Gender Based Violence

        final SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.be_the_one_to_say_no_to_gender_based_violence));
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        final StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
        sb.setSpan(bss, 18, 20, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 10 characters Bold
//        sb.setSpan(iss, 10, 91, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last 2 characters Italic
        tvRegisterBeTheOneLBL.setText(sb);
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
