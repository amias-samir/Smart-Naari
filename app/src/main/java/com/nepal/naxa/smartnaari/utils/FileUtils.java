package com.nepal.naxa.smartnaari.utils;

import com.nepal.naxa.smartnaari.application.SmartNaari;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nishon on 3/2/18.
 */

public class FileUtils {

    public static String readFromFile(int resourceId) {


        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;


        try {
            fIn = SmartNaari.getAppContext().getResources().openRawResource(resourceId);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {

            e.printStackTrace();
            e.getMessage();

        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {

                e2.printStackTrace();
                e2.getMessage();

            }
        }

        return returnString.toString();
    }
}
