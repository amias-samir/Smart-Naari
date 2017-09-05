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

import android.support.annotation.DrawableRes;

import com.nepal.naxa.smartnaari.R;

import java.util.ArrayList;

public class ViewModel {
    private String text;


    private int image;

    public ViewModel(String text, int image) {
        this.text = text;
        this.image = image;
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
        viewModel = new ViewModel("X's and Y's", R.color.colorPrimary);
        viewModels.add(viewModel);

        viewModel = new ViewModel("What's the Difference", R.color.accent_translucent);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Crime and Punishment", R.color.accent_bright);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Myth Busters", R.color.colorPrimary);
        viewModels.add(viewModel);

        return viewModels;

    }


    public static ArrayList<ViewModel> getGridItems() {
        ArrayList<ViewModel> viewModels = new ArrayList<>();


        ViewModel viewModel;


        viewModel = new ViewModel("Report a cause", R.drawable.grid_ma_chup_basdina);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Time is of the essence", R.drawable.grid_services);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Sakshyam Chu", R.drawable.grid_ma_saksam_chu);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Yuwa asda", R.drawable.grid_yuwa_pusta);
        viewModels.add(viewModel);

        viewModel = new ViewModel("Smart Parenting", R.drawable.grid_yuwa_pusta);
        viewModels.add(viewModel);

        viewModel = new ViewModel("", R.drawable.grid_i_am_amazing);
        viewModels.add(viewModel);

        return viewModels;

    }
}
