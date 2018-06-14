package com.nepal.naxa.smartnaari.machupbasdina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;

public class MaChupBasdinaFeedBackForm extends BaseActivity implements View.OnClickListener {


    public EditText etWhoHelped;
    private TextInputLayout tiHowInfoHelped;
    private Button btnSubmitForm;
    private String userId;


    public static void start(Context context) {
        Intent intent = new Intent(context, MaChupBasdinaActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_chup_basdina_feed_back_form);
        userId = new SessionManager(this).getUserId();
        initView();
    }

    private void initView() {

        tiHowInfoHelped = findViewById(R.id.ti_how_was_info_helpfull);
        etWhoHelped = findViewById(R.id.et_who_helped);
        btnSubmitForm = findViewById(R.id.btn_upload_form);
        btnSubmitForm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_form:
                if (validateText(tiHowInfoHelped) && validateText(etWhoHelped)) {
                    String whoHelped = etWhoHelped.getText().toString();
                    String howInfoHelped = tiHowInfoHelped.getEditText().getText().toString();
                    Feedback feedback = new Feedback(whoHelped, howInfoHelped, userId);
                    showInfoToast(feedback.toString());
                    Log.i("Ram",feedback.toString());
                }
                break;
        }
    }

    private boolean validateText(EditText editText) {
        String siteName = editText.getText().toString();
        Boolean isValid = siteName.length() > 0;
        return isValid;

    }


    private boolean validateText(TextInputLayout textInputLayout) {

        String siteName = textInputLayout.getEditText().getText().toString();
        Boolean isValid = siteName.length() > 0;
        if (isValid) {
            textInputLayout.setErrorEnabled(false);

            return true;
        }


        textInputLayout.requestFocus();
        textInputLayout.setError("This is a required field.");
        return false;
    }
}
