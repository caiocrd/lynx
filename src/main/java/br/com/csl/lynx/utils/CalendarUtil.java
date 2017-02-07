package br.com.csl.lynx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class CalendarUtil {

	public static Calendar getToday() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		now.setLenient(false);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		
		return now;
	}
	
    public static Calendar getCalendar(Date date) {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	return calendar;
    }
    
    public static Calendar getNow() {
    	return Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
    }
    
    public static String dateToString(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    	return sdf.format(date);
    }
    
    public static String dateToYYYYString(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	return sdf.format(date);
    }
    
	public static Calendar getStripTime(Calendar calendar) {
		calendar.setLenient(false);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar;
	}
}
