package com.nepal.naxa.smartnaari.mycircle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.BaseActivity;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.NetworkApiInterface;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.utils.Constants;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_400;
import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_401;
import static com.nepal.naxa.smartnaari.data.network.UrlClass.REQUEST_OK;

public class MyCircleActivity extends BaseActivity {

    @BindView(R.id.btnNewMobileNo)
    ImageButton btnNewMobileNo;

    int count = 0;
    static final int PICK_CONTACT = 01;

    @BindView(R.id.my_circle_first_contact)
    EditText tvFirstContact;
    @BindView(R.id.my_circle_second_contact)
    EditText tvSecondContact;
    @BindView(R.id.my_circle_third_contact)
    EditText tvThirdContact;
    @BindView(R.id.my_circle_fourth_contact)
    EditText tvFourthContact;
    @BindView(R.id.my_circle_fifth_contact)
    EditText tvFifthContact;
    @BindView(R.id.btnSelectContactNo)
    Button btnSelectContactNo;
    @BindView(R.id.btnDone)
    Button btnDone;

    @BindView(R.id.txtLBLenterContacts)
    TextView tvEnterTwoNumber;
    @BindView(R.id.top_linear_layout)
    LinearLayout topLinearLayout;
    @BindView(R.id.body_relative_layout)
    LinearLayout bodyRelativeLayout;
    @BindView(R.id.layoutSelectContactNo)
    LinearLayout layoutSelectContactNo;
    @BindView(R.id.bottom_relative_layout)
    LinearLayout bottomRelativeLayout;

    String jsonToSend = null;

