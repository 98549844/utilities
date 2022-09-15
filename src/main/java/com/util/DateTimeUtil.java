package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
    private static final Logger log = LogManager.getLogger(DateTimeUtil.class.getName());


    public static Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    public static void printCurrentDateTime(Object time) {
        Date date = null;
        if (time instanceof Long) {
            date = new Date((Long) time);
        } else if (time instanceof Integer) {
            Long i = TypeUtil.integerToLong((Integer) time);
            date = new Date(i);
        } else if (time instanceof Date) {
            date = (Date) time;
        } else {
            System.out.println("input parameter not require");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(formatter.format(date));
    }

    public static void differenceSystemCurrentTimeMillis(Long start, Long end) {
        Long temp;
        if (NullUtil.isNotNull(start) || NullUtil.isNotNull(end)) {
            if (start > end) {
                temp = end;
                end = start;
                start = temp;
            }
            Long result = end - start;
            printCurrentDateTime(result);
        } else {
            Console.println("Start time or End time is null , pLease check", Console.BOLD, Console.RED);
        }
    }


    //获取月份的天数
    public static int getDaysOfCurrentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }


    /**
     * 返回一定时间后的日期
     *
     * @param date   开始计时的时间
     * @param year   增加的年
     * @param month  增加的月
     * @param day    增加的日
     * @param hour   增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return Date
     */
    public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (NullUtil.isNull(date)) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }


    /**
     * 方法名：getStartDayOfWeekNo
     * 功能：某周的开始日期
     */
    public static String getStartDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    /**
     * 方法名：getEndDayOfWeekNo
     * 功能：某周的结束日期
     */
    public static String getEndDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    private static Calendar getCalendarFormYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    /**
     * 方法名：getFirstDayOfMonth
     * 功能：获取某月的第一天
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getMinimum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Console.println(sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }

    /**
     * 方法名：getLastDayOfMonth
     * 功能：获取某月的最后一天
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Console.println(sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }


    public static void printSimpleDate(Date date) throws ParseException {
        if (NullUtil.isNull(date)) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = sdf.format(date);
        log.info("时间：{}", dateformat);
    }

    public static void printCurrentDate() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = sdf.format(date);
        log.info("时间：{}", dateformat);
    }


    /**
     * @param start
     * @param end
     * @return nanos
     */
    public static long differenceNanosByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long nanos = getDuration(start, end).toNanos();
            return nanos;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return millis
     */
    public static long differenceMillisByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long millis = getDuration(start, end).toMillis();
            return millis;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return minutes
     */
    public static long differenceMinutesByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long minutes = getDuration(start, end).toMinutes();
            return minutes;
        } else {
            return 0l;
        }

    }

    /**
     * @param start
     * @param end
     * @return hours
     */
    public static long differenceHoursByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long hours = getDuration(start, end).toHours();
            return hours;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return days
     */
    public static long differenceDaysByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long days = getDuration(start, end).toDays();
            return days;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return Years
     */
    public static long differenceYearsByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNotNull(getDuration(start, end))) {
            long days = getDuration(start, end).toDays();
            return days / 365;
        } else {
            return 0l;
        }
    }

    private static Duration getDuration(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNull(start) || NullUtil.isNull(end)) {
            log.error("LocalDateTime is null");
            return null;
        }
        Duration duration = Duration.between(start, end);
        return duration;
    }


}
