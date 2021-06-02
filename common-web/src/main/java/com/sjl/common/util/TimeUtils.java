package com.sjl.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类add by Kelly on 20170308.
 * @copyright(C) 2019 song
 */
public class TimeUtils {
    /**
     * 设置每个阶段时间，单位秒
     */

    /**
     * 1分钟60秒
     */
    private static final int SECONDS_OF_1MINUTE = 60;

    private static final int SECONDS_OF_30MINUTES = 30 * 60;

    /**
     * 1小时60*60秒
     */
    private static final int SECONDS_OF_1HOUR = 60 * 60;

    /**
     * 1天24*60*60秒
     */
    private static final int SECONDS_OF_1DAY = 24 * 60 * 60;

    private static final int SECONDS_OF_15DAYS = SECONDS_OF_1DAY * 15;

    private static final int SECONDS_OF_30DAYS = SECONDS_OF_1DAY * 30;

    private static final int SECONDS_OF_6MONTHS = SECONDS_OF_30DAYS * 6;

    private static final int SECONDS_OF_1YEAR = SECONDS_OF_30DAYS * 12;

    public static final String DATEFORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT_2 = "yyyy-MM-dd HH:mm";
    public static final String DATEFORMAT_3 = "HH:mm";
    public static final String DATEFORMAT_4 = "yyyy-MM-dd";
    private TimeUtils() {

    }


    /**
     * 获取已过时间
     *
     * @param startTime
     * @return
     */
    public static String getRangeByStringTime(String startTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_1);
        Date date = null;
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertTime(date, new Date());

    }

    /**
     * 获取已过时间
     *
     * @param startTime
     * @return
     */
    public static String getRangeByDate(Date startTime) {
        return convertTime(startTime, new Date());
    }

    /**
     * 时间差转换
     *
     * @param startTime
     * @param curDate
     * @return
     */
    private static String convertTime(Date startTime, Date curDate) {
        /**除以1000是为了转换成秒*/
        long between = (curDate.getTime() - startTime.getTime()) / 1000;
        int elapsedTime = (int) (between);
        if (elapsedTime < SECONDS_OF_1MINUTE) {

            return "刚刚";
        }
        if (elapsedTime < SECONDS_OF_30MINUTES) {

            return elapsedTime / SECONDS_OF_1MINUTE + "分钟前";
        }
        if (elapsedTime < SECONDS_OF_1HOUR) {

            return "半小时前";
        }
        if (elapsedTime < SECONDS_OF_1DAY) {

            return elapsedTime / SECONDS_OF_1HOUR + "小时前";
        }
        if (elapsedTime < SECONDS_OF_15DAYS) {

            return elapsedTime / SECONDS_OF_1DAY + "天前";
        }
        if (elapsedTime < SECONDS_OF_30DAYS) {

            return "半个月前";
        }
        if (elapsedTime < SECONDS_OF_6MONTHS) {

            return elapsedTime / SECONDS_OF_30DAYS + "月前";
        }
        if (elapsedTime < SECONDS_OF_1YEAR) {

            return "半年前";
        }
        if (elapsedTime >= SECONDS_OF_1YEAR) {

            return elapsedTime / SECONDS_OF_1YEAR + "年前";
        }
        return "";
    }

    public static String formatDateToStr(Date date, String dateformat1) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat1);
        return sdf.format(date);
    }

    public static Date strToDate(String date, String dateformat1) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat1);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算时间差
     *
     * @param starTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param type
     *            返回类型 ==1----天，时，分,秒。 ==2----时，分
     * @return 返回时间差
     */
    public static String getTimeDifference(String starTime, String endTime,int type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = dateFormat.parse(starTime);
            Date endDate = dateFormat.parse(endTime);
            return  computeTime(startDate,endDate,type);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算时间差（即距离某时间还剩多少时间）
     *
     * @param startDatetime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param type
     *            返回类型 ==1----天，时，分,秒。 ==2----时，分
     * @return 返回时间差
     */
    public static String getTimeDifference(Date startDatetime, String endTime,int type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date endDate = dateFormat.parse(endTime);
            return  computeTime(startDatetime,endDate,type);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String computeTime(Date startDate, Date endDate,int type) {
        String timeString = "";
        long diff = (endDate.getTime() - startDate.getTime())/1000;//秒
        if (diff <= 0){
            return "-1";//结束
        }
        if (type == 0){
            long day = diff / (SECONDS_OF_1DAY);
            long hour = (diff / (SECONDS_OF_1HOUR) - day * 24);
            long min = ((diff / (SECONDS_OF_1MINUTE)) - day * 24 * 60 - hour * 60);
            long s = (diff  - day * SECONDS_OF_1DAY- hour * SECONDS_OF_1HOUR - min * 60);
            timeString = day + "天" + hour + "小时" + min + "分" + s + "秒";
        }else if(type == 1){
            long hour1 = diff / (SECONDS_OF_1HOUR);
            long min1 = ((diff / (SECONDS_OF_1MINUTE)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
        }
        return timeString;
    }


	public static Date UKstrToDate(String dateStr, String dateformat) {
		Date date = null;
		try {
			date=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
