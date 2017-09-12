/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nepal.naxa.smartnaari.homescreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.widgets.GridRecyclerView;
import com.nepal.naxa.smartnaari.login.LoginActivity;
import com.nepal.naxa.smartnaari.mycircle.MyCircleActivity;
import com.nepal.naxa.smartnaari.passion_of_life.ComplexListActivity;
import com.nepal.naxa.smartnaari.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, HorizontalRecyclerViewAdapter.OnItemClickListener {


    private static List<ViewModel> items = new ArrayList<>();


    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_act_recycler_hori)
    RecyclerView horizontalRecyclerView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.act_main_logo)
    TextView actMainLogo;
    @BindView(R.id.recycler)
    GridRecyclerView recyclerView;
    @BindView(R.id.content)
    CoordinatorLayout content;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
    private Handler handler;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initGridRecyclerView();
        initHorizontalRecyclerView();
        initToolbar();
        setupDrawerLayout();
        setupTopSlider(slider);


        autoScrollHorizontalRecycler();

        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        avatar.setImageResource(R.drawable.ic_camera_profile_photo);

        setRecyclerAdapter(recyclerView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        if (hasFocus) {
//            slider.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
//                    android.R.anim.fade_in));
//            actMainLogo.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
//                    android.R.anim.fade_in));
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setRecyclerAdapter(recyclerView);
        recyclerView.scheduleLayoutAnimation();
        slider.startAutoCycle();
        startIntroAnimation();
    }


    private void initGridRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_large);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        recyclerView.setNestedScrollingEnabled(false);


    }


    private void setRecyclerAdapter(RecyclerView recyclerView) {

        items = ViewModel.getGridItems();
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initHorizontalRecyclerView() {
        List<ViewModel> framelist = ViewModel.getHorizontalViewItems();
        horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(framelist);

        horizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerViewAdapter.setOnItemClickListener(this);

        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(horizontalRecyclerView);

        // pager indicator
        horizontalRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        horizontalRecyclerView.setNestedScrollingEnabled(false);
    }


    public void autoScrollHorizontalRecycler() {
        final int speedScroll = 5000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int positionToScrollTo = 0;

            @Override
            public void run() {
                if (positionToScrollTo == horizontalRecyclerViewAdapter.getItemCount())
                    positionToScrollTo = 0;
                if (positionToScrollTo < horizontalRecyclerViewAdapter.getItemCount()) {
                    horizontalRecyclerView.smoothScrollToPosition(++positionToScrollTo);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_red_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                if (menuItem.getTitle().equals("My Circle")) {
                    Intent intent = new Intent(MainActivity.this, MyCircleActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        DetailActivity.navigate(this, view.findViewById(R.id.root_layout_item_recycler), viewModel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        slider.stopAutoCycle();
    }

    private void setupTopSlider(SliderLayout sliderLayout) {

        HashMap<String, Integer> file_maps = getTopSliderImages();

        for (String name : file_maps.keySet()) {
            ArrowSliderView textSliderView = new ArrowSliderView(this);
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            startActivity(new Intent(MainActivity.this, ComplexListActivity.class));
                        }
                    });


            sliderLayout.stopAutoCycle();

            sliderLayout.addSlider(textSliderView);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Tablet);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setDuration(10000);


        }
    }

    private HashMap<String, Integer> getTopSliderImages() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("First", R.drawable.food_1);
        file_maps.put("Second", R.drawable.food_2);

        return file_maps;
    }

    private void startIntroAnimation() {

        final int ANIM_DURATION_TOOLBAR = 800;

        float actionbarSize = ScreenUtils.dpToPx(56);
        toolbar.setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
    }


    @Override
    public void onHorizontalItemClick(View view, ViewModel viewModel) {

    }


}
