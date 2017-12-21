package com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.FormEndFragment;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.FormStartFragment;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.LifeCoachingTestFormFragment;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.onAnswerSelectedListener;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.onEvaluationCompletionListner;
import com.nepal.naxa.smartnaari.masakchamchu.lifecoachingtest.fragment.onFormFinishedListener;
import com.nepal.naxa.smartnaari.utils.ConstantData;
import com.nepal.naxa.smartnaari.utils.ui.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LifeCoachingTestActivity extends BaseActivity implements onAnswerSelectedListener, onFormFinishedListener{

    public static final String TAG = "LifeCoachingTest";



    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public String jsonToSend;
    private JSONObject header = null;
    String dataSentStatus, dateString;
    ProgressDialog mProgressDlg;
    String formid ;
    Context context = this;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    Button ButtonPrev, ButtonNext ;
    int countYes = 0 , countNo = 0 ;
    String evaluatedText = "";

    onEvaluationCompletionListner evaluationListner ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_coaching_test);

//        initialize toolbar
        initToolbar();
        startFormFilling();

        ButtonPrev = (Button) findViewById(R.id.prevBtn);
        ButtonNext = (Button) findViewById(R.id.nextBtn);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onFragmentVisibleListener fragment = (onFragmentVisibleListener) adapter.instantiateItem(viewPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Life Coaching test");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FormStartFragment(), " ");
        for (int i = 0; i < ConstantData.LifeCoachingTestQuestion.length; i++) {
            LifeCoachingTestFormFragment lifeCoachingTestFormFragment = new LifeCoachingTestFormFragment();
            lifeCoachingTestFormFragment.prepareQuestionsAndAnswers(ConstantData.LifeCoachingTestQuestion[i], i);
            int questionNumber = i + 1;
            String fragmentName = "Question no. " + questionNumber;
            adapter.addFragment(lifeCoachingTestFormFragment, fragmentName);
        }

        adapter.addFragment(new FormEndFragment(), "");
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onAnswerSelected(String question, String answer) {
        addAnswerToJSON(question, answer);
    }

    @Override
    public void evaluateForm() {
        //todo call upload method here
        finalizeAnswers();
        Log.i(TAG, jsonToSend);
        Log.e(TAG, "evaluateFormSAMIR: "+jsonToSend );
//        Toast.makeText(this, jsonToSend, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(jsonToSend) ;

                for(int i = 1 ; i <= 20 ; i ++){
                    if(jsonObject.getString("q"+i).equals("yes")){
                        countYes++;
                    }else {
                        countNo++;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(countYes > countNo){
                evaluatedText = ConstantData.moreYES;
//                evaluationListner.displayResult(evaluatedText);
                header = new JSONObject();
                jsonToSend = "";


            }else {
                evaluatedText = ConstantData.moreNO;
                header = new JSONObject();
                jsonToSend = "";

            }

            }




    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public String  displayResult(){
        return evaluatedText;
    }

    public interface onFragmentVisibleListener {
        void fragmentBecameVisible(int position);
    }


    public void startFormFilling() {
        header = new JSONObject();
    }

    public void addAnswerToJSON(String questionId, String answer) {
        Log.e(TAG, "addAnswerToJSON: "+questionId +" , "+ answer);

        try {
            if (header.has(questionId)) {
                header.remove(questionId);
            }
            header.put(questionId, answer);
        } catch (JSONException e) {
            Log.e(TAG, "Error - While Adding Answer to JSON \n" + e);

            e.printStackTrace();
        }
    }

    public void finalizeAnswers() {
        try {
            header.put("token", "534545sDfkjHuy589io8gj983jtdfkjj&ihs@->89<-ioj389OiJijor9834%67");
        } catch (JSONException e) {
            Log.e(TAG, "Error - While Adding Token to JSON \n" + e);
            e.printStackTrace();
        }
        jsonToSend = header.toString();

    }

    public void nextFragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void prevFragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }


    @Override
    public void lastFragment() {
        ButtonNext.setVisibility(View.GONE);
    }

    @Override
    public void firstFragment() {
        ButtonPrev.setVisibility(View.INVISIBLE);
    }

    @Override
    public void formFragment() {
        ButtonNext.setVisibility(View.VISIBLE);
        ButtonPrev.setVisibility(View.VISIBLE);
    }
}