    public String user_id = "";
    public String first_contact = "";
    public String second_contact = "";
    public String third_contact = "";
    public String fourth_contact = "";
    public String fifth_contact = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.bind(this);
        setupUI();
        initializeContactsUI();


    }


    private void setupUI() {

        String numberMsgText = getString(R.string.enter_at_least_two_phone_numbers);
        String textToBold = "Two";
        SpannableStringBuilder sb = SpanUtils.makeSectionOfTextBold(numberMsgText, textToBold);


        tvEnterTwoNumber.setText(sb);

    }

    @OnClick(R.id.btnNewMobileNo)
    public void onViewClicked() {

        if (count == 0) {
            tvThirdContact.setVisibility(View.VISIBLE);
            count = count + 1;
        } else if (count == 1) {
            tvThirdContact.setVisibility(View.VISIBLE);
            tvFourthContact.setVisibility(View.VISIBLE);
            count = count + 1;

        } else if (count == 2) {
            tvThirdContact.setVisibility(View.VISIBLE);
            tvFourthContact.setVisibility(View.VISIBLE);
            tvFifthContact.setVisibility(View.VISIBLE);
            count = count + 1;
        } else if (count > 2) {

            Toast.makeText(this, "You can add only five numbers", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick({R.id.btnSelectContactNo, R.id.btnDone})
    public void onBtnClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSelectContactNo:

                Constants.first_contact = tvFirstContact.getText().toString();
                Constants.second_contact = tvSecondContact.getText().toString();
                Constants.third_contact = tvThirdContact.getText().toString();
                Constants.fourth_contact = tvFourthContact.getText().toString();
                Constants.fifth_contact = tvFifthContact.getText().toString();


                first_contact = tvFirstContact.getText().toString();
                second_contact = tvSecondContact.getText().toString();
                third_contact = tvThirdContact.getText().toString();
                fourth_contact = tvFourthContact.getText().toString();
                fifth_contact = tvFifthContact.getText().toString();


                if (Constants.first_contact.equals("") || Constants.second_contact.equals("") || Constants.third_contact.equals("") ||
                        Constants.fourth_contact.equals("") || Constants.fifth_contact.equals("")) {
                    Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intentContact, PICK_CONTACT);
                } else {
                    showInfoToast("You can only add five phone number");

                }

                break;


            case R.id.btnDone:

                prepareToUpload();


                break;
        }
    }

    private void prepareToUpload() {

        if (TextUtils.isEmpty(first_contact)) {
            showErrorToast("Enter First Contact Number");
            return;
        }

        if (first_contact.length() < 10) {
            showErrorToast("Enter Valid Phone Number");
            return;
        }

        if (TextUtils.isEmpty(second_contact)) {
            showErrorToast("Enter Second Contact Number");
            return;
        }

        if (first_contact.length() < 10) {
            showErrorToast("Enter Valid Phone Number");
            return;
        }

        if (isNetworkDisconnected()) {
            showErrorToast("Device is offline.");
            return;
        }


        showLoading("Adding Phone Numbers");

        convertDataToJSON();
        sendDataToServer();
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            phones.moveToFirst();
                            String contact_number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            contact_name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                            contact_image_path = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

                            setContcts(contact_number);
                            initializeContactsUI();
                        }
                    }
                }
                break;

        }
    }


    public void setContcts(String contact_no) {

        String first, second, third, fourth, fifth;

        first = Constants.first_contact;
        second = Constants.second_contact;
        third = Constants.third_contact;
        fourth = Constants.fourth_contact;
        fifth = Constants.fifth_contact;

        try {
            if (first.equals("")) {
                Constants.first_contact = contact_no;
                tvFirstContact.setText(Constants.first_contact);

            } else if (second.equals("")) {
                Constants.second_contact = contact_no;
                tvSecondContact.setText(Constants.second_contact);

            } else if (third.equals("")) {
                count = count + 1;
                Constants.third_contact = contact_no;
                tvThirdContact.setText(Constants.third_contact);

            } else if (fourth.equals("")) {
                count = count + 1;
                Constants.fourth_contact = contact_no;
                tvFourthContact.setText(Constants.fourth_contact);

            } else if (fifth.equals("")) {
                count = count + 1;
                Constants.fifth_contact = contact_no;
                tvFifthContact.setText(Constants.fifth_contact);

            }
        } catch (Exception e) {

        }
    }


    public void initializeContactsUI() {

        tvFirstContact.setText(Constants.first_contact);
        tvSecondContact.setText(Constants.second_contact);

        if (!Constants.third_contact.equals("")) {
            tvThirdContact.setVisibility(View.VISIBLE);
        }
        if (!Constants.fourth_contact.equals("")) {
            tvFourthContact.setVisibility(View.VISIBLE);
        }
        if (!Constants.fifth_contact.equals("")) {
            tvFifthContact.setVisibility(View.VISIBLE);
        }
        tvThirdContact.setText(Constants.third_contact);
        tvFourthContact.setText(Constants.fourth_contact);
        tvFifthContact.setText(Constants.fifth_contact);
    }


    public void convertDataToJSON() {

        try {

            JSONObject header = new JSONObject();

            header.put("user_id", "8");
            header.put("c1", tvFirstContact.getText().toString());
            header.put("c2", tvSecondContact.getText().toString());
            header.put("c3", tvThirdContact.getText().toString());
            header.put("c4", tvFourthContact.getText().toString());
            header.put("c5", tvFifthContact.getText().toString());
            header.put("n1", "");
            header.put("n2", "");
            header.put("n3", "");
            header.put("n4", "");
            header.put("n5", "");


            jsonToSend = header.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendDataToServer() {

        NetworkApiInterface apiService =
                NetworkApiClient.getNotifictionApiClient().create(NetworkApiInterface.class);

        Call<MyCircleDetails> call = apiService.getCircleData(jsonToSend);
        call.enqueue(new Callback<MyCircleDetails>() {
            @Override
            public void onResponse(Call<MyCircleDetails> call, Response<MyCircleDetails> response) {

                hideLoading();

                if (response == null) {
                    showErrorToast(getString(R.string.general_error_msg));
                    return;
                }

                handleCircleResponse(response.body());

            }

            private void handleCircleResponse(MyCircleDetails body) {
                switch (body.getStatus()) {
                    case REQUEST_OK:
                        showInfoToast(body.getData());

                        Intent intent = new Intent(MyCircleActivity.this, MainActivity.class);
                        Constants.first_contact = tvFirstContact.getText().toString();
                        Constants.second_contact = tvSecondContact.getText().toString();
                        Constants.third_contact = tvThirdContact.getText().toString();
                        Constants.fourth_contact = tvFourthContact.getText().toString();
                        Constants.fifth_contact = tvFifthContact.getText().toString();
                        startActivity(intent);

                        break;
                    case REQUEST_401:
                    case REQUEST_400:
                    default:
                        showErrorToast(body.getData());
                        break;
                }
            }

            @Override
            public void onFailure(Call<MyCircleDetails> call, Throwable t) {
                hideLoading();

                String message = getString(R.string.general_error_msg);

                if (t instanceof SocketTimeoutException) {
                    message = getString(R.string.network_error_timeout);
                }

                showErrorToast(message);
            }
        });
    }

}
