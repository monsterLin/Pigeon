package com.monsterlin.pigeon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pluto  on 2016/5/6.
 */
public class MyDateUtils {
    private static StringBuffer mStringBuffer;

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getTimeAsDate() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

    /**
     * 将Date转换为String
     *
     * @param date
     * @return
     */
    public static String getTimeAsString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm");
//        /* 【"yyyy年MM月dd日"】长度11 */
        return formatter.format(date);
    }

    /**
     * 截取时间 HH:mm
     *
     * @param dateString
     * @return
     */
    public static String subTime(String dateString) {
        return dateString.substring(8, dateString.length());
    }

    /**
     * 截取年份
     *
     * @param dateString
     * @return
     */
    public static String subYear(String dateString) {
        return dateString.substring(0, 4);
    }

    /**
     * 截取月份
     *
     * @param dateString
     * @return
     */
    public static String subMonth(String dateString) {
        if (Integer.parseInt(dateString.substring(4, 5)) == 0) {/*如果第一个数字为0 如03月*/
            return dateString.substring(5, 6);/*3月*/
        } else {
            return dateString.substring(4, 6);/*11月*/
        }
    }

    /**
     * 截取日期
     *
     * @param dateString
     * @return
     */
    public static String subDay(String dateString) {
        if (Integer.parseInt(dateString.substring(6, 7)) == 0) {/*如果第一个数字为0 如03日*/
            return dateString.substring(7,8 );/*3日*/
        } else {
            return dateString.substring(6, 8);/*11日*/
        }
    }

    /**
     * 获取yyyy年MM月dd日
     *
     * @param dateString
     * @return
     */
    public static String getDealTime(String dateString) {
        if (mStringBuffer == null) {
            mStringBuffer = new StringBuffer();
        } else {
            mStringBuffer.delete(0, mStringBuffer.length());/*清空内容*/
        }
        mStringBuffer.append(subYear(dateString)).append("年")
                .append(subMonth(dateString)).append("月")
                .append(subDay(dateString)).append("日");
        return mStringBuffer.toString();
    }

    /**
     * 获取MM月dd日
     *
     * @param dateString
     * @return
     */
    public static String getMonthAndDay(String dateString) {
        if (mStringBuffer == null) {
            mStringBuffer = new StringBuffer();
        } else {
            mStringBuffer.delete(0, mStringBuffer.length());/*清空内容*/
        }
        mStringBuffer.append(subMonth(dateString)).append("月")
                .append(subDay(dateString)).append("日");
        return mStringBuffer.toString();
    }

    public static String getStringForFormat(String oldDate) {
        String currentTime = getTimeAsString(getTimeAsDate());
        if (subYear(currentTime).equals(subYear(oldDate))) {/*同一年份*/
            return getMonthAndDay(currentTime).equals(getMonthAndDay(oldDate))/*同年同日？*/
                    ? subTime(oldDate)/*同年同日 HH:mm*/
                    : getMonthAndDay(oldDate);/*同年不同日 a月b日*/
        } else {/*不同年份*/
            return getDealTime(oldDate);
        }
    }
}
