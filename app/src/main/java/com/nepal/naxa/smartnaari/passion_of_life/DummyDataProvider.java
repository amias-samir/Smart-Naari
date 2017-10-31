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

import android.graphics.Color;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.passion_of_life.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public class DummyDataProvider {

    private static Random random = new Random();

    private static String[] articleDummyTimes = {
            "Few seconds ago", "3 minutes ago", "2 hours ago", "1 Sep", "24 Aug", "22 Aug", "21 Aug",
            "21 Aug", "20 Aug", "20 Aug",
    };

    private static String[] articleDummyTitles = {
            "Create recyclerview adapters like a boss", "On the road with Android",
            "Life can be tough - Here are few SDK improvements", "Google developer podcast - Android",
            "A maze of twisty little passages", "A stitch in time", "Android first week",
            "Just show me the code", "This is the droid you are looking for"
    };

    private static int[] colorList = {
            Color.parseColor("#ef9a9a"), Color.parseColor("#F48FB1"), Color.parseColor("#CE93D8"),
            Color.parseColor("#B39DDB"), Color.parseColor("#9FA8DA"), Color.parseColor("#90CAF9"),
            Color.parseColor("#81D4FA"), Color.parseColor("#C5E1A5"), Color.parseColor("#FFCC80")
    };

    private static int[] drawableList =
            {R.drawable.ic_profile_background, R.drawable.ic_profile_background, R.drawable.ic_profile_background, R.drawable.ic_profile_background,
                    R.drawable.ic_profile_background, R.drawable.ic_profile_background, R.drawable.ic_profile_background};

    public static List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();

        int i = -1;
        for (String title : articleDummyTitles) {
            Article article = new Article(++i, title, "TEXT", articleDummyTimes[i], true, drawableList[random.nextInt(3)], colorList[i], "http://lorempixel.com/1000/500/food/" + i);
            articles.add(article);
        }
        return articles;
    }

    public static List<Article> getExpandableNews() {
        List<Article> articles = new ArrayList<>();

        int i = -1;
        for (String title : articleDummyTitles) {
            Article article = new Article(++i, title, "EXPAND", articleDummyTimes[i], false, drawableList[random.nextInt(3)], colorList[i], "http://lorempixel.com/400/200/food/" + i);
            articles.add(article);
        }
        return articles;
    }

    public static List<Article> getFavoriteNews() {
        List<Article> articles = new ArrayList<>();

        int i = -1;
        for (String title : articleDummyTitles) {
            Article article = new Article(++i, title, "FAVORITE", articleDummyTimes[i], false, drawableList[random.nextInt(3)], colorList[i], "http://lorempixel.com/400/200/food/" + i);
            articles.add(article);
        }
        return articles;
    }

    public static List<Article> getNews() {

        List<Article> articles = new ArrayList<>();

        Article article = new Article(1231231, "Moon", "FAVORITE", articleDummyTimes[0], false, drawableList[random.nextInt(3)], colorList[0], "http://lorempixel.com/400/200/food/" + 6);
        articles.add(article);

        return articles;
    }
}