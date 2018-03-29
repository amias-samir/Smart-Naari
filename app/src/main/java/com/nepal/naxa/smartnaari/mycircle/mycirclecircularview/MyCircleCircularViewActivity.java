package com.nepal.naxa.smartnaari.mycircle.mycirclecircularview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.SessionManager;
import com.nepal.naxa.smartnaari.data.network.MyCircleData;
import com.nepal.naxa.smartnaari.masakchamchu.IAmAmazingActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyCircleCircularViewActivity extends BaseActivity {

    @BindView(R.id.imagePerson1)
    CircleImageView imagePerson1;
    @BindView(R.id.namePerson1)
    TextView namePerson1;
    @BindView(R.id.numberPerson1)
    TextView numberPerson1;
    @BindView(R.id.person1_layout)
    RelativeLayout person1Layout;
    @BindView(R.id.imagePerson2)
    CircleImageView imagePerson2;
    @BindView(R.id.namePerson2)
    TextView namePerson2;
    @BindView(R.id.numberPerson2)
    TextView numberPerson2;
    @BindView(R.id.person2_layout)
    RelativeLayout person2Layout;
    @BindView(R.id.imagePerson3)
    CircleImageView imagePerson3;
    @BindView(R.id.namePerson3)
    TextView namePerson3;
    @BindView(R.id.numberPerson3)
    TextView numberPerson3;
    @BindView(R.id.person3_layout)
    RelativeLayout person3Layout;
    @BindView(R.id.imagePerson4)
    CircleImageView imagePerson4;
    @BindView(R.id.namePerson4)
    TextView namePerson4;
    @BindView(R.id.numberPerson4)
    TextView numberPerson4;
    @BindView(R.id.person4_layout)
    RelativeLayout person4Layout;
    @BindView(R.id.imagePerson5)
    CircleImageView imagePerson5;
    @BindView(R.id.namePerson5)
    TextView namePerson5;
    @BindView(R.id.numberPerson5)
    TextView numberPerson5;
    @BindView(R.id.person5_layout)
    RelativeLayout person5Layout;
    @BindView(R.id.divider_img)
    ImageView dividerImg;
    @BindView(R.id.btn_edit_circle)
    Button btnEditCircle;
    @BindView(R.id.btn_update_circle)
    Button btnUpdateCircle;

    SessionManager sessionManager;
    MyCircleData myCircleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle_circular_view);
        ButterKnife.bind(this);


        sessionManager = new SessionManager(this);
        myCircleData = new MyCircleData();

        if(sessionManager.doesUserHaveCircle()) {
            myCircleData = sessionManager.getMyCircleContact();
        }

        initToolbar();

        initializeContactsUI();

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Circle");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void initializeContactsUI() {

        person1Layout.setVisibility(View.INVISIBLE);
        person2Layout.setVisibility(View.INVISIBLE);
        person3Layout.setVisibility(View.INVISIBLE);
        person4Layout.setVisibility(View.INVISIBLE);
        person5Layout.setVisibility(View.INVISIBLE);




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
            if (!myCircleData.getContactNumber1().equals("")) {
                person1Layout.setVisibility(View.VISIBLE);
                numberPerson1.setText(myCircleData.getContactNumber1());
                namePerson1.setText(myCircleData.getContactName1());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber2().equals("")) {
                person2Layout.setVisibility(View.VISIBLE);
                numberPerson2.setText(myCircleData.getContactNumber2());
                namePerson2.setText(myCircleData.getContactName2());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber3().equals("")) {
                person3Layout.setVisibility(View.VISIBLE);
                numberPerson3.setText(myCircleData.getContactNumber3());
                namePerson3.setText(myCircleData.getContactName3());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber4().equals("")) {

                person4Layout.setVisibility(View.VISIBLE);
                namePerson4.setText(myCircleData.getContactName4());
                numberPerson4.setText(myCircleData.getContactNumber4());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (!myCircleData.getContactNumber5().equals("")) {
                person5Layout.setVisibility(View.VISIBLE);
                numberPerson5.setText(myCircleData.getContactNumber5());
                namePerson5.setText(myCircleData.getContactName5());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.item_call:
                Intent intent = new Intent(MyCircleCircularViewActivity.this, TapItStopItActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.btn_edit_circle, R.id.btn_update_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_circle:
                Intent intent = new Intent(MyCircleCircularViewActivity.this, MyCircleActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_update_circle:
                Intent intent1 = new Intent(MyCircleCircularViewActivity.this, MyCircleActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }



}
