package com.nepal.naxa.smartnaari.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.PasswordResetResponse;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.register.SignUpActivity;
import com.nepal.naxa.smartnaari.utils.ui.DialogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_OK;

public class ForgotPasswordActivity extends BaseActivity {

    private static final String TAG = "ForgotPasswordActivity";

    @BindView(R.id.tv_pin_input)
    EditText tvPinInput;
    @BindView(R.id.btn_pin_submit)
    Button btnPinSubmit;
    @BindView(R.id.pin_layout)
    LinearLayout pinLayout;
    @BindView(R.id.tv_password_input)
    EditText tvPasswordInput;
    @BindView(R.id.tv_confirm_password_input)
    EditText tvConfirmPasswordInput;
    @BindView(R.id.btn_password_reset)
    Button btnPasswordReset;
    @BindView(R.id.password_reset_layout)
    LinearLayout passwordResetLayout;


    String pin;
    String mobile;
    String resetPasswordJson = null;
    String newPassword = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        pin = intent.getStringExtra("pin");
        mobile = intent.getStringExtra("mobile");

        passwordResetLayout.setVisibility(View.GONE);

    }


    @OnClick({R.id.btn_pin_submit, R.id.btn_password_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pin_submit:

                if (TextUtils.isEmpty(tvPinInput.getText().toString())) {
                    showErrorToast("Field is empty");
                    tvPinInput.requestFocus();
                } else {
                    if (tvPinInput.getText().toString().equals(pin)) {
                        pinLayout.setVisibility(View.GONE);
                        passwordResetLayout.setVisibility(View.VISIBLE);
                    } else {
                        showErrorToast("Invalid Pin");
                        tvPinInput.requestFocus();
                    }
                }


                break;
            case R.id.btn_password_reset:

                if (TextUtils.isEmpty(tvPasswordInput.getText().toString())) {
                    showErrorToast("Field is empty");
                    tvPasswordInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(tvConfirmPasswordInput.getText().toString())) {
                    showErrorToast("Field is empty");
                    tvConfirmPasswordInput.requestFocus();
                    return;
                }

                if (tvPasswordInput.getText().toString().equals(tvConfirmPasswordInput.getText().toString())) {
                    newPassword = tvConfirmPasswordInput.getText().toString();
                    sendResetPasswordRequestoServer(newPassword);
                } else {
                    showErrorToast("Password didn't match.");
                    tvPasswordInput.requestFocus();
                }


                break;
        }
    }

    public void sendResetPasswordRequestoServer(String newPassword) {

        try {

            JSONObject header = new JSONObject();

            header.put("mobile", mobile);
            header.put("new_password", newPassword);
            resetPasswordJson = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkApiInterface apiService = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);

        Call<PasswordResetResponse> call = apiService.getPasswordResetResponse(resetPasswordJson);
        call.enqueue(new Callback<PasswordResetResponse>() {
            @Override
            public void onResponse(Call<PasswordResetResponse> call, Response<PasswordResetResponse> response) {

                hideLoading();


                if (response == null) {
                    showErrorToast("Null Response");
                    return;
                }
                Log.e(TAG, "onResponse: reset pin" + response.body().getMsg().toString());

                handleLoginResponse(response.body());
            }

            private void handleLoginResponse(PasswordResetResponse passwordResetResponse) {
                switch (passwordResetResponse.getStatus()) {
                    case REQUEST_OK:

                        handleLoginSucess(passwordResetResponse);
                        break;
                    default:
                        showErrorToast("Error occurred !!!\nPlease try again later");
                        break;
                }
            }

            private void handleLoginSucess(PasswordResetResponse passwordResetResponse) {

                hideLoading();

                showInfoToast("Password reset successfully,\nPlease login to enter.");

                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<PasswordResetResponse> call, Throwable t) {
                hideLoading();
                String message = "Internet Connection Error!, please try again later";
                if (t instanceof SocketTimeoutException) {
                    message = "slow internet connection, please try again later";
                }
                Toasty.error(getApplicationContext(), "" + message, Toast.LENGTH_LONG, true).show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        DialogFactory.createActionDialog(this, "Warning!", "Your entered data will be lost. Do you want to exit Reset Password?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ForgotPasswordActivity.super.onBackPressed();

                    }
                }).setNegativeButton("No", null)
                .show();

    }
}
