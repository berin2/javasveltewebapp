package com.svelteup.app.backend.utils.dateutils;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StaticDateService {
    public static final String YYYYMMDDHHMMSSformat = "yyyy-mm-dd hh:mm";
    public static final DateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMMSSformat);
    public static final Long MS_IN_DAY = 86400000L;
    public static final Long MS_IN_HOUR = MS_IN_DAY / 24L;

    public static Date getNow()
    {
        return new Date(System.currentTimeMillis());
    }

    public static String getDateString(Date date)
    {
        return dateFormat.format(date);
    }

    public static String getDateString(Timestamp date)
    {

        return dateFormat.format(date);
    }
}
