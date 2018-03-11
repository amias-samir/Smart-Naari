package com.nepal.naxa.smartnaari.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.retrofit.ErrorSupportCallback;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleOnBoardingActivity;
import com.nepal.naxa.smartnaari.mycircle.PermissionActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.SpanUtils;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_OK;

public class LoginActivity extends BaseActivity {

    private static String TAG = "LoginActivity";
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
    ProgressDialog mProgressDlg;
    MyCircleData myCircleData;
    @BindView(R.id.top_img)
    ImageView topImgTapItStopIt;

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
        tvUserPassword.setOnKeyListener(new View.OnKeyListener()
        {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            public boolean onKey(View arg0, int arg1, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
                        (arg1           == KeyEvent.KEYCODE_ENTER)   )
                {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(tvUserPassword.getWindowToken(), 0);
                    userLogin();

                    return true;
                }
                return false;
            }
        } );
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


    private void userLogin (){

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
        call.enqueue(new ErrorSupportCallback<>(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                hideLoading();

                if (response == null) {
                    showErrorToast(null);
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

                        MyCircleOnBoardingActivity.startSafe(LoginActivity.this,true);
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

                String message = "Some Error Occured!";

                if (t instanceof SocketTimeoutException) {
                    message = "Socket Time out. Please try again.";
                }

                showErrorToast(message);
            }
        }));
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


}
