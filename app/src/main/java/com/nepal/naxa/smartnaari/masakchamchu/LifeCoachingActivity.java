package com.nepal.naxa.smartnaari.masakchamchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Majestic on 9/12/2017.
 */

public class LifeCoachingActivity extends AppCompatActivity {
    @BindView(R.id.btnLifeCoachingTakeaTest)
    Button btnLifeCoachingTakeaTest;
    @BindView(R.id.btnLifeCoachingFreeOnlineCourse)
    Button btnLifeCoachingFreeOnlineCourse;
    @BindView(R.id.tvLifeCoachingDetail)
    TextView tvLifeCoachingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_coaching);
        ButterKnife.bind(this);
    }

}
