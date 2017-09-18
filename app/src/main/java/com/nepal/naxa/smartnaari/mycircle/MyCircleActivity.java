package com.nepal.naxa.smartnaari.mycircle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;
import com.nepal.naxa.smartnaari.utils.Constants;
import com.nepal.naxa.smartnaari.utils.SpanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCircleActivity extends Activity {

    @BindView(R.id.btnNewMobileNo)
    ImageButton btnNewMobileNo;

    int count = 0;
    static final int PICK_CONTACT = 01;

    //    @BindView(R.id.my_circle_first_contact)
//    EditText myCircleFirstContact;
//    @BindView(R.id.my_circle_second_contact)
//    EditText myCircleSecondContact;
//    @BindView(R.id.my_circle_third_contact)
//    EditText myCircleThirdContact;
//    @BindView(R.id.my_circle_fourth_contact)
//    EditText myCircleFourthContact;
//    @BindView(R.id.my_circle_fifth_contact)
//    EditText myCircleFifthContact;
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
                if (Constants.fifth_contact.equals("")) {
                    Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intentContact, PICK_CONTACT);
                } else {
                    Toast.makeText(this, "You can add only five numbers", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.btnDone:
                Intent intentMainActivity = new Intent(MyCircleActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
                break;
        }
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
//        tvFirstContact.setText(Constants.first_contact);
//        tvSecondContact.setText(Constants.second_contact);
//        tvThirdContact.setText(Constants.third_contact);
//        tvFourthContact.setText(Constants.fourth_contact);
//        tvFifthContact.setText(Constants.fifth_contact);

        String first, second, third, fourth, fifth;

//        first = tvFirstContact.getText().toString();
//        second = tvSecondContact.getText().toString();
//        third = tvThirdContact.getText().toString();
//        fourth = tvFourthContact.getText().toString();
//        fifth = tvFifthContact.getText().toString();

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


}
