package net.lybf.chat.system;
import android.os.Build;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import android.util.Log;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Utils
  {

	private boolean root = false;
	public String getDeviceInfo(){
        StringBuffer sb =new StringBuffer();
       	sb.append("版本名:"+Build.VERSION.RELEASE);
        sb.append("\n版本号:"+Build.VERSION.SDK_INT);
        sb.append("\n主板："+Build.BOARD);
        sb.append("\n系统启动程序版本号："+Build.BOOTLOADER);
        sb.append("\n系统定制商："+Build.BRAND);
        sb.append("\ncpu指令集："+Build.CPU_ABI);
        sb.append("\ncpu指令集2："+Build.CPU_ABI2);
        sb.append("\n设置参数："+Build.DEVICE);
        sb.append("\n显示屏参数："+Build.DISPLAY);
        sb.append("\n无线电固件版本："+Build.getRadioVersion());
        sb.append("\n硬件识别码："+Build.FINGERPRINT);
        sb.append("\n硬件名称："+Build.HARDWARE);
        sb.append("\nHOST:"+Build.HOST);
        sb.append("\n修订版本列表："+Build.ID);
        sb.append("\n硬件制造商："+Build.MANUFACTURER);
        sb.append("\n版本："+Build.MODEL);
        sb.append("\n硬件序列号："+Build.SERIAL);
        sb.append("\n手机制造商："+Build.PRODUCT);
        sb.append("\n描述Build的标签："+Build.TAGS);
        sb.append("\nBuilder类型："+Build.TYPE);
        sb.append("\nUSER:"+Build.USER);
        sb.append("\n编译时间:"+Long2Date(Build.TIME));
        sb.append("\n当前开发者代号:"+Build.VERSION.CODENAME);
        sb.append("\n源码控制版本号:"+Build.VERSION.INCREMENTAL);
       	String[][] des={
       		{"os.version","OS版本号"}
		  ,{"os.name","OS名称"}
		  ,{"os.arch","OS架构"}
		  ,{"user.dir","dir属性"}
		  ,{"path.separator","路径分隔符"}
		  ,{"line.separator","行文分隔符"}
		  ,{"file.separator","文件分隔符"}
		  ,{"java.version","Java 版本"}
		  };

		try{
			for(String[] s:des)
			  sb.append("\n"+s[1]+":"+System.getProperty(s[0]));
		  }catch(Exception e){
			print(e);
		  }
        return sb.toString();
	  }


	public String Long2Date(long l){
		Date d=new Date(l);
		SimpleDateFormat da=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		return da.format(d);
	  }

	  

    public boolean isEmail(String email){
        Pattern p=Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher	m= p.matcher(email);
        if(m.find())
          return true;
        else
          return false;
      }
	
	  
	public boolean isRoot(){
		try{
			if(!new File("su/bin/su").exists())
			  root=false;
			else
			  root=true;
		  }catch(Exception e){
			print(e);
		  }
		return root;
	  }

	

	public void print(Object obj){
		System.out.println(obj);
	  }

	public void print(String classname,Object msg){
		System.out.println(String.format("\n%s.class:%s\n",classname,msg.toString()));
	  }

	public void print(java.lang.Class mclass,Object obj){
		System.out.println(String.format("\n%s.class :%s\n",mclass.getPackage(),obj));
	  }


	public void logE(String tag,String msg){
		Log.e(tag,msg);
		}
}

	  
