package com.nepal.naxa.smartnaari.machupbasdina;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.YuwaPustaQueryResponse;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.service.MaChupBasdinaResponse;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;

public class MaChupBasdinaActivity extends BaseActivity {


    private static final String TAG = "MaChupBasdinaActivity";

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

    private String u_id = "", u_name = "", u_address = "", u_ph_num = "", u_email = "", reporting_for = "Myself", incident_district = "",
            voilence_type = "", voilence_occur_time = "", prepetrator = "", desc_GBV = "";

    private String KEY_USER_ID = "u_id";
    private String KEY_USER_NAME = "u_name";
    private String KEY_USER_ADDRESS = "u_address";
    private String KEY_USER_PH_NUM = "u_ph_num";
    private String KEY_USER_EMAIL = "u_email";
    private String KEY_REPORTING_FOR = "reporting_for";
    private String KEY_INCIDENT_DISTRICT = "incident_district";
    private String KEY_VOILENCE_TYPE = "voilence_type";
    private String KEY_VOILENCE_OCCUR_TIME = "voilence_occur_time";
    private String KEY_PREPETRATOR = "prepetrator";
    private String KEY_GBV_DESC = "desc_GBV";

    private String jsonToSend = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_chup_basdina);
        ButterKnife.bind(this);


        //initialize toolbar
        initToolbar();

        //initialize spinner dat adapter
        initSpinnerData();


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
                    // reporting for myself
                    reporting_for = "Myself";

                break;
            case R.id.radio_machupbasdina_victim_other:
                if (checked)
                    // reporting for some one within the family
                    reporting_for = "Some one within the family";

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

        showLoading("Sending ... \n Please Wait! ");

//        validate user input data
        validateData();

//        convert user data to JSON
        convertDataToJson();

//        send converted JSON data to server
        sendDataToServer();

    }


    public void initSpinnerData() {
        ArrayAdapter<String> birthDistArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.districtListEnglish);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrictOfIncident.setAdapter(birthDistArray);

        ArrayAdapter<String> voilenceType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.voilenceType);
        voilenceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfViolence.setAdapter(voilenceType);

        ArrayAdapter<String> voilenceOccurTimeArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.voilenceOccurTime);
        birthDistArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoilenceOccur.setAdapter(voilenceOccurTimeArray);
    }

    public void validateData() {

//        if (spinnerNoConsent.getSelectedItem().toString().isEmpty() || spinnerNoConsent.getSelectedItem().toString().equals("")) {
//
//            showInfoToast("Please select No Consent option.");
//            return;
//        }

        if (spinnerDistrictOfIncident.getSelectedItem().toString().isEmpty() || spinnerDistrictOfIncident.getSelectedItem().toString().equals("Choose District")) {

            showInfoToast("Please select District of incident.");
            return;
        }

        if (spinnerTypeOfViolence.getSelectedItem().toString().isEmpty() || spinnerTypeOfViolence.getSelectedItem().toString().equals("Select Voilence Type")) {

            showInfoToast("Please select voilence type.");
            return;
        }

        if (spinnerVoilenceOccur.getSelectedItem().toString().isEmpty() || spinnerVoilenceOccur.getSelectedItem().toString().equals("Select time")) {

            showInfoToast("Please select voilence occured time.");
            return;
        }

        if (tvPerpetrator.getText().toString().isEmpty() || tvPerpetrator.getText().toString().equals("")) {

            showInfoToast("Perpetrator field cannot be empty");
            return;
        }

        if (tvDescGBVInputId.getText().toString().isEmpty() || tvDescGBVInputId.getText().toString().equals("")) {

            showInfoToast("Further details of the reported GBV cannot be empty");
            return;
        }

        if (tvDescGBVInputId.getText().toString().length() > 250) {

            showInfoToast("Further details of the reported GBV cannot be greter than 250 character");
            return;
        }
    }

    public void convertDataToJson() {

        SessionManager sessionManager = new SessionManager(this);


        try {
            JSONObject header = new JSONObject();
            header.put(KEY_USER_ID, sessionManager.getUser().getUserId());
            header.put(KEY_USER_NAME, sessionManager.getUser().getUsername());
            header.put(KEY_USER_ADDRESS, sessionManager.getUser().getCurrentDistrict());
            header.put(KEY_USER_PH_NUM, sessionManager.getUser().getPersonalMobileNumber());
            header.put(KEY_USER_EMAIL, sessionManager.getUser().getEmail());
            header.put(KEY_REPORTING_FOR, reporting_for);
            header.put(KEY_INCIDENT_DISTRICT, spinnerDistrictOfIncident.getSelectedItem().toString());
            header.put(KEY_VOILENCE_TYPE, spinnerTypeOfViolence.getSelectedItem().toString());
            header.put(KEY_VOILENCE_OCCUR_TIME, spinnerVoilenceOccur.getSelectedItem().toString());
            header.put(KEY_PREPETRATOR, tvPerpetrator.getText().toString());
            header.put(KEY_GBV_DESC, tvDescGBVInputId.getText().toString());

            jsonToSend = header.toString();

            Log.d(TAG, "convertDataToJson: SAMIR" + jsonToSend);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendDataToServer() {

        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<MaChupBasdinaResponse> call = apiService.getMaChupBasdinaDetails(jsonToSend);
        call.enqueue(new Callback<MaChupBasdinaResponse>() {
            @Override
            public void onResponse(Call<MaChupBasdinaResponse> call, Response<MaChupBasdinaResponse> response) {
                Log.d(TAG, "onPostExecute: " + response.toString());

                hideLoading();

                if (response.body() == null) {
                    showErrorToast("Failed to send \n unknown error occured");
                    return;
                }

                if (response.body() != null) {
                    String status = "";
                    String data = "";

                    try {

                        status = response.body().getStatus();
                        data = response.body().getData();

                        switch (status) {
                            case "200":
                                showInfoToast(data);
                                hideLoading();
                                break;
                            case "201":
                                showInfoToast(data);
                                hideLoading();

                                break;
                            case "406":
                                showInfoToast(data);
                                hideLoading();
                                break;

                            default:
                                showInfoToast(data);
                                hideLoading();
                        }
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<MaChupBasdinaResponse> call, Throwable t) {
                hideLoading();
            }
        });
    }


}
