package com.nepal.naxa.smartnaari.machupbasdina;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.ServicesData;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.data.network.service.MaChupBasdinaResponse;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.DataGlossaryWordDetailsActivity;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.WordsWithDetailsModel;
import com.nepal.naxa.smartnaari.services.ServicesActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.ui.DialogFactory;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;

public class MaChupBasdinaActivity extends BaseActivity {

    Context context = this;


    private static final String TAG = "MaChupBasdinaActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.spinner_ma_chup_basdina_no_consent)
    Spinner spinnerNoConsent;
    @BindView(R.id.radio_machupbasdina_victim_myself)
    RadioButton radioVictimMyself;
    @BindView(R.id.radio_machupbasdina_victim_family_members)
    RadioButton radioFamilyMembers;
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
    @BindView(R.id.tv_type_of_violence)
    TextView tvTypeOfViolence;


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


    ServicesListDialogAdapter servicesListDialogAdapter;


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
        RunAnimation();

        //initialize tutoshowcase, similar to tooltagget view
//        initTutoShow();


        tvDescGBVInputId.setHint(R.string.ma_chup_basdina_other_info_hint);
        tvDescGBVInputId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvDescGBVInputId.setHint("");
                    Log.d(TAG, "onFocusChange: " + "Focus");
                } else {
                    tvDescGBVInputId.setHint(R.string.ma_chup_basdina_other_info_hint);
                    Log.d(TAG, "onFocusChange: " + "Release");

                }
            }
        });
    }


    private void initTutoShow() {
        final TutoShowcase view1 = TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                        initTutoShow2();
                    }
                })
                .setContentView(R.layout.tuto_showcase_consent)
                .setFitsSystemWindows(true)
                .on(R.id.tvLBL_Consent)
                .addRoundRect()
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MaChupBasdinaActivity.this, "Click", Toast.LENGTH_SHORT).show();
                    }
                })
                .onClickContentView(R.id.btn_learn_more, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchAndLoadGlossary("Consent");
                    }
                })
                .show();


        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                view1.dismiss();
                initTutoShow2();
            }
        });
    }

    private void initTutoShow2() {
        final TutoShowcase view = TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                    }
                })
                .setContentView(R.layout.tuto_showcase_no_concent)
                .setFitsSystemWindows(true)
                .on(R.id.tvLBL_no_consent)
                .addRoundRect()
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MaChupBasdinaActivity.this, "Click", Toast.LENGTH_SHORT).show();
                    }
                })
                .onClickContentView(R.id.btn_learn_more, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchAndLoadGlossary("No Consent");
                    }
                })
                .show();


        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.dismiss();
            }
        });

    }

