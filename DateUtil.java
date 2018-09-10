package com.mhc.gwsti.biz.utils;

/**
 * Created by kechangqiang on 16/9/26.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.SimpleTimeZone;

@Slf4j
public class DateUtil {

    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMDNO = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YMD = "yyyyMMdd";
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String DATE_FORMAT_MDHM = "MM-dd HH:mm";

    public static Date yyyyMMddParse(String date) throws ParseException {
        try {
            SimpleDateFormat dateParse = new SimpleDateFormat("yyyyMMdd");
            Date targetDate = dateParse.parse(date);
            return targetDate;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 自定义时间格式来格式化日期
     * @param date
     * @param dateFormat
     * @return 转换失败返回空字符串
     */
    public static String dateFormatter(Date date, String dateFormat) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
            return dateFormatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String yyMMddFormate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String yyMMddDate = dateFormat.format(date);
        return yyMMddDate;
    }

    public static String yyyyMMddHHmmssParse(Date date) {
        try {
            SimpleDateFormat dateParse = new SimpleDateFormat("yyyyMMddHHmmss");
            return dateParse.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * convert LocalDateTime to "yyyy-MM-dd HH:mm" String
     */
    public static String yyyyMMddHHmmParse(LocalDateTime localDateTime) {
        return convertLocalDateTimeToString(localDateTime, DATE_FORMAT_YMDHM);
    }

    /**
     * convert LocalDateTime to "MM-dd HH:mm" String
     */
    public static String MMddHHmmParse(LocalDateTime localDateTime) {
        return convertLocalDateTimeToString(localDateTime, DATE_FORMAT_MDHM);
    }

    public static String yyyyMMddHHmmssParseDate(LocalDateTime localDateTime) {
        return convertLocalDateTimeToString(localDateTime, DATE_FORMAT_YMDHMS);
    }
    public static String yyyyMMddHHmmss(LocalDateTime localDateTime) {
        return convertLocalDateTimeToString(localDateTime, DATE_FORMAT_YMDNO);
    }

    public static String yyyyMMdd(LocalDateTime localDateTime) {
        return convertLocalDateTimeToString(localDateTime, DATE_FORMAT_YMD);
    }

    public static String dateParse(Date date) {
        try {
            SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateParse.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDateString(LocalDateTime localDateTime) {
        return dateParse(convertLocalDateTimeToDate(localDateTime));
    }

    /**
     * 格林尼治时间转化为北京时间
     *
     * @param localDateTime
     * @return
     */
    public static String getUTCString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZonedDateTime zdt = localDateTime.atZone(ZoneOffset.UTC);
        return dateParse(Date.from(zdt.toInstant()));
    }


    public static String yyyyMMddHHmmssParse(LocalDateTime localDateTime) {
        return yyyyMMddHHmmssParse(convertLocalDateTimeToDate(localDateTime));
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM:ss"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHHmm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM:ss"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHM(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy年MM月dd日"
     *
     * @param date
     * @return
     */
    public static String convertDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        if (date != null) {
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYM(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        return f.format(date);
    }

    public static String convertDateToStr(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(formatStr);
        return f.format(date);
    }

    public static String convertDateToYM1(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy_MM");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "MM-dd hh:MM"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToMD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("MM-dd");
        return f.format(date);
    }


    /**
     * 获取Calendar的日期
     * "yyyy-MM-dd"
     *
     * @param cal 时间
     * @return 格式化以后的时间字符串
     */
    public static String getDate(Calendar cal) {
        String v_strDate = "";
        int v_intYear = cal.get(Calendar.YEAR);
        int v_intMonth = cal.get(Calendar.MONTH) + 1;
        int v_intDAY = cal.get(Calendar.DAY_OF_MONTH);

        v_strDate = v_strDate + v_intYear + "-";

        if (v_intMonth < 10) {
            v_strDate = v_strDate + "0" + v_intMonth + "-";
        } else {
            v_strDate = v_strDate + v_intMonth + "-";
        }
        if (v_intDAY < 10) {
            v_strDate = v_strDate + "0" + v_intDAY + "";
        } else {
            v_strDate = v_strDate + v_intDAY + "";
        }

        return v_strDate;
    }

    /**
     * 获取选定日期的目标间隔日期
     *
     * @param date   选定日期
     * @param number 间隔日期
     */
    public static String getDateStr(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    /**
     * 获取选定日期的目标间隔日期
     *
     * @param date   选定日期
     * @param number 间隔日期
     */
    public static Date getDateBy(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return calendar.getTime();
    }

    /**
     * 获取选定日期的目标间隔日期
     *
     * @param date   选定日期
     * @param number 间隔日期
     */
    public static LocalDateTime getDateBy(LocalDateTime date, int number) {
        if (null == date) {
            return null;
        }
        date.plusDays(number);
        return date;
    }

    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * @param date
     * @param number
     * @return
     */
    public static String getDateStr2(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    /**
     * 指定日期格式化 yyyy-MM
     *
     * @param date
     * @param number
     * @return
     */
    public static String getDateStr3(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(calendar.getTime());
    }


    /**
     * 比较两个时间的间隔天数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static int getSpaceByCompareTwoDate(Date startTime, Date endTime) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startTime);
        end.setTime(endTime);
        int betweenYears = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
        int betweenDays = end.get(Calendar.DAY_OF_YEAR)
                - start.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            start.set(Calendar.YEAR, (start.get(Calendar.YEAR) + 1));
            betweenDays += start.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }


    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy.MM.dd hh:MM:ss"
     *
     * @param date 时间
     * @return 格式化以后的时间字符串
     */
    public static String convertDateYMDHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "HH:mm"
     *
     * @param date
     * @return
     */
    public static String convertDateToTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        return f.format(date);
    }

    public static String getISO8601Time(Date date) {
        Date nowDate = date;
        if (null == date) {
            nowDate = new Date();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(nowDate);
    }

    /**
     * create by jason 2015-07-20
     * 获得今天的开始时间 e.g. 2015-07-10 00 :00:00
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date startTime = todayStart.getTime();
        return startTime;
    }

    /**
     * create by jason 2015-07-20
     * 获得该时间所处的开始时间 e.g. 2015-07-10 00 :00:00
     */
    public static Date getStartTime(Date startTime) {
        if (startTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startTime = calendar.getTime();
        return startTime;
    }

    /**
     * create by jason 2015-07-20
     * 获得昨天的开始时间 e.g. 2015-07-10 00 :00:00
     */
    public static Date getYstdStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.DATE, todayStart.get(Calendar.DATE) - 1);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date startTime = todayStart.getTime();
        return startTime;
    }

    /**
     * create by jason 2015-07-20
     * 获得该时间所处的结束时间 e.g. 2015-07-10 23 :59:59
     */
    public static Date getEndTime(Date endTime) {
        if (endTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        endTime = calendar.getTime();
        return endTime;
    }

    /**
     * 获取当天的结束时间 e.g 2016-09-18 23:59:59
     *
     * @return
     */
    public static Date getEndTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 59);
        Date endTime = todayStart.getTime();
        return endTime;
    }


    /**
     * create by jason 2015-11-07
     * 获得昨天的结束时间 e.g. 2015-11-07 59:59:59
     */
    public static Date getYstdEndTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.DATE, todayStart.get(Calendar.DATE) - 1);
        todayStart.set(Calendar.HOUR_OF_DAY, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 59);
        Date startTime = todayStart.getTime();
        return startTime;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 获取制定时间的月初时间，默认当月
     *
     * @param dateTime
     * @return
     */
    public static Date getStartMonth(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        if (dateTime != null) {
            calendar.setTime(dateTime);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取制定时间的月末时间，默认当月
     *
     * @param dateTime
     * @return
     */
    public static Date getEndMonth(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        if (dateTime != null) {
            calendar.setTime(dateTime);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取指定月份的第一天
     * 如现在是2015-12-14，
     * day = 1  ：返回2016-01-01
     * day = -1 ：返回2015-11-01
     * day = 0  ：返回2015-12-01
     *
     * @param day
     * @return
     */
    public static String getFirstMonthDay(Integer day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, day);//设置月份
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String day_first = df.format(calendar.getTime());
        return day_first;
    }

    /**
     * 获取指定月份的最后一天
     * 如现在是2015-12-14，
     * day = 1  ：返回2016-01-31
     * day = -1 ：返回2015-11-30
     * day = 0  ：返回2015-12-31
     *
     * @param day
     * @return
     */
    public static String getLastMonthDay(Integer day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, day);//设置月份
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号
        calendar.add(Calendar.MONTH, 1);//月增加1天
        calendar.add(Calendar.DAY_OF_MONTH, -1);//日期倒数一日,既得到此月份的最后一天
        String day_last = df.format(calendar.getTime());
        return day_last;
    }

    /**
     * 获取上一年
     *
     * @param date   当前日期
     * @param number 间隔
     * @return
     */
    public static Date getLastYear(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, number);
        return calendar.getTime();
    }

    public static String convertDateToHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        return f.format(date);
    }

    /**
     * 返回给定date的小时数字, 24小时制
     *
     * @param date 给定的date对象
     * @return 小时数字
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static String getDHBetween(Date startDate, Date endDate) {
        String str = "%s天%s小时";
        long m = endDate.getTime() - startDate.getTime();
        long days = m / (1000 * 3600 * 24);
        long hours = (m % (1000 * 3600 * 24)) / (1000 * 3600);
        return String.format(str, days, hours);
    }

    public static LocalDateTime convertToLocatDateTime(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime;
        }
    }

    public static String getTimeFromNow(Date date) {
        if (date == null) {
            return "0小时";
        }
        String str = "%s小时%s分";
        Date now = new Date();
        long between = now.getTime() - date.getTime();
        long hours = between / (1000 * 60 * 60);
        long minutes = (between % (1000 * 60 * 60)) / (1000 * 60);
        return String.format(str, hours, minutes);
    }

    /**
     * 将localDateTime转成date
     *
     * @param ldt
     * @return
     */
    public static Date ofDate(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        } else {
            return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    public static LocalDateTime UDateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 把date转化为新版的LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime ofLocalDateTime(Date date) {
        if (date == null) {
            return null;
        } else {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
    }

    public static LocalDateTime ofLocalDateTime(long timestampSecond) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampSecond), ZoneId.systemDefault());
    }

    public static LocalDateTime ofLocalDateTimeMilli(long timestampMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMilli), ZoneId.systemDefault());
    }

    public static long getMilliTimestamp(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return 0;
        }
        return ofDate(localDateTime).getTime();
    }

    /**
     * 将LocalDateTime按照pattern转换为String
     *
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String convertLocalDateTimeToString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || StringUtils.isBlank(pattern)) {
            return StringUtils.EMPTY;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(dateTime);
    }

    /**
     * 将LocalDateTime转换为Date
     *
     * @param dateTime
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将String转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHMS);
        LocalDateTime ldt = LocalDateTime.parse(date, df);
        return ldt;
    }

    /**
     * 获取Unix时间戳
     * @return
     */
    public static String getNowUnixTimestamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp;
    }

    /**
     * 通过Unix时间戳，获取Date
     * @return
     */
    public static Date getByUnixTimestamp(Integer unixTimestamp) {
        return new Date(unixTimestamp * 1000);
    }

}

