package net.lybf.chat.util;
import android.content.Context;
import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import net.lybf.chat.system.Utils;
public class CrashHandler implements UncaughtExceptionHandler
  {
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private Context m;
	public CrashHandler(Context main){
		m=main;
		mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
	  }

	public void init(){
		Thread.setDefaultUncaughtExceptionHandler(this);
	  }
	@Override
	public void uncaughtException(Thread thread,Throwable ex){
		if(!handleException(ex)&&mDefaultHandler!=null){ 
			mDefaultHandler.uncaughtException(thread,ex); 
		  }
	  }

	private boolean handleException(Throwable ex){
		if(ex==null){
			return false;
		  }else{
			StackTraceMessage msg=new StackTraceMessage();
			msg.init(ex);
			StringBuilder builder=msg.getMessage();

			try{
				new Utils().print("ErrorMessage:"+ex.toString());
				Intent i=new Intent(m,Class.forName("net.lybf.chat.ui.ErrorActivity"));
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle b=new Bundle();

				b.putString("error",builder.toString());
				i.putExtra("Error",b);
				m.startActivity(i);
				Process.killProcess(Process.myPid());
			  }catch(Exception e){
				new Utils().print(this.getClass(),e);
			  }
			if(builder.toString().length()>1)
			  return true;
			else
			  return false;
		  }
	  }	
	
  }
