package com.teamhelper.phone.utils;

import com.teamhelper.meeting.manager.MeetingManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtils {

    public static GregorianCalendar get0Day() {
        return new GregorianCalendar(1, 0, 1);
    }

    public static Calendar getCurrentTime() {
        return Calendar.getInstance();
    }

    public static boolean isToday(Calendar calendar) {
        Calendar current = getCurrentTime();
        return current.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && current.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static String date(Calendar calendar) {
        StringBuilder sb = new StringBuilder(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        if (StringUtil.equals(MeetingManager.getLanguage().getLanguage(), Locale.ENGLISH.getLanguage())) {
            sb.append(" ").append(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("日");
        }
        sb.append(" ").append(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        return sb.toString();
    }

    public static String timeString(Calendar calendar) {
        //判断当前日期是否是今年
        Calendar current = getCurrentTime();
        if (current.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.equals(MeetingManager.getLanguage().getLanguage(), Locale.ENGLISH.getLanguage()) ? "MM-dd HH:mm" : "MM月dd日 HH:mm", Locale.getDefault());
            return sdf.format(calendar.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.equals(MeetingManager.getLanguage().getLanguage(), Locale.ENGLISH.getLanguage()) ? "yyyy-MM-dd HH:mm" : "yyyy年MM月dd日 HH:mm", Locale.getDefault());
            return sdf.format(calendar.getTime());
        }
    }

    private static SimpleDateFormat HHmm;

    public static String time(Calendar calendar) {
        if (HHmm == null) {
            HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        }
        return HHmm.format(calendar.getTime());
    }

    public static int compare(Calendar time1, Calendar time2) {
        long time1Millis = time1.getTimeInMillis();
        long time2Millis = time2.getTimeInMillis();
        if (time1Millis == time2Millis) return 0;
        else return time1Millis > time2Millis ? 1 : -1;
    }

    public static int daySwitchesBetween(Calendar time1, Calendar time2) {
        long time1Millis = time1.getTimeInMillis();
        long time2Millis = time2.getTimeInMillis();
        int fix = 0;
        if (tomorrowOClock(time1) - time1Millis < tomorrowOClock(time2) - time2Millis) {
            fix = 1;
        }

        return (int) ((time2Millis - time1Millis) / (24 * 60 * 60 * 1000)) + fix;
    }

    public static long todayOClock(Calendar time) {
        Calendar temp = (Calendar) time.clone();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);
        return temp.getTimeInMillis();
    }

    public static long tomorrowOClock(Calendar time) {
        Calendar temp = (Calendar) time.clone();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);
        if (temp.getTimeInMillis() < time.getTimeInMillis()) {
            temp.add(Calendar.DAY_OF_YEAR, 1);
        }
        return temp.getTimeInMillis();
    }

    public static boolean isAtStartDay(Calendar startDate, Calendar selectedDate) {
        return selectedDate.get(Calendar.YEAR) == startDate.get(Calendar.YEAR)
                && selectedDate.get(Calendar.DAY_OF_YEAR) == startDate.get(Calendar.DAY_OF_YEAR);
    }

    public static int calculateStepOffset(Calendar startDate, Calendar selectedDate, int minutesInterval) {
        if (!isAtStartDay(startDate, selectedDate)) return 0;
        int stepOffset = 0;

        int hourValue = startDate.get(Calendar.HOUR_OF_DAY);
        int minuteValue = startDate.get(Calendar.MINUTE);

        stepOffset += (hourValue) * (60 / minutesInterval);
        boolean remain = minuteValue % minutesInterval > 0;
        minuteValue = (minuteValue / minutesInterval + (remain ? 1 : 0)) * minutesInterval;
        stepOffset += minuteValue / minutesInterval;

        return stepOffset;
    }

    public static int calculateStep(Calendar date, int minutesInterval) {
        int hours = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE);
        return (hours * 60 + minutes) / minutesInterval;
    }

    public static int calculateStep(Calendar startDate, Calendar toDate, int minutesInterval) {
        int hours;
        int minutes;
        if (isAtStartDay(startDate, toDate)) {
            hours = toDate.get(Calendar.HOUR_OF_DAY) - startDate.get(Calendar.HOUR_OF_DAY);
            if (hours == 0) {
                minutes = toDate.get(Calendar.MINUTE) - startDate.get(Calendar.MINUTE);
            } else {
                minutes = toDate.get(Calendar.MINUTE);
            }
        } else {
            hours = toDate.get(Calendar.HOUR_OF_DAY);
            minutes = toDate.get(Calendar.MINUTE);
        }
        return (hours * 60 + minutes) / minutesInterval;
    }

    public static String convertTimestampToTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertTimestampToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.equals(MeetingManager.getLanguage().getLanguage(), Locale.ENGLISH.getLanguage()) ? "yyyy-MM-dd" : "yyyy年MM月dd日", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertTimestampToJoinTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
