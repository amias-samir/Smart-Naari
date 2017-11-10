package com.nepal.naxa.smartnaari.machupbasdina;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaChupBasdinaActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.spinner_ma_chup_basdina_no_consent)
    Spinner spinnerNoConsent;
    @BindView(R.id.radio_machupbasdina_victim_myself)
    RadioButton radioVictimMyself;
    @BindView(R.id.radio_machupbasdina_victim_other)
    RadioButton radioVictimOther;
    @BindView(R.id.tv_district_of_incident)
    TextView tvDistrictOfIncident;
    @BindView(R.id.spinner_ma_chup_basdina_district_of_incident)
    Spinner spinnerDistrictOfIncident;
    @BindView(R.id.spinner_type_of_violence)
    Spinner spinnerTypeOfViolence;
    @BindView(R.id.spinner_ma_chup_basdina_voilence_occur)
    Spinner spinnerVoilenceOccur;
    @BindView(R.id.ma_chup_basdina_perpetrator)
    EditText tvPerpetrator;
    @BindView(R.id.ma_chup_basdina_desc_GBV_input_id)
    EditText tvDescGBVInputId;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_chup_basdina);
        ButterKnife.bind(this);

        initToolbar();

        ArrayAdapter<String> birthDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.districtListEnglish);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictOfIncident.setAdapter(birthDistArray);

        ArrayAdapter<String> voilenceOccurTimeArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.voilenceOccurTime);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoilenceOccur.setAdapter(voilenceOccurTimeArray);
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Report a case");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_machupbasdina_victim_myself:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_machupbasdina_victim_other:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) { w
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSignUp)
    public void onViewClicked() {

        validateData();

    }

    public void validateData() {

//        if (spinnerNoConsent.getSelectedItem().toString().isEmpty() || spinnerNoConsent.getSelectedItem().toString().equals("")) {
//
//            showInfoToast("Please select No Consent option.");
//            return;
//        }

        if (spinnerDistrictOfIncident.getSelectedItem().toString().isEmpty() || spinnerDistrictOfIncident.getSelectedItem().toString().equals("Choose District")){

            showInfoToast("Please select District of incident.");
            return;
        }

//        if(spinnerTypeOfViolence.getSelectedItem().toString().isEmpty()){
//
//            showInfoToast("Please select types of voilence.");
//            return;
//        }

        if(spinnerVoilenceOccur.getSelectedItem().toString().isEmpty() || spinnerVoilenceOccur.getSelectedItem().toString().equals("Select time")){

            showInfoToast("Please select voilence occured time.");
            return;
        }

        if(tvPerpetrator.getText().toString().isEmpty() || tvPerpetrator.getText().toString().equals("")){

            showInfoToast("Perpetrator field cannot be empty");
            return;
        }

        if(tvDescGBVInputId.getText().toString().isEmpty() || tvDescGBVInputId.getText().toString().equals("")){

            showInfoToast("Further details of the reported GBV cannot be empty");
            return;
        }

        if(tvDescGBVInputId.getText().toString().length() >250){

            showInfoToast("Further details of the reported GBV cannot be greter than 250 character");
            return;
        }
    }
}
