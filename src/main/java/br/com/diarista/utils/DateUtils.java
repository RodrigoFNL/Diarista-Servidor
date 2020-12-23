package br.com.diarista.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils
{
	public static Date getDate(String value) throws ParseException 
	{
		if(StringUtils.isNull(value)) return null;		
		value = value.replace("T", " ").replace("Z", "");	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		return dateFormat.parse(value);	
	}

	public static boolean isOfAge(Date date) 
	{		
		if(date == null) return false;		
		Calendar calendar = Calendar.getInstance();		
		calendar.add(Calendar.YEAR, -18);		
		Date lessDate = calendar.getTime();
		return date.before(lessDate);	
	}

	public static Date getRemoveDaysInDate(Date date,  int days) 
	{
	    GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.DATE, gc.get(Calendar.DATE) - days);          
        return gc.getTime();	
	}
}
