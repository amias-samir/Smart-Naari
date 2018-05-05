package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.SpanUtils;
import com.nepal.naxa.smartnaari.utils.TextViewUtils;
import com.nepal.naxa.smartnaari.utils.ui.DialogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_OK;
import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;

public class SignUpActivity extends Activity {

    private static final String TAG = "SignUpActivity";

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.tv_terms_and_condition)
    TextView textViewTermsAndCondition;

    @BindView(R.id.user_name_input_id)
    EditText etUserName;

    @BindView(R.id.user_password_input_id)
    EditText etPassword;

    @BindView(R.id.user_confirm_password_input_id)
    EditText etConformPassword;

    @BindView(R.id.user_firstname_input_id)
    EditText etFirstName;

    @BindView(R.id.user_surname_input_id)
    EditText etSurName;

//    @BindView(R.id.user_age_input_id)
//    EditText etAge;

    @BindView(R.id.is_checked_radio_btn_group)
    RadioGroup rgGender;

    @BindView(R.id.radio_sex_male)
    RadioButton rdMale;

    @BindView(R.id.radio_sex_female)
    RadioButton rdFemale;

    @BindView(R.id.radio_sex_other)
    RadioButton rdOther;

    @BindView(R.id.spinner_user_birth_place_input_id)
    Spinner spBirthPlace;

    @BindView(R.id.spinner_user_current_place_input_id)
    Spinner spCurrentPlace;

    @BindView(R.id.user_email_input_id)
    EditText etEmail;

    @BindView(R.id.user_contact_no_input_id)
    EditText etContact;

//    @BindView(R.id.cb_terms_and_condition)
//    CheckBox cbAgreement;

    ProgressDialog mProgressDlg;
    String jsonToSend = "";

    String userName = "";
    String password = "";
    String confirmPassword = "";
    String firstName = "";
    String surName = "";
    String age = "";
    String gender = "";
    String birthPlace = "";
    String currentPlace = "";
    String email = "";
    String mobileNumber = "";
    @BindView(R.id.spinner_birth_year)
    Spinner spinnerBirthYear;
    @BindView(R.id.spinner_birth_month)
    Spinner spinnerBirthMonth;
    @BindView(R.id.spinner_birth_day)
    Spinner spinnerBirthDay;
    @BindView(R.id.rlFirstInputPage)
    LinearLayout rlFirstInputPage;
    @BindView(R.id.rlSecondInputPage)
    LinearLayout rlSecondInputPage;
    @BindView(R.id.btnPrev)
    Button btnPrev;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.rlTermsAndCondition)
    RelativeLayout rlTermsAndCondition;
    @BindView(R.id.tv_user_birth_place_input_id)
    AutoCompleteTextView tvUserBirthPlace;
    @BindView(R.id.tv_user_current_place_input_id)
    AutoCompleteTextView tvUserCurrentPlace;

    ArrayAdapter<String> birthDistArray;
    ArrayAdapter<String> currentDistArray;

    //todo write style for api < 21 for checkbox
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);

        setUpSpinners();

        setUpAutoCompleteDistrict();

        textViewTermsAndCondition.setText("By Signing up, you are indicating that you agree to the Privacy Policy and Terms.");
        List<String> wordlist = new ArrayList<>();
        wordlist.add("Privacy Policy and Terms");
