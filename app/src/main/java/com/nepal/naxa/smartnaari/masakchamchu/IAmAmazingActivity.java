package com.nepal.naxa.smartnaari.masakchamchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Majestic on 9/12/2017.
 */

public class IAmAmazingActivity extends AppCompatActivity {
    @BindView(R.id.tvIAmAmazingDetail)
    TextView tvIAmAmazingDetail;
    @BindView(R.id.ivIAmAmazingPersonOfTheMonth)
    ImageView ivIAmAmazingPersonOfTheMonth;
    @BindView(R.id.tvIAmAmazingPersonOfTheMonthDetail)
    TextView tvIAmAmazingPersonOfTheMonthDetail;
    @BindView(R.id.tvIAmAmazingReadMore)
    TextView tvIAmAmazingReadMore;
    @BindView(R.id.ivIAmAmazingFlappingPheonix)
    ImageView ivIAmAmazingFlappingPheonix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_am_amazing);
        ButterKnife.bind(this);
    }
}
