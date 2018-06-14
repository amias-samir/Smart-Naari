package com.nepal.naxa.smartnaari.userprofileupdate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.ProfileUpdateResponse;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.NetworkUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_OK;
import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;

public class UserProfileUpdateActivity extends BaseActivity {

    private static final String TAG = "ProfileUpdate";

    public static String KEY_MALE = "Male";
    public static String KEY_FEMALE = "Female";
    public static String KEY_OTHERS = "Others";

    ArrayAdapter<String> birthDistArray;
    ArrayAdapter<String> currentDistArray;

    String imageName = null;

    ProgressDialog mProgressDlg;
    String jsonToSend = "";

    String userName = "";
    String firstName = "";
    String surName = "";
    String age = "";
    String gender = "";
    String birthPlace = "";
    String currentPlace = "";
    String email = "";
    String mobileNumber = "";

    @BindView(R.id.iv_member_image)
    CircleImageView ivMemberImage;
    @BindView(R.id.user_firstname_input_id)
    EditText tvUserFirstname;
    @BindView(R.id.user_surname_input_id)
    EditText tvUserSurname;
    @BindView(R.id.user_name_input_id)
    EditText tvUserName;
    @BindView(R.id.user_contact_no_input_id)
    EditText tvUserContactNo;
    @BindView(R.id.spinner_birth_year)
    Spinner spinnerBirthYear;
    @BindView(R.id.spinner_birth_month)
    Spinner spinnerBirthMonth;
    @BindView(R.id.spinner_birth_day)
    Spinner spinnerBirthDay;
    @BindView(R.id.radio_sex_male)
    RadioButton radioSexMale;
    @BindView(R.id.radio_sex_female)
    RadioButton radioSexFemale;
    @BindView(R.id.radio_sex_other)
    RadioButton radioSexOther;
    @BindView(R.id.is_checked_radio_btn_group)
    RadioGroup isCheckedRadioBtnGroup;
    @BindView(R.id.tv_user_birth_place_input_id)
    AutoCompleteTextView tvUserBirthPlace;
    @BindView(R.id.tv_user_current_place_input_id)
    AutoCompleteTextView tvUserCurrentPlace;
    @BindView(R.id.user_email_input_id)
    EditText tvUserEmailInputId;
    @BindView(R.id.btnUpdateProfile)
    Button btnUpdateProfile;
    @BindView(R.id.fab_take_user_photo)
    FloatingActionButton fabTakeUserPhoto;


    SessionManager sessionManager;
    UserData userData;

    private boolean hasNewImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        userData = sessionManager.getUser();


        mProgressDlg = new ProgressDialog(this);

