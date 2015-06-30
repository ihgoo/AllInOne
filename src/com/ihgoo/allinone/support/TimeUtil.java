package com.ihgoo.allinone.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import android.util.TimeUtils;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class TimeUtil {
    /**
     * 时间转换 输入一个string 输出 yyyy/MM/dd HH:mm
     */
    public static String getDateFromStr(String strTime) {
        String date;
        if (strTime != null && !"".equals(strTime)) {

            long updateTime = Long.parseLong(strTime);
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            date = dateformat1.format(new Date(updateTime * 1000));
            return date;
        } else {
            return "";
        }
    }

    /**
     * 不乘1000
     * 时间转换 输入一个string 输出 yyyy-MM-dd
     */
    public static String getDateFromStr2(String strTime) {
        String date;
        if (strTime != null && !"".equals(strTime)) {

            long updateTime = Long.parseLong(strTime);
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
            date = dateformat1.format(new Date(updateTime));
            return date;
        } else {
            return "";
        }
    }

    public static String timeFormat(long date) {
        long ssaa = new Date().getTime();
        long delta = ssaa - date;
        final String ONE_SECOND_AGO = "秒前";
        final String ONE_MINUTE_AGO = "分钟前";
        final String ONE_HOUR_AGO = "小时前";
        final String ONE_DAY_AGO = "天前";
        final long ONE_MINUTE = 60000L;
        final long ONE_HOUR = 3600000L;
        final long ONE_DAY = 86400000L;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        } else {
            String time111 = getDateStr(date);
            return time111;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static String getDateStr(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        Formatter ft = new Formatter(Locale.CHINA);
        return ft.format("%1$tY年%1$tm月%1$td日", cal).toString();
    }


    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间字符串格式化成yyyy年MM月dd日格式的字符串
     *
     * @param dataString
     * @return
     */
    public static String paserData2Data(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = 0;
        try {
            Date d = sdf.parse(dataString);
            l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        long lcc_time = Long.valueOf(l + "");
        return sdf1.format(new Date(lcc_time));
    }


    public static String paserDataNB(String timeStmp) {
        long i = ParseUtil.parseLong(timeStmp, 0);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(i + "");
        return sdf1.format(new Date(lcc_time));
    }

    public static String parserData2ymd(String timeStmp) {
        long i = ParseUtil.parseLong(timeStmp, 0);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        long lcc_time = Long.valueOf(i + "");
        return sdf1.format(new Date(lcc_time));
    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static int getWeek(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //第几天，从周日开始
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day - 1;
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
