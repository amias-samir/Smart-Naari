package com.nepal.naxa.smartnaari.yuwapusta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.LinePagerIndicatorDecoration;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YuwaPustActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.yuwa_pusta_iv_header)
    ImageView yuwaPustaIvHeader;
    @BindView(R.id.yuwa_pusta_act_tv_header)
    TextView yuwaPustaActTvHeader;
    @BindView(R.id.yuwa_pusta_act_rv_owlslist)
    RecyclerView yuwaPustaActRvOwlslist;
    @BindView(R.id.yuwa_pusta_act_rv_reviewslist)
    RecyclerView yuwaPustaActRvReviewslist;
    private RecyclerViewAdapter horizontalRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwa_pust);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initHorizontalRecyclerView();


    }


    private void initHorizontalRecyclerView() {
        List<Owl> owlslist = Owl.getOwlsList();
        horizontalRecyclerViewAdapter = new RecyclerViewAdapter(owlslist);

        yuwaPustaActRvOwlslist.setAdapter(horizontalRecyclerViewAdapter);
        yuwaPustaActRvOwlslist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerViewAdapter.setOnItemClickListener(this);

        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(yuwaPustaActRvOwlslist);

        // pager indicator
        yuwaPustaActRvOwlslist.addItemDecoration(new LinePagerIndicatorDecoration());
        yuwaPustaActRvOwlslist.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {

    }
}
