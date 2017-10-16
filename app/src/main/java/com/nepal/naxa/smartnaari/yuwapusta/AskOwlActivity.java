package com.nepal.naxa.smartnaari.yuwapusta;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;

import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;

import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.YuwaPustaQueryResponse;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;

import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    ArrayList<String> owlArray , owlIDArray;
    ArrayAdapter owlListAdpt;

    String owlIdentity, txtQnToOwl;
    String jsonToSend = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_owl);
        ButterKnife.bind(this);

        initToolbar();
        initQuestionsRecyclerView();

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


    private void initQuestionsRecyclerView() {

        List<YuwaQuestion> yuwaQuestions = appDataManager.getAllYuwaQuestions();

        YuwaQuestionAdapter yuwaQuestionAdapter = new YuwaQuestionAdapter(yuwaQuestions);
        questionList.setAdapter(yuwaQuestionAdapter);

        questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        yuwaQuestionAdapter.setOnItemClickListener(this);

        questionList.setNestedScrollingEnabled(false);


    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
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
    public void onItemClick(View view, YuwaQuestion yuwaQuery) {

    }

    @OnClick(R.id.btn_ask_a_owl)
    public void onViewClicked() {


        String question = wrapperTextQuestionToOwl.getEditText().getText().toString();
        question = "dssdcbx cbvxcvxcmvbc cmn nc c c  ??";

        if(spinnerChooseOwl.getSelectedItemId() < 1 ){
            showInfoToast("Please select an Owl ");
            return;

        }
        if(TextUtils.isEmpty(question)){
            showInfoToast("Question field cannot be empty");

            return;
        }
        showLoading("Please Wait! \n Sending...");

        convertDataToJSON();

        sendDataToServer();
    }



    public void addOwlNameAndIDToArray(){
        owlArray = new ArrayList<>();
        owlIDArray = new ArrayList<>();

        AppDataManager appDataManager = new AppDataManager(this);
        Log.e(TAG, "onCreate: " + appDataManager.getOwls().get(0).getOwlName());
        owlArray.add("Choose Owl");
        owlIDArray.add("owlID");

        for (int i = 0 ; i< appDataManager.getOwls().size(); i++){
            owlArray.add(appDataManager.getOwls().get(i).getOwlName());
            owlIDArray.add(appDataManager.getOwls().get(i).getOwlId());
        }

    }

    public void convertDataToJSON() {

        SessionManager sessionManager = new SessionManager(this);

        try {

            JSONObject header = new JSONObject();

            Integer spinnerPosition = (int) (long)spinnerChooseOwl.getSelectedItemId();

            header.put("user_id", sessionManager.getUserId());
            header.put("owl_id", owlIDArray.get(spinnerPosition));
            header.put("qstn", wrapperTextQuestionToOwl.getEditText().getText().toString());
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendDataToServer() {

        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<YuwaPustaQueryResponse> call = apiService.getYuwaPusaQueryDetails(jsonToSend);
        call.enqueue(new Callback<YuwaPustaQueryResponse>() {
            @Override
            public void onResponse(Call<YuwaPustaQueryResponse> call, Response<YuwaPustaQueryResponse> response) {
                Log.d(TAG, "onPostExecute: " + response.toString());

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
                        }
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<YuwaPustaQueryResponse> call, Throwable t) {
               hideLoading();
            }
        });
    }
}
