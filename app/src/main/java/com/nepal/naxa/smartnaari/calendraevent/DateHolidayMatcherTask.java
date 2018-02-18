package com.nepal.naxa.smartnaari.calendraevent;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.utils.date.NepaliDate;

import org.joda.time.LocalDateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by nishon on 1/8/18.
 */

public class DateHolidayMatcherTask extends AsyncTask<Void, Void, List<Day>> {

    private String calendraEventJSON;
    private onDateMatchComplete onDateMatchComplete;
    private String error;
    private String TAG = "DateHolidayMatcherTask";

    private DateHolidayMatcherTask() {

    }

    public static void start(String holidaysJson, onDateMatchComplete onDateMatchComplete) {

        Timber.i("Date string %s",NepaliDate.getEnglishLocalDateTime().toLocalDate().toString());

        DateHolidayMatcherTask dateHolidayMatcherTask = new DateHolidayMatcherTask();
        dateHolidayMatcherTask.setCalendraEventsJSON(holidaysJson);
        dateHolidayMatcherTask.setTaskListener(onDateMatchComplete);
        dateHolidayMatcherTask.execute();
    }

    private void setTaskListener(onDateMatchComplete onDateMatchComplete) {
        this.onDateMatchComplete = onDateMatchComplete;
    }

    private void setCalendraEventsJSON(String calendraEventJSON) {
        this.calendraEventJSON = calendraEventJSON;
    }

    @Override
    protected List<Day> doInBackground(Void... voids) {
        LocalDateTime englishDate = NepaliDate.getEnglishLocalDateTime();



        Type listType = new TypeToken<List<CalendraEvent>>() {
        }.getType();
        ArrayList<CalendraEvent> calendraEventsList = new Gson().fromJson(calendraEventJSON, listType);
        List<Day> day = new ArrayList<>();

        for (CalendraEvent calendraEvent : calendraEventsList) {
            LocalDateTime jsonDateTime = stringToDate(calendraEvent.getEnglishDate());
            if (!isCancelled()) {
                if (isEnglishDateEqual(englishDate, jsonDateTime)) {
                    day = calendraEvent.getDay();
                }
            }
        }


        return day;
    }

    @Override
    protected void onPostExecute(List<Day> days) {
        super.onPostExecute(days);
        onDateMatchComplete.matchComplete(days);

    }

    private boolean isEnglishDateEqual(LocalDateTime first, LocalDateTime second) {

        Timber.i("first %s second %s ",first.toLocalDate().toString(),second.toLocalDate().toString());

        return first.toLocalDate().isEqual(second.toLocalDate());
    }

    private LocalDateTime stringToDate(String date) {
        LocalDateTime dt = null;
        try {
            dt = new LocalDateTime(date);
        } catch (Exception e) {
            cancel(true);
            e.printStackTrace();
            error += "Failed to convert string into datetime object";
            Log.e(TAG, "Failed to convert string into datetime object");
        }
        return dt;

    }


    public interface onDateMatchComplete {
        void matchComplete(List<Day> days);
    }
}
