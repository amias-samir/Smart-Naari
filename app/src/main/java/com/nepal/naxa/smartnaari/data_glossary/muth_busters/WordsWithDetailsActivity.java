package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.YuwaPustaQueryDetailsActivity;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.yuwapusta.YuwaPustaActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WordsWithDetailsActivity extends BaseActivity implements JSONAssetLoadListener {
    final private String TAG = "WordsWithDetails";

    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;

    private JSONAssetLoadTask jsonAssetLoadTask;
    public static List<WordsWithDetailsModel> wordsWithDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_with_details);

        initToolbar();

        jsonAssetLoadTask = new JSONAssetLoadTask(R.raw.data_glossary, this, this);
        jsonAssetLoadTask.execute();


    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Data Glossary");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    @Override
    public void onFileLoadComplete(String s) {

        Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
        }.getType();
        wordsWithDetailsList = new Gson().fromJson(s, listType);

        Log.e(TAG, "SAMIR This data is: " + s);


        setSectionedRecycleView();

//        setRecyclerClickListner();


    }

    @Override
    public void onFileLoadError(String errorMsg) {

    }


    public void setSectionedRecycleView() {
        //My RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Your RecyclerView.Adapter
//        mAdapter = new SimpleAdapter(this,getResources().getStringArray(R.array.add_a_new_post_categories));
        ArrayList<String> dataGlossaryList = new ArrayList<>();

        for (int i = 0; i < wordsWithDetailsList.size(); i++) {

            dataGlossaryList.add(wordsWithDetailsList.get(i).getTitle());
//            mAdapter = new SimpleAdapter(this, wordsWithDetailsList.get(i));

        }
        String[] stringArray = dataGlossaryList.toArray(new String[0]);

        mAdapter = new SimpleAdapter(this, stringArray);



        //This is the code to provide a sectioned list
        String category = wordsWithDetailsList.get(0).getCategory();

        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, ""+category));

        for (int i = 0; i < wordsWithDetailsList.size(); i++) {
            if(!category.equals(wordsWithDetailsList.get(i).getCategory())){
                category = wordsWithDetailsList.get(i).getCategory();
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i, ""+wordsWithDetailsList.get(i).getCategory()));
            }

        }


        //This is the code to provide a sectioned list
//        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
//                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "Section 2"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12, "Section 3"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14, "Section 4"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20, "Section 5"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }


//    public void setRecyclerClickListner (){
//        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return true;
//            }
//
//        });
//
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//
//
//                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
////                    Drawer.closeDrawers();
//                    int position = recyclerView.getChildPosition(child);
//
//                    Intent intent = new Intent(WordsWithDetailsActivity.this, DataGlossaryWordDetailsActivity.class);
//                    intent.putExtra("wordsWithDetails", wordsWithDetailsList.get(position));
//                    startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
//    }
}
