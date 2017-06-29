package net.lybf.chat.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import net.lybf.chat.system.Utils;

public class DateTools
  {
    public static final String TIME_YEAR="yyyy";
    public static final String TIME_MOTHER="MM";
    public static final String TIME_DAY="dd";
    public static final String TIME_HOURS="HH";
    public static final String TIME_MINUTES="mm";
    public static final String TIME_SECONDS="ss";

    public String format(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
      }

    public String format(long date,String pattern){
        Date dat=new Date(date);
        return new SimpleDateFormat(pattern).format(dat);
      }


	public Date String2Date(String date,String type){
		SimpleDateFormat f=new SimpleDateFormat();
		f.applyPattern(type);
		Date dat = null;
		try{
			dat=f.parse(date);
		  }catch(Exception e){
            Utils.print(this.getClass(),e);
		  }
		return dat;
	  }

    public Date getDate(String date,String pattern){
        SimpleDateFormat f=new SimpleDateFormat();
        f.applyPattern(pattern);
        Date dat = null;
        try{
            dat=f.parse(date);
          }catch(Exception e){
            Utils.print(this.getClass(),e);
          }
        return dat;
      }

	public long getLong(Date date){
		if(date!=null)
		  return date.getTime();
		return 0;
	  }

    public long getLong(String date,String pattern){
        Date dt=getDate(date,pattern);
        return dt.getTime();
      }

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
