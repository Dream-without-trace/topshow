package com.luwei.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @program: topshow
 * @description: 时间
 * @author: ZhangHongJie
 * @create: 2018-12-07 15:53
 **/
@Slf4j
public class DateTimeUtis {

    public DateTimeUtis() {
    }

    /**
     *  日期转星期几
     * @param tmpDate  日期
     * @return
     */
    public static String dayForWeek(Date tmpDate){
        Calendar cal = Calendar.getInstance();
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    
    public static void main(String[] args){
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        System.out.println(zero.toString());
        System.out.println(new Date(new Long("1544242334000")));

        Date date = new Date("1544112000");
        String s = DateTimeUtis.dayForWeek(date);*/

        /*int timesMonthmorning = getTimesMonthmorning();
        System.out.println(timesMonthmorning);
        int timesMonthnight = getTimesMonthnight();
        System.out.println(timesMonthnight);*/


    }

    /*
        获取当天零点时间
     */
    public static Date getZeroDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }


    /**
     * 获取当天零点的时间戳 秒
     * @return
     */
    public static int getZeroTimestamp(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 获得本月第一天0点时间
     * @return
     */
    public static int getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获得本月最后一天24点时间
     * @return
     */
    public static int getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return (int) (cal.getTimeInMillis() / 1000);
    }
    
}
