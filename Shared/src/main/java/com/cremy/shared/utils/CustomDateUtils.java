package com.cremy.shared.utils;

import java.util.Calendar;

/**
 * Created by remychantenay on 26/05/2016.
 */
public class CustomDateUtils {

    /**
     * Allows to get the date (string format) of now
     * @return
     */
    public static String getNow() {
        return Calendar.getInstance().getTime().toString();
    }

    /**
     * Allows to know if a given calendar instance is set before today
     * @param _calendar
     * @return
     */
    public static boolean isBeforeToday(Calendar _calendar) {
        return _calendar.before(Calendar.getInstance());
    }

    /**
     * Allows to know if a given calendar instance is set today
     * @param _calendar
     * @return
     */
    public static boolean isToday(Calendar _calendar) {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DAY_OF_MONTH)==_calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==_calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==_calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Allows to know if a given calendar instance is set Today+1 (Tomorrow)
     * @param _calendar
     * @return
     */
    public static boolean isTomorrow(Calendar _calendar) {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.DAY_OF_MONTH)+1)==_calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==_calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==_calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

}
