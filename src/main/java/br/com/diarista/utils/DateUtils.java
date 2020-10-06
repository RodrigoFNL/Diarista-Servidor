package br.com.diarista.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
	public static Date getDate(String value) 
	{
		if(StringUtils.isNull(value)) return null;		
		value = value.replace("T", " ").replace("Z", "");		
		System.out.println(value);		

		return null;
	}

	public static boolean isOfAge(Date date) 
	{		
		if(date == null) return false;		
		Calendar calendar = Calendar.getInstance();		
		calendar.add(Calendar.YEAR, -18);		
		Date lessDate = calendar.getTime();
		return date.before(lessDate);	
	}
}
