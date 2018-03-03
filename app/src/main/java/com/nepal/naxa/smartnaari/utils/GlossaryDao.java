package com.nepal.naxa.smartnaari.utils;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.DataGlossaryWordDetailsActivity;
import com.nepal.naxa.smartnaari.data_glossary.muth_busters.WordsWithDetailsModel;
import com.nepal.naxa.smartnaari.machupbasdina.MaChupBasdinaActivity;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by nishon on 3/3/18.
 */

public class GlossaryDao {



    public Observable<WordsWithDetailsModel> searchAndOpenDetail(final String jsonString, final String searchString) {

        return Observable.just(jsonString)
                .flatMap(new Function<String, ObservableSource<List<WordsWithDetailsModel>>>() {
                    @Override
                    public ObservableSource<List<WordsWithDetailsModel>> apply(String s) throws Exception {
                        Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
                        }.getType();
                        List<WordsWithDetailsModel> list = new Gson().fromJson(jsonString, listType);
                        return Observable.just(list);

                    }
                })
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .filter(new Predicate<WordsWithDetailsModel>() {
                    @Override
                    public boolean test(WordsWithDetailsModel wordsWithDetailsModel) throws Exception {
                        return wordsWithDetailsModel.getTitle().equalsIgnoreCase(searchString.trim());

                    }
                })
                .defaultIfEmpty(new WordsWithDetailsModel("error"));
    }
}
