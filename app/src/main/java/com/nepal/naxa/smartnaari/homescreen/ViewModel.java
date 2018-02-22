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

import com.nepal.naxa.smartnaari.R;

import java.util.ArrayList;

public class ViewModel {
    private String text;
    private int image;
    private String imageUrl;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ViewModel(String text, int image) {
        this.text = text;

        this.image = image;
    }

    public ViewModel(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }


    public static ArrayList<ViewModel> getHorizontalViewItems() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;
        viewModel = new ViewModel("X's and Y's", R.color.xandy);
        viewModels.add(viewModel);

        viewModel = new ViewModel("What's the Difference", R.color.whatdiff);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Crime and Punishment", R.color.crimepunis);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Myth Busters", R.color.mythbus);
        viewModels.add(viewModel);

        return viewModels;

    }


    public static ArrayList<ViewModel> getGridItems() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;

        viewModel = new ViewModel("Report", R.drawable.grid_ma_chup_basdina);
        viewModels.add(viewModel);


        viewModel = new ViewModel("\t", R.drawable.grid_services);
        // viewModel = new ViewModel("", R.drawable.grid_services);
        viewModels.add(viewModel);

        viewModel = new ViewModel("\t\t", R.drawable.grid_ma_saksam_chu);
        //viewModel = new ViewModel("", R.drawable.grid_ma_saksam_chu);

        viewModels.add(viewModel);

        viewModel = new ViewModel("Yuwa Pusta", R.drawable.grid_yuwa_pusta);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Smart Parenting", R.drawable.grid_smart_parenting);
        viewModels.add(viewModel);

        viewModel = new ViewModel("\t\t\t", R.drawable.i_am_amazing_green);
        //viewModel = new ViewModel("", R.drawable.grid_i_am_amazing);

        viewModels.add(viewModel);

        return viewModels;

    }

    public static ArrayList<ViewModel> getOwlsList() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;
        viewModel = new ViewModel("X's and Y's", "http://lorempixel.com/500/500/animals/1/");
        viewModels.add(viewModel);

        viewModel = new ViewModel("What's the Difference", "http://lorempixel.com/500/500/animals/1/");
        viewModels.add(viewModel);

        viewModel = new ViewModel("Crime and Punishment", R.color.crimepunis);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Myth Busters", R.color.mythbus);
        viewModels.add(viewModel);

        return viewModels;

    }

    public static ArrayList<ViewModel> getServicesList() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;

//        AppDataManager appDataManager = new AppDataManager(this);

        viewModel = new ViewModel("Home", R.color.black);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Friend", R.color.whatdiff);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Hospital", R.color.crimepunis);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Police Station", R.color.colorAccent);
        viewModels.add(viewModel);


        return viewModels;

    }


    public static ArrayList<ViewModel> getTapItStopItGridItem() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;

        viewModel = new ViewModel("Police", R.drawable.grid_tapitstopit_police_logo);
        viewModels.add(viewModel);


        viewModel = new ViewModel("Ambulance", R.drawable.grid_tapitstopit_ambulance);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Hospitals", R.drawable.grid_tapitstopit_hospital_icon);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Hotline", R.drawable.grid_tapitstopit_hotline);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Fire Brigade", R.drawable.grid_tapitstopit_fire_brigade);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Blood Bank", R.drawable.grid_tapitstopit_blood_bank);
        viewModels.add(viewModel);

        return viewModels;

    }



}
