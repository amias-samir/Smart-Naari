package com.nepal.naxa.smartnaari.login;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.BaseActivity;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;
import com.nepal.naxa.smartnaari.utils.Constants;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLinkToSignup)
    Button btnLinkToSignup;
    @BindView(R.id.user_name_input_id)
    EditText tvUserName;
    @BindView(R.id.user_password_input_id)
    EditText tvUserPassword;

    @BindView(R.id.registerLBL)
    TextView registerLBL;
    @BindView(R.id.registerBeTheOneLBL)
    TextView tvRegisterBeTheOneLBL;


    String jsonToSend = null;
    ProgressDialog mProgressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);
    }

    private void setupUI() {

        String rawString = getResources().getString(R.string.be_the_one_to_say_no_to_gender_based_violence);
        String textToBigSize = "No";
        int bigSize = getResources().getDimensionPixelSize(R.dimen.material_text_headline);
        SpannableStringBuilder sb = SpanUtils.makeSectionOfTextBigger(rawString, bigSize, textToBigSize);

        tvRegisterBeTheOneLBL.setText(sb);

    }

    @OnClick({R.id.btnLogin, R.id.btnLinkToSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                hideKeyboard();

                String username = tvUserName.getText().toString();
                String password = tvUserPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    showErrorToast("Username is empty");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    showErrorToast("Password is empty");
                    return;
                }
                if (isNetworkDisconnected()) {
                    showErrorToast("Device is offline.");
                    return;
                }

                showLoading("Please Wait!");
                convertDataToJSON();
                sendDataToServer();


                break;
            case R.id.btnLinkToSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }


    public void convertDataToJSON() {

        try {

            JSONObject header = new JSONObject();

            header.put("username", tvUserName.getText().toString());
            header.put("password", tvUserPassword.getText().toString());
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToServer() {

        NetworkApiInterface apiService = NetworkApiClient.getNotifictionApiClient().create(NetworkApiInterface.class);


        Log.e(TAG, "Retrofit Json to send: " + jsonToSend.toString());

        Call<UserDetail> call = apiService.getUserData(jsonToSend);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                hideLoading();

                if (response == null) {
                    showErrorToast(null);
                    return;
                }

                String status = response.body().getStatus();
                String data = response.body().getData();
                UserData userData = response.body().getUserData();

                switch (status) {
                    case "200":
                        Constants.user_id = userData.getUserId();
                        Constants.first_contact = userData.getCircleMobileNumber1();
                        Constants.second_contact = userData.getCircleMobileNumber2();
                        Constants.third_contact = userData.getCircleMobileNumber3();
                        Constants.fourth_contact = userData.getCircleMobileNumber4();
                        Constants.fifth_contact = userData.getCircleMobileNumber5();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        break;
                    default:

                        showErrorToast(data);
                        break;
                }


            }


            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                hideLoading();

                String message = "Some Error Occured!";

                if (t instanceof SocketTimeoutException) {
                    message = "Socket Time out. Please try again.";
                }

                showErrorToast(message);
            }
        });
    }


}