//    private void initTutoShow() {
//        final TutoShowcase view = TutoShowcase.from(this)
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//
//
//                        Toast.makeText(MaChupBasdinaActivity.this, "Tutorial dismissed", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setContentView(R.layout.tuto_showcase)
//                .setFitsSystemWindows(true)
//                .on(R.id.con)
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MaChupBasdinaActivity.this, "Click", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .onClickContentView(R.id.btn_learn_more, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MaChupBasdinaActivity.this, "Open Definition", Toast.LENGTH_SHORT).show();
//                    }
//                })
//
//
//                .show();
//
//
//        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.dismiss();
//            }
//        });


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
            case R.id.radio_machupbasdina_victim_family_members:
                if (checked)
                    // reporting for some one within the family
                    reporting_for = "Family Members";

                break;

            case R.id.radio_machupbasdina_victim_other:
                if (checked)
                    // reporting for some one within the family
                    reporting_for = "Others";

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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.item_call:
                Intent intent = new Intent(MaChupBasdinaActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

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


    @OnClick(R.id.tv_type_of_violence)
    public void onVoilenceTypeLBLViewClicked() {

        if (!spinnerTypeOfViolence.getSelectedItem().toString().equals("Select Type")) {
            final String selectedItem = spinnerTypeOfViolence.getSelectedItem().toString();
//            final ProgressDialog dialog = DialogFactory.createProgressDialog(MaChupBasdinaActivity.this,
//                    "Loading definition of " + selectedItem);
//            dialog.setCancelable(false);


            searchAndLoadGlossary(selectedItem);


        }
    }

    @OnItemSelected(R.id.spinner_type_of_violence)
    public void onSpinnerTypeOfVoilenceClicked() {

        if (!spinnerTypeOfViolence.getSelectedItem().toString().equals("Select Type")) {

            tvTypeOfViolence.setTextColor(getResources().getColor(R.color.blue));

            Animation a = AnimationUtils.loadAnimation(this, R.anim.shake);
            a.reset();
            tvTypeOfViolence.clearAnimation();
            tvTypeOfViolence.startAnimation(a);

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

//        ArrayAdapter<String> noConsent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConstantData.noConsentType);
//        voilenceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerNoConsent.setAdapter(noConsent);
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
                                showServicesListDialog(data);

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


    //Tap target view ko detail haru # purano
    public void toolTargetViewConsentNoconsent() {

        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, 0, 0);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final SpannableString sassyDesc = new SpannableString("It allows you to go back");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "somtimes".length(), sassyDesc.length(), 0);

        // We have a sequence of targets, so lets build it!
        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
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
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.txtLBLUnderstanding), "")
                .cancelable(false)
                .drawShadow(true)
                .dimColor(android.R.color.black)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(android.R.color.black)
                .targetRadius(display.getWidth() / 4)
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
                RunAnimation();
            }
        });
    }


    public void showServicesListDialog(String response) {

        List<ServicesData> servicesnearincident = appDataManager.getAllServicesdataNearIncident(spinnerDistrictOfIncident.getSelectedItem().toString().toLowerCase().trim());
        Log.d(TAG, "showServicesListDialog: " + servicesnearincident.size());

        for (int i = 0; i < servicesnearincident.size(); i++) {
            Log.d(TAG, "showServicesListDialog: " + servicesnearincident.get(i).getOfficeName());

        }


        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(this);
        showDialog.setContentView(R.layout.services_list_near_incident_dialog_layout);

//         initialize
        RecyclerView rvServicesNearIncident;
        TextView tvReportResponse;
        Button btnLinkToServices = (Button) showDialog.findViewById(R.id.btn_see_all_services);
        rvServicesNearIncident = (RecyclerView) showDialog.findViewById(R.id.rv_services_near_incident);
        tvReportResponse = (TextView) showDialog.findViewById(R.id.tv_report_response);


//        set text to textview
        tvReportResponse.setText(response);

//        set recycler adapter
        servicesListDialogAdapter = new ServicesListDialogAdapter(servicesnearincident);
        rvServicesNearIncident.setAdapter(servicesListDialogAdapter);
        rvServicesNearIncident.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        showDialog.setTitle("Successfully Reported");
        showDialog.getActionBar();
        showDialog.show();
        showDialog.getWindow().setLayout((width), LinearLayout.LayoutParams.WRAP_CONTENT);

        btnLinkToServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog.dismiss();

                ConstantData.isFromMaChupBasdina = true;
                Intent intent = new Intent(MaChupBasdinaActivity.this, ServicesActivity.class);
                intent.putExtra(ConstantData.KEY_DISTRICT, spinnerDistrictOfIncident.getSelectedItem().toString().trim().toLowerCase());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        DialogFactory.createActionDialog(context, "Warning!!!", "Are you sure want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MaChupBasdinaActivity.super.onBackPressed();

                    }
                }).setNegativeButton("No", null)
                .show();

    }


    @OnClick({R.id.tvLBL_Consent, R.id.tvLBL_no_consent, R.id.tv_perpetrator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLBL_Consent:
                searchAndLoadGlossary("Consent");
                break;

            case R.id.tvLBL_no_consent:
                searchAndLoadGlossary("No Consent");
                break;

            case R.id.tv_perpetrator:
                searchAndLoadGlossary("Perpetrator");
                break;
        }
    }


    private void searchAndLoadGlossary(final String filter) {
        new JSONAssetLoadTask(R.raw.data_glossary, new JSONAssetLoadListener() {
            @Override
            public void onFileLoadComplete(String jsonString) {

                searchAndOpenDetail(jsonString, filter)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
//                                dialog.show();
                            }
                        })
                        .subscribe(new DisposableObserver<WordsWithDetailsModel>() {
                            @Override
                            public void onNext(WordsWithDetailsModel wordsWithDetailsModel) {
//                                dialog.dismiss();

                                if (!TextUtils.isEmpty(wordsWithDetailsModel.getError())) {

                                    //todo ask what to do
                                    return;
                                }

                                Intent intent = new Intent(MaChupBasdinaActivity.this, DataGlossaryWordDetailsActivity.class);
                                intent.putExtra("wordsWithDetails", wordsWithDetailsModel);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //not implemented
//                                dialog.dismiss();
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                //not implemented

                            }
                        });
            }

            @Override
            public void onFileLoadError(String errorMsg) {

            }


        }, MaChupBasdinaActivity.this).execute();

    }

    private Observable<WordsWithDetailsModel> searchAndOpenDetail(final String jsonString, final String searchString) {

        return Observable.just(jsonString)
                .flatMap(new Function<String, ObservableSource<List<WordsWithDetailsModel>>>() {
                    @Override
                    public ObservableSource<List<WordsWithDetailsModel>> apply(String s) throws Exception {
                        Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
                        }.getType();
                        List<WordsWithDetailsModel> list = new Gson().fromJson(jsonString, listType);
                        return Observable.just(list);

                    }
                })
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .filter(new Predicate<WordsWithDetailsModel>() {
                    @Override
                    public boolean test(WordsWithDetailsModel wordsWithDetailsModel) throws Exception {
                        return wordsWithDetailsModel.getTitle().equalsIgnoreCase(searchString.trim());

                    }
                })
                .defaultIfEmpty(new WordsWithDetailsModel("error"));
    }

    private void RunAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.shake);
        a.reset();
        tvLBLConsent.clearAnimation();
        tvLBLConsent.startAnimation(a);

        tvLBLNoConsent.clearAnimation();
        tvLBLNoConsent.startAnimation(a);
    }


}
