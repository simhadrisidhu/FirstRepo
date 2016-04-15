package com.itaas.ott;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormats {
	
	public static void main(String[] args) {
		
		String date_string ="20160308";
		String  date_changed = changeDateTimeFormat(date_string,"yyyymmdd","mmddyyyy");
		System.out.println(date_changed);
		
		String starttime ="2016-03-08 19:48:15 UTC+05:30";
		String endtime ="2016-03-03 20:18:15 UTC+05:30";
		 int timeDiff = getTimeDifference(starttime,endtime);
		 System.out.println(timeDiff);
		 
		 String date = changeDateTimeFormat(starttime,"yyyy-mm-dd","mmddyyyy");
		 System.out.println(date);
		 
		 String programTime = getProgramTime(endtime,"yyyy-mm-dd hh:mm:ss","hh:mm");
		 System.out.println(programTime);
	}
	
	
	public static String getProgramTime(String dateTime,
			String currentFormat, String desiredFormat) {
		try {
			return new SimpleDateFormat(desiredFormat)
					.format(new SimpleDateFormat(currentFormat).parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getTimeDifference(String time1, String time2) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		try {
			Date t1 = parser.parse(time1);
			Date t2 = parser.parse(time2);
			return (t2.getHours() * 60 + t2.getMinutes())
					- (t1.getHours() * 60 + t1.getMinutes());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -99999999;
	}
	
	
	public static String changeDateTimeFormat(String dateTime,
			String currentFormat, String desiredFormat) {
		try {
			return new SimpleDateFormat(desiredFormat)
					.format(new SimpleDateFormat(currentFormat).parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
