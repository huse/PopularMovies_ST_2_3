package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateAdding {


    private static Date getFormattedDate(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Log.v("hhhh", "DateAdding");
        return simpleDateFormat.parse(date);
    }


    public static String getLocalizedDate(Context context, String date, String format)
            throws ParseException {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        return dateFormat.format(getFormattedDate(date, format));
    }
}