//        wordlist.add("Terms");
        TextViewUtils.highlightWordToBlue(wordlist, textViewTermsAndCondition);
        TextViewUtils.linkWordToPrivacyPolicy(new String[]{"Privacy Policy and Terms"}, textViewTermsAndCondition);


    }

    private void setUpSpinners() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = (thisYear - 16); i >= (thisYear - 106); i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinnerBirthYear.setAdapter(yearAdapter);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.months);
        spinnerBirthMonth.setAdapter(monthAdapter);

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        spinnerBirthDay.setAdapter(dayAdapter);


        ArrayAdapter<String> birthDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.birthDistrictListEnglish);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBirthPlace.setAdapter(birthDistArray);

        ArrayAdapter<String> currentDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.currentDistrictListEnglish);
        currentDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrentPlace.setAdapter(currentDistArray);
    }

    private void setUpAutoCompleteDistrict(){
        currentDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.birthDistrictListEnglish);
        birthDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.birthDistrictListEnglish);
        tvUserBirthPlace.setAdapter(birthDistArray);
        tvUserCurrentPlace.setAdapter(currentDistArray);
    }

    private void setupUI() {
        String textTermsAndCondition = getString(R.string.string_terms_and_condition);
        String textToBold = "Gender Based Violence";

        SpannableStringBuilder sb = SpanUtils.makeSectionOfTextBold(textTermsAndCondition, textToBold);
        textViewTermsAndCondition.setText(sb);

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_sex_male:
                if (checked)
                    gender = "Male";
                break;
            case R.id.radio_sex_female:
                if (checked)
                    gender = "Female";
                break;
            case R.id.radio_sex_other:
                if (checked)
                    gender = "Others";
                break;
        }
    }


    public boolean validateUserDetails() {

        userName = etUserName.getText().toString().trim();
        if (userName.equals("")) {
            Toasty.error(getApplicationContext(), "Username field is empty", Toast.LENGTH_SHORT, true).show();
            etUserName.requestFocus();
            return false;
        }


        password = etPassword.getText().toString().trim();
        if (password.equals("")) {
            Toasty.error(getApplicationContext(), "Password field is empty", Toast.LENGTH_SHORT, true).show();
            etPassword.requestFocus();

            return false;
        }


        confirmPassword = etConformPassword.getText().toString().trim();
        if (confirmPassword.equals("")) {
            Toasty.error(getApplicationContext(), "Confirm password field is empty", Toast.LENGTH_SHORT, true).show();
            etConformPassword.requestFocus();

            return false;
        } else {
            if (confirmPassword.equals(password)) {
            } else {
                Toasty.error(getApplicationContext(), "Password didn't matched.\n Try again", Toast.LENGTH_SHORT, true).show();
                etConformPassword.requestFocus();

                return false;
            }
        }


        firstName = etFirstName.getText().toString().trim();
        if (firstName.equals("")) {
            Toasty.error(getApplicationContext(), "Name field is empty", Toast.LENGTH_SHORT, true).show();
            etFirstName.requestFocus();
            return false;
        }


        surName = etSurName.getText().toString().trim();
        if (surName.equals("")) {
            Toasty.error(getApplicationContext(), "Sur name field is empty", Toast.LENGTH_SHORT, true).show();
            etSurName.requestFocus();
            return false;
        }


//        age = etAge.getText().toString().trim();
//        if (age.equals("")) {
//            Toasty.error(getApplicationContext(), "Age field is empty", Toast.LENGTH_SHORT, true).show();
//            etAge.requestFocus();
//            return false;
//        }


        int id = rgGender.getCheckedRadioButtonId();
        if (id != -1) {
            switch (id) {
                case R.id.radio_sex_male:
                    gender = "Male";
                    break;
                case R.id.radio_sex_female:
                    gender = "Female";
                    break;
                case R.id.radio_sex_other:
                    gender = "Others";
                    break;
            }
        } else {
            Toasty.error(getApplicationContext(), "No gender selected.", Toast.LENGTH_SHORT, true).show();
            rgGender.requestFocus();
            return false;
        }


        birthPlace = spBirthPlace.getSelectedItem().toString();
        if (birthPlace.equals("Birth District")) {
            Toasty.error(getApplicationContext(), "Birth place field is empty", Toast.LENGTH_SHORT, true).show();
            spBirthPlace.requestFocus();
            return false;
        }


        currentPlace = spCurrentPlace.getSelectedItem().toString();
        if (currentPlace.equals("Current District")) {
            Toasty.error(getApplicationContext(), "Current place field is empty", Toast.LENGTH_SHORT, true).show();
            spCurrentPlace.requestFocus();
            return false;
        }


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        email = etEmail.getText().toString().trim();
        if (!email.equals("")) {
            if (email.matches(emailPattern)) {
//                Toasty.success(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(getApplicationContext(), "Invalid email address. \n Try again.", Toast.LENGTH_SHORT, true).show();
                etEmail.requestFocus();
                return false;
            }
        } else {
            Toasty.error(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT, true).show();
            etEmail.requestFocus();
            return false;
        }


        mobileNumber = etContact.getText().toString().trim();
        if (mobileNumber.equals("")) {
            Toasty.error(getApplicationContext(), "Contact field is empty", Toast.LENGTH_SHORT, true).show();
            etContact.requestFocus();
            return false;
        }
        if (mobileNumber.length() < 10) {
            Toasty.error(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT, true).show();
            etContact.requestFocus();
            return false;
        }


//        if (cbAgreement.isChecked()) {
//            mobileNumber = etContact.getText().toString().trim();
//        } else {
//            Toasty.error(getApplicationContext(), "You must check the agreement.", Toast.LENGTH_SHORT, true).show();
//            return false;
//        }


        return true;
    }


    public boolean validateUserFirstPageDetails() {
        userName = etUserName.getText().toString().trim();
        if (userName.equals("")) {
            Toasty.error(getApplicationContext(), "Username field is empty", Toast.LENGTH_SHORT, true).show();
            etUserName.requestFocus();
            return false;
        }


        password = etPassword.getText().toString().trim();
        if (password.equals("")) {
            Toasty.error(getApplicationContext(), "Password field is empty", Toast.LENGTH_SHORT, true).show();
            etPassword.requestFocus();

            return false;
        }


        confirmPassword = etConformPassword.getText().toString().trim();
        if (confirmPassword.equals("")) {
            Toasty.error(getApplicationContext(), "Confirm password field is empty", Toast.LENGTH_SHORT, true).show();
            etConformPassword.requestFocus();

            return false;
        } else {
            if (confirmPassword.equals(password)) {
            } else {
                Toasty.error(getApplicationContext(), "Password didn't matched.\n Try again", Toast.LENGTH_SHORT, true).show();
                etConformPassword.requestFocus();

                return false;
            }
        }


        firstName = etFirstName.getText().toString().trim();
        if (firstName.equals("")) {
            Toasty.error(getApplicationContext(), "Name field is empty", Toast.LENGTH_SHORT, true).show();
            etFirstName.requestFocus();
            return false;
        }


        surName = etSurName.getText().toString().trim();
        if (surName.equals("")) {
            Toasty.error(getApplicationContext(), "Sur name field is empty", Toast.LENGTH_SHORT, true).show();
            etSurName.requestFocus();
            return false;
        }

        mobileNumber = etContact.getText().toString().trim();
        if (mobileNumber.equals("")) {
            Toasty.error(getApplicationContext(), "Contact field is empty", Toast.LENGTH_SHORT, true).show();
            etContact.requestFocus();
            return false;
        }
        if (mobileNumber.length() < 10) {
            Toasty.error(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT, true).show();
            etContact.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validateUserSecondPageDetails() {
        int id = rgGender.getCheckedRadioButtonId();
        if (id != -1) {
            switch (id) {
                case R.id.radio_sex_male:
                    gender = "Male";
                    break;
                case R.id.radio_sex_female:
                    gender = "Female";
                    break;
                case R.id.radio_sex_other:
                    gender = "Others";
                    break;
            }
        } else {
            Toasty.error(getApplicationContext(), "No gender selected.", Toast.LENGTH_SHORT, true).show();
            rgGender.requestFocus();
            return false;
        }


//        birthPlace = spBirthPlace.getSelectedItem().toString();
        birthPlace = tvUserBirthPlace.getText().toString();
        int birthDistPos = birthDistArray.getPosition(birthPlace);
        Log.d(TAG, "validateUserSecondPageDetails: BirthDistrict"+birthDistPos);

        if (TextUtils.isEmpty(birthPlace)) {
            Toasty.error(getApplicationContext(), "Birth District field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserBirthPlace.requestFocus();
            return false;
        }
        if(birthDistPos < 0){
            Toasty.error(getApplicationContext(), "Birth District is invalid", Toast.LENGTH_SHORT, true).show();
            tvUserBirthPlace.requestFocus();
            return false;
        }


//        currentPlace = spCurrentPlace.getSelectedItem().toString();
        currentPlace = tvUserCurrentPlace.getText().toString();
        int currentDistPos = currentDistArray.getPosition(currentPlace);
        Log.d(TAG, "validateUserSecondPageDetails: currentDistrict"+currentDistPos);
        if (TextUtils.isEmpty(currentPlace)) {
            Toasty.error(getApplicationContext(), "Current District field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserCurrentPlace.requestFocus();
            return false;
        }
        if(currentDistPos < 0){
            Toasty.error(getApplicationContext(), "Current District is invalid", Toast.LENGTH_SHORT, true).show();
            tvUserCurrentPlace.requestFocus();
            return false;
        }


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        email = etEmail.getText().toString().trim();
        if (!email.equals("")) {
            if (email.matches(emailPattern)) {
//                Toasty.success(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(getApplicationContext(), "Invalid email address. \n Try again.", Toast.LENGTH_SHORT, true).show();
                etEmail.requestFocus();
                return false;
            }
        } else {
            Toasty.error(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT, true).show();
            etEmail.requestFocus();
            return false;
        }

        return true;
    }


    @OnClick(R.id.btnSignUp)
    public void SignUpBtnClicked() {

        if (validateUserSecondPageDetails()) {

            mProgressDlg.setMessage("Please Wait...\nSigning In");
            mProgressDlg.setIndeterminate(false);
            mProgressDlg.setCancelable(false);
            mProgressDlg.show();

            userName = etUserName.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            confirmPassword = etConformPassword.getText().toString().trim();
            firstName = etFirstName.getText().toString().trim();
            surName = etSurName.getText().toString().trim();
//            age = etAge.getText().toString().trim();
            age = spinnerBirthYear.getSelectedItem().toString()
                    + "/"
                    + (spinnerBirthMonth.getSelectedItemPosition() + 1)
                    + "/"
                    + spinnerBirthDay.getSelectedItem().toString();
            Log.i("Shree", age);
            birthPlace = tvUserBirthPlace.getText().toString();
            currentPlace = tvUserCurrentPlace.getText().toString();
            email = etEmail.getText().toString().trim();
            mobileNumber = etContact.getText().toString().trim();


            if (confirmPassword.equals("")) {
                Toasty.error(getApplicationContext(), "Confirm password field is empty", Toast.LENGTH_SHORT, true).show();
            } else {
                if (confirmPassword.equals(password)) {
                    convertDataToJson();

                } else {
                    mProgressDlg.dismiss();
                    Toasty.error(getApplicationContext(), "Password didn't matched.\n Try again", Toast.LENGTH_SHORT, true).show();
                }
            }

        } else {
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openYearView(DatePickerDialog datePicker) {
        try {
            Field mDelegateField = datePicker.getClass().getDeclaredField("mDelegate");
            mDelegateField.setAccessible(true);
            Object delegate = mDelegateField.get(datePicker);
            Method setCurrentViewMethod = delegate.getClass().getDeclaredMethod("setCurrentView", int.class);
            setCurrentViewMethod.setAccessible(true);
            setCurrentViewMethod.invoke(delegate, 1);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        }
    }


    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
//                etAge.setText(day1 + "/" + month1 + "/" + year1);

        }
    };

    public void convertDataToJson() {

        try {

            JSONObject header = new JSONObject();

            Log.d("SUSAN", "convertDataToJson: " + "\n"
                    + userName + "\n"
                    + password + "\n"
                    + confirmPassword + "\n"
                    + surName + "\n"
                    + age + "\n"
                    + gender + "\n"
                    + birthPlace + "\n"
                    + currentPlace + "\n"
                    + email + "\n"
                    + mobileNumber);

            header.put("username", userName);
            header.put("password", password);
            header.put("first_name", firstName);
            header.put("surname", surName);
            header.put("dob", age);
            header.put("gender", gender);
            header.put("birth_district", birthPlace);
            header.put("current_district", currentPlace);
            header.put("email", email);
            header.put("personal_mobile_number", mobileNumber);

            jsonToSend = header.toString();

            signUpRetrofitAPI(jsonToSend);

            Log.d("Signup", "convertDataToJson: " + jsonToSend.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void signUpRetrofitAPI(String jsonData) {

        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<SignUpDetailsResponse> call = apiService.getSignupDetails(jsonData);
        call.enqueue(new Callback<SignUpDetailsResponse>() {
            @Override
            public void onResponse(Call<SignUpDetailsResponse> call, Response<SignUpDetailsResponse> response) {
                if (mProgressDlg != null && mProgressDlg.isShowing()) {
                    mProgressDlg.dismiss();
                }
                if (response.body() == null) {
                    Toasty.error(getApplicationContext(), "null response", Toast.LENGTH_SHORT).show();
                    return;
                }

                handleSignupResponse(response.body());
            }

            private void handleSignupResponse(SignUpDetailsResponse signUpDetailsResponse) {
                switch (signUpDetailsResponse.getStatus()) {
                    case REQUEST_OK:
                        handleSuccess(signUpDetailsResponse);
                        break;

                    default:
                        Toasty.error(getApplicationContext(), signUpDetailsResponse.getData(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }


            private void handleSuccess(SignUpDetailsResponse signUpDetailsResponse) {

                Toasty.success(getApplicationContext(), signUpDetailsResponse.getData() + "\n Please Login with your Username", Toast.LENGTH_SHORT, true).show();
                startActivity(new Intent(getApplication(), LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<SignUpDetailsResponse> call, Throwable t) {
                if (mProgressDlg != null && mProgressDlg.isShowing()) {
                    mProgressDlg.dismiss();
                }
                String message = "Internet Connection Error!, please try again later";

                if (t instanceof SocketTimeoutException) {
                    message = "slow internet connection, please try again later";
                }
                Toasty.error(getApplicationContext(), "" + message, Toast.LENGTH_LONG, true).show();
            }
        });
    }

    @OnClick({R.id.btnPrev, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPrev:

                rlFirstInputPage.setVisibility(View.VISIBLE);
                rlSecondInputPage.setVisibility(View.GONE);
                rlTermsAndCondition.setVisibility(View.GONE);
                btnPrev.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.GONE);

                break;

            case R.id.btnNext:
                if (validateUserFirstPageDetails()) {
                    rlFirstInputPage.setVisibility(View.GONE);
                    rlSecondInputPage.setVisibility(View.VISIBLE);
                    rlTermsAndCondition.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnPrev.setVisibility(View.VISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                } else {
                    return;
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {

        DialogFactory.createActionDialog(this, "Warning!", "Your data will be lost. Do you want to exit Sign up?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SignUpActivity.super.onBackPressed();

                    }
                }).setNegativeButton("No", null)
                .show();
    }
}
