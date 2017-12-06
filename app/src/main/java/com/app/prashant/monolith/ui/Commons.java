package com.app.prashant.monolith.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commons {

    public static String getFormattedDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return format.format(newDate);
    }
}
