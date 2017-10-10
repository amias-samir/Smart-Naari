/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nepal.naxa.smartnaari.passion_of_life;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;

import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.passion_of_life.adapter.ComplexListAdapter;
import com.nepal.naxa.smartnaari.passion_of_life.model.Article;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public class ComplexListActivity extends ComplexListBaseActivity {

    private ComplexListAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ComplexListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void setUpAdapter() {
        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);

         adapter = new ComplexListAdapter(this);
        adapter.setSpanCount(1);

        adapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);



        recyclerView.addItemDecoration(adapter.getItemDecorationManager());
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(findViewById(R.id.general_recycler_empty));



    }

    @Override
    protected void onResume() {
        super.onResume();


        // Single list
        final List<Article> dataListThree = DummyDataProvider.getArticles();

        List<Article> expandableNews = DummyDataProvider.getExpandableNews();
        dataListThree.addAll(expandableNews);

        List<Article> favoriteNews = DummyDataProvider.getFavoriteNews();
        dataListThree.addAll(favoriteNews);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addAllModelItem(dataListThree);
            }
        }, TimeUnit.SECONDS.toMillis(10));


    }
}