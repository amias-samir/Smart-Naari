package com.nepal.naxa.smartnaari.mycircle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
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
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.data.network.MyCircleDetails;
import com.nepal.naxa.smartnaari.data.network.UserData;
import com.nepal.naxa.smartnaari.data.network.UserDetail;
import com.nepal.naxa.smartnaari.data.network.retrofit.ErrorSupportCallback;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiClient;
import com.nepal.naxa.smartnaari.data.network.retrofit.NetworkApiInterface;
import com.nepal.naxa.smartnaari.utils.Constants;
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
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID; // contacts unique ID



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


    SessionManager sessionManager ;
    MyCircleData myCircleData ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        myCircleData = new MyCircleData();
        myCircleData = sessionManager.getMyCircleContact();

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
            tvThirdContactName.setVisibility(View.VISIBLE);
            count = count + 1;
        } else if (count == 1) {
            tvThirdContactName.setVisibility(View.VISIBLE);
            tvThirdContact.setVisibility(View.VISIBLE);

            tvFourthContact.setVisibility(View.VISIBLE);
            tvFourthContactName.setVisibility(View.VISIBLE);
            count = count + 1;

        } else if (count == 2) {
            tvThirdContact.setVisibility(View.VISIBLE);
            tvThirdContactName.setVisibility(View.VISIBLE);

            tvFourthContact.setVisibility(View.VISIBLE);
            tvFourthContactName.setVisibility(View.VISIBLE);

            tvFifthContact.setVisibility(View.VISIBLE);
            tvFifthContactName.setVisibility(View.VISIBLE);

            count = count + 1;
        } else if (count > 2) {

            Toast.makeText(this, "You can add only five numbers", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick({R.id.btnSelectContactNo, R.id.btnDone})
    public void onBtnClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSelectContactNo:




                myCircleData.setContactNumber1(tvFirstContact.getText().toString());
                myCircleData.setContactNumber2(tvSecondContact.getText().toString());
                myCircleData.setContactNumber3(tvThirdContact.getText().toString());
                myCircleData.setContactNumber4(tvFourthContact.getText().toString());
                myCircleData.setContactNumber5(tvFifthContact.getText().toString());

                first_contact = myCircleData.getContactNumber1();
                second_contact = myCircleData.getContactNumber2();
                third_contact = myCircleData.getContactNumber3();
                fourth_contact = myCircleData.getContactNumber4();
                fifth_contact = myCircleData.getContactNumber5();


                if(hasPermission(Manifest.permission.READ_CONTACTS)) {

                    if (first_contact.equals("") || second_contact.equals("") || third_contact.equals("") ||
                            fourth_contact.equals("") || fifth_contact.equals("")) {
//                    Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                    startActivityForResult(intentContact, PICK_CONTACT);
                        // using native contacts selection
                        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
                    } else {
                        showInfoToast("You can only add five phone number");

                    }
                } else {
                    requestPermissionsSafely((new String[]{Manifest.permission.READ_CONTACTS}), 01);
                }

                break;


            case R.id.btnDone:

                myCircleData.setContactNumber1(tvFirstContact.getText().toString());
                myCircleData.setContactNumber2(tvSecondContact.getText().toString());
                myCircleData.setContactNumber3(tvThirdContact.getText().toString());
                myCircleData.setContactNumber4(tvFourthContact.getText().toString());
                myCircleData.setContactNumber5(tvFifthContact.getText().toString());

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

        if (second_contact.length() < 10) {
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


//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        switch (reqCode) {
//            case (PICK_CONTACT):
//
//                if (resultCode == Activity.RESULT_OK) {
//                    Uri contactData = data.getData();
//                    Cursor c = managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                        Log.d("MyCircleActivity", "onActivityResult: hasPhone : SAMIR : "+hasPhone);
//
//                        if (hasPhone.equalsIgnoreCase("1")) {
//                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
//                            phones.moveToFirst();
//                            String contact_number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            String contact_name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                            // contact_image_path = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
//
//                            Log.d("MyCircleActivity", "onActivityResult: SAMIR \n \n \t \t \t "+ contact_name + " : " + contact_number+" \n \n \n");
//
//                            setContcts(contact_name,contact_number);
//                            initializeContactsUI();
//                        }
//                    }
//                }
//                break;
//
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

//            retrieveContactName();
//            retrieveContactNumber();

            setContcts(retrieveContactName(),retrieveContactNumber());
//            initializeContactsUI();
//            retrieveContactPhoto();

        }
    }


    public void setContcts(String contact_name, String contact_no) {

        String first, second, third, fourth, fifth;


        first = myCircleData.getContactNumber1();
        second = myCircleData.getContactNumber2();
        third = myCircleData.getContactNumber3();
        fourth = myCircleData.getContactNumber4();
        fifth = myCircleData.getContactNumber5();

//        first = tvFirstContact.getText().toString();
//        second = tvSecondContact.getText().toString();
//        third = tvThirdContact.getText().toString();
//        fourth = tvFourthContact.getText().toString();
//        fifth = tvFifthContact.getText().toString();



        try {
            if (first.equals("")) {
//                Constants.first_contact = contact_no;
                tvFirstContact.setText(contact_no);
                tvFirstContactName.setText(contact_name);
                myCircleData.setContactName1(contact_name);
                myCircleData.setContactNumber1(contact_no);

            } else if (second.equals("")) {
//                Constants.second_contact = contact_no;
                tvSecondContactName.setText(contact_name);
                tvSecondContact.setText(contact_no);

                myCircleData.setContactName2(contact_name);
                myCircleData.setContactNumber2(contact_no);

            } else if (third.equals("")) {
                count = count + 1;
//                Constants.third_contact = contact_no;
                tvThirdContactName.setVisibility(View.VISIBLE);
                tvThirdContact.setVisibility(View.VISIBLE);
                tvThirdContactName.setText(contact_name);
                tvThirdContact.setText(contact_no);
                myCircleData.setContactName3(contact_name);
                myCircleData.setContactNumber3(contact_no);

            } else if (fourth.equals("")) {
                count = count + 1;
//                Constants.fourth_contact = contact_no;
                tvFourthContactName.setVisibility(View.VISIBLE);
                tvFourthContact.setVisibility(View.VISIBLE);
                tvFourthContactName.setText(contact_name);
                tvFourthContact.setText(contact_no);
                myCircleData.setContactName4(contact_name);
                myCircleData.setContactNumber4(contact_no);

            } else if (fifth.equals("")) {
                count = count + 1;
//                Constants.fifth_contact = contact_no;
                tvFifthContactName.setVisibility(View.VISIBLE);
                tvFifthContact.setVisibility(View.VISIBLE);
                tvFifthContactName.setText(contact_name);
                tvFifthContact.setText(contact_no);
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

            myCircleData.setContactName2(jOBJ.getString("c1"));
            myCircleData.setContactNumber2(jOBJ.getString("n1"));

            myCircleData.setContactName3(jOBJ.getString("c2"));
            myCircleData.setContactNumber3(jOBJ.getString("n2"));

            myCircleData.setContactName4(jOBJ.getString("c3"));
            myCircleData.setContactNumber4(jOBJ.getString("n3"));

            myCircleData.setContactName5(jOBJ.getString("c4"));
            myCircleData.setContactNumber5(jOBJ.getString("n4"));

            myCircleData.setContactName1(jOBJ.getString("c5"));
            myCircleData.setContactNumber1(jOBJ.getString("n5"));

        }catch (Exception e){
            e.printStackTrace();
        }

//         myCircleData = sessionManager.getMyCircleContact();


        try {
            tvFirstContact.setText(myCircleData.getContactNumber1());
            tvFirstContactName.setText(myCircleData.getContactName1());
            tvSecondContact.setText(myCircleData.getContactNumber2());
            tvSecondContactName.setText(myCircleData.getContactName2());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
        if (!myCircleData.getContactNumber3().equals("")) {
            tvThirdContact.setVisibility(View.VISIBLE);
            tvThirdContactName.setVisibility(View.VISIBLE);
            tvThirdContact.setText(myCircleData.getContactNumber3());
            tvThirdContactName.setText(myCircleData.getContactName3());
        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
        if (!myCircleData.getContactNumber4().equals("")) {
            tvFourthContact.setVisibility(View.VISIBLE);
            tvFourthContactName.setVisibility(View.VISIBLE);
            tvFourthContactName.setText(myCircleData.getContactName4());
            tvFourthContact.setText(myCircleData.getContactNumber4());
        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
        if (!myCircleData.getContactNumber5().equals("")) {
            tvFifthContact.setVisibility(View.VISIBLE);
            tvFifthContactName.setVisibility(View.VISIBLE);
            tvFifthContact.setText(myCircleData.getContactNumber5());
            tvFifthContactName.setText(myCircleData.getContactName5());
        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }




    }


    public void convertDataToJSON() {

        UserData userData = sessionManager.getUser();

        myCircleData.setContactNumber1(tvFirstContact.getText().toString());
        myCircleData.setContactNumber2(tvSecondContact.getText().toString());
        myCircleData.setContactNumber3(tvThirdContact.getText().toString());
        myCircleData.setContactNumber4(tvFourthContact.getText().toString());
        myCircleData.setContactNumber5(tvFifthContact.getText().toString());

        myCircleData.setContactName1(tvFirstContactName.getText().toString());
        myCircleData.setContactName2(tvSecondContactName.getText().toString());
        myCircleData.setContactName3(tvThirdContactName.getText().toString());
        myCircleData.setContactName4(tvFourthContactName.getText().toString());
        myCircleData.setContactName5(tvFifthContactName.getText().toString());

        myCircleData.setUserId(userData.getUserId());


        try {



            JSONObject header = new JSONObject();

            header.put("user_id", sessionManager.getUserId() );
            header.put("c1", tvFirstContact.getText().toString());
            header.put("c2", tvSecondContact.getText().toString());
            header.put("c3", tvThirdContact.getText().toString());
            header.put("c4", tvFourthContact.getText().toString());
            header.put("c5", tvFifthContact.getText().toString());
            header.put("n1", tvFirstContactName.getText().toString());
            header.put("n2", tvSecondContactName.getText().toString());
            header.put("n3", tvThirdContactName.getText().toString());
            header.put("n4", tvFourthContactName.getText().toString());
            header.put("n5", tvFifthContactName.getText().toString());


            jsonToSend = header.toString();

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

                        if(sessionManager.doesHaveIntentBackgroundService()){
                            Intent intent = new Intent(MyCircleActivity.this, BeautifulMainActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MyCircleActivity.this, PermissionActivity.class);
                            startActivity(intent);
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

}
