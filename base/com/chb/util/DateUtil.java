package com.chb.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: 日期函数
 * </p>
 * <p>
 * Description:
 * </p>
 * 该类主要提供日期类型的操作，如：格式化、比较、转化等。<br>
 */
@Component
public class DateUtil {
	private static String dateFormat[] = { "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
			"yyyy\345\271\264MM\346\234\210dd\346\227\245HH\346\227\266mm\345\210\206ss\347\247\222", "yyyy-MM-dd",
			"yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy\345\271\264MM\346\234\210dd\346\227\245", "HH:mm:ss",
			"yyyyMMddHHmmss", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd", "yyyyMMddHHmmss000", "MM-dd" };
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getDayTime(1531209768));
		/*
		 * List<String> dateList = new ArrayList<String>(); String beginTime =
		 * "20140707005201"; String endTime = "20140707125201"; try { dateList =
		 * DateMtxFileUtil.getDateForMinuteStrList(beginTime, endTime); for (String
		 * string : dateList) { if (StringUtils.equalsIgnoreCase(string,
		 * "201407070618")) { System.out.println("222");
		 * 
		 * } } } catch (ParseException e) { e.printStackTrace(); }
		 */}
	
	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Timestamp convUtilCalendarToSqlTimestamp(Calendar date) {
		if (date == null) {
			return null;
		} else {
			return new Timestamp(date.getTimeInMillis());
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param dateStr
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Calendar parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		} else {
			Date result = parseDate(dateStr, 0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			return cal;
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:获得当前时间转化成long类型
	 * </p>
	 * 
	 * @return
	 * @author:彭晓民 pengxm@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static long getNowLongTime() {
		Calendar cal = Calendar.getInstance();
		String Date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		Calendar cd = parseDate(Date);
		if (cd == null) {
			return 0;
		}
		return (cd.getTimeInMillis() / 1000);
	}

	/**
	 * 
	 * <p>
	 * Discription:将字符串转化成对应的日历对象
	 * </p>
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Calendar parseDateToCal(String dateStr, int index) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		} else {
			Date result = parseDate(dateStr, index);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			return cal;
		}
	}

	/**
	 * <p>
	 * Discription:得到当前时间
	 * </p>
	 * 
	 * @param dateStr
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Calendar parseDateNow(String dateStr) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		} else {
			Date result = parseDate(dateStr, 0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			return cal;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String toDateTimeStr(Calendar date) {
		if (date == null) {
			return null;
		} else {
			return (new SimpleDateFormat(dateFormat[0])).format(date.getTime());
		}
	}

	/**
	 * <p>
	 * Discription:[根据index得到时间]
	 * </p>
	 * 
	 * @param date
	 * @param index
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String toDateTimeStr(Calendar date, int index) {
		if (date == null) {
			return null;
		} else {
			String str = new SimpleDateFormat(dateFormat[index]).format(date.getTime());
			return str;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String toDateStr(Calendar date) {
		if (date == null) {
			return null;
		} else {
			return (new SimpleDateFormat(dateFormat[3])).format(date.getTime());
		}
	}

	/**
	 * <p>
	 * Discription:[得到两个时间的分钟差]
	 * </p>
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static int calendarSec(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0;
		} else {
			// d1.set(11, 0);
			// d1.set(12, 0);
			// d1.set(13, 0);
			// d2.set(11, 0);
			// d2.set(12, 0);
			// d2.set(13, 0);
			long t1 = d1.getTimeInMillis();
			long t2 = d2.getTimeInMillis();
			int temp = (int) ((t1 - t2) / 60000);
			// //LogWritter.sysDebug("DateUtils: d1 = " + toDateTimeStr(d1) +
			// "(" + t1 + ")");
			// //LogWritter.sysDebug("DateUtils: d2 = " + toDateTimeStr(d2) +
			// "(" + t2 + ")");
			// long daylong = 0x1CF7C58000L;
			// t1 = t1 % daylong;
			// t2 = t2 % daylong;
			// long t = t1 - t2;
			// int value = (int) (t / daylong);
			// LogWritter.sysDebug("DateUtils: d2 -d1 = " + value +
			// " \357\274\210\345\244\251\357\274\211");
			return temp;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static int calendarMinus(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0;
		} else {
			d1.set(11, 0);
			d1.set(12, 0);
			d1.set(13, 0);
			d2.set(11, 0);
			d2.set(12, 0);
			d2.set(13, 0);
			long t1 = d1.getTimeInMillis();
			long t2 = d2.getTimeInMillis();
			// LogWritter.sysDebug("DateUtils: d1 = " + toDateTimeStr(d1) + "("
			// + t1 + ")");
			// LogWritter.sysDebug("DateUtils: d2 = " + toDateTimeStr(d2) + "("
			// + t2 + ")");
			long daylong = 0x5265c00L;
			t1 -= t1 % daylong;
			t2 -= t2 % daylong;
			long t = t1 - t2;
			int value = (int) (t / daylong);
			// LogWritter.sysDebug("DateUtils: d2 -d1 = " + value +
			// " \357\274\210\345\244\251\357\274\211");
			return value;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Date parseDate(String dateStr, int index) {
		DateFormat df = null;
		df = new SimpleDateFormat(dateFormat[index]);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getSysDateTime(int index) {
		Calendar cal = Calendar.getInstance();
		return DateUtil.toDateTimeStr(cal, index);
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getSysDate() {
		Calendar cal = Calendar.getInstance();
		return DateUtil.toDateStr(cal);
	}

	/**
	 * <p>
	 * Discription:将格式为[yyyy-MM-dd HH:mm:ss]的时间转化成c语言中的long
	 * </p>
	 * 
	 * @param Date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getTime(String Date) {
		String time = String.valueOf(DateUtil.parseDate(Date).getTimeInMillis());
		return time.substring(0, time.length() - 3);
	}

	/**
	 * <p>
	 * Discription:将格式为毫秒的时间转化成c语言中的long
	 * </p>
	 * 
	 * @param Date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getTimeHaveMillis(String Date) {
		String time = String.valueOf(DateUtil.parseDate(Date).getTimeInMillis());
		return time.substring(0, time.length() - 3);
	}

	/**
	 * <p>
	 * Discription:将格式为[yyyy-MM-dd HH:mm:ss]的时间转化成long类型，符合C语言函数要求
	 * </p>
	 * 
	 * @param Date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static long getTimeLong(String Date) {
		Calendar cd = parseDate(Date);
		if (cd == null) {
			return 0;
		}

		return (cd.getTimeInMillis() / 1000);
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 20120233
	 * 
	 * @param strDate
	 * @return
	 */
	public static long strToDateLong(String strDate) {
		String yy = strDate.substring(0, 4);
		String mm = strDate.substring(4, 6);
		String dd = strDate.substring(6, 8);
		String hh = strDate.substring(8, 10);
		String ms = strDate.substring(10, 12);
		String ss = strDate.substring(12, 14);
		StringBuffer datastr = new StringBuffer();
		datastr.append(yy).append("-").append(mm).append("-").append(dd).append(" ").append(hh).append(":").append(ms)
				.append(":").append(ss);
		return getTimeLong(datastr.toString());
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param time
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return (new SimpleDateFormat(dateFormat[0])).format(cal.getTime());
	}

	public static String getDate(long time, int index) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return (new SimpleDateFormat(dateFormat[index])).format(cal.getTime());
	}

	public static String getDate(long time, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return (new SimpleDateFormat(pattern)).format(cal.getTime());
	}
	
	public static long getDayTime(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		String str = (new SimpleDateFormat(dateFormat[3])).format(cal.getTime());
		DateTimeFormatter dtf=DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"); 
		DateTime dt1=new DateTime();  
        dt1=DateTime.parse(str+" 00:00:00",dtf);
		return dt1.getMillis()/1000;
	}


	/**
	 * 
	 * <p>
	 * Discription:将c语言中的long转化成格式为[yyyyMMddHHmmss]
	 * </p>
	 * 
	 * @param time
	 * @return parseDate
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getDateFormatYY(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return (new SimpleDateFormat(dateFormat[9])).format(cal.getTime());
	}

	/**
	 * 
	 * <p>
	 * Discription:[将格式为[yyyyMMddHHmmss]的时间转化成long类型，符合C语言函数要求]
	 * </p>
	 * 
	 * @param Date
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public long getTimeLongByFormat(String Date, int index) {
		Calendar cd = parseDateByFormat(Date, index);
		if (cd == null) {
			return 0;
		}

		return (cd.getTimeInMillis() / 1000);
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据日期格式把日期字符串转化为日期格式]
	 * </p>
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Calendar parseDateByFormat(String dateStr, int index) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		} else {
			Date result = parseDate(dateStr, index);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			return cal;
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:将c语言中的long转化成格式为[yyyy-MM-dd HH:mm:ss]的时间
	 * </p>
	 * 
	 * @param time
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getDate(String time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(time.concat("000")));
		return (new SimpleDateFormat(dateFormat[0])).format(cal.getTime());
	}

	public static String String2Date(String date) {
		if (date.length() >= 14) {
			String fdate = date.substring(0, 4).concat("年").concat(date.substring(4, 6)).concat("月")
					.concat(date.substring(6, 8)).concat("日").concat("  ").concat(date.substring(8, 10)).concat(":")
					.concat(date.substring(10, 12)).concat(":").concat(date.substring(12, 14));
			return fdate;
		} else {
			return date;
		}

	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param index
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getDateByFormat(int index) {
		Calendar cal = Calendar.getInstance();
		return (new SimpleDateFormat(dateFormat[index])).format(cal.getTime());
	}

	/**
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param Date
	 * @param interval
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getPreDate(String Date, long interval) {
		return DateUtil.getDate(String.valueOf(Long.parseLong(DateUtil.getTime(Date)) - interval));
	}

	public static long getNowTimeLong() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 转换oracle中Date类型
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 */
	public static Calendar parseOracleDateByFormat(String dateStr, int index) {
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		} else {
			dateStr = dateStr.replace(".0", "");
			Date result = parseDate(dateStr, index);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			return cal;
		}
	}

	/**
	 * 计算两个时间的精准差值(到毫秒) 使用举例 Long b=DateUtil.getWorkdayTimeInMillis( "2013-07-03
	 * 8:00:00", "2013-07-03 9:10:00", "yyyy-MM-dd HH:mm:ss");
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long getWorkdayTimeInMillis(long start, long end) {
		// 如果起始时间大于结束时间，将二者交换
		if (start > end) {
			long temp = start;
			start = end;
			end = temp;
		}
		// 根据参数获取起始时间与结束时间的日历类型对象
		Calendar sdate = Calendar.getInstance();
		Calendar edate = Calendar.getInstance();
		sdate.setTimeInMillis(start);
		edate.setTimeInMillis(end);
		// 如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
		if (sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR)
				&& sdate.get(Calendar.WEEK_OF_YEAR) == edate.get(Calendar.WEEK_OF_YEAR)
				&& sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7
				&& edate.get(Calendar.DAY_OF_WEEK) != 1 && edate.get(Calendar.DAY_OF_WEEK) != 7) {
			return new Long(end - start);
		}
		// 首先取得起始日期与结束日期的下个周一的日期
		Calendar snextM = getNextMonday(sdate);
		Calendar enextM = getNextMonday(edate);
		// 获取这两个周一之间的实际天数
		int days = getDaysBetween(snextM, enextM);
		// 获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)
		int workdays = days / 7 * 5;
		// 获取开始时间的偏移量
		long scharge = 0;
		if (sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7) {
			// 只有在开始时间为非周末的时候才计算偏移量
			scharge += (7 - sdate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
			scharge -= sdate.get(Calendar.HOUR_OF_DAY) * 3600000;
			scharge -= sdate.get(Calendar.MINUTE) * 60000;
			scharge -= sdate.get(Calendar.SECOND) * 1000;
			scharge -= sdate.get(Calendar.MILLISECOND);
		}
		// 获取结束时间的偏移量
		long echarge = 0;
		if (edate.get(Calendar.DAY_OF_WEEK) != 1 && edate.get(Calendar.DAY_OF_WEEK) != 7) {
			// 只有在结束时间为非周末的时候才计算偏移量
			echarge += (7 - edate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
			echarge -= edate.get(Calendar.HOUR_OF_DAY) * 3600000;
			echarge -= edate.get(Calendar.MINUTE) * 60000;
			echarge -= edate.get(Calendar.SECOND) * 1000;
			echarge -= edate.get(Calendar.MILLISECOND);
		}
		// 计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
		return workdays * 24 * 3600000 + scharge - echarge;
	}

	public static Long getWorkdayTimeInMillis(Long start, Long end) {
		return getWorkdayTimeInMillis(start.longValue(), end.longValue());
	}

	public static Long getWorkdayTimeInMillis(Date start, Date end) {
		return getWorkdayTimeInMillis(start.getTime(), end.getTime());
	}

	public static Long getWorkdayTimeInMillis(String start, String end, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sdate;
		Date edate;
		try {
			sdate = sdf.parse(start);
			edate = sdf.parse(end);
			return getWorkdayTimeInMillis(sdate, edate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Long(0);
		}
	}

	private static Calendar getNextMonday(Calendar cal) {
		int addnum = 9 - cal.get(Calendar.DAY_OF_WEEK);
		if (addnum == 8)
			addnum = 1;// 周日的情况
		cal.add(Calendar.DATE, addnum);
		return cal;
	}

	/**
	 * 获取两个日期之间的实际天数，支持跨年
	 */
	public static int getDaysBetween(Calendar start, Calendar end) {
		if (start.after(end)) {
			Calendar swap = start;
			start = end;
			end = swap;
		}
		int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
		int y2 = end.get(Calendar.YEAR);
		if (start.get(Calendar.YEAR) != y2) {
			start = (Calendar) start.clone();
			do {
				days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
				start.add(Calendar.YEAR, 1);
			} while (start.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 转换mdss时间为标准时间格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static String praseMdssDateToStr(String strDate) {
		String yy = strDate.substring(0, 4);
		String mm = strDate.substring(4, 6);
		String dd = strDate.substring(6, 8);
		String hh = strDate.substring(8, 10);
		String ms = strDate.substring(10, 12);
		String ss = strDate.substring(12, 14);
		StringBuffer datastr = new StringBuffer();
		datastr.append(yy).append("-").append(mm).append("-").append(dd).append(" ").append(hh).append(":").append(ms)
				.append(":").append(ss);
		return datastr.toString();
	}

	/**
	 * 获时间年、月、日、时、分、秒
	 * 
	 * @param args
	 */
	public static List<Integer> getDateArr(String dateStr) {

		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		}
		List<Integer> result = new ArrayList<Integer>();
		Date date = parseDate(dateStr, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		result.add(cal.get(Calendar.YEAR));
		result.add((cal.get(Calendar.MONTH) + 1));
		result.add(cal.get(Calendar.DAY_OF_MONTH));
		result.add(cal.get(Calendar.HOUR_OF_DAY));
		result.add(cal.get(Calendar.MINUTE));
		result.add(cal.get(Calendar.SECOND));
		return result;
	}

	/**
	 * 转换mysql 时间戳类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDate(Timestamp timestamp) {
		String date = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 返回下个还款日前三个工作日
	 * 
	 * @param dt1
	 *            应还日期
	 * @return
	 */
	public static DateTime getAdvanceEndDate(DateTime dt1) {
		int day = dt1.getDayOfMonth();// 当月第几天
		int lastDay = dt1.dayOfMonth().withMaximumValue().getDayOfMonth();// 当月最后一天

		DateTime dt2;// 下月应还日期
		DateTime dtTemp = dt1.plusMonths(1);
		if (day == lastDay) {
			int lastDayTemp = dtTemp.dayOfMonth().withMaximumValue().getDayOfMonth();
			dt2 = dtTemp.withDayOfMonth(lastDayTemp);
		} else {
			dt2 = dtTemp.withDayOfMonth(day);
		}

		DateTime dt3 = null;// 下月应还日期的前三个工作日
		int dayOfWeek = dt2.getDayOfWeek();// 星期几
		switch (dayOfWeek) {
		case 1:// 周一
			dt3 = dt2.minusDays(3);
			break;
		case 2:// 周二
			dt3 = dt2.minusDays(1);
			break;
		case 3:// 周三
			dt3 = dt2.minusDays(1);
			break;
		case 4:// 周四
			dt3 = dt2.minusDays(1);
			break;
		case 5:// 周五
			dt3 = dt2.minusDays(1);
			break;
		case 6:// 周六
			dt3 = dt2.minusDays(1);
			break;
		case 7:// 周日
			dt3 = dt2.minusDays(2);
			break;
		}
		return dt3;
	}
}
