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

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ui.recyclerview.RecyclerViewEmptySupport;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public abstract class ComplexListBaseActivity extends AppCompatActivity {

    protected RecyclerViewEmptySupport recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // uncomment while testing performance
        //TinyDancer.create()
        //    .redFlagPercentage(.1f)
        //    .startingGravity(Gravity.TOP)
        //    .startingXPosition(200)
        //    .startingYPosition(600)
        //    .show(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.rcv_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setUpAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void setUpAdapter();
}
