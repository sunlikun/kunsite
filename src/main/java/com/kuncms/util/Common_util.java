package com.kuncms.util;

import java.util.Calendar;

public class Common_util {
	//产生4位的随机数(不足4位前加零)
	public static String getRandomNum4(){  
        String fourRandom = "";
        int   randomNum =   (int)(Math.random()*10000);
        fourRandom = randomNum +"";
        int randLength =  fourRandom.length();
        if(randLength <4){
            for(int i=1; i <=4-randLength; i++)
                fourRandom = fourRandom + "0";
        } 
        StringBuilder sb = new StringBuilder("");
         Calendar cal=Calendar.getInstance();
         sb.append(fourRandom);
        return sb.toString(); 
  } 
}
