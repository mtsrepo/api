package com.mts.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class FormatterUtil {

	public static String CIC_DATE_FORMAT = "dd/MM/yyyy";
	public static String CIC_DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static String CIC_DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
	public static String BILL_DESK_DATE_FORMAT = "yyyy/MM/dd";
	public static String CIC_DATETIMESECS_FORMAT = "dd/MM/yyyy hh:mm:ss a";
	public static String CIC_DATETIME_FORMAT = "dd/MM/yyyy hh:mm a";
	public static String CIC_TIMEHOURMIN_FORMAT = "hh:mm a";
	public static String IST_TIME = "GMT+5.30";
	public static final String MINUTE_FORMAT = "mm";
	
	public static String arrayToString(String[] array) {
		String returnString = "";

		for (int i = 0; i < array.length; i++) {
			returnString += array[i] + ",";
		}

		if (array.length > 0) {
			returnString = returnString.substring(0, returnString.lastIndexOf(","));
		}

		return returnString;
	}

	public static String[] stringToArray(String inputString) {
		String[] returnArray = inputString.split(",");

		return returnArray;
	}

	public static Long getEpochDateFromDateString(String dateString, String dateFormat) throws ParseException {

		long dateLong = 0l;
		if (dateString != null && !"".equals(dateString)) {
			SimpleDateFormat dF = new SimpleDateFormat(dateFormat); // The mask
			// 'a' value in the Mask represents AM / PM - h means hours in AM/PM mode
			Date date = dF.parse(dateString); // parsing the String into a Date using the mask

			// Date.getTime() method gives you the Long with milliseconds since Epoch.

			dateLong = date.getTime();
		}
		return dateLong;
	}

	public static Long getEpochDateFromDateStringIST(String dateString, String dateFormat) throws ParseException {

		long dateLong = 0l;
		if (dateString != null && !"".equals(dateString)) {
			SimpleDateFormat dF = new SimpleDateFormat(dateFormat); // The mask
			dF.setTimeZone(TimeZone.getTimeZone(IST_TIME));
			Date date = dF.parse(dateString); // parsing the String into a Date using the mask
			dateLong = date.getTime();
		}
		return dateLong;
	}

	public static String getStringDateFromEpoch(Long epochDate, String dateFormat) throws ParseException {
		String formatted = "";
		if (epochDate != null && epochDate != 0L) {
			Date date = new Date(epochDate);
			DateFormat format = new SimpleDateFormat(dateFormat);
			// format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
			formatted = format.format(date);
		}
		return formatted;
	}

//	public static void main(String[] abc) throws Exception {
//
//		/*
//		 * String hour = "12"; String min = "15"; String sec = "18"; String amPm = "AM";
//		 * 
//		 * 
//		 * String retString =
//		 * getHourMinSecFromLong(getTimeInLongFromHHmmss(hour,min,sec,amPm));
//		 * 
//		 * hour = retString.substring(0,2); min = retString.substring(3,5); sec =
//		 * retString.substring(6,8); amPm = retString.substring(9,11);
//		 */
//
//		Long val = getTimeInLongFromStringTodayDatehhmmssaa("12:5:1 PM");
//		System.out.println(val);
//
//		String str = getStringDateFromEpoch(val, MINUTE_FORMAT);
//		System.out.println(str);
//
//		// dd/MM/yyyy
//		// 02/07/1996
//	}

	public static long getTimeInLongFromHHmmss(String hour, String min, String sec, String a) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
		Date d = df.parse("2015-01-15 " + hour + ":" + min + ":" + sec + " " + a);
		long mills = d.getTime();
		return mills;

	}

	public static Long getTimeInLongFromString(String hhmmssa) throws ParseException {
		Long mills = null;
		if (hhmmssa != null && !"".equals(hhmmssa)) {

			SimpleDateFormat df = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);

			Date d = df.parse("15/12/2015 " + hhmmssa);
			mills = d.getTime();
		}
		return mills;

	}

	public static Long getDateTimeInLongFromString(String datehhmmssa) throws ParseException {
		Long mills = null;
		if (datehhmmssa != null && !"".equals(datehhmmssa)) {

			SimpleDateFormat df = new SimpleDateFormat(CIC_DATETIME_FORMAT);

			Date d = df.parse(datehhmmssa);
			mills = d.getTime();
		}
		return mills;

	}

	public static Long getTimeInLongFromStringhhmmssa(String hhmma) throws ParseException {
		Long mills = null;
		if (hhmma != null && !"".equals(hhmma)) {
			SimpleDateFormat df = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
			Date d = df.parse(hhmma);
			mills = d.getTime();
		}
		return mills;

	}

	public static Long getTimeInLongFromStringTodayDatehhmmaa(String hhmma) throws ParseException {
		Long mills = null;
		SimpleDateFormat df = new SimpleDateFormat(CIC_DATE_FORMAT);
		Date today = new Date();
		String todayStr = df.format(today);
		hhmma = todayStr + " " + hhmma;

		if (hhmma != null && !"".equals(hhmma)) {
			df = new SimpleDateFormat(CIC_DATETIME_FORMAT);
			Date d = df.parse(hhmma);
			mills = d.getTime();
		}
		return mills;
	}

	public static Long getTimeInLongFromStringTodayDatehhmmssaa(String hhmmssa) throws ParseException {
		Long mills = null;
		SimpleDateFormat df = new SimpleDateFormat(CIC_DATE_FORMAT);
		Date today = new Date();
		String todayStr = df.format(today);
		hhmmssa = todayStr + " " + hhmmssa;

		if (hhmmssa != null && !"".equals(hhmmssa)) {
			df = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
			Date d = df.parse(hhmmssa);
			mills = d.getTime();
		}
		return mills;
	}

	public static Long getTimeInLongFromStringhhmma(String hhmma) throws ParseException {
		Long mills = null;
		if (hhmma != null && !"".equals(hhmma)) {
			SimpleDateFormat df = new SimpleDateFormat(CIC_DATETIME_FORMAT);
			Date d = df.parse("15/12/2015 " + hhmma);
			mills = d.getTime();
		}
		return mills;

	}

	public static String getHourMinSecFromLong(Long timeInMilSec) {
		String timeStr = "";
		if (timeInMilSec != null && timeInMilSec != 0l) {
			SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
			timeStr = df.format(timeInMilSec);
		}
		return timeStr;
	}

	public static long todayLongStartTime() {
		long todayLongStartTime = 0L;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(CIC_DATE_FORMAT);
			String UR_CALL_BACK_DATE_TEXT = sdf.format(cal.getTime());

			SimpleDateFormat formatter = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
			Date date = (Date) formatter.parse(UR_CALL_BACK_DATE_TEXT + " 00:00:00");
			todayLongStartTime = date.getTime();
		} catch (Exception e) {
			e.getMessage();
		}

		return todayLongStartTime;
	}

	public static long todayLongEndTime() {
		long todayLongEndTime = 0l;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(CIC_DATE_FORMAT);
			String UR_CALL_BACK_DATE_TEXT = sdf.format(cal.getTime());

			SimpleDateFormat formatter = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
			Date date = (Date) formatter.parse(UR_CALL_BACK_DATE_TEXT + " 23:59:59");
			todayLongEndTime = date.getTime();

		} catch (Exception e) {
			e.getMessage();
		}

		return todayLongEndTime;
	}

	public static Long getLongDateFromDDMMYYYYString(String dateString) throws ParseException {

		long dateLong = 0l;

		if (dateString != null && !"".equals(dateString)) {
			SimpleDateFormat dF = new SimpleDateFormat(CIC_DATE_FORMAT); // The mask
			// 'a' value in the Mask represents AM / PM - h means hours in AM/PM mode
			Date date = dF.parse(dateString); // parsing the String into a Date using the mask

			// Date.getTime() method gives you the Long with milliseconds since Epoch.

			dateLong = date.getTime();
		}
		return dateLong;
	}

	public static String getStringDateinDDMMYYYYFromEpoch(Long epochDate) throws ParseException {
		return getStringDateFromEpoch(epochDate, CIC_DATE_FORMAT);
	}

	public static String getStringDateinDDMMYYYYHHMMSSFromEpoch(Long epochDate) throws ParseException {
		return getStringDateFromEpoch(epochDate, CIC_DATETIMESECS_FORMAT);
	}

	public static String getStringDateinBillDeskFormatFromEpoch(Long epochDate) throws ParseException {
		return getStringDateFromEpoch(epochDate, BILL_DESK_DATE_FORMAT);
	}

	public static long getCurrentDateAsLong() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CIC_DATE_FORMAT);
		String paraDate = dateFormat.format(new Date().getTime());

		Date d = null;
		long milliseconds = 0l;
		try {
			d = dateFormat.parse(paraDate);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return milliseconds;
	}

	public static long getCurrentDateTimeAsLong() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CIC_DATETIMESECS_FORMAT);
		String paraDate = dateFormat.format(new Date().getTime());

		Date d = null;
		long milliseconds = 0l;
		try {
			d = dateFormat.parse(paraDate);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return milliseconds;
	}

	public static long getCurrentDateTimeAsLong(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String paraDate = dateFormat.format(new Date().getTime());

		Date d = null;
		long milliseconds = 0l;
		try {
			d = dateFormat.parse(paraDate);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return milliseconds;
	}

	public static boolean isValidDate(String dateString, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			df.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static double getPercentage(double price, double discount) {
		DecimalFormat df = new DecimalFormat("#.##");
		double discountPercentage = 0.00;
		if (price > 0 && discount > 0) {
			discountPercentage = (discount / price) * 100;
			discountPercentage = Double.valueOf(df.format(discountPercentage));
		}
		return discountPercentage;
	}

	public static String getHourMinSecFromLongTime(Long timeInMilSec) {
		String timeStr = "";
		if (timeInMilSec != null && timeInMilSec != 0l) {
			SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
			timeStr = df.format(timeInMilSec);
		}
		return timeStr;
	}

	public static String getDateStringByInputFormat(String givenDateStr, String givenFormat, String expectedFormat)
			throws ParseException {
		SimpleDateFormat sdfGivenFormat = new SimpleDateFormat(givenFormat);
		SimpleDateFormat sdfExpectedFormat = new SimpleDateFormat(expectedFormat);
		return sdfExpectedFormat.format(sdfGivenFormat.parse(givenDateStr));
	}

	public static String addSomeDaysToCurrentDate(String expectedFormat, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, days); // Adding days days
		String output = sdf.format(c.getTime());
		return output;
	}

	public static long getEpochDateFromCurrentDate() {
		Date today = Calendar.getInstance().getTime();

		// Constructs a SimpleDateFormat using the given pattern
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

		// format() formats a Date into a date/time string.
		String currentTime = crunchifyFormat.format(today);
		// log("Current Time = " + currentTime);
		long epochTime = 0L;
		try {

			// parse() parses text from the beginning of the given string to produce a date.
			Date date = crunchifyFormat.parse(currentTime);

			// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
			// GMT represented by this Date object.

			epochTime = date.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return epochTime;

	}

	/* This two function was added for Feedback to Esteemed Clients */
	/* Feedback to Esteemed Clients Start */
	public static String getEarlyDate() {

		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -15);
		String formatted = format1.format(cal.getTime());
		System.out.println(formatted);

		return formatted;

	}

	public static String getCurrentDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String curD = formatter.format(date);

		return curD;
	}

	/* Feedback to Esteemed Clients End */

}
