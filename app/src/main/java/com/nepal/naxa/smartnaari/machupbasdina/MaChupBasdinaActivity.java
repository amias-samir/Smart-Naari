package com.nepal.naxa.smartnaari.machupbasdina;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.service.MaChupBasdinaResponse;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.WordsWithDetailsActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
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
    @BindView(R.id.tvLBL_Consent)
    TextView tvLBLConsent;
    @BindView(R.id.tvLBL_no_consent)
    TextView tvLBLNoConsent;
    @BindView(R.id.txtLBLUnderstanding)
    TextView txtLBLUnderstanding;

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

//        initialize tooltagget view
        toolTargetViewConsentNoconsent();


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
                    reporting_for = "Family Members";

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


//        validate user input data
        if (validateData()) {

            showLoading("Sending ... \nPlease Wait! ");
//        convert user data to JSON
            convertDataToJson();

//        send converted JSON data to server
            sendDataToServer();
        }

    }

    @OnItemSelected(R.id.spinner_ma_chup_basdina_no_consent)
    public void onSpinnerNoConsentClicked() {

        if(!spinnerNoConsent.getSelectedItem().toString().equals("Select Type")){
            Intent glossaryIntent = new Intent(MaChupBasdinaActivity.this, WordsWithDetailsActivity.class);
            startActivity(glossaryIntent);
        }

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

        ArrayAdapter<String> noConsent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.voilenceType);
        voilenceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNoConsent.setAdapter(voilenceType);
    }

    public boolean validateData() {

//        if (spinnerNoConsent.getSelectedItem().toString().isEmpty() || spinnerNoConsent.getSelectedItem().toString().equals("")) {
//
//            showInfoToast("Please select No Consent option.");
//            return;
//        }

        if (spinnerDistrictOfIncident.getSelectedItem().toString().isEmpty() || spinnerDistrictOfIncident.getSelectedItem().toString().equals("Choose District")) {

            showInfoToast("Please select District of incident.");
            return false;
        }

        if (spinnerTypeOfViolence.getSelectedItem().toString().isEmpty() || spinnerTypeOfViolence.getSelectedItem().toString().equals("Select Voilence Type")) {

            showInfoToast("Please select voilence type.");
            return false;
        }

        if (spinnerVoilenceOccur.getSelectedItem().toString().isEmpty() || spinnerVoilenceOccur.getSelectedItem().toString().equals("Select time")) {

            showInfoToast("Please select voilence occured time.");
            return false;
        }

        if (tvPerpetrator.getText().toString().isEmpty() || tvPerpetrator.getText().toString().equals("")) {

            showInfoToast("Perpetrator field cannot be empty");
            return false;
        }

//        if (tvDescGBVInputId.getText().toString().isEmpty() || tvDescGBVInputId.getText().toString().equals("")) {
//
//            showInfoToast("Further details of the reported GBV cannot be empty");
//            return false;
//        }

        if (tvDescGBVInputId.getText().toString().length() > 500) {

            showInfoToast("Further details of the reported GBV cannot be greter than 250 character");
            return false;
        }
        return true;
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


    public void toolTargetViewConsentNoconsent() {

        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
//        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_android_black_24dp);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, 0, 0);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final SpannableString sassyDesc = new SpannableString("It allows you to go back");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "somtimes".length(), sassyDesc.length(), 0);

        // We have a sequence of targets, so lets build it!
        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forToolbarNavigationIcon(toolbar, "This is the back button", sassyDesc)
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forView(tvLBLConsent, "Permission or agreement. It is a voluntary actby a person, willingly given to another person or persons.", "Consent or a consensual act, it is always positive, an enthusiastic affirmation that both people and/or more have mutually agreed to engage in the activity.")
                                .cancelable(false)
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .tintTarget(false)
                                .id(2),
                        // You can also target the overflow button in your toolbar
                        TapTarget.forView(tvLBLNoConsent, "Permission or agreement is not given voluntarily by a person or persons to another person or persons. It always means the act in whatever form or type is unwelcome.", "All acts and forms of Gender Based Violence involve no consent and each one of them is termed a non-consensual act (rape,sexual assault, physical assault, denial of resources andopportunities, psychological/emotional abuse).")
                                .cancelable(false)
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .tintTarget(false)
                                .id(3)

                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
//                        ((TextView) findViewById(R.id.tvLBL_no_consent)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(MaChupBasdinaActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        // You don't always need a sequence, and for that there's a single time tap target
        final SpannableString spannedDesc = new SpannableString("You need to understand first what is Consent and No Consent");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "Consent and No Consent".length(), spannedDesc.length(), 0);
//        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "No Consent".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.txtLBLUnderstanding), "Ma Chup Basdina", spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .dimColor(android.R.color.black)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(android.R.color.black)
                .transparentTarget(true)
                .textColor(android.R.color.black)
                .titleTextDimen(R.dimen.material_text_title)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                // .. which evidently starts the sequence we defined earlier
                sequence.start();
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetView", "You dismissed me :(");
            }
        });
    }

}
