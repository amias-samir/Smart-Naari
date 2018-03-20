package com.nepal.naxa.smartnaari.yuwapusta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutsmartnaari.AboutSmartNaariActivity;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;
import com.nepal.naxa.smartnaari.data.network.AskAnOwlResponse;
import com.nepal.naxa.smartnaari.data.network.retrofit.ErrorSupportCallback;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient.getAPIClient;


public class AskOwlActivity extends BaseActivity implements YuwaQuestionAdapter.OnItemClickListener {


    String TAG = "AskOwlActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_owlList)
    Spinner spinnerChooseOwl;
    @BindView(R.id.btn_ask_a_owl)
    Button btnAskAOwl;
    @BindView(R.id.yuwa_pusta_act_rv_questionlist)
    RecyclerView questionList;

    @BindView(R.id.yuwa_pusta_act_tv_header)
    TextView tvHeader;
    @BindView(R.id.wrapper_text_QuestionToOwl)
    TextInputLayout wrapperTextQuestionToOwl;

    ArrayList<String> owlArray, owlIDArray;
    ArrayAdapter owlListAdpt;

    String owlIdentity, txtQnToOwl;
    String jsonToSend = null;
    @BindView(R.id.tvQuestionToOWL)
    EditText tvQuestionToOWL;
    @BindView(R.id.cbMakeAnonymous)
    CheckBox cbMakeAnonymous;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_owl);
        ButterKnife.bind(this);

        initToolbar();
        tvQuestionToOWL.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tvQuestionToOWL, InputMethodManager.SHOW_IMPLICIT);

//        initQuestionsRecyclerView();

        addOwlNameAndIDToArray();
        Log.e(TAG, "onCreate: owlArray SAMIR" + owlArray);
        owlListAdpt = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, owlArray);
        owlListAdpt
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseOwl.setAdapter(owlListAdpt);


        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        SpanUtils.setColor(tvHeader, "Yuwa Pusta", "Yuwa", color);

    }


//    private void initQuestionsRecyclerView() {
//
//        List<YuwaQuestion> yuwaQuestions = appDataManager.getAllYuwaQuestions();
//
//        YuwaQuestionAdapter yuwaQuestionAdapter = new YuwaQuestionAdapter(yuwaQuestions);
//        questionList.setAdapter(yuwaQuestionAdapter);
//
//        questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        yuwaQuestionAdapter.setOnItemClickListener(this);
//
//        questionList.setNestedScrollingEnabled(false);
//
//
//    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ask an Owl");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setTitle("Ask An Owl");
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
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

            case R.id.item_call:
                Intent intent = new Intent(AskOwlActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, YuwaQuestion yuwaQuery) {

    }

    @OnClick(R.id.btn_ask_a_owl)
    public void onViewClicked() {


        String question = wrapperTextQuestionToOwl.getEditText().getText().toString();
//        question = "dssdcbx cbvxcvxcmvbc cmn nc c c  ??";

        if (spinnerChooseOwl.getSelectedItemId() < 1) {
            showInfoToast("Please select an Owl ");
            return;

        }
        if (TextUtils.isEmpty(question)) {
            showInfoToast("Question field cannot be empty");

            return;
        }

        showLoading("Please Wait! \n Sending...");

        convertDataToJSON();

        sendDataToServer();
    }


    public void addOwlNameAndIDToArray() {
        owlArray = new ArrayList<>();
        owlIDArray = new ArrayList<>();

        AppDataManager appDataManager = new AppDataManager(this);
        Log.e(TAG, "onCreate: " + appDataManager.getOwls().get(0).getOwlName());
        owlArray.add("Choose Owl");
        owlIDArray.add("owlID");

        for (int i = 0; i < appDataManager.getOwls().size(); i++) {
            owlArray.add(appDataManager.getOwls().get(i).getOwlName());
            owlIDArray.add(appDataManager.getOwls().get(i).getOwlId());
        }

    }

    public void convertDataToJSON() {

        SessionManager sessionManager = new SessionManager(this);

        try {

            String makeAnonymous = "no";
            if (cbMakeAnonymous.isChecked()) {
                makeAnonymous = "yes";
            }


            JSONObject header = new JSONObject();

            Integer spinnerPosition = (int) (long) spinnerChooseOwl.getSelectedItemId();

            header.put("user_id_fk", sessionManager.getUserId());
            header.put("anonymous", makeAnonymous);
            header.put("owl_id", owlIDArray.get(spinnerPosition));
            header.put("qstn", wrapperTextQuestionToOwl.getEditText().getText().toString());
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendDataToServer() {

        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<AskAnOwlResponse> call = apiService.getAskAnOwlResponseDetails(jsonToSend);
        call.enqueue(new ErrorSupportCallback<>(new Callback<AskAnOwlResponse>() {
            @Override
            public void onResponse(Call<AskAnOwlResponse> call, Response<AskAnOwlResponse> response) {
                Log.d(TAG, "onPostExecute: " + response.toString());

                if (response.body() != null) {
                    String status = "";
                    String data = "";

                    try {

                        status = response.body().getStatus();
                        data = response.body().getData();

                        switch (status) {
                            case "200":
                                hideLoading();
//                                showInfoToast(data);

                                ConstantData.isFromAskAnOwl = true;
                                showInfoToast("Thank you for your query. \nWe will respond you as soon as possible");
                                Intent intent = new Intent(AskOwlActivity.this, YuwaPustaActivity.class);
                                startActivity(intent);

                                break;
                            case "201":
                                showInfoToast(data);
                                hideLoading();

                                break;
                            case "406":
                                showInfoToast(data);
                                hideLoading();
                                break;
                        }
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<AskAnOwlResponse> call, Throwable t) {
                hideLoading();
            }
        }));
    }
}
