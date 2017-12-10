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

package com.nepal.naxa.smartnaari.passion_of_life.adapter;

import android.content.Context;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.nepal.naxa.smartnaari.passion_of_life.binder.NewsPhotoExpandableBinder;
import com.nepal.naxa.smartnaari.passion_of_life.binder.NewsPhotoFavoriteBinder;
import com.nepal.naxa.smartnaari.passion_of_life.binder.NewsTextPhotoBinder;
import com.nepal.naxa.smartnaari.passion_of_life.decorator.ArticleItemDecorator;
import com.nepal.naxa.smartnaari.passion_of_life.model.Article;

import java.util.List;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public class ComplexListAdapter extends RecyclerAdapter {

  private DataListManager<Article> simpleNewsList;
  private DataListManager<Article> expandableNewsList;
  private DataListManager<Article> favoriteNewsList;

  public ComplexListAdapter(Context context) {

    simpleNewsList = new DataListManager<>(this);
    expandableNewsList = new DataListManager<>(this);
    favoriteNewsList = new DataListManager<>(this);

    registerBinder(new NewsTextPhotoBinder(new ArticleItemDecorator()));
    registerBinder(new NewsPhotoExpandableBinder(new ArticleItemDecorator()));
    registerBinder(new NewsPhotoFavoriteBinder(new ArticleItemDecorator()));

    addDataManager(simpleNewsList);
    addDataManager(expandableNewsList);
    addDataManager(favoriteNewsList);
  }

  public void addAllModelItem(List<Article> dataList) {
    expandableNewsList.addAll(dataList);
  }
}