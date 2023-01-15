package com.blog.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: lxk
 * @date 2022/7/14 11:21
 * @description: 日期处理工具类
 */

public class DateUtil {


    public final static String DATE = "yyyy-MM-dd";

    public final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_HOURS = "yyyy-MM-dd HH";
    public final static String DATE_TIME_MINUTES = "yyyy-MM-dd HH:mm";

    public final static String DATE_TIME_MILLION = "yyyy-MM-dd HH:mm:ss:SSS";

    public final static String ZERO_TIME = " 00:00:00";
    public final static String ZERO_TIME_MILLION = " 00:00:00:000";
    public final static String ZERO_TIME_WITHOUT_HOURS = ":00:00";
    public final static String ZERO_TIME_WITHOUT_MINUTES = ":00";


    /**
     * 字符串转成日期、时间格式
     * @param dateString 日期字符串
     * @param pattern 格式化类型，默认为yyyy-MM-dd，其它使用DateUtils.xxx
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString, String pattern) throws ParseException {
        if(StringUtils.isBlank(dateString)){
            return null;
        }else{
            dateString = dateString.trim();
            if(StringUtils.isBlank(pattern)){
                pattern = DATE;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(dateString);
        }
    }

    /**
     * 字符串转成日期（yyyy-MM-dd）格式
     * @param dateString 日期字符串
     * @return Date
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException{
        return parse(dateString, null);
    }

    /**
     * 字符串转成时间（yyyy-MM-dd HH:mm:ss）格式
     * @param dateString 日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String dateString) throws ParseException{
        if(StringUtils.isBlank(dateString)){
            return null;
        }else{
            dateString = dateString.trim();
            if(dateString.length() == DATE_TIME_HOURS.length()){
                return parse(dateString, DATE_TIME_HOURS);
            }else if(dateString.length() == DATE_TIME_MINUTES.length()){
                return parse(dateString, DATE_TIME_MINUTES);
            }else if(dateString.length() == DATE_TIME_MILLION.length()){
                return parse(dateString, DATE_TIME_MILLION);
            }else{
                return parse(dateString, DATE_TIME);
            }
        }
    }

    /**
     * 时间转字符串
     * @param date 时间
     * @param pattern 格式化类型，默认为yyyy-MM-dd HH:mm:ss，其它使用DateUtils.xxx
     * @return
     */
    public static String format(Date date, String pattern){
        if(date == null){
            return "";
        }else{
            if(StringUtils.isBlank(pattern)){
                pattern = DATE_TIME;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        }
    }

    /**
     * 时间转日期字符串（yyyy-MM-dd）
     * @param date 时间
     * @return
     */
    public static String formatDate(Date date){
        return format(date, DATE);
    }

    /**
     * 时间转日期字符串（yyyy-MM-dd HH:mm:ss）
     * @param date 时间
     * @return
     */
    public static String formatDateTime(Date date){
        return format(date, null);
    }

    /**
     * 将日期格式转换成时间（yyyy-MM-dd HH:mm:ss）格式
     * @param dateString 日期字符串
     * @return
     */
    public static String dateToDateTime(String dateString){
        if(StringUtils.isBlank(dateString)){
            return "";
        }else{
            dateString = dateString.trim();
            if(dateString.length() == DATE.length()){
                return dateString + ZERO_TIME;
            }else if(dateString.length() == DATE_TIME_HOURS.length()){
                return dateString + ZERO_TIME_WITHOUT_HOURS;
            }else if(dateString.length() == DATE_TIME_MINUTES.length()){
                return dateString + ZERO_TIME_WITHOUT_MINUTES;
            }else if(dateString.length() == DATE_TIME_MILLION.length()){
                return dateString.substring(0, DATE_TIME.length());
            }else{
                return dateString;
            }
        }
    }

    /**
     * 将日期格式转换成时间（时分秒毫秒）格式
     * @param dateString 日期字符串
     * @return
     */
    public static String dateToDateTimeMillion(String dateString){
        if(StringUtils.isBlank(dateString)){
            return "";
        }else{
            dateString = dateString.trim();
            return dateString + ZERO_TIME_MILLION;
        }
    }


    /**
     * 将时间字（yyyy-MM-dd HH:mm:ss）符串转换成日期（yyyy-MM-dd）格式
     * @param dateTimeString 时间字符串
     * @return String
     */
    public static String dateTimeToDate(String dateTimeString){
        if(StringUtils.isBlank(dateTimeString)){
            return "";
        }else{
            dateTimeString = dateTimeString.trim();
            if(dateTimeString.length() >= DATE.length()){
                return dateTimeString.substring(0, DATE.length());
            }else{
                return dateTimeString;
            }
        }
    }

    /**
     * 将时间（yyyy-MM-dd HH:mm:ss）转换成日期（yyyy-MM-dd）
     * @param dateTime 时间
     * @return Date
     * @throws ParseException
     */
    public static Date dateTimeToDate(Date dateTime) throws ParseException{
        if(dateTime == null){
            return null;
        }else{
            return parseDate(formatDate(dateTime));
        }
    }

    /**
     * 获取当前时间（yyyy-MM-dd HH:mm:ss）
     * @return Date
     */
    public static Date now(){
        return new Date();
    }

    /**
     * 获取当前时间（yyyy-MM-dd HH:mm:ss）
     * @return Date
     */
    public static Date dateTime(){
        return new Date();
    }

    /**
     * 获取当前时间（yyyy-MM-dd HH:mm:ss）
     * @return Date
     */
    public static Date getDateTime(){
        return dateTime();
    }

    /**
     * 获取当前日期（yyyy-MM-dd）
     * @return Date
     * @throws ParseException
     */
    public static Date date() throws ParseException{
        return dateTimeToDate(new Date());
    }

    /**
     * 获取当前日期（yyyy-MM-dd）
     * @return Date
     * @throws ParseException
     */
    public static Date getDate() throws ParseException{
        return date();
    }

    /**
     * 日期加减天数
     * @param date 日期，为空时默认当前时间，包括时分秒
     * @param days 加减的天数
     * @return
     * @throws ParseException
     */
    public static Date dateAdd(Date date, int days) throws ParseException{
        if(date == null){
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 日期加减多少月
     * @param date 日期，为空时默认当前时间，包括时分秒
     * @param months 加减的月数
     * @return
     * @throws ParseException
     */
    public static Date monthAdd(Date date, int months) throws ParseException{
        if(date == null){
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }


    /**
     * 时间比较
     * <p>如果date大于compareDate返回1，小于返回-1，相等返回0</p>
     * @param date
     * @param compareDate
     * @return
     * @throws ParseException
     */
    public static int dateCompare(Date date, Date compareDate) throws ParseException{
        Calendar cal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        cal.setTime(date);
        compareCal.setTime(date);
        return cal.compareTo(compareCal);
    }


    /**
     * 获取两个日期相差的天数，不包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetween(Date startDate, Date endDate) throws ParseException{
        Date dateStart = parse(format(startDate, DATE), DATE);
        Date dateEnd = parse(format(endDate, DATE), DATE);
        return (int)((dateEnd.getTime() - dateStart.getTime()) / 1000/60/60/24);
    }


    /**
     * 获取两个日期相差的天数，包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetweenIncludeToday(Date startDate, Date endDate) throws ParseException{
        return dateBetween(startDate, endDate) + 1;
    }


    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
//        String config = "{\"DetectRegion\":\"[{\\\"Point\\\":\\\"15,630\\\"},{\\\"Point\\\":\\\"7817,630\\\"},{\\\"Point\\\":\\\"7817,7649\\\"},{\\\"Point\\\":\\\"15,7649\\\"}]\",\"WorkClothesDescription\":[{\"AlarmRepeatTime\":0,\"AlarmTime\":60,\"Helmet\":{\"MultiColor\":[5],\"Weared\":1},\"Sensitivity\":5}]}";
//        config = config.replace("\\", "");
//        System.out.println(config);
//        System.out.println(config.replaceAll("\\{\"Point\":\"([0-9]+),([0-9]+)\"}","[$1,$2]"));

//        String str2 = "http://172.25.238.129:9876/65fbf56f-1930-11ed-b10f-d094663eb298/20220929/1/b66ee81d-3fa1-11ed-9910-d094663eb298.png";
//        int x = str2.lastIndexOf(".");
//        System.out.println(str2.substring(x));

//        String messageStr = "\"LinkGroup\":[{\"CutoutPolicy\":0,\"Enable\":true,\"GroupID\":\"11\",\"GroupName\":\"大白\",\"Similarity\":67},{\"CutoutPolicy\":0,\"Enable\":true,\"GroupID\":\"12\",\"GroupName\":\"大白\",\"Similarity\":67}],";
//        Pattern p = Pattern.compile("\"GroupID\":\"([0-9]+)\"");
//        Matcher m = p.matcher(messageStr);
//        if (m.find()) {
//            System.out.println(m.group());
//            System.out.println(m.group().substring(m.group().lastIndexOf(":")+2, m.group().length()-1));
//        }

//        Integer[][] integers = new Integer[][]{new Integer[]{0}};
//        Integer[][] integers = new Integer[5][];
//        integers[0] = new Integer[]{1};
//        for (int i=0; i<integers.length; i++) {
//            integers[i] = new Integer[]{1};
//        }
//        System.out.println(Arrays.deepToString(integers));

//        Calendar cal = Calendar.getInstance();
//        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        System.out.println(w);

//        int len = 5;
//        Integer[][] integers = new Integer[len][];
//        for (int i=0; i<len; i++) {
//            integers[i] = new Integer[]{1};
//        }
//        System.out.println(Arrays.deepToString(integers));

//        int len = 6;
//        Integer[] s = new Integer[len];
//        for (int k=0; k<len; k++) {
//            s[k]=1;
//        }
//        System.out.println(Arrays.toString(s));

//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.format(date));
//        System.out.println(date);

//        Random random = new Random();
//        String s = random.nextInt(9999) + "";
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 4-s.length(); i++) {
//            builder.append("0");
//        }
//        System.out.println(s + builder.toString());
//
//        System.out.println(cal.get(Calendar.YEAR));
//
//
//        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
//        System.out.println(cal.get(Calendar.MINUTE));
//        System.out.println(cal.get(Calendar.SECOND));
//
//
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        calendar.setTime(date);
//        int month = calendar.get(Calendar.MONTH)+1; //获取月（月份从0开始）
//        int dayMonth = calendar.get(Calendar.DAY_OF_MONTH);//获取日（月中的某一天）
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int min = calendar.get(Calendar.MINUTE);
//        int sec = calendar.get(Calendar.SECOND);
//        String strMonth;
//        String strDay;
//        String strHour;
//        String strMin;
//        String strSec;
//        if (month < 10) {
//            strMonth = "0" + month;
//        } else {
//            strMonth = String.valueOf(month);
//        }
//        if (dayMonth < 10) {
//            strDay = "0" + dayMonth;
//        } else {
//            strDay = String.valueOf(dayMonth);
//        }
//        if (hour < 10) {
//            strHour = "0" + hour;
//        } else {
//            strHour = String.valueOf(hour);
//        }
//        if (min < 10) {
//            strMin = "0" + min;
//        } else {
//            strMin = String.valueOf(min);
//        }
//        if (sec < 10) {
//            strSec = "0" + sec;
//        } else {
//            strSec = String.valueOf(sec);
//        }
//
//        System.out.println((calendar.get(Calendar.YEAR) % 100 + strMonth + strDay + strHour + strMin + strSec));

//        String messageStr = "{\"info\":{\"devicecode\":\"1000083\",\"channelnum\":0,\"recordImg\":\"http://192.168.3.2:9876/3856a5e1-6270-11ed-92bd-0894efc5061e/20221202/1/8be865d2-720c-11ed-9b22-0894efc5061e.png\",\"swipetime\":1669963332,\"cardno\":\"3857047613\",\"openresult\":1,\"opentype\":61},\"method\":\"card.record\",\"result\":false,\"success\":false}";
//        Pattern p = Pattern.compile("\"cardno\":\"([0-9]+)\"");
//        Matcher m = p.matcher(messageStr);
//        if (m.find()) {
//            if (m.group().substring(m.group().lastIndexOf(":")+2, m.group().length()-1).length()==10) {
//                String cardNo = updateCardNo(m.group().substring(m.group().lastIndexOf(":")+2, m.group().length()-1));
//                messageStr = messageStr.replaceAll("\"cardno\":\"([0-9]+)\"", "\"cardno\":\""+ cardNo +"\"");
//                System.out.println(cardNo);
//                System.out.println(messageStr);
//            }
//        }

//        // 时间戳转时间
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = sdf1.format(System.currentTimeMillis());
//        System.out.println("format: " + format + "l: " + System.currentTimeMillis());

        // 时间戳转时间
        String strDate = "1673332343000";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = new Long(strDate);
        System.out.println("l: " + l);
        Date date = new Date(l);
        System.out.println("date: " + date);
        System.out.println("date: " + sdf2.format(date));

//        // 时间转时间戳
//        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = sdf3.parse("2023-01-10 14:19:42");
//        long ts = date.getTime();
//        System.out.println("long: " + ts);

    }

    /**
     * 卡号转换 获取海康返回的十位十进制卡号 3857047613
     * 实际十六进制卡号 E5E5E03D
     * 转换后的卡号 3DE0E5E5 （定长8位）
     * 返回卡号不为十位不做处理
     * @param cardNo
     * @return
     */
    private static String updateCardNo(String cardNo) {
        if (cardNo.length() == 10) {
            String oldCard = Long.toHexString(Long.parseLong(cardNo));
            while (oldCard.length()<8) {
                oldCard = "0" + oldCard;
            }
            char[] chars = oldCard.toCharArray();
            char ch;
            ch=chars[0];
            chars[0] = chars[6];
            chars[6] = ch;
            ch=chars[1];
            chars[1] = chars[7];
            chars[7] = ch;
            ch=chars[2];
            chars[2]=chars[4];
            chars[4]=ch;
            ch=chars[3];
            chars[3]=chars[5];
            chars[5]=ch;
            return (new String(chars)).toUpperCase();
        }
        return cardNo;
    }

}
