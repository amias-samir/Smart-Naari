package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.SignUpDetailsResponse;
import com.nepal.naxa.smartnaari.data.network.UrlClass;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.utils.DistrictAndAgeGroupConstants;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.NetworkApiClient.getNotifictionApiClient;

//public class SignUpActivity extends Activity implements AdapterView.OnItemSelectedListener {
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

    @BindView(R.id.user_age_input_id)
    EditText etAge;

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

    ProgressDialog mProgressDlg;
    String jsonToSend = "";

    String gender = "";
    String birthPlace = "";
    String currentPlace = "";


    //todo write style for api < 21 for checkbox
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);

        ArrayAdapter<String> birthDistArray= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, DistrictAndAgeGroupConstants.districtListEnglish);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBirthPlace.setAdapter(birthDistArray);
        birthPlace = spBirthPlace.getSelectedItem().toString();

        ArrayAdapter<String> currentDistArray= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, DistrictAndAgeGroupConstants.districtListEnglish);
        currentDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrentPlace.setAdapter(currentDistArray);
        currentPlace = spCurrentPlace.getSelectedItem().toString();
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
                gender = "Others";
                break;
        }
    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        int spinnerId = parent.getId();
//
//        if (spinnerId == R.id.spinner_user_birth_place_input_id) {
//            switch (position) {
//                case 0:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[0];
//                    break;
//                case 1:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[1];
//                    break;
//                case 2:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[2];
//                    break;
//                case 3:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[3];
//                    break;
//                case 4:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[4];
//                    break;
//                case 5:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[5];
//                    break;
//                case 6:
//                    birthPlace = DistrictAndAgeGroupConstants.districtListEnglish[6];
//                    break;
//            }
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }


    @OnClick(R.id.btnSignUp)
    public void SignUpBtnClicked() {

        mProgressDlg.setMessage("Please Wait...\nLogging In");
        mProgressDlg.setIndeterminate(false);
        mProgressDlg.setCancelable(false);
        mProgressDlg.show();

        convertDataToJson();

        signUpRetrofitAPI(jsonToSend);

        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.user_age_input_id)
    public void getDOB_BtnClicked(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

// Create the DatePickerDialog instance
        DatePickerDialog datePicker = new DatePickerDialog(this,
                R.style.AppTheme, datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select the date");
        datePicker.show();

    }

    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            etAge.setText(day1 + "/" + month1 + "/" + year1);

        }
    };

    public void convertDataToJson() {
        //function in the activity that corresponds to the layout button

        try {

            JSONObject header = new JSONObject();

            header.put("username", etUserName.getText());
            header.put("password", etPassword.getText());
            header.put("first_name", etFirstName.getText());
            header.put("surname", etSurName.getText());
            header.put("dob", etAge.getText());
            header.put("gender", gender);
            header.put("birth_district", birthPlace);
            header.put("current_district", currentPlace);
            header.put("email", etEmail.getText());
//            header.put("personal_mobile_number", "");
//            header.put("circle_mobile_number_1", "");
//            header.put("circle_mobile_number_2", "");
//            header.put("circle_mobile_number_3", "");
//            header.put("circle_mobile_number_4", "");
//            header.put("circle_mobile_number_5", "");
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void signUpRetrofitAPI(String jsonData) {

        NetworkApiInterface apiService = getNotifictionApiClient().create(NetworkApiInterface.class);
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

                        if (status.equals("406")) {
                            mProgressDlg.dismiss();
                            Toast.makeText(SignUpActivity.this, data, Toast.LENGTH_SHORT).show();

                        } else if (status.equals("201")) {
                            mProgressDlg.dismiss();
                            Toast.makeText(SignUpActivity.this, data, Toast.LENGTH_SHORT).show();

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
