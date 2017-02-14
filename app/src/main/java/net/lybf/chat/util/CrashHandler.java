package net.lybf.chat.util;
import android.content.Context;
import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
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
			// 如果自定义的没有处理则让系统默认的异常处理器来处理 
			mDefaultHandler.uncaughtException(thread,ex); 
		  }
	  }

	private boolean handleException(Throwable ex){
		if(ex==null) 
		  return false;
		else
		  try{
			  print("ErrorMessage:"+ex.toString());
			  Intent i=new Intent(m,Class.forName("net.lybf.chat.ui.ErrorActivity"));
			  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			  Bundle b=new Bundle();
			  StringBuilder builder=new StringBuilder();
			  builder.append(ex.toString());

			  for(StackTraceElement stack:ex.getStackTrace()){
				  builder.append("\n    at "+stack.getClassName()
				  +"."+stack.getMethodName()
				  +"("+stack.getFileName()
				  +"."+stack.getLineNumber()+")");

				}

			  b.putString("error",builder.toString());
			  i.putExtra("Error",b);
			  m.startActivity(i);
			  Process.killProcess(Process.myPid());
			}catch(Exception e){
			  print(e);
			}
		return true;
	  }	

	private void print(Object o){
		System.out.println("CrashHandler.class:"+o);
	  }
  }
