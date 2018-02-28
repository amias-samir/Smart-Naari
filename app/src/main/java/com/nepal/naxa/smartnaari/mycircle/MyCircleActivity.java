package com.nepal.naxa.smartnaari.mycircle;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.retrofit.ErrorSupportCallback;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.utils.SpanUtils;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

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


    private static final String TAG = MyCircleActivity.class.getSimpleName();
    private static int REQUEST_CODE_PICK_CONTACTS = 00;
    private static final int REQUEST_CODE_PICK_FIRST_CONTACTS = 10;
    private static final int REQUEST_CODE_PICK_SECOND_CONTACTS = 20;
    private static final int REQUEST_CODE_PICK_THIRD_CONTACTS = 30;
    private static final int REQUEST_CODE_PICK_FOURTH_CONTACTS = 40;
    private static final int REQUEST_CODE_PICK_FIFTH_CONTACTS = 50;

    @BindView(R.id.txtLBLmyCircleSafety)
    TextView tvMyCircleSafety;
    @BindView(R.id.first_contact_delete)
    ImageButton btnFirstContactDelete;
    @BindView(R.id.second_contact_delete)
    ImageButton btnSecondContactDelete;
    @BindView(R.id.third_contact_delete)
    ImageButton btnThirdContactDelete;
    @BindView(R.id.fourth_contact_delete)
    ImageButton btnFourthContactDelete;
    @BindView(R.id.fifth_contact_delete)
    ImageButton btnFifthContactDelete;
    @BindView(R.id.select_first_contact_from_list)
    ImageButton btnSelectFirstContactFromList;
    @BindView(R.id.select_second_contact_from_list)
    ImageButton btnSelectSecondContactFromList;
    @BindView(R.id.select_third_contact_from_list)
    ImageButton btnSelectThirdContactFromList;
    @BindView(R.id.select_fourth_contact_from_list)
    ImageButton btnSelectFourthContactFromList;
    @BindView(R.id.select_fifth_contact_from_list)
    ImageButton btnSelectFifthContactFromList;
    private Uri uriContact;
    private String contactID; // contacts unique ID


    @BindView(R.id.my_circle_first_contact)
    TextInputLayout tvFirstContact;
    @BindView(R.id.my_circle_second_contact)
    TextInputLayout tvSecondContact;
    @BindView(R.id.my_circle_third_contact)
    TextInputLayout tvThirdContact;
    @BindView(R.id.my_circle_fourth_contact)
    TextInputLayout tvFourthContact;
    @BindView(R.id.my_circle_fifth_contact)
    TextInputLayout tvFifthContact;
    @BindView(R.id.btnSelectContactNo)
    Button btnSelectContactNo;
    @BindView(R.id.btnDone)
    Button btnDone;

    @BindView(R.id.txtLBLenterContacts)
    TextView tvEnterTwoNumber;
    //    @BindView(R.id.top_linear_layout)
