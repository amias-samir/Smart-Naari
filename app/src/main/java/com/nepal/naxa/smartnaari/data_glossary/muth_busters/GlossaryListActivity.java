package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.data_glossary.JSONLoadImpl;
import com.nepal.naxa.smartnaari.masakchamchu.IAmAmazingActivity;
import com.nepal.naxa.smartnaari.tapitstopit.TapItStopItActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GlossaryListActivity extends BaseActivity implements JSONAssetLoadListener {
    final private String TAG = "WordsWithDetails";

    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;
    SimpleAdapter mFilteredAdapter;
    SimpleSectionedRecyclerViewAdapter mSectionedAdapter;

    private SearchView searchView;
    private boolean isFiltering = false;


    private JSONAssetLoadTask jsonAssetLoadTask;
    public static List<WordsWithDetailsModel> wordsWithDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_with_details);

        initToolbar();

        new JSONLoadImpl().getGlossaryObject();
        jsonAssetLoadTask = new JSONAssetLoadTask(R.raw.data_glossary, this, this);
        jsonAssetLoadTask.execute();

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Data Glossary");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_data_glossary, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {

                if(TextUtils.isEmpty(query)){
                    isFiltering = false;
                    setSectionedRecycleView(mAdapter);
                }else {
                    // filter recycler view when query submitted
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 0.55s = 500ms
                            isFiltering = true;
                            mFilteredAdapter.getFilter().filter(query);
                            if(mFilteredAdapter != null) {
                                setSectionedRecycleView(mFilteredAdapter);
                            }
                        }
                    }, 500);


                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                // filter recycler view when text is changed
//                mAdapterFiltered.getFilter().filter(query);
                if(TextUtils.isEmpty(query)){
                    isFiltering = false;
                    setSectionedRecycleView(mAdapter);
                }else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 0.55s = 500ms
                            isFiltering = true;
                            mFilteredAdapter.getFilter().filter(query);
                            if(mFilteredAdapter != null) {
                                setSectionedRecycleView(mFilteredAdapter);
                            }
                        }
                    }, 500);
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            //noinspection SimplifiableIfStatement
            case R.id.action_search:
                mAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFileLoadComplete(String s) {

        Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
        }.getType();
        wordsWithDetailsList = new Gson().fromJson(s, listType);

        Log.e(TAG, "SAMIR This data is: " + s);


        mAdapter = new SimpleAdapter(this, wordsWithDetailsList);
        mFilteredAdapter = new SimpleAdapter(this, wordsWithDetailsList);
        setSectionedRecycleView(mAdapter);

    }

    @Override
    public void onFileLoadError(String errorMsg) {

    }


    public void setSectionedRecycleView(SimpleAdapter simpleAdapter) {
        //My RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



if(isFiltering){
    mSectionedAdapter = new
            SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, simpleAdapter);
    mRecyclerView.setAdapter(mSectionedAdapter);
    simpleAdapter.notifyDataSetChanged();
    mSectionedAdapter.notifyDataSetChanged();

}else {
    //This is the code to provide a sectioned list
    String category = wordsWithDetailsList.get(0).getCategory();

    List<SimpleSectionedRecyclerViewAdapter.Section> sections =
            new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "" + category));

    for (int i = 0; i < wordsWithDetailsList.size(); i++) {
        if (!category.equals(wordsWithDetailsList.get(i).getCategory())) {
            category = wordsWithDetailsList.get(i).getCategory();
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i, "" + wordsWithDetailsList.get(i).getCategory()));
        }
    }

    //Add your adapter to the sectionAdapter
    SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
    mSectionedAdapter = new
            SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, simpleAdapter);
    mSectionedAdapter.setSections(sections.toArray(dummy));


    //Apply this adapter to the RecyclerView
    mRecyclerView.setAdapter(mSectionedAdapter);
    simpleAdapter.notifyDataSetChanged();
    mSectionedAdapter.notifyDataSetChanged();
}

    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        fileList();
        super.onBackPressed();
    }


}
