package com.nepal.naxa.smartnaari.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.LoginDetailsModel;
import com.nepal.naxa.smartnaari.data.network.LoginResponse;
import com.nepal.naxa.smartnaari.data.network.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.NetworkApiInterface;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.register.SignUpActivity;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnLinkToSignup)
    Button btnLinkToSignup;
    @BindView(R.id.user_name_input_id)
    EditText tvUserName;
    @BindView(R.id.user_password_input_id)
    EditText tvUserPassword;
    @BindView(R.id.btnDown)
    ImageButton btnDown;
    @BindView(R.id.registerLBL)
    TextView registerLBL;
    @BindView(R.id.registerBeTheOneLBL)
    TextView tvRegisterBeTheOneLBL;


    String jsonToSend = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupUI();





    }

    private void setupUI(){

        String rawString = getResources().getString(R.string.be_the_one_to_say_no_to_gender_based_violence);
        String textToBigSize = "No";
        int bigSize = getResources().getDimensionPixelSize(R.dimen.material_text_headline);
        SpannableStringBuilder sb = SpanUtils.makeSectionOfTextBigger(rawString, bigSize, textToBigSize);

        tvRegisterBeTheOneLBL.setText(sb);

    }

    @OnClick({R.id.btnLogin, R.id.btnLinkToSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String username = tvUserName.getText().toString();
                String password = tvUserPassword.getText().toString();

                if(!username.isEmpty() && !username.equals("") && !password.isEmpty() && !password.equals("")){

                    convertDataToJSON();

                    sendDataToServer();

                }else {
                    Toast.makeText(this, "Username and Password field cannot be empty", Toast.LENGTH_SHORT).show();
                }





                break;
            case R.id.btnLinkToSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }


    public void convertDataToJSON(){

        try {

            JSONObject header = new JSONObject();

            header.put("username", tvUserName.getText().toString());
            header.put("password", tvUserPassword.getText().toString());
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToServer(){

        NetworkApiInterface apiService =
                NetworkApiClient.getNotifictionApiClient().create(NetworkApiInterface.class);


        Log.e(TAG, "Retrofit Json to send: "+jsonToSend.toString());

        Call<LoginResponse> call = apiService.getLoginDetails(jsonToSend);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int statusCode = response.code();

                Log.e(TAG, "Retrofit onResponse: "+response.body().toString());
                String status = response.body().getStatus();

                if(status.equals("200")){

                    List<LoginDetailsModel> loginDetailsModel = response.body().getUser_data();

                    Log.e(TAG, "onResponse: success"+ loginDetailsModel.get(2) );

//                    SugarRecord.saveInTx(nagarBudgetDetails);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);



                }
//                String receiveddata = response.body().getDevice_id() + ", "+ response.body().getToken();




//                List<Movie> movies = response.body().getResults();
//                Log.e(TAG, "onResponse: "+ movies );
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("LoginActivity"+"Failure", t.toString());
            }
        });
    }


}
