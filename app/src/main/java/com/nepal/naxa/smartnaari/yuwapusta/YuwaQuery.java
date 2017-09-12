package com.nepal.naxa.smartnaari.yuwapusta;

import java.util.ArrayList;

/**
 * Created on 9/12/17
 * by nishon.tan@gmail.com
 */

public class YuwaQuery {

    private String question;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public YuwaQuery(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }


    public YuwaQuery() {
    }

    public static ArrayList<YuwaQuery> getDemoQueries() {

        ArrayList<YuwaQuery> yuwaQueries = new ArrayList<>();
        yuwaQueries.add(new YuwaQuery());
        yuwaQueries.add(new YuwaQuery());
        yuwaQueries.add(new YuwaQuery());

        return yuwaQueries;

    }
}
