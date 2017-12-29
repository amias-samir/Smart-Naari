package com.nepal.naxa.smartnaari.passion_of_life.heteregenouscomplexrecycler;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data.local.AppDataManager;
import com.nepal.naxa.smartnaari.data.network.HotPotOfPassionData;

import java.util.ArrayList;
import java.util.List;

public class HotPotComplexRecyclerViewActivity extends BaseActivity {

    RecyclerView rvHeterogeneousLst;

    AppDataManager appDataManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_pot_complex_recycler_view);
        initToolbar();

//        getSampleArrayList();
        appDataManager = new AppDataManager(this);

        // Lookup the recyclerview in activity layout
        rvHeterogeneousLst = (RecyclerView) findViewById(R.id.rvHeteroRecycler);

        bindDataToAdapter();

    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hot Pot of Passion");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.color.colorAccent);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();

        List<HotPotOfPassionData> hotPotOfPassionData = appDataManager.getAllHotPotOfPassiondata() ;

        int dataSize = hotPotOfPassionData.size();
        Log.d("HeterogeneousRecycler", " getSampleArrayList: Activity "+dataSize);
        for (int i = 0 ; i < dataSize ; i++){

            if(hotPotOfPassionData.get(i).getType().equals("photo")){

                items.add(new HotPotImage(hotPotOfPassionData.get(i).getPhoto(), hotPotOfPassionData.get(i).getUploadedBy()));

            }
            if(hotPotOfPassionData.get(i).getType().equals("blog")){

                items.add(new HotPotBlogRecipe(hotPotOfPassionData.get(i).getHead(), hotPotOfPassionData.get(i).getBody(),
                        hotPotOfPassionData.get(i).getPhoto(), hotPotOfPassionData.get(i).getUploadedBy()));

            }
            if(hotPotOfPassionData.get(i).getType().equals("recipe")){

                items.add(new HotPotBlogRecipe(hotPotOfPassionData.get(i).getHead(), hotPotOfPassionData.get(i).getBody(),
                        hotPotOfPassionData.get(i).getPhoto(), hotPotOfPassionData.get(i).getUploadedBy()));
            }
        }
        return items;
    }

    private void bindDataToAdapter() {
        // Bind adapter to recycler view object
        rvHeterogeneousLst.setAdapter(new ComplexRecyclerViewAdapter(getSampleArrayList()));
        rvHeterogeneousLst.setLayoutManager(new LinearLayoutManager(this));

    }
}
