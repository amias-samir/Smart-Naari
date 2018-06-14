package com.nepal.naxa.smartnaari.utils.date;

import android.util.Log;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import timber.log.Timber;

import static java.lang.String.format;

public class NepaliDate {

    private static final int OFFSET = 2;
    private static final String TAG = "NepaliDate";
    private static String[] nepaliMonths = new String[]{"बैशाख", "जेठ", "असार", "साउन", "भदौ", "असोज", "कार्तिक", "मंसिर", "पौष", "माघ", "फाल्गुन", "चैत"};
    private static String[] nepaliDays = new String[]{ "", "सोमवार", "मगलवार", "बुधवार", "बिहिवार", "शुक्रवार","शनिवार","आइतवार"};

    private static String formatNepaliDate(LocalDateTime localDateTime) throws NepaliDateException {

        LocalDateTime nepaliDateTime = getNepaliDateTime(localDateTime);
        int monthIndex = nepaliDateTime.getMonthOfYear() - 1;
        int dayIndex = localDateTime.getDayOfWeek();


        Timber.i(nepaliDateTime.toString(DateTimeFormat.mediumDate()));
        Timber.i(nepaliDateTime.getDayOfWeek() + "");
        return nepaliMonths[monthIndex] + " " + nepaliDateTime.getDayOfMonth() + "," + nepaliDays[dayIndex];

    }

    public static String getCurrentEngDate() {

        LocalDateTime localDateTime = new LocalDateTime();
        return formatEngDate(localDateTime);

    }


    public static LocalDateTime getEnglishLocalDateTime() {
        LocalDateTime localDateTime = new LocalDateTime();
        return localDateTime;
    }

    public static String getCurrentNepaliDate() throws NepaliDateException {

        return formatNepaliDate(new LocalDateTime());

    }

    private static String formatEngDate(LocalDateTime localDateTime) {

        return localDateTime.toString(DateTimeFormat.longDate());

    }


    private static LocalDateTime getNepaliDateTime(LocalDateTime localDateTime) throws NepaliDateException {


        // We have defined our own Epoch for Bikram Sambat:
        //   1-1-2007 BS / 13-4-1950 AD
        final long MS_PER_DAY = 86400000L;
        final long BS_EPOCH_TS = -622359900000L; // 1950-4-13 AD
        final long BS_YEAR_ZERO = 2007L;

        int year = 2007;
        int days;
        days = (int) Math.floor((localDateTime.toDateTime().getMillis() - BS_EPOCH_TS) / MS_PER_DAY) + 1;

        while (days > 0) {
            for (int m = 1; m <= 12; ++m) {
                int dM = NepaliDaysInMonth(year, m);
                if (days <= dM) {

                    String dateTime = year + "-" + m + "-" + days;
                    Log.e(TAG, "getNepaliDateTime: "+dateTime);
                    return new LocalDateTime(dateTime);
                }
                days -= dM;
            }
            ++year;
        }

        throw new NepaliDateException("Date outside supported range: " + localDateTime.getYear() + " AD");

    }

    /**
     * Magic numbers:
     * 2000 <- the first year encoded in ENCODED_MONTH_LENGTHS
     * month #5 <- this is the only month which has a day variation of more than 1
     * & 3 <- this is a 2 bit mask, i.e. 0...011
     */
    private static int NepaliDaysInMonth(int year, int month) throws NepaliDateException {

        final long[] ENCODED_MONTH_LENGTHS = {
                8673005L, 5315258L, 5314298L, 9459438L, 8673005L, 5315258L, 5314298L, 9459438L, 8473322L, 5315258L, 5314298L, 9459438L, 5327594L, 5315258L, 5314298L, 9459438L, 5327594L, 5315258L, 5314286L, 8673006L, 5315306L, 5315258L, 5265134L, 8673006L, 5315306L, 5315258L, 9459438L, 8673005L, 5315258L, 5314490L, 9459438L, 8673005L, 5315258L, 5314298L, 9459438L, 8473325L, 5315258L, 5314298L, 9459438L, 5327594L, 5315258L, 5314298L, 9459438L, 5327594L, 5315258L, 5314286L, 9459438L, 5315306L, 5315258L, 5265134L, 8673006L, 5315306L, 5315258L, 5265134L, 8673006L, 5315258L, 5314490L, 9459438L, 8673005L, 5315258L, 5314298L, 9459438L, 8669933L, 5315258L, 5314298L, 9459438L, 8473322L, 5315258L, 5314298L, 9459438L, 5327594L, 5315258L, 5314286L, 9459438L, 5315306L, 5315258L, 5265134L, 8673006L, 5315306L, 5315258L, 5265134L, 5527290L, 5527277L, 5527226L, 5527226L, 5528046L, 5527277L, 5528250L, 5528057L, 5527277L, 5527277L,
        };

        try {
            return 29 + (int) ((ENCODED_MONTH_LENGTHS[year - 2000] >>>
                    (((month - 1) << 1))) & 3);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new NepaliDateException(format("Unsupported year/month combination: %s/%s", year, month));
        }
    }
}