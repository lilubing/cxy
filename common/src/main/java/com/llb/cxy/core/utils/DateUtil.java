package com.llb.cxy.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author 李鲁兵 2010-04-28
 *
 * System.out.println("//本月的第一天");
 * System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
 * System.out.println("//今年的程序员日");
 * System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(255));
 * System.out.println("//今天之前的一个周六");
 * System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));
 * System.out.println("//本月最后一个工作日");
 * System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));
 *
 *         关于日期格式的处理
 * 
 */
@SuppressWarnings("unused")
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
	
	private final static String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	/**
	 * 需要注意yyyy 必须是小写，不然会有跨年问题，大写Y是计算周所在的年
	 * jdk8 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
	 * 现成安全用如下方式
	 * private static ThreadLocal<SimpleDateFormat> threadSafeSimpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	 */
	public final static SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
	public final static SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");
	public final static SimpleDateFormat shortSdf_year2 = new SimpleDateFormat("yy-MM-dd");
	public final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat shortSdf_noSymbol = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat longHourSdf_noSymbol = new SimpleDateFormat("yyyyMMddHH");
	public final static SimpleDateFormat longMinuteSdf_noSymbol = new SimpleDateFormat("yyyyMMddHHmm");
	public final static SimpleDateFormat longSecondSdf_noSymbol = new SimpleDateFormat("yyyyMMddHHmmss");
	public final static SimpleDateFormat longSdf_year2 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
	public final static SimpleDateFormat minuteSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat millisSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
	public final static SimpleDateFormat longHourMinuteSdf = new SimpleDateFormat("HH:mm");

	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
			"yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"
	};

	/**
	 * 将字符串 格式化为时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date dateFormatByLength(String time) {
		Date resultDate = null;
		int timeLength = time.length();
		Pattern patternNum = Pattern.compile("[0-9]*");
		Pattern patternEng = Pattern.compile("^[A-Z].*");
		// 先判断是否是英文的
		Matcher isEng = patternEng.matcher(time);
		// 如果是因为则转换为数字
		if (isEng.matches()) {
			time = currtDay("Wed Mar 18 00:00:00 CST 2015");
		}
		Matcher isNum = patternNum.matcher(time);
		if (isNum.matches()) {
			switch (timeLength) {
			case 4:
				resultDate = DateUtil.getDate(time, DateUtil.yearSdf.toPattern());
				break;
			case 6:
				time = time.substring(0, 4) + "-" + time.substring(4, 6);
				resultDate = DateUtil.getDate(time, DateUtil.monthSdf.toPattern());
				break;
			case 8:
				time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
				resultDate = DateUtil.getDate(time, DateUtil.shortSdf.toPattern());
				break;
			case 10:
				time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10);
				resultDate = DateUtil.getDate(time, DateUtil.longHourSdf.toPattern());
				break;
			case 12:
				time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12);
				resultDate = DateUtil.getDate(time, DateUtil.minuteSdf.toPattern());
				break;
			case 14:
				time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
				resultDate = DateUtil.getDate(time, DateUtil.longSdf.toPattern());
				break;
			default:
				resultDate = DateUtil.getDate(time, DateUtil.millisSdf.toPattern());
				break;
			}
		} else {
			if (timeLength == 4) {
				resultDate = DateUtil.getDate(time, DateUtil.yearSdf.toPattern());
			} else if (timeLength <= 7) {
				resultDate = DateUtil.getDate(time, DateUtil.monthSdf.toPattern());
			} else if (timeLength <= 10) {
				resultDate = DateUtil.getDate(time, DateUtil.shortSdf.toPattern());
			} else if (timeLength <= 13) {
				resultDate = DateUtil.getDate(time, DateUtil.longHourSdf.toPattern());
			} else if (timeLength <= 16) {
				resultDate = DateUtil.getDate(time, DateUtil.longHourSdf.toPattern());
			} else if (timeLength <= 19) {
				resultDate = DateUtil.getDate(time, DateUtil.longSdf.toPattern());
			} else {
				resultDate = DateUtil.getDate(time, DateUtil.millisSdf.toPattern());
			}
		}
		return resultDate;
	}

	/**
	 * 本日日期 YYYY-MM-DD
	 * 
	 * @return
	 */
	public static String getToday() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(parsePatterns[0]);
		return LocalDateTime.now().format(format);
	}

	/**
	 * 本日日期和时间YYYY-MM-DD HH:MM:SS
	 * 
	 * @return Date类型
	 */
	public static Date getTodayTimeDate() {
		return new Date();
	}

	/**
	 * 将字符串类型的日期转换成 YYYY-MM-dd
	 *
	 * @return
	 */

	public static String stringTocalendar(String s1) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date odate = sFormat.parse(s1);
			sFormat = new SimpleDateFormat("yyyy-MM-dd");
			s1 = sFormat.format(odate);
		} catch (Exception e) {
			return "";
		}
		return s1;
	}

	/**
	 * 将字符串类型的时间转换成 hh:mm:ss
	 * 
	 * @return
	 */

	public static String stringTotime(String s1) {
		SimpleDateFormat sFormat = new SimpleDateFormat("HHmmss");
		try {
			Date odate = sFormat.parse(s1);
			sFormat = new SimpleDateFormat("HH:mm:ss");
			s1 = sFormat.format(odate);
		} catch (Exception e) {
			return "";
		}
		return s1;
	}

	/**
	 * 自定义格式输出日期 yyyyMMdd
	 */
	public static String getToday(String sFormatStr) {
		SimpleDateFormat sFormat = new SimpleDateFormat(sFormatStr);
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * getDate
	 * 
	 * @param pdate
	 * @param pattern
	 * @return
	 */
	public static final String getDate(Date pdate, String pattern) {
		if (pattern == null)
			pattern = "yyyyMMdd";
		return (new SimpleDateFormat(pattern)).format(pdate);

	}

	/**
	 * 用于日历方式的日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return getDate(date, "yyyy-MM-dd");
	}

	/**
	 * YYYY-MM
	 * 
	 * @return
	 */
	public static String getYearMonth() {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * 获取当前时间戳（yyyyMMddHHmmss）
	 *
	 * @return nowTimeStamp
	 */
	public static long getCurrentTimestamp() {
		long nowTimeStamp = Long.parseLong(getYearHourToString());
		return nowTimeStamp;
	}

	/**
	 * 获取当前年到小时yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getYearHourToString() {
		Date date = new Date();
		return longHourSdf_noSymbol.format(date);
	}

	/**
	 * 上一个小时yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getPrevHourToString() {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		return longHourSdf_noSymbol.format(calendar.getTime());
	}

	/**
	 * 下一个小时yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getNextHourToString() {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
		return longHourSdf_noSymbol.format(calendar.getTime());
	}

	/**
	 * 获取当前年到分钟yyyyMMddHH
	 * 
	 * @return
	 */
	public static String getYearMinuteToString() {
		Date date = new Date();
		return longMinuteSdf_noSymbol.format(date);
	}

	/**
	 * 获取当前年到秒yyyyMMddHHss
	 *
	 * @return
	 */
	public static String getYearSecondToString() {
		Date date = new Date();
		return longSecondSdf_noSymbol.format(date);
	}


	/**
	 * 获取上X秒的时间 yyyyMMddHHmm
	 * 
	 * @param minute
	 * @return
	 */
	public static String getPrevMinuteToString(int minute) {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - minute);
		return longMinuteSdf_noSymbol.format(calendar.getTime());
	}

	/**
	 * 获取下X秒的时间 yyyyMMddHHmm
	 * 
	 * @param minute
	 * @return
	 */
	public static String getNextMinuteToString(int minute) {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + minute);
		return longMinuteSdf_noSymbol.format(calendar.getTime());
	}

	/**
	 * YYYY-MM 上一月
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getPrevYearMonth(String sDate) {
		String sBack = "";
		if (sDate == null)
			return "";
		sDate = sDate.trim();
		String[] ss = sDate.split("-");
		if (ss.length != 2)
			return "";
		try {
			int iYear = new Integer(ss[0]).intValue();
			int iMonth = new Integer(ss[1]).intValue();
			if (iYear < 1900 || iYear > 2100)
				iYear = new Integer(getYear()).intValue();
			if (iMonth < 1 || iMonth > 12)
				iMonth = 1;
			if (iMonth == 1) { // 跨年
				iYear--;
				iMonth = 12;
			} else
				iMonth--;
			sBack = iYear + "-" + (iMonth < 10 ? "0" : "") + iMonth;
		} catch (Exception e) {
			return "";
		}
		return sBack;
	}

	/**
	 * YYYY-MM 下一月份
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getNextYearMonth(String sDate) {
		String sBack = "";
		if (sDate == null)
			return "";
		sDate = sDate.trim();
		String[] ss = sDate.split("-");
		if (ss.length != 2)
			return "";
		try {
			int iYear = new Integer(ss[0]).intValue();
			int iMonth = new Integer(ss[1]).intValue();
			if (iYear < 1900 || iYear > 2100)
				iYear = new Integer(getYear()).intValue();
			if (iMonth < 1 || iMonth > 12)
				iMonth = 1;
			if (iMonth == 12) { // 跨年
				iYear++;
				iMonth = 1;
			} else
				iMonth++;
			sBack = iYear + "-" + (iMonth < 10 ? "0" : "") + iMonth;
		} catch (Exception e) {
			return "";
		}
		return sBack;
	}

	/**
	 * 日
	 * 
	 * @return
	 */
	public static String getDay() {
		SimpleDateFormat sFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * 本日日期和时间YYYY-MM-DD HH:MM:SS
	 * 
	 * @return
	 */
	public static String getTodayTime() {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * 将整型星期转为中文星期
	 * 
	 * @return
	 */
	public static String chgWeekToGB(int iWeek) {
		switch (iWeek) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return null;
		}
	}

	/**
	 * 将整型星期转为英文星期
	 * 
	 * @param iWeek
	 * @return
	 */
	public static String chgWeekToEN(int iWeek) {
		int i = 0;
		switch (iWeek) {
		case 1:
			return (i == 0) ? "SUNDAY" : "SUN";
		case 2:
			return (i == 0) ? "MONDAY" : "MON";
		case 3:
			return (i == 0) ? "TUESDAY" : "TUE";
		case 4:
			return (i == 0) ? "WEDNESDAY" : "WED";
		case 5:
			return (i == 0) ? "THURSDAY" : "THU";
		case 6:
			return (i == 0) ? "FRIDAY" : "FRI";
		case 7:
			return (i == 0) ? "SATURDAY" : "SAT";
		default:
			return null;
		}
	}

	/**
	 * 将中文日期转为整型
	 * 
	 * @param
	 * @return
	 */
	public static int chgWeekToInt(String sWeek) {
		if (sWeek.equals("星期日"))
			return 1;
		else if (sWeek.equals("星期一"))
			return 2;
		else if (sWeek.equals("星期二"))
			return 3;
		else if (sWeek.equals("星期三"))
			return 4;
		else if (sWeek.equals("星期四"))
			return 5;
		else if (sWeek.equals("星期五"))
			return 6;
		else if (sWeek.equals("星期六"))
			return 7;
		else
			return 0;
	}

	/**
	 * 返回当前日期是星期几
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		return getDayOfWeek(getToday());
	}

	/**
	 * 返回指定日期是星期几
	 * 
	 * @param sDate
	 * @return
	 */
	public static int getDayOfWeek(String sDate) {
		Calendar cal = getCalendar(sDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取得指定生产月份的开始日期
	 * 
	 * @param sMonth
	 */
	public static String getMonthBegin(String sMonth) {
		return getPrevYearMonth(sMonth) + "-26";
	}

	public static String getMonthBegin() {
		return getMonthBegin(getYearMonth());
	}

	/**
	 * 取得指定生产月份的结束日期
	 * 
	 * @param sMonth
	 */
	public static String getMonthEnd(String sMonth) {
		return sMonth + "-25";
	}

	public static String getMonthEnd() {
		return getMonthEnd(getYearMonth());
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * {
	 * "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
	 * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
	 * "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"
	 * }
	 */
	public static Date parseDate(String str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDate(String sDate, String sFormat) {
		try {
			Date d1 = new SimpleDateFormat(sFormat).parse(sDate);
			return d1;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 形如YYYY-MM-DD的日期字符串转为 Calendar 型
	 * 
	 * @param sDate
	 * @param
	 * @return
	 */
	public static Calendar getCalendar(String sDate) {
		Date date = parseDate(sDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// cal.setTimeInMillis(date.getTime());
		return cal;
	}

	public static Calendar getCalendar(String sDate, String sFormat) {
		Date date = getDate(sDate, sFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// cal.setTimeInMillis(date.getTime());
		return cal;
	}

	/**
	 * 指定日期+或-iTs天
	 * 
	 * @param sDate
	 * @param iTs
	 * @return
	 */
	public static String calendarAdd(String sDate, int iTs) {
		Calendar cal = getCalendar(sDate, "yyyy-MM-dd");
		cal.add(Calendar.DATE, iTs);
		return calendarToDate(cal);
	}

	/**
	 * 指定日期+或-iTs天 是Time
	 */
	public static String calendarTimeAdd(String sDateTime, int iTs) {
		Calendar cal = getCalendar(sDateTime, "yyyy-MM-dd HH:mm:ss");
		cal.add(Calendar.DATE, iTs);
		return calendarToTime(cal);
	}

	/**
	 * 日历转换为 yyyy-MM-dd HH:mm:ss的字符串
	 */
	public static String calendarToDate(Calendar cal) {
		SimpleDateFormat sDateFormat = null;
		// Date date = new Date( cal.getTimeInMillis() );
		Date date = new Date(cal.getTime().getTime());
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sDateFormat.format(date);
	}

	/**
	 * 日历转换为 yyyy-MM-dd HH:mm:ss的字符串
	 */
	public static String calendarToTime(Calendar cal) {
		SimpleDateFormat sDateFormat = null;
		// Date date = new Date( cal.getTimeInMillis() );
		Date date = new Date(cal.getTime().getTime());
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sDateFormat.format(date);
	}

	/**
	 * 得到 sBdate 与 sEdate 之间的天数(按实际天数计)
	 * 
	 * @param sBdate
	 * @param sEdate
	 * @return
	 */
	public static int getDays(String sBdate, String sEdate) {
		Date dateB = parseDate(sBdate);
		Date dateE = parseDate(sEdate);
		return (int) ((dateE.getTime() - dateB.getTime()) / (3600 * 24 * 1000));
	}

	/**
	 * 取得指定年份月份的天数
	 */
	public static int getDaysMonth(int iYear, int iMonth) {
		int iFebDays = (iYear % 4 == 0 && (iYear % 100 != 0 || iYear % 400 == 0)) ? 29 : 28; // 闰年二月为29天
		int[] iDaysMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		iDaysMonth[1] = iFebDays;
		if (iMonth < 1 || iMonth > 12)
			return iDaysMonth[0];
		else
			return iDaysMonth[iMonth - 1];
	}

	public static int getDaysMonth(Calendar cal) {
		int iYear = cal.get(Calendar.YEAR); // 年
		int iMonth = cal.get(Calendar.MONTH) + 1; // 月
		return getDaysMonth(iYear, iMonth);
	}

	public static int getDaysMonth(String sDate) {
		return getDaysMonth(getCalendar(sDate));
	}

	/**
	 * 获取当前日期的年份
	 */
	public static String getYear() {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * 获取当前日期的月份
	 */
	public static String getMonth() {
		SimpleDateFormat sFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		return sFormat.format(date);
	}

	/**
	 * pdate：日期字符串 fpat:
	 */
	public static final String formatDate(String pdate, String fpat, String tpat) {
		if (pdate == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(fpat);
		Date tmp;
		try {
			tmp = formatter.parse(pdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		formatter.applyPattern(tpat);
		return formatter.format(tmp);
	}

	/**
	 * 
	 * 将yyyyMMdd转换成yyyy-MM-dd的字符串
	 * 
	 * @param source
	 * @param
	 * @return
	 * 
	 * @author 李鲁兵 2010-05-18
	 */
	public static String ConvertDateTime(String source) {
		String dateStr = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date cDate = df.parse(source);

			SimpleDateFormat _df = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = _df.format(cDate);
		} catch (ParseException e) {
			e.printStackTrace();
			dateStr = source;
		}
		return dateStr;

	}

	/**
	 * 根据要求的格式取得当前系统时间时间戳 李鲁兵
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp(String format) {
		SimpleDateFormat myFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		String mystrdate = myFormat.format(calendar.getTime());
		return Timestamp.valueOf(mystrdate);
	}

	/**
	 * 获取现在时间
	 * 
	 * 李鲁兵
	 * 
	 * @return当前日期
	 * 
	 */
	public static Date getNowDateShort(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 得到两个日期的差的天数
	 * 
	 * 李鲁兵
	 * 
	 * @return
	 * 
	 */
	public static int getDayOffset(Date begin, Date end) {
		Calendar c = Calendar.getInstance();
		c.setTime(begin);
		long l1 = c.getTimeInMillis();
		c.setTime(end);
		long l2 = c.getTimeInMillis();
		return (int) ((l2 - l1) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 获取当天0点时间戳
	 * @param aFewDays 传入正负数天数
	 * @return
	 */
	public static Long dayTimeInMillis(int aFewDays) {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, aFewDays);
		return calendar.getTimeInMillis();
	}

	/**
	 * 年月日到秒的
	 * @param aFewDays
	 * @return
	 */
	public static Long dayToSecondsInMillis(int aFewDays) {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.add(Calendar.DAY_OF_MONTH, aFewDays);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据指定时间获取毫秒
	 * 
	 * @param targetDate
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	public static long dateConvertMillisecond(String targetDate, SimpleDateFormat fmt) {
		Date date = null;
		try {
			date = fmt.parse(targetDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long result = date.getTime();
		return result;
	}

	/**
	 * 根据指定时间获取毫秒
	 * 
	 * @param targetDate
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	public static long dateConvertDate(String targetDate, String dateFormat) throws Exception {
		DateFormat fmt = new SimpleDateFormat(dateFormat);
		Date date = fmt.parse(targetDate);
		long result = date.getTime();
		return result;
	}

	/**
	 * 根据指定毫秒数获取到时间格式字符串
	 * 
	 * @param targetMillisecond
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	public static String dateConvertMillisecond(long targetMillisecond) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date reslutDate = new Date(targetMillisecond);
		return fmt.format(reslutDate);
	}

	/**
	 * 根据指定毫秒数获取到时间格式字符串
	 * 
	 * @param targetMillisecond
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	public static String dateConvertDate(long targetMillisecond, String dateFormat) {
		DateFormat fmt = new SimpleDateFormat(dateFormat);
		Date reslutDate = new Date(targetMillisecond);
		return fmt.format(reslutDate);
	}

	/**
	 * 英文时间转换为字符串 2013-01-01
	 * 
	 * @param
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	@SuppressWarnings("deprecation")
	public static String engConvert(String time) throws Exception {
		DateFormat f3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return f3.format(new Date(time));
	}

	/**
	 * 数字时间转换为字符串 2013-01-01
	 * 
	 * @param
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	@SuppressWarnings("deprecation")
	public static String numConvert(String time) throws Exception {
		DateFormat f3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return f3.format(new Date(time));
	}

	/**
	 * 英文时间转换为yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param
	 * @return yyyy-MM-dd
	 * @throws Exception
	 *             李鲁兵
	 */
	@SuppressWarnings("deprecation")
	public static String currtDay(String time) {
		long time1;
		try {
			time1 = new Date(time).getTime();
			String str = DateUtil.dateConvertMillisecond(time1);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据指定时间加一天
	 * 
	 * @param
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 * @throws Exception
	 *             李鲁兵
	 */
	@SuppressWarnings("deprecation")
	public static String addOneDay(String time) {
		long time1;
		try {
			time1 = new Date(time).getTime() + 86400000;
			String str = DateUtil.dateConvertMillisecond(time1);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * <B>方法名称：</B>天数加1<BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @param date
	 * @return
	 */
	public static Date addOneDay(Date date) {
		return addDay(date, 1);
	}

	/**
	 * 
	 * <B>方法名称：</B>添加num天<BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addDay(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);
		return c.getTime();
	}

	/**
	 * 根据指定时间加一天
	 * 
	 * @param
	 *            time, int num
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 * @throws Exception
	 *             李鲁兵
	 */
	@SuppressWarnings("deprecation")
	public static String addDay(String time, int num) {
		long time1;
		try {
			time1 = new Date(time).getTime() - (86400000 * num);
			String str = DateUtil.dateConvertMillisecond(time1);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * String 转换为 date
	 * 
	 * @param time
	 * @return
	 */
	public static Date StringConvertDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据指定Date 获取 yyyy-MM-dd
	 * 
	 * @param
	 *            time, int num
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 * @throws Exception
	 *             李鲁兵
	 */
	public static String dateFormat(Date time) {
		DateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
		return f3.format(time);
	}

	/**
	 * 根据指定Date 获取无符号的日期 yyyyMMdd
	 * 
	 * @param time
	 * @return
	 */
	public static String dateFormatshortSdf_noSymbol(Date time) {
		String result = "";
		if (time != null)
			result = shortSdf_noSymbol.format(time);
		return result;
	}

	/**
	 * 根据指定字符串 获取无符号的日期 yyyyMMdd
	 * 
	 * @param time
	 * @return
	 */
	public static String stringFormatshortSdf_noSymbol(String time) {
		String result = "";
		if (time != null) {
			try {
				Date temp = longSdf.parse(time);
				result = dateFormatshortSdf_noSymbol(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取当前月的最后一天
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime) + " 23:59:59";
		return endTime1;
	}

	/**
	 * 获取当前月的第一天
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime) + " 00:00:00";
		return beginTime1;
	}

	/**
	 * 功能：获取本周的开始时间 示例：2013-05-13 00:00:00
	 */
	public static String getWeekStart() {// 当周开始时间
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return datef.format(currentDate.getTime()) + " 00:00:00";
	}

	/**
	 * 功能：获取本周的结束时间 示例：2013-05-19 23:59:59
	 */
	public static String getWeekEnd() {// 当周结束时间
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return datef.format(currentDate.getTime()) + " 23:59:59";
	}

	public static String[] getCurrentQuarterStartTime() {
		String year = getYear();
		String fmonth = "";
		String emonth = "";
		String[] quarter = new String[2];
		int currentMonth = Integer.valueOf(getMonth());
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				fmonth = "01-01";
				emonth = "03-31";
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				fmonth = "04-01";
				emonth = "06-30";
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				fmonth = "07-01";
				emonth = "09-30";
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				fmonth = "10-01";
				emonth = "12-31";
			}
			String startQuarter = year + "-" + fmonth + " 00:00:00";
			String endQuarter = year + "-" + emonth + " 23:59:59";
			quarter[0] = startQuarter;
			quarter[1] = endQuarter;
			// now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return quarter;
	}

	public static Long get10Date() {
		return (Long) (System.currentTimeMillis() / 1000);
	}

	public static long get10Date(long timeMillis) {
		return timeMillis / 1000;
	}

	public static String timestamp2Date(String str_num, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (str_num.length() == 13) {
			String date = sdf.format(new Date(Long.parseLong(str_num)));
			System.out.println("timestamp2Date" + "将13位时间戳:" + str_num + "转化为字符串:" + date);
			return date;
		} else {
			String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
			System.out.println("timestamp2Date" + "将10位时间戳:" + str_num + "转化为字符串:" + date);
			return date;
		}
	}

	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

	/**
	 * 
	 * <B>方法名称：根据日期获取周几</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(datetime);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return chgWeekToGB(w);
	}

	public static void main(String args[]) {
		try {
			System.out.println(DateUtil.dayToSecondsInMillis(-1) / 1000);
			System.out.println(DateUtil.dayTimeInMillis(-1) / 1000);
			Long a = dateConvertMillisecond("2020-06-05", shortSdf);
			Long b = dateConvertMillisecond("2020-06-05 23:59:59", longSdf_year2);
			System.out.println(a + "==" + b);
			Date t = StringConvertDate("2018-10-31");
			System.out.println(t.getTime() / 1000);
			System.out.println(get10Date());
			String time = "1591055516000";
			timestamp2Date(time, "yyyy-MM-dd HH:mm:ss.s");
			timestamp2Date(time.substring(0, 10), "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Timestamp stringToTimestamp(String date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Timestamp time = null;
		try {
			Date date2 = dateFormat.parse(date);
			time = new Timestamp(date2.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 获取俩个时间之间的年份
	 * @Title: getYearBetween
	 * @Description: 
	 * @param startYear
	 * @param maxDate
	 * @return
	 */
	public static List<Integer> getYearBetween(Integer startYear, Date maxDate) {
		List<Integer> result = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(maxDate);
		int year = calendar.get(Calendar.YEAR);
		for (int i = startYear; i <= year; i++) {
			result.add(i);
		}
		return result;
	}
	
	/**
	 * 获取俩个时间之间的月份
	 * @Title: getMonthBetween
	 * @Description: 
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMonthBetween(String minDate, Date maxDate) throws Exception {
		List<String> result = new ArrayList<String>();

		Calendar min = Calendar.getInstance();
		min.setTime(monthSdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		Calendar max = Calendar.getInstance();
		max.setTime(maxDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(monthSdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}
	
	/**
	 * 获取俩个时间之间的日期
	 * @Title: getMonthBetween
	 * @Description:
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDatesBetween(String start, String end) throws ParseException {
		List<String> dates = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);
		dates.add(start);
		Calendar calendar = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calendar.setTime(startDate);
		// 测试此日期是否在指定日期之后
		while (endDate.after(calendar.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(dateFormat.format(calendar.getTime()));
		}
		return dates;
	}
	
}