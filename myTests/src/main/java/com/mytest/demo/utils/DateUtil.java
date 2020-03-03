package com.mytest.demo.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public final class DateUtil {

    public static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static final String TIMEZONE = "GMT+8";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String EIGHT_CLOCK = "20:00:00";

    /**
     * 前提：minMonth和maxMonth的格式均符合format，否则返回空
     *
     * @param format
     * @param minMonth
     * @param maxMonth
     * @return
     */
    public static List<String> getMonthListBetween(SimpleDateFormat format, String minMonth, String maxMonth) {
        List<String> monthList = new ArrayList<>();
        try {
            Calendar min = Calendar.getInstance();
            min.setTime(format.parse(minMonth));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            Calendar max = Calendar.getInstance();
            max.setTime(format.parse(maxMonth));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                monthList.add(format.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return monthList;
    }

    public static SimpleDateFormat getDFDateTime() {
        return new SimpleDateFormat(DATE_TIME_PATTERN);
    }

    public static SimpleDateFormat getDate() {
        return new SimpleDateFormat(DATE_PATTERN);
    }

    public static String getDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDateTime().format(date);
    }

    public static Date strToDateTime(String szDateTime) {
        try {
            return getDFDateTime().parse(szDateTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date strToDateMonth(String month) {
        try {
            return getDFPatternTightYM().parse(month);
        } catch (Exception e) {
            return null;
        }
    }

    private static final String DATE_PATTERN_PK = "yyMMddHHmmss";

    public static SimpleDateFormat getDFPatternPK() {
        return new SimpleDateFormat(DATE_PATTERN_PK);
    }

    private static final String DATE_PATTERN_UNIONPAY = "yyyyMMddHHmmss";

    public static SimpleDateFormat getDFPatternUnion() {
        return new SimpleDateFormat(DATE_PATTERN_UNIONPAY);
    }

    public static String getDateUnion(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternUnion().format(date);
    }

    public static Date strToUnionDateTime(String unionDateTime) {
        try {
            return getDFPatternUnion().parse(unionDateTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUnionDateTime(Date unionDateTime) {
        try {
            return getDFPatternUnion().format(unionDateTime);
        } catch (Exception e) {
            return null;
        }
    }


    public static String getDatePK(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternPK().format(date);
    }

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat getDFDate() {
        return new SimpleDateFormat(DATE_PATTERN);
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDate().format(date);
    }

    public static Date strToDate(String strDate) {
        try {
            return getDFDate().parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static final String YEAR_PATTERN = "yyyy";

    private static SimpleDateFormat getDFYear() {
        return new SimpleDateFormat(YEAR_PATTERN);
    }

    public static String getYear(Date date) {
        if (date == null) {
            return "";
        }
        return getDFYear().format(date);
    }

    public static Date strToYear(String strDate) {
        try {
            return getDFYear().parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    private static final String DATE_TIME_PATTERN_YMDHMS = "yyyy/MM/dd HH:mm:ss";

    public static SimpleDateFormat getDFPatternYMDHMS() {
        return new SimpleDateFormat(DATE_TIME_PATTERN_YMDHMS);
    }

    private static final String TIME_PATTERN_HMS = "yyyy/MM/dd HH:mm:ss";

    public static SimpleDateFormat getDFPatternHMS() {
        return new SimpleDateFormat(TIME_PATTERN_HMS);
    }

    public static Date strToDateYMDHMS(String strDate) {
        try {
            return getDFPatternYMDHMS().parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    private static final String DATE_TIME_PATTERN_MDHM = "MM/dd HH:mm";

    public static SimpleDateFormat getDFPatternMDHM() {
        return new SimpleDateFormat(DATE_TIME_PATTERN_MDHM);
    }

    public static String getDateMDHM(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternMDHM().format(date);
    }

    private static final String DATE_PATTERN_YMD = "yyyy/MM/dd";

    public static SimpleDateFormat getDFPatternYMD() {
        return new SimpleDateFormat(DATE_PATTERN_YMD);
    }

    public static String getDateYMD(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternYMD().format(date);
    }

    private static final String DATE_PATTERN_TIGHT_YMD = "yyyyMMdd";

    public static SimpleDateFormat getDFPatternTightYMD() {
        return new SimpleDateFormat(DATE_PATTERN_TIGHT_YMD);
    }

    public static String getDateTightYMD(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternTightYMD().format(date);
    }

    public static String getDateMinusYMD(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDate().format(date);
    }

    public static String getTimeHMS(Date date) {
         if (date == null) {
            return "";
        }
        return getDFPatternHMS().format(date);
    }

    public static Date strToDateTightYMD(String strDate) {
        try {
            return getDFPatternTightYMD().parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static final String DATE_PATTERN_TIGHT_YM = "yyyyMM";

    public static SimpleDateFormat getDFPatternTightYM() {
        return new SimpleDateFormat(DATE_PATTERN_TIGHT_YM);
    }

    public static String getDateTightYM(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternTightYM().format(date);
    }

    public static Date strToDateYM(String date) {
        try {
            return getDFPatternTightYM().parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private static final String DATE_PATTERN_TIGHT_MD = "MM.dd";

    private static SimpleDateFormat getDFPatternTightMD1() {
        return new SimpleDateFormat(DATE_PATTERN_TIGHT_MD);
    }

    public static String getDateTightMD1(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternTightMD1().format(date);
    }

    private static final String DATE_PATTERN_TIGHT_YMD1 = "yyyy.MM.dd";

    private static SimpleDateFormat getDFPatternTightYMD1() {
        return new SimpleDateFormat(DATE_PATTERN_TIGHT_YMD1);
    }

    public static Date strToDateYMD1(String date) {
        try {
            return getDFPatternTightYMD1().parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateTightYMD1(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternTightYMD1().format(date);
    }


    private static final String DATE_PATTERN_YMD1 = "yyyy年MM月dd日";
    private static final String DATE_PATTERN_YMD2 = "yyyy年MM月dd日 HH时mm分";

    public static SimpleDateFormat getDFPatternYMD1() {
        return new SimpleDateFormat(DATE_PATTERN_YMD1);
    }

    public static SimpleDateFormat getDFPatternYMD2() {
        return new SimpleDateFormat(DATE_PATTERN_YMD2);
    }

    public static String getDateYMD1(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternYMD1().format(date);
    }

    public static String getDateYMD2(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternYMD2().format(date);
    }

    private static final String DATE_PATTERN_YM = "yyyy年MM月";

    public static SimpleDateFormat getDFPatternYM() {
        return new SimpleDateFormat(DATE_PATTERN_YM);
    }

    public static String getDateYM(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternYM().format(date);
    }

    public static Date strToDateYM2(String date) {
        try {
            return getDFPatternYM().parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    private static final String DATE_PATTERN_MD = "MM/dd";

    public static SimpleDateFormat getDFPatternMD() {
        return new SimpleDateFormat(DATE_PATTERN_MD);
    }

    public static String getDateMD(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternMD().format(date);
    }

    private static final String ZH_CN_MONTH_DAY = "MM月dd日";

    public static SimpleDateFormat getZhCnMDDate() {
        return new SimpleDateFormat(ZH_CN_MONTH_DAY);
    }

    public static String getDateZhCnMDDate(Date date) {
        if (date == null) {
            return "";
        }
        return getZhCnMDDate().format(date);
    }

    private static final String TIME_PATTERN = "HH:mm";

    public static SimpleDateFormat getTimePatternDate() {
        return new SimpleDateFormat(TIME_PATTERN);
    }

    public static String getDateTimePattern(Date date) {
        if (date == null) {
            return "";
        }
        return getTimePatternDate().format(date);
    }

    
    private static final String SHORT_DATE_TIME_PATTERN = "M月d日 HH:mm";

    public static SimpleDateFormat getShortDateTimePattern() {
        return new SimpleDateFormat(SHORT_DATE_TIME_PATTERN);
    }

    public static String getShortDateTimePattern(Date date) {
        if (date == null) {
            return "";
        }
        return getShortDateTimePattern().format(date);
    }

    private static final String DATE_TIME_NOT_YEAR_PATTERN = "MM-dd";

    public static SimpleDateFormat getDateTimeNotYearPattern() {
        return new SimpleDateFormat(DATE_TIME_NOT_YEAR_PATTERN);
    }

    public static String getDateTimeNotYearPattern(Date date) {
        if (date == null) {
            return "";
        }
        return getDateTimeNotYearPattern().format(date);
    }

    private static final String SHORT_DATE_TIME_PATTERN2 = "MM-dd HH:mm";

    public static SimpleDateFormat getShortDateTimePattern2() {
        return new SimpleDateFormat(SHORT_DATE_TIME_PATTERN2);
    }

    public static String getShortDateTimePattern2(Date date) {
        if (date == null) {
            return "";
        }
        return getShortDateTimePattern2().format(date);
    }

    private static final String SHORT_DATE_TIME_PATTERN3 = "MM";

    public static SimpleDateFormat getShortDateTimePattern3() {
        return new SimpleDateFormat(SHORT_DATE_TIME_PATTERN3);
    }

    public static String getShortDateTimePattern3(Date date) {
        if (date == null) {
            return "";
        }
        return getShortDateTimePattern3().format(date);
    }

    private static final String DATE_PATTERN_SHORT_YM = "yyyy-MM";

    public static SimpleDateFormat getDFPatternShortYM() {
        return new SimpleDateFormat(DATE_PATTERN_SHORT_YM);
    }

    public static String getDateTightShortYM(Date date) {
        if (date == null) {
            return "";
        }
        return getDFPatternShortYM().format(date);
    }


    private static final String DATE_PATTERN_SHORT_YMDHM = "yyyy-MM-dd HH:mm";

    public static SimpleDateFormat getDatePatternShortYMDHM() {
        return new SimpleDateFormat(DATE_PATTERN_SHORT_YMDHM);
    }

    public static String getFormatYMDHM(Date date) {
        if (date == null) {
            return "";
        }
        return getDatePatternShortYMDHM().format(date);
    }

    // 重新定义名称
    @Deprecated
    private static final String DATE_TIME_PATTERN2 = "MM/dd HH:mm";
    @Deprecated
    private static final String DATE_PATTERN2 = "yyyy/MM/dd";
    @Deprecated
    private static final String DATE_PATTERN3 = "MM/dd";
    @Deprecated
    private static ThreadLocal<SimpleDateFormat> DF_DATE_TIME2 = new ThreadLocal<>();
    @Deprecated
    private static ThreadLocal<SimpleDateFormat> DF_DATE2 = new ThreadLocal<>();
    @Deprecated
    private static ThreadLocal<SimpleDateFormat> DF_DATE3 = new ThreadLocal<>();

    @Deprecated
    private static SimpleDateFormat getDFDateTime2() {
        if (DF_DATE_TIME2.get() == null) {
            DF_DATE_TIME2.set(new SimpleDateFormat(DATE_TIME_PATTERN2));
        }
        return DF_DATE_TIME2.get();
    }

    @Deprecated
    private static SimpleDateFormat getDFDate2() {
        if (DF_DATE2.get() == null) {
            DF_DATE2.set(new SimpleDateFormat(DATE_PATTERN2));
        }
        return DF_DATE2.get();
    }

    @Deprecated
    private static SimpleDateFormat getDFDate3() {
        if (DF_DATE3.get() == null) {
            DF_DATE3.set(new SimpleDateFormat(DATE_PATTERN3));
        }
        return DF_DATE3.get();
    }

    @Deprecated
    public static String getDateTime2(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDateTime2().format(date);
    }

    @Deprecated
    public static String getDate2(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDate2().format(date);
    }

    @Deprecated
    public static String getDate3(Date date) {
        if (date == null) {
            return "";
        }
        return getDFDate3().format(date);
    }

    // 增加年数
    public static Date addYear(Date date, int yearInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearInc);
        return calendar.getTime();
    }

    // 增加月数
    public static Date addMonth(Date date, int monInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monInc);
        return calendar.getTime();
    }

    // 增加天数
    public static Date addDay(Date date, int dayInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, dayInc);
        return calendar.getTime();
    }

    // 增加小时
    public static Date addHour(Date date, int hourInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hourInc);
        return calendar.getTime();
    }

    // 增加分钟
    public static Date addMinute(Date date, int minuteInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuteInc);
        return calendar.getTime();
    }
    
    // 增加秒
    public static Date addSecond(Date date, int secondInc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, secondInc);
        return calendar.getTime();
    }

    // 获取某天的第一个时间点
    public static Date getDateFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 获取某天的最后一个时间点
    public static Date getDateLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getDateLastTimeWithoutMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 获取某个月的第一个时间点
    public static Date getMonthFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 获取某个月的最后一个时间点
    public static Date getMonthLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    public static Date getMonthFirstTimeWithoutMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthLastTimeWithoutMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
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
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int getDaysBetween(Date smdate, Date bdate) {
        if (smdate == null || bdate == null) {
            return Integer.MAX_VALUE;
        }
        try {
            smdate = DateUtil.getDateFirstTime(smdate);
            bdate = DateUtil.getDateFirstTime(bdate);
            long time1 = smdate.getTime();
            long time2 = bdate.getTime();
            return (int) ((time2 - time1) / (1000 * 3600 * 24L));
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    public static long getMinutesBetween(Date smdate, Date bdate) {
        long between_minutes = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        between_minutes = (time2 - time1) / (1000 * 60);
        return between_minutes;
    }

    public static int getHoursBetween(Date smdate, Date bdate) {
        int between_hours = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        between_hours =(int) ((time2 - time1) / (1000 * 60 * 60));
        return between_hours;
    }

    public static String getYMHBetween(Date smdate, Date bdate) {
        long between = bdate.getTime() - smdate.getTime();
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day == 0) {
            return hour + "小时" + min + "分" + s + "秒";
        }else {
            return day + "天" + hour + "小时" + min + "分" + s + "秒";
        }
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthDay
     * @return
     */
    public static int calAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        // 当前时间的年、月、日
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        // 生日的年、月、日
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        // 年份相减，得出年龄
        int age = yearNow - yearBirth;
        // 当前月份小于等于生日月份，比较月份是否相等
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // 月份相等，继续比较日期
                if (dayOfMonthNow < dayOfMonthBirth) {
                    // 当前日期小于生日日期，年龄age减1
                    age--;
                }
            } else {
                // 当前月份小于生日月份，年龄age 减1
                age--;
            }
        }
        return age;
    }

    // 获取集合中的最大日期
    public static Date getMax(Date... dates) {
        if (dates == null || dates.length == 0) {
            return null;
        }
        Date maxDate = dates[0];
        for (Date date : dates) {
            if (maxDate.before(date)) {
                maxDate = date;
            }
        }
        return maxDate;
    }

    // 计算年龄
    public static int getAge(Date birthday, Date currentDay) {
        if (birthday == null) {
            return 0;
        }
        return (int) ((currentDay.getTime() - birthday.getTime()) / 86400 / 365 / 1000);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) (Math.abs(d2.getTime() - d1.getTime()) / 1000 / 86400);
    }

    public static int secondsBetween(Date d1, Date d2) {
        return (int)((d2.getTime() - d1.getTime()) / 1000);
    }

    public static boolean checkDateYMD(String date) {
        if(StringUtils.hasText(date)){
            String pattern = "(^20[0-9]{2}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1})";
            return Pattern.compile(pattern).matcher(date).matches();
        }
        return false;
    }

    public static String getDateTimeWithFormat(Date date, String format) {
        String returnValue = null;
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            returnValue = dateFormat.format(date);
        }
        return returnValue;
    }

    public static String getDatePattern() {
        return DATE_PATTERN;
    }

    public static String getDateTimePattern() {
        return DATE_TIME_PATTERN;
    }

    /**
     * 结算两个时间月份差
     * @param start
     * @param end
     * @return
     */
    public static int monthBetween(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH);

        calendar.setTime(end);
        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH);
        return (startYear - endYear) * 12 + startMonth - endMonth;
    }

    /*
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * 计算2个日期之间相差的  相差多少年月
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String dayComparePrecise(Date fromDate,Date toDate){
        if(fromDate==null||toDate==null){
           return null;
        }
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(getDateFirstTime(fromDate));
        aft.setTime(getDateFirstTime(toDate));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int result1 = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        int total= Math.abs(result + result1);
        int year=total/12;
        int month=total%12;
        if(month==0){
            return String.format("%s岁", year+"");
        }else{
            return String.format("%s岁%s个月", year+"",month+"");
        }
    }
    
    public static boolean isTimespanOverlapped(Date targetBeginAt, Date targetEndAt, Date baselineBeginAt,
            Date baselineEndAt) {
        return !(targetBeginAt.compareTo(baselineEndAt) > 0 || targetEndAt.compareTo(baselineBeginAt) < 0);
    }


}
