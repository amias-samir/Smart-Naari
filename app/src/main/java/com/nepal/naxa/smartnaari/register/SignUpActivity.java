package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.UrlClass;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends Activity {

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.tv_terms_and_condition)
    TextView textViewTermsAndCondition;

    //todo write style for api < 21 for checkbox

    ProgressDialog mProgressDlg;
    String jsonToSend = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupUI();

        mProgressDlg = new ProgressDialog(this);

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
                    // Pirates are the best
                    break;
            case R.id.radio_sex_female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    @OnClick(R.id.btnSignUp)
    public void SignUpBtnClicked() {

        mProgressDlg.setMessage("Please Wait...\nLogging In");
        mProgressDlg.setIndeterminate(false);
        mProgressDlg.setCancelable(false);
        mProgressDlg.show();

        convertDataToJson();

        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.execute();

        startActivity(new Intent(this, LoginActivity.class));
    }

    public void convertDataToJson() {
        //function in the activity that corresponds to the layout button
        try {

            JSONObject header = new JSONObject();

            header.put("username", );
            header.put("password", );
            header.put("first_name", );
            header.put("surname", );
            header.put("dob", );
            header.put("gender", );
            header.put("birth_district", );
            header.put("current_district", );
            header.put("email", );
            header.put("personal_mobile_number", );
            header.put("circle_mobile_number_1", );
            header.put("circle_mobile_number_2", );
            header.put("circle_mobile_number_3", );
            header.put("circle_mobile_number_4", );
            header.put("circle_mobile_number_5", );
            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class SignUpAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... string) {

            String text = "";
            text = POST(UrlClass.SIGNUP_URL);

            return text.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                String status = "";
                String data = "";

                try {

                    JSONObject allJson = new JSONObject(result);
                    status = allJson.getString("status");
                    data = allJson.getString("data");

                    if (status.equals("406")) {
                        mProgressDlg.dismiss();
                        Toast.makeText(SignUpActivity.this, data, Toast.LENGTH_SHORT).show();

                    } else if (status.equals("201")){
                        mProgressDlg.dismiss();
                        Toast.makeText(SignUpActivity.this, data, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

                Log.d("SUSAN", "onPostExecute: " + result.toString());

            }
        }

        public String POST(String urll) {
            String result = "";
            URL url;

            try {
                url = new URL(urll);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("data", jsonToSend);



                Log.d("SUSAN", jsonToSend + " " + jsonToSend);
                String query = builder.build().getEncodedQuery();

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        result += line;
                    }
                } else {
                    result = "";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
}
