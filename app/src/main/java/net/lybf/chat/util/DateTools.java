package net.lybf.chat.util;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateTools
  {
	public  String date(Date startDate){ 
		Date endDate=new Date(System.currentTimeMillis());
		if(startDate==null||endDate==null)
		  return null;
		long timeLong = endDate.getTime()-startDate.getTime();
		if(timeLong<60*1000)
		  return timeLong/1000+"秒前";
		else if(timeLong<60*60*1000){
			timeLong=timeLong/1000/60;
			return timeLong+"分钟前";
		  }else if(timeLong<60*60*24*1000){
			timeLong=timeLong/60/60/1000;
			return timeLong+"小时前";
		  }else if(timeLong<60*60*24*1000*7){
			timeLong=timeLong/1000/60/60/24;
			return timeLong+"天前";
		  }else if(timeLong<60*60*24*1000*7*4){
			timeLong=timeLong/1000/60/60/24/7;
			return timeLong+"周前";
		  }else if(startDate.getYear()==endDate.getYear()){
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			return sdf.format(startDate);
		  }else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			return sdf.format(startDate);
		  }
		}
  }