        initToolbar();
        setUpSpinners();
        setUpAutoCompleteDistrict();
        initPreviousDataSetup();

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile Update");
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    }

    private void setUpAutoCompleteDistrict() {
        currentDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.birthDistrictListEnglish);
        birthDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.birthDistrictListEnglish);
        tvUserBirthPlace.setAdapter(birthDistArray);
        tvUserCurrentPlace.setAdapter(currentDistArray);
    }

    private void initPreviousDataSetup() {

        tvUserFirstname.setText(userData.getFirstName());
        tvUserSurname.setText(userData.getSurname());
        tvUserName.setText(userData.getUsername());
        tvUserContactNo.setText(userData.getPersonalMobileNumber());
        tvUserBirthPlace.setText(userData.getBirthDistrict());
        tvUserCurrentPlace.setText(userData.getCurrentDistrict());
        tvUserEmailInputId.setText(userData.getEmail());

        initSpinnerDOB(userData);

        initRadioGenderButton(userData);

        if (!TextUtils.isEmpty(userData.getImagePath())) {

            if(NetworkUtils.isNetworkDisconnected(this)) {
                Glide
                        .with(this)
                        .load(userData.getImagePath())
                        .into(ivMemberImage);
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getApplicationContext()).clearDiskCache();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide
                                        .with(getApplicationContext())
                                        .load(userData.getImagePath())
                                        .into(ivMemberImage);
                            }
                        });
                    }
                }).start();
            }
        } else {
            ivMemberImage.setImageResource(R.drawable.default_avatar);
        }

    }

    private void initSpinnerDOB(UserData userData) {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = (thisYear - 16); i >= (thisYear - 106); i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.months);

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);


        String rawDOB = userData.getDob();
        Log.d(TAG, "initSpinnerDOB: " + rawDOB);
        String[] parts = rawDOB.split("/");
        String day = parts[0]; // year
        int month = Integer.parseInt(parts[1]); // month
        String year = parts[2]; // day

        spinnerBirthYear.setSelection(yearAdapter.getPosition(year));
        spinnerBirthMonth.setSelection(month - 1);
        spinnerBirthDay.setSelection(dayAdapter.getPosition(day));
    }

    private void initRadioGenderButton(UserData userData) {

        if (userData.getGender().equals(KEY_MALE)) {
            radioSexMale.setChecked(true);
            radioSexFemale.setChecked(false);
            radioSexOther.setChecked(false);

        }

        if (userData.getGender().equals(KEY_FEMALE)) {
            radioSexMale.setChecked(false);
            radioSexFemale.setChecked(true);
            radioSexOther.setChecked(false);

        }

        if (userData.getGender().equals(KEY_OTHERS)) {
            radioSexMale.setChecked(false);
            radioSexFemale.setChecked(false);
            radioSexOther.setChecked(true);

        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_sex_male:
                if (checked)
                    gender = KEY_MALE;
                break;
            case R.id.radio_sex_female:
                if (checked)
                    gender = KEY_FEMALE;
                break;
            case R.id.radio_sex_other:
                if (checked)
                    gender = KEY_OTHERS;
                break;
        }
    }

    @OnClick({R.id.btnUpdateProfile, R.id.fab_take_user_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateProfile:

                if (validateUserDetails()) {
                    mProgressDlg.setMessage("Please Wait...\nUpdating profile");
                    mProgressDlg.setIndeterminate(false);
                    mProgressDlg.setCancelable(false);
                    mProgressDlg.show();
                    convertDataToJson();
                }

                break;
            case R.id.fab_take_user_photo:
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ivMemberImage.setImageURI(result.getUri());

//                imageName = result.getUri().toString();
                Log.d(TAG, "onActivityResult: URI" + result.getUri().toString());

                hasNewImage = true;


                Toast.makeText(
                        this, "Cropping successful: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validateUserDetails() {

        userName = tvUserName.getText().toString().trim();
        if (userName.equals("")) {
            Toasty.error(getApplicationContext(), "Username field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserName.requestFocus();
            return false;
        }


        firstName = tvUserFirstname.getText().toString().trim();
        if (firstName.equals("")) {
            Toasty.error(getApplicationContext(), "Name field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserFirstname.requestFocus();
            return false;
        }


        surName = tvUserSurname.getText().toString().trim();
        if (surName.equals("")) {
            Toasty.error(getApplicationContext(), "Sur name field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserSurname.requestFocus();
            return false;
        }


        age = spinnerBirthYear.getSelectedItem().toString()
                + "/"
                + (spinnerBirthMonth.getSelectedItemPosition() + 1)
                + "/"
                + spinnerBirthDay.getSelectedItem().toString();


        int id = isCheckedRadioBtnGroup.getCheckedRadioButtonId();
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
            isCheckedRadioBtnGroup.requestFocus();
            return false;
        }


        //        birthPlace = spBirthPlace.getSelectedItem().toString();
        birthPlace = tvUserBirthPlace.getText().toString();
        int birthDistPos = birthDistArray.getPosition(birthPlace);
        Log.d(TAG, "validateUserSecondPageDetails: BirthDistrict" + birthDistPos);

        if (TextUtils.isEmpty(birthPlace)) {
            Toasty.error(getApplicationContext(), "Birth District field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserBirthPlace.requestFocus();
            return false;
        }
        if (birthDistPos < 0) {
            Toasty.error(getApplicationContext(), "Birth District is invalid", Toast.LENGTH_SHORT, true).show();
            tvUserBirthPlace.requestFocus();
            return false;
        }


//        currentPlace = spCurrentPlace.getSelectedItem().toString();
        currentPlace = tvUserCurrentPlace.getText().toString();
        int currentDistPos = currentDistArray.getPosition(currentPlace);
        Log.d(TAG, "validateUserSecondPageDetails: currentDistrict" + currentDistPos);
        if (TextUtils.isEmpty(currentPlace)) {
            Toasty.error(getApplicationContext(), "Current District field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserCurrentPlace.requestFocus();
            return false;
        }
        if (currentDistPos < 0) {
            Toasty.error(getApplicationContext(), "Current District is invalid", Toast.LENGTH_SHORT, true).show();
            tvUserCurrentPlace.requestFocus();
            return false;
        }


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        email = tvUserEmailInputId.getText().toString().trim();
        if (!email.equals("")) {
            if (email.matches(emailPattern)) {
//                Toasty.success(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(getApplicationContext(), "Invalid email address. \n Try again.", Toast.LENGTH_SHORT, true).show();
                tvUserEmailInputId.requestFocus();
                return false;
            }
        } else {
            Toasty.error(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserEmailInputId.requestFocus();
            return false;
        }


        mobileNumber = tvUserContactNo.getText().toString().trim();
        if (mobileNumber.equals("")) {
            Toasty.error(getApplicationContext(), "Contact field is empty", Toast.LENGTH_SHORT, true).show();
            tvUserContactNo.requestFocus();
            return false;
        }
        if (mobileNumber.length() < 10) {
            Toasty.error(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT, true).show();
            tvUserContactNo.requestFocus();
            return false;
        }

        return true;
    }

    public void convertDataToJson() {

        try {

            JSONObject header = new JSONObject();

            header.put("user_id", userData.getUserId());
            header.put("username", userName);
            header.put("first_name", firstName);
            header.put("surname", surName);
            header.put("dob", age);
            header.put("gender", gender);
            header.put("birth_district", birthPlace);
            header.put("current_district", currentPlace);
            header.put("email", email);
            header.put("personal_mobile_number", mobileNumber);

            jsonToSend = header.toString();
            Log.d(TAG, "convertDataToJson: " + jsonToSend.toString());


            sendDataToServer(jsonToSend);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String saveToExternalSorage(Bitmap thumbnail) {
        // TODO Auto-generated method stub
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();

        imageName = userData.getUsername() + timeInMillis + ".jpg";

        File file1 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), imageName);
//        if (!file1.mkdirs()) {
//            Toast.makeText(getApplicationContext(), "Not Created", Toast.LENGTH_SHORT).show();
//        }

        if (file1.exists()) file1.delete();
        try {
            FileOutputStream out = new FileOutputStream(file1);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), "Saved " + imageName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "saveToExternalSorage: " + imageName);
        return imageName;
    }

    Uri ImageToBeUploaded;
    File imageFile;
    MultipartBody.Part body = null;
    Bitmap bitmap;

    private void sendDataToServer(String jsonData) {

        BitmapDrawable drawable = (BitmapDrawable) ivMemberImage.getDrawable();
        bitmap = drawable.getBitmap();

        if (hasNewImage) {
            File file1 = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), saveToExternalSorage(bitmap));
            String image_Path = file1.getAbsolutePath();
//            Log.d(TAG, "sendDataToServer: imagePath "+image_Path);

            imageFile = new File(image_Path);
            if (imageFile.exists()) {
//                Log.d(TAG, "sendDataToServer: image file exist");
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                body = MultipartBody.Part.createFormData("user_pic", imageFile.getName(), surveyBody);

            } else {
                body = null;
            }
        }


        RequestBody data = RequestBody.create(MediaType.parse("text/plain"), jsonData);
        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<ProfileUpdateResponse> call = apiService.getProfileUpdateDetails(body, data);


        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                if (mProgressDlg != null && mProgressDlg.isShowing()) {
                    mProgressDlg.dismiss();
                }
                if (response.body() == null) {
                    Toasty.error(getApplicationContext(), "null response", Toast.LENGTH_LONG).show();
                    return;
                }

                handleProfileUpdateResponse(response.body());
                Log.d(TAG, "onResponse: got response");
            }

            private void handleProfileUpdateResponse(ProfileUpdateResponse profileUpdateResponse) {
                switch (profileUpdateResponse.getStatus()) {
                    case REQUEST_OK:
                        handleSuccess(profileUpdateResponse);
                        Log.d(TAG, "handleProfileUpdateResponse: 200");
                        break;

                    default:
                        Toasty.error(getApplicationContext(), profileUpdateResponse.getData(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }


            private void handleSuccess(ProfileUpdateResponse profileUpdateResponse) {

                UserData userData = sessionManager.getUser();

                userData.setImagePath(profileUpdateResponse.getUserData().getImagePath());
                userData.setFirstName(profileUpdateResponse.getUserData().getFirstName());
                userData.setSurname(profileUpdateResponse.getUserData().getSurname());
                userData.setPersonalMobileNumber(profileUpdateResponse.getUserData().getPersonalMobileNumber());
                userData.setDob(profileUpdateResponse.getUserData().getDob());
                userData.setGender(profileUpdateResponse.getUserData().getGender());
                userData.setCurrentDistrict(profileUpdateResponse.getUserData().getCurrentDistrict());
                userData.setBirthDistrict(profileUpdateResponse.getUserData().getBirthDistrict());
                userData.setEmail(profileUpdateResponse.getUserData().getEmail());

                Log.d(TAG, "handleSuccess: "+profileUpdateResponse.getUserData().getImagePath());


                if (hasNewImage) {
                    sessionManager.saveUser(userData);
                } else {
//                    UserData userDataNew = profileUpdateResponse.getUserData();
//                    userDataNew.setImagePath(userData.getImagePath());
                    sessionManager.saveUser(userData);
                }
                Toasty.success(getApplicationContext(), profileUpdateResponse.getData(), Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                if (mProgressDlg != null && mProgressDlg.isShowing()) {
                    mProgressDlg.dismiss();
                }
                String message = "Internet Connection Error!, please try again later";
                Log.d(TAG, "onFailure: ");

                if (t instanceof SocketTimeoutException) {
                    message = "slow internet connection, please try again later";
                }
                Toasty.error(getApplicationContext(), "Failed to update ", Toast.LENGTH_LONG, true).show();
            }
        });
    }
}