//    LinearLayout topLinearLayout;
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

    @BindView(R.id.my_circle_first_contact_name)
    EditText tvFirstContactName;
    @BindView(R.id.my_circle_second_contact_name)
    EditText tvSecondContactName;
    @BindView(R.id.my_circle_third_contact_name)
    EditText tvThirdContactName;
    @BindView(R.id.my_circle_fourth_contact_name)
    EditText tvFourthContactName;
    @BindView(R.id.my_circle_fifth_contact_name)
    EditText tvFifthContactName;


    SessionManager sessionManager;
    MyCircleData myCircleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        myCircleData = new MyCircleData();
        myCircleData = sessionManager.getMyCircleContact();

        final SpannableStringBuilder sb = new SpannableStringBuilder("For my Saftey");

        final StyleSpan bss1 = new StyleSpan(Typeface.BOLD); // Span to make text bold
        final StyleSpan bss2 = new StyleSpan(Typeface.BOLD); //Span to make text italic
        sb.setSpan(bss1, 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        sb.setSpan(bss2, 7, sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last  word Bold
        tvMyCircleSafety.setText(sb);

        setupUI();
        initializeContactsUI();

        showHideNewContactAddPlusIcon();


    }


    private void setupUI() {

        String numberMsgText = getString(R.string.enter_at_least_two_phone_numbers);
        String textToBold = "Two";
        SpannableStringBuilder sb = SpanUtils.makeSectionOfTextBold(numberMsgText, textToBold);


        tvEnterTwoNumber.setText(sb);

    }

    private void showHideNewContactAddPlusIcon() {

        if (visibilityStatusCount() == 5) {
            btnNewMobileNo.setVisibility(View.GONE);
        } else {
            btnNewMobileNo.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btnNewMobileNo)
    public void onViewClicked() {

        if (count == 2 || visibilityStatusCount() == 5) {
            Toast.makeText(this, "You can add only five numbers", Toast.LENGTH_SHORT).show();
        }

        if (count == 0) {

            tvFourthContact.setVisibility(View.VISIBLE);
            tvFourthContactName.setVisibility(View.VISIBLE);
            btnSelectFourthContactFromList.setVisibility(View.VISIBLE);
            btnFourthContactDelete.setVisibility(View.VISIBLE);
            count = count + 1;

        } else if (count == 1) {
//            tvFirstContact.setVisibility(View.VISIBLE);
//            tvFirstContactName.setVisibility(View.VISIBLE);
//
//            tvSecondContact.setVisibility(View.VISIBLE);
//            tvSecondContactName.setVisibility(View.VISIBLE);

//            tvThirdContact.setVisibility(View.VISIBLE);
//            tvThirdContactName.setVisibility(View.VISIBLE);

            tvFourthContact.setVisibility(View.VISIBLE);
            tvFourthContactName.setVisibility(View.VISIBLE);
            btnSelectFourthContactFromList.setVisibility(View.VISIBLE);
            btnFourthContactDelete.setVisibility(View.VISIBLE);

            tvFifthContact.setVisibility(View.VISIBLE);
            tvFifthContactName.setVisibility(View.VISIBLE);
            btnSelectFifthContactFromList.setVisibility(View.VISIBLE);
            btnFifthContactDelete.setVisibility(View.VISIBLE);
            count = count + 1;
            btnNewMobileNo.setVisibility(View.GONE);
        }
    }

    private int visibilityStatusCount() {
        int countStatus = 0;
        if (tvFirstContactName.getVisibility() == View.VISIBLE) {
            countStatus++;
        }
        if (tvSecondContactName.getVisibility() == View.VISIBLE) {
            countStatus++;
        }
        if (tvThirdContactName.getVisibility() == View.VISIBLE) {
            countStatus++;
        }
        if (tvFourthContactName.getVisibility() == View.VISIBLE) {
            countStatus++;
        }
        if (tvFifthContactName.getVisibility() == View.VISIBLE) {
            countStatus++;
        }
        Log.d(TAG, "visibilityStatusCount: " + countStatus);
        return countStatus;
    }


    @OnClick({R.id.btnSelectContactNo, R.id.btnDone})
    public void onBtnClicked(View view) {
        switch (view.getId()) {
//
//            case R.id.btnSelectContactNo:
//
//                myCircleData.setContactNumber1(tvFirstContact.getEditText().getText().toString());
//                myCircleData.setContactNumber2(tvSecondContact.getEditText().getText().toString());
//                myCircleData.setContactNumber3(tvThirdContact.getEditText().getText().toString());
//                myCircleData.setContactNumber4(tvFourthContact.getEditText().getText().toString());
//                myCircleData.setContactNumber5(tvFifthContact.getEditText().getText().toString());
//
//                first_contact = myCircleData.getContactNumber1();
//                second_contact = myCircleData.getContactNumber2();
//                third_contact = myCircleData.getContactNumber3();
//                fourth_contact = myCircleData.getContactNumber4();
//                fifth_contact = myCircleData.getContactNumber5();
//
//
//                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
//
//                    if (first_contact.equals("") || second_contact.equals("") || third_contact.equals("") ||
//                            fourth_contact.equals("") || fifth_contact.equals("")) {
////                    Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
////                    startActivityForResult(intentContact, PICK_CONTACT);
//                        // using native contacts selection
//                        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
//                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
//                    } else {
//                        showInfoToast("You can only add five phone number");
//
//                    }
//                } else {
//                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), 01);
//                }
//
//                break;


            case R.id.btnDone:

                myCircleData.setContactNumber1(tvFirstContact.getEditText().getText().toString());
                myCircleData.setContactNumber2(tvSecondContact.getEditText().getText().toString());
                myCircleData.setContactNumber3(tvThirdContact.getEditText().getText().toString());
                myCircleData.setContactNumber4(tvFourthContact.getEditText().getText().toString());
                myCircleData.setContactNumber5(tvFifthContact.getEditText().getText().toString());

                first_contact = myCircleData.getContactNumber1();
                second_contact = myCircleData.getContactNumber2();
                third_contact = myCircleData.getContactNumber3();
                fourth_contact = myCircleData.getContactNumber4();
                fifth_contact = myCircleData.getContactNumber5();

                prepareToUpload();


                break;
        }
    }

    private void prepareToUpload() {

        int countTotalContactNumber = 0;

        if (first_contact.length() > 0) {
            if (first_contact.length() >= 10 && first_contact.length() <= 15) {
                if (TextUtils.isEmpty(tvFirstContactName.getText().toString())) {
                    showErrorToast("Enter First Contact Name");
                    tvFirstContactName.requestFocus();
                    return;
                }
                countTotalContactNumber++;
            } else {
                showErrorToast("Enter Valid Phone Number");
                tvFirstContact.getEditText().requestFocus();
                return;
            }
        }

        if (second_contact.length() > 0) {
            if (second_contact.length() >= 10 && second_contact.length() <= 15) {
                if (TextUtils.isEmpty(tvSecondContactName.getText().toString())) {
                    showErrorToast("Enter Second Contact Name");
                    tvSecondContactName.requestFocus();
                    return;
                }
                countTotalContactNumber++;
            } else {
                showErrorToast("Enter Valid Phone Number");
                tvSecondContact.getEditText().requestFocus();
                return;
            }
        }

        if (third_contact.length() > 0) {
            if (third_contact.length() >= 10 && third_contact.length() <= 15) {
                if (TextUtils.isEmpty(tvThirdContactName.getText().toString())) {
                    showErrorToast("Enter Third Contact Name");
                    tvThirdContactName.requestFocus();
                    return;
                }
                countTotalContactNumber++;
            } else {
                showErrorToast("Enter Valid Phone Number");
                tvThirdContact.getEditText().requestFocus();
                return;
            }
        }

        if (fourth_contact.length() > 0) {
            if (fourth_contact.length() >= 10 && fourth_contact.length() <= 15) {
                if (TextUtils.isEmpty(tvFourthContactName.getText().toString())) {
                    showErrorToast("Enter Fourth Contact Name");
                    tvFirstContactName.requestFocus();
                    return;
                }
                countTotalContactNumber++;
            } else {
                showErrorToast("Enter Valid Phone Number");
                tvFourthContact.getEditText().requestFocus();
                return;
            }
        }

        if (fifth_contact.length() > 0) {
            if (fifth_contact.length() >= 10 && fifth_contact.length() <= 15) {
                if (TextUtils.isEmpty(tvFifthContactName.getText().toString())) {
                    showErrorToast("Enter Fifth Contact Name");
                    tvFifthContactName.requestFocus();
                    return;
                }
                countTotalContactNumber++;
            } else {
                showErrorToast("Enter Valid Phone Number");
                tvFifthContact.getEditText().requestFocus();
                return;
            }
        }


        if (countTotalContactNumber < 3) {
            showErrorToast("At least three contact number is required");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_FIRST_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            setContcts(retrieveContactName(), retrieveContactNumber(), REQUEST_CODE_PICK_FIRST_CONTACTS);
        }
        if (requestCode == REQUEST_CODE_PICK_SECOND_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            setContcts(retrieveContactName(), retrieveContactNumber(), REQUEST_CODE_PICK_SECOND_CONTACTS);
        }
        if (requestCode == REQUEST_CODE_PICK_THIRD_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            setContcts(retrieveContactName(), retrieveContactNumber(), REQUEST_CODE_PICK_THIRD_CONTACTS);
        }
        if (requestCode == REQUEST_CODE_PICK_FOURTH_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            setContcts(retrieveContactName(), retrieveContactNumber(), REQUEST_CODE_PICK_FOURTH_CONTACTS);
        }
        if (requestCode == REQUEST_CODE_PICK_FIFTH_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            setContcts(retrieveContactName(), retrieveContactNumber(), REQUEST_CODE_PICK_FIFTH_CONTACTS);
        }
    }


    public void setContcts(String contact_name, String contact_no, int requestCODE) {

        String first = "", second = "", third = "", fourth = "", fifth = "";

        first = tvFirstContact.getEditText().getText().toString();
        second = tvSecondContact.getEditText().getText().toString();
        third = tvThirdContact.getEditText().getText().toString();
        fourth = tvFourthContact.getEditText().getText().toString();
        fifth = tvFifthContact.getEditText().getText().toString();


        try {
            if (requestCODE == REQUEST_CODE_PICK_FIRST_CONTACTS) {
                tvFirstContact.getEditText().setText(contact_no);
                tvFirstContactName.setText(contact_name);
                myCircleData.setContactName1(contact_name);
                myCircleData.setContactNumber1(contact_no);

            }
            if (requestCODE == REQUEST_CODE_PICK_SECOND_CONTACTS) {
                tvSecondContactName.setText(contact_name);
                tvSecondContact.getEditText().setText(contact_no);
                myCircleData.setContactName2(contact_name);
                myCircleData.setContactNumber2(contact_no);

            }
            if (requestCODE == REQUEST_CODE_PICK_THIRD_CONTACTS) {
//                count = count + 1;
//                tvThirdContactName.setVisibility(View.VISIBLE);
//                tvThirdContact.setVisibility(View.VISIBLE);
                tvThirdContactName.setText(contact_name);
                tvThirdContact.getEditText().setText(contact_no);
                myCircleData.setContactName3(contact_name);
                myCircleData.setContactNumber3(contact_no);

            }
            if (requestCODE == REQUEST_CODE_PICK_FOURTH_CONTACTS) {
//                count = count + 1;
//                tvFourthContactName.setVisibility(View.VISIBLE);
//                tvFourthContact.setVisibility(View.VISIBLE);
                tvFourthContactName.setText(contact_name);
                tvFourthContact.getEditText().setText(contact_no);
                myCircleData.setContactName4(contact_name);
                myCircleData.setContactNumber4(contact_no);

            }
            if (requestCODE == REQUEST_CODE_PICK_FIFTH_CONTACTS) {
//                count = count + 1;
//                tvFifthContactName.setVisibility(View.VISIBLE);
//                tvFifthContact.setVisibility(View.VISIBLE);
                tvFifthContactName.setText(contact_name);
                tvFifthContact.getEditText().setText(contact_no);
                myCircleData.setContactName5(contact_name);
                myCircleData.setContactNumber5(contact_no);

            }
        } catch (Exception e) {

        }
    }


    public void initializeContactsUI() {


        try {
            JSONObject jOBJ = new JSONObject(sessionManager.usresCircleContact());
            myCircleData.setUserId(jOBJ.getString("user_id"));

            myCircleData.setContactName1(jOBJ.getString("c1"));
            myCircleData.setContactNumber1(jOBJ.getString("n1"));

            myCircleData.setContactName2(jOBJ.getString("c2"));
            myCircleData.setContactNumber2(jOBJ.getString("n2"));

            myCircleData.setContactName3(jOBJ.getString("c3"));
            myCircleData.setContactNumber3(jOBJ.getString("n3"));

            myCircleData.setContactName4(jOBJ.getString("c4"));
            myCircleData.setContactNumber4(jOBJ.getString("n4"));

            myCircleData.setContactName5(jOBJ.getString("c5"));
            myCircleData.setContactNumber5(jOBJ.getString("n5"));

        } catch (Exception e) {
            e.printStackTrace();
        }

//         myCircleData = sessionManager.getMyCircleContact();


        try {
            tvFirstContact.getEditText().setText(myCircleData.getContactNumber1());
            tvFirstContactName.setText(myCircleData.getContactName1());
            tvSecondContact.getEditText().setText(myCircleData.getContactNumber2());
            tvSecondContactName.setText(myCircleData.getContactName2());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber3().equals("")) {
                tvThirdContact.setVisibility(View.VISIBLE);
                tvThirdContactName.setVisibility(View.VISIBLE);
                tvThirdContact.getEditText().setText(myCircleData.getContactNumber3());
                tvThirdContactName.setText(myCircleData.getContactName3());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber4().equals("")) {

                tvFourthContact.setVisibility(View.VISIBLE);
                tvFourthContactName.setVisibility(View.VISIBLE);
                btnSelectFourthContactFromList.setVisibility(View.VISIBLE);
                btnFourthContactDelete.setVisibility(View.VISIBLE);
                tvFourthContactName.setText(myCircleData.getContactName4());
                tvFourthContact.getEditText().setText(myCircleData.getContactNumber4());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber5().equals("")) {
                tvFifthContact.setVisibility(View.VISIBLE);
                tvFifthContactName.setVisibility(View.VISIBLE);
                btnSelectFifthContactFromList.setVisibility(View.VISIBLE);
                btnFifthContactDelete.setVisibility(View.VISIBLE);
                tvFifthContact.getEditText().setText(myCircleData.getContactNumber5());
                tvFifthContactName.setText(myCircleData.getContactName5());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void convertDataToJSON() {

        UserData userData = sessionManager.getUser();

        myCircleData.setContactNumber1(tvFirstContact.getEditText().getText().toString());
        myCircleData.setContactNumber2(tvSecondContact.getEditText().getText().toString());
        myCircleData.setContactNumber3(tvThirdContact.getEditText().getText().toString());
        myCircleData.setContactNumber4(tvFourthContact.getEditText().getText().toString());
        myCircleData.setContactNumber5(tvFifthContact.getEditText().getText().toString());

        myCircleData.setContactName1(tvFirstContactName.getText().toString());
        myCircleData.setContactName2(tvSecondContactName.getText().toString());
        myCircleData.setContactName3(tvThirdContactName.getText().toString());
        myCircleData.setContactName4(tvFourthContactName.getText().toString());
        myCircleData.setContactName5(tvFifthContactName.getText().toString());

        myCircleData.setUserId(userData.getUserId());


        try {


            JSONObject header = new JSONObject();

            header.put("user_id", sessionManager.getUserId());
            header.put("c1", tvFirstContact.getEditText().getText().toString());
            header.put("c2", tvSecondContact.getEditText().getText().toString());
            header.put("c3", tvThirdContact.getEditText().getText().toString());
            header.put("c4", tvFourthContact.getEditText().getText().toString());
            header.put("c5", tvFifthContact.getEditText().getText().toString());
            header.put("n1", tvFirstContactName.getText().toString());
            header.put("n2", tvSecondContactName.getText().toString());
            header.put("n3", tvThirdContactName.getText().toString());
            header.put("n4", tvFourthContactName.getText().toString());
            header.put("n5", tvFifthContactName.getText().toString());


            jsonToSend = header.toString();
            Log.d(TAG, "convertDataToJSON: SAMIR" + jsonToSend);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendDataToServer() {

        NetworkApiInterface apiService =
                NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);

        Call<MyCircleDetails> call = apiService.getCircleData(jsonToSend);
        call.enqueue(new ErrorSupportCallback<>(new Callback<MyCircleDetails>() {
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

                        sessionManager.saveUserCircle(myCircleData);

                        if (sessionManager.doesHaveIntentBackgroundService()) {
                            Intent intent = new Intent(MyCircleActivity.this, BeautifulMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(MyCircleActivity.this, PermissionActivity.class);
                            startActivity(intent);
                            finish();
                        }


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
        }));
    }


    private String retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);

        return contactNumber;
    }

    private String retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

        return contactName;
    }


    @OnClick({R.id.first_contact_delete, R.id.second_contact_delete, R.id.third_contact_delete, R.id.fourth_contact_delete, R.id.fifth_contact_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first_contact_delete:
                showContactDeleteActionDialog("First Contact Name and Number", 1);
                break;

            case R.id.second_contact_delete:
                showContactDeleteActionDialog("Second Contact Name and Number", 2);
                break;

            case R.id.third_contact_delete:
                showContactDeleteActionDialog("Third Contact Name and Number", 3);
                break;

            case R.id.fourth_contact_delete:
                showContactDeleteActionDialog("Fourth Contact Name and Number", 4);
                break;

            case R.id.fifth_contact_delete:
                showContactDeleteActionDialog("Fifth Contact Name and Number", 5);
                break;
        }
    }

    public void showContactDeleteActionDialog(final String title, final int contactPos) {

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(this);
        showDialog.setContentView(R.layout.action_dialog_custom_layout);

//         initialize
        TextView tvTextDetails = (TextView) showDialog.findViewById(R.id.dialog_text_details);
        Button btnAgree = (Button) showDialog.findViewById(R.id.btn_agree_dialog);
        btnAgree.setText("DELETE");
        btnAgree.setVisibility(View.VISIBLE);
        Button btnClose = (Button) showDialog.findViewById(R.id.btn_close_dialog);
        btnClose.setText("CANCEL");

        tvTextDetails.setText(getString((R.string.want_to_delete)) + " " + title + " ?");
//        tvTextDetails.setText("Are you sure want to delete" + " "+ title+" ?");

        showDialog.setTitle("Warning !!!");
        showDialog.getActionBar();
        showDialog.show();
        showDialog.getWindow().setLayout((width), LinearLayout.LayoutParams.WRAP_CONTENT);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (contactPos == 1) {
                    tvFirstContactName.setText("");
                    tvFirstContact.getEditText().setText("");
                }
                if (contactPos == 2) {
                    tvSecondContactName.setText("");
                    tvSecondContact.getEditText().setText("");
                }
                if (contactPos == 3) {
                    tvThirdContactName.setText("");
                    tvThirdContact.getEditText().setText("");
                }
                if (contactPos == 4) {
                    tvFourthContactName.setText("");
                    tvFourthContact.getEditText().setText("");
                }
                if (contactPos == 5) {
                    tvFifthContactName.setText("");
                    tvFifthContact.getEditText().setText("");
                }
                showDialog.dismiss();

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.select_first_contact_from_list, R.id.select_second_contact_from_list, R.id.select_third_contact_from_list, R.id.select_fourth_contact_from_list, R.id.select_fifth_contact_from_list})
    public void onContactSelectViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_first_contact_from_list:
                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FIRST_CONTACTS);
                        REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FIRST_CONTACTS ;
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), PICK_CONTACT);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FIRST_CONTACTS ;
                }
                break;

            case R.id.select_second_contact_from_list:
                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                    // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_SECOND_CONTACTS);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_SECOND_CONTACTS ;
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), PICK_CONTACT);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_SECOND_CONTACTS ;
                }
                break;

            case R.id.select_third_contact_from_list:
                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                    // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_THIRD_CONTACTS);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_THIRD_CONTACTS ;
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), PICK_CONTACT);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_THIRD_CONTACTS ;

                }
                break;

            case R.id.select_fourth_contact_from_list:
                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                    // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FOURTH_CONTACTS);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FOURTH_CONTACTS ;
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), PICK_CONTACT);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FOURTH_CONTACTS ;
                }
                break;

            case R.id.select_fifth_contact_from_list:
                if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                    // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FIFTH_CONTACTS);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FIFTH_CONTACTS ;
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), PICK_CONTACT);
                    REQUEST_CODE_PICK_CONTACTS = REQUEST_CODE_PICK_FIFTH_CONTACTS ;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(REQUEST_CODE_PICK_CONTACTS == REQUEST_CODE_PICK_FIRST_CONTACTS){
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FIRST_CONTACTS);
                    }
                    if(REQUEST_CODE_PICK_CONTACTS == REQUEST_CODE_PICK_SECOND_CONTACTS){
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_SECOND_CONTACTS);
                    }
                    if(REQUEST_CODE_PICK_CONTACTS == REQUEST_CODE_PICK_THIRD_CONTACTS){
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_THIRD_CONTACTS);
                    }
                    if(REQUEST_CODE_PICK_CONTACTS == REQUEST_CODE_PICK_FOURTH_CONTACTS){
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FOURTH_CONTACTS);
                    }
                    if(REQUEST_CODE_PICK_CONTACTS == REQUEST_CODE_PICK_FIFTH_CONTACTS){
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_FIFTH_CONTACTS);
                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    showErrorToast("    oops !! \n permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }
}
