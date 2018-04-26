package com.nepal.naxa.smartnaari.userprofileupdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileUpdateActivity extends BaseActivity {

    private static final String TAG ="ProfileUpdate" ;
    String gender;
    String imagePath="";

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


    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);

        initToolbar();
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

    private void initPreviousDataSetup(){
        UserData userData = sessionManager.getUser();

        tvUserFirstname.setText(userData.getFirstName());
        tvUserSurname.setText(userData.getSurname());
        tvUserName.setText(userData.getUsername());
        tvUserContactNo.setText(userData.getPersonalMobileNumber());
        tvUserBirthPlace.setText(userData.getBirthDistrict());
        tvUserCurrentPlace.setText(userData.getCurrentDistrict());
        tvUserEmailInputId.setText(userData.getEmail());

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

    @OnClick({R.id.btnUpdateProfile, R.id.fab_take_user_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateProfile:
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

                imagePath = result.getOriginalUri().toString();
                Log.d(TAG, "onActivityResult: URI"+result.getUri().toString());


                Toast.makeText(
                        this, "Cropping successful: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
