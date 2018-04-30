package com.nepal.naxa.smartnaari.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.GetPinResponse;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleOnBoardingActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.SpanUtils;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;
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

public class LoginActivity extends BaseActivity {

    private static String TAG = "LoginActivity";
    @BindView(R.id.tv_forgot_your_password)
    TextView tvForgotYourPassword;
    private long timeStampWhenBackWasPressed;


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
    String resetPasswordJson = null;

    ProgressDialog mProgressDlg;
    MyCircleData myCircleData;
    @BindView(R.id.top_img)
    ImageView topImgTapItStopIt;

    private String mobNoToResetPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);
        myCircleData = new MyCircleData();

//        change all username text to lower case only
//        tvUserName.setFilters(new InputFilter[]{
//                new InputFilter.AllCaps() {
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        return String.valueOf(source).toLowerCase();
//                    }
//                }
//        });


//        tvUserPassword.setInputType( InputType.TYPE_TEXT_VARIATION_URI ); // optional - sets the keyboard to URL mode
// kill keyboard when enter is pressed
        tvUserPassword.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            public boolean onKey(View arg0, int arg1, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (arg1 == KeyEvent.KEYCODE_ENTER)) {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(tvUserPassword.getWindowToken(), 0);
                    userLogin();

                    return true;
                }
                return false;
            }
        });
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

                userLogin();
                break;

            case R.id.btnLinkToSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }


    private void userLogin() {

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

        NetworkApiInterface apiService = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);

        Call<UserDetail> call = apiService.getUserData(jsonToSend);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                hideLoading();

                Log.e(TAG, "onResponse: " + response.body().getData().toString());

                if (response == null) {
                    showErrorToast("Null Response");
                    return;
                }

                handleLoginResponse(response.body());
            }

            private void handleLoginResponse(UserDetail userDetail) {
                switch (userDetail.getStatus()) {
                    case REQUEST_OK:

                        handleLoginSucess(userDetail);
                        break;
                    default:
                        showErrorToast(userDetail.getData());
                        break;
                }
            }

            private void handleLoginSucess(UserDetail userDetail) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.saveUser(userDetail.getUserData());

                myCircleData.setUserId(userDetail.getUserData().getUserId());
                myCircleData.setContactNumber1(userDetail.getUserData().getCircleMobileNumber1());
                myCircleData.setContactNumber2(userDetail.getUserData().getCircleMobileNumber2());
                myCircleData.setContactNumber3(userDetail.getUserData().getCircleMobileNumber3());
                myCircleData.setContactNumber4(userDetail.getUserData().getCircleMobileNumber4());
                myCircleData.setContactNumber5(userDetail.getUserData().getCircleMobileNumber5());
                myCircleData.setContactName1(userDetail.getUserData().getCircleName1());
                myCircleData.setContactName2(userDetail.getUserData().getCircleName2());
                myCircleData.setContactName3(userDetail.getUserData().getCircleName3());
                myCircleData.setContactName4(userDetail.getUserData().getCircleName4());
                myCircleData.setContactName5(userDetail.getUserData().getCircleName5());

                Log.d(TAG, "handleLoginSucess: SAMIR" + myCircleData.getContactName2());
                sessionManager.saveUserCircle(myCircleData
                );


                if (sessionManager.doesUserHaveCircle()) {

                    Log.d(TAG, "handleLoginSucess: " + sessionManager.doesUserHaveCircle());

                    if (sessionManager.doesHaveIntentBackgroundService()) {

                        Intent intent = new Intent(LoginActivity.this, BeautifulMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        MyCircleOnBoardingActivity.startSafe(LoginActivity.this, true);
                        finish();
                    }

                } else {

                    Intent intent = new Intent(LoginActivity.this, MyCircleActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                hideLoading();
                String message = "Internet Connection Error!, please try again later";
                if (t instanceof SocketTimeoutException) {
                    message = "slow internet connection, please try again later";
                }
                Toasty.error(getApplicationContext(), "" + message, Toast.LENGTH_LONG, true).show();
            }
        });
    }


    @OnClick(R.id.top_img)
    public void onViewClicked() {
        Intent intent = new Intent(LoginActivity.this, TapItStopItActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        doubleTapToExit();
    }

    private void doubleTapToExit() {
        long timeRangeForDoubleTap = 3000;
        long totalAcceptedDelay = timeStampWhenBackWasPressed + timeRangeForDoubleTap;
        long currentTime = System.currentTimeMillis();
        if (totalAcceptedDelay > currentTime) {

            finishAffinity();
            System.exit(0);

//            finish();
            return;
        }

        showInfoToast(getString(R.string.app_exit_msg));
        timeStampWhenBackWasPressed = System.currentTimeMillis();
    }


    @OnClick(R.id.tv_forgot_your_password)
    public void onForgotPasswordViewClicked() {

        showDialogBox();

    }


    public void sendResetPasswordRequestoServer() {

        try {

            JSONObject header = new JSONObject();

            header.put("mobile", mobNoToResetPW);
//            header.put("email", userData.getEmail());
            resetPasswordJson = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkApiInterface apiService = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);

        Call<GetPinResponse> call = apiService.getPasswordResetPin(resetPasswordJson);
        call.enqueue(new Callback<GetPinResponse>() {
            @Override
            public void onResponse(Call<GetPinResponse> call, Response<GetPinResponse> response) {

                hideLoading();


                if (response.body() == null) {
                    showErrorToast("Null Response");
                    return;
                }
                Log.e(TAG, "onResponse: reset pin" + response.body().getPin().toString());

                handleLoginResponse(response.body());
            }

            private void handleLoginResponse(GetPinResponse getPinResponse) {
                switch (getPinResponse.getStatus()) {
                    case REQUEST_OK:

                        handleLoginSucess(getPinResponse);
                        break;
                    default:
                        showErrorToast("Error occurred !!!\nPlease try again later");
                        break;
                }
            }

            private void handleLoginSucess(GetPinResponse getPinResponse) {

                hideLoading();

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.putExtra("mobile", mobNoToResetPW);
                intent.putExtra("pin", getPinResponse.getPin());
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<GetPinResponse> call, Throwable t) {
                hideLoading();
                String message = "Internet Connection Error!, please try again later";
                if (t instanceof SocketTimeoutException) {
                    message = "slow internet connection, please try again later";
                }
                Toasty.error(getApplicationContext(), "" + message, Toast.LENGTH_LONG, true).show();
            }
        });
    }


    private void showDialogBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Registered mobile no.");


// Set up the input
        final EditText input = new EditText(this);
        input.setBackground(getResources().getDrawable(R.drawable.edit_text_boarder_recg_white_bg_red_boarder));
        input.setHint("Enter your mobile no.");
        input.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );
        params.setMargins(16, 20, 16, 20);
        input.setLayoutParams(params);
//        input.setBackgroundTintList( ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)) );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_TEXT_VARIATION_PHONETIC);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mobNoToResetPW = input.getText().toString();
                dialog.dismiss();

                showLoading("Sending Request");

                sendResetPasswordRequestoServer();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
