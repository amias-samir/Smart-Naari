package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.Constants;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;

//public class SignUpActivity extends Activity implements AdapterView.OnItemSelectedListener {
//public class SignUpActivity extends Activity implements View.OnFocusChangeListener {
public class SignUpActivity extends Activity {

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


    //todo write style for api < 21 for checkbox
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);

        setUpSpinners();


//change all letter to lower case
        etUserName.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase();
                    }
                }
        });


        

    }

    private void setUpSpinners() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = (thisYear-16); i >= (thisYear-106); i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinnerBirthYear.setAdapter(yearAdapter);

        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.months);
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


    @OnClick(R.id.btnSignUp)
    public void SignUpBtnClicked() {

        if (validateUserDetails()) {

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
            age=spinnerBirthYear.getSelectedItem().toString()
                    +"/"
                    +(spinnerBirthMonth.getSelectedItemPosition()+1)
                    +"/"
                    +spinnerBirthDay.getSelectedItem().toString();
            Log.i("Shree",age);
            birthPlace = spBirthPlace.getSelectedItem().toString();
            currentPlace = spCurrentPlace.getSelectedItem().toString();
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


//    @OnClick(R.id.user_age_input_id)
//    public void getDOB_BtnClicked() {
//        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
//
//// Create the DatePickerDialog instance
//        DatePickerDialog datePicker = new DatePickerDialog(this,
//                R.style.AppTheme, datePickerListener,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH));
//        datePicker.setCancelable(false);
//        datePicker.setTitle("Select the date");
//openYearView(datePicker);
//        datePicker.show();
//    }

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
            header.put("first_name", confirmPassword);
            header.put("surname", surName);
            header.put("dob", age);
            header.put("gender", gender);
            header.put("birth_district", birthPlace);
            header.put("current_district", currentPlace);
            header.put("email", email);
            header.put("personal_mobile_number", mobileNumber);

            jsonToSend = header.toString();

            signUpRetrofitAPI(jsonToSend);

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
                Log.d("SUSAN", "onPostExecute: " + response.toString());

                if (response.body() != null) {
                    String status = "";
                    String data = "";

                    try {

                        status = response.body().getStatus();
                        data = response.body().getData();

                        switch (status) {
                            case "200":
                                mProgressDlg.dismiss();
                                Toasty.success(getApplicationContext(), data + "\n Please Login with your Username", Toast.LENGTH_SHORT, true).show();
                                startActivity(new Intent(getApplication(), LoginActivity.class));
                                break;
                            case "201":
                                mProgressDlg.dismiss();
                                Toasty.error(getApplicationContext(), data, Toast.LENGTH_SHORT, true).show();
                                break;
                            case "406":
                                mProgressDlg.dismiss();
                                Toasty.error(getApplicationContext(), data, Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpDetailsResponse> call, Throwable t) {
                mProgressDlg.dismiss();
            }
        });
    }
}
