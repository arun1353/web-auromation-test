package com.goldclub.common;

import java.text.*;
import java.util.*;


public class GCCommonTestCase extends GCBaseTestCase{
	public static final String GENERIC_FILE_PATH = "src/main/resources/com/goldclub/";
	
	/**
	 * Return next year
	 * @return String
	 */
    public static String nextYear() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

	/**
	 * Return current month of year. For use with p13n and calendar
	 * date picker (i.e. verify/pick current day/date)
	 * @return String
	 */
	public static String currentMonthOfYear() {
    	Date date = new Date();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
    	return simpleDateFormat.format(date);
	}
	
	/**
	 * Return current day of month. For use with p13n and calendar
	 * date picker (i.e. verify/pick current day/date)
	 * @return String
	 */
	public static String currentDayOfMonth() {
    	Date date = new Date();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
    	return simpleDateFormat.format(date);
	}
	
	public static String currentDayOfMonth2() {
    	Date date = new Date();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d");
    	return simpleDateFormat.format(date);
	}
	
	public static String currentDate() {
    	Date date = new Date();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyy");
    	return simpleDateFormat.format(date);
	}
	
	/**
	 * Return a random number as a String
	 * @return String
	 */
	public static String randomNum() {
		Long random = new Random().nextInt() + System.currentTimeMillis();
		return random.toString();
	}
	
	
	/**
	 * Return a random number as a String with specified length
	 * @return String
	 */
	public static String randomNum(int length) {
		Long random = new Random().nextInt(length) + System.currentTimeMillis();
		return random.toString();
	}

}
