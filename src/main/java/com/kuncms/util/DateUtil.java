package com.kuncms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	Date date=new Date();
	
	public String getNow(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=format.format(date);
		return now;
		
	}
}
