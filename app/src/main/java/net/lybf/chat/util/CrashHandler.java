package net.lybf.chat.util;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import java.lang.Thread.UncaughtExceptionHandler;
import net.lybf.chat.system.Utils;
public class CrashHandler implements UncaughtExceptionHandler
  {
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private Context m;
	public String TGA="CrashHandler";
	public CrashHandler(Context main){
		m=main;
		mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
	  }

	public void init(){
		Thread.setDefaultUncaughtExceptionHandler(this);
	  }

    public Context getContext(){
        return this.m;
      }

	@Override
	public void uncaughtException(Thread thread,Throwable ex){
        if(!handleException(ex,thread)&&mDefaultHandler!=null){ 
			mDefaultHandler.uncaughtException(thread,ex); 
		  }
	  }

	private boolean handleException(Throwable ex,Thread thread){
		if(ex==null){
			return false;
		  }else{
			StackTraceMessage msg=new StackTraceMessage();
			msg.init(ex);
			StringBuilder builder=msg.getMessage();
            if(listener!=null){
                listener.onError(builder.toString(),(Exception)ex,thread);
              }
			try{
				new Utils().print(this.getClass(),ex.toString());
				Intent i=new Intent(m,Class.forName("net.lybf.chat.ui.ErrorActivity"));
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle b=new Bundle();

				b.putString("error",builder.toString());
				i.putExtra("Error",b);
				m.startActivity(i);

				Process.killProcess(Process.myPid());
			  }catch(Exception e){
				Utils.print(this.getClass(),e);
			  }
			if(builder.toString().length()>1)
			  return true;
			else
			  return false;
		  }
	  }	




    public interface ErrorListener
      {
        void onError(String string,Exception e,Thread thread);
      }

    private ErrorListener listener;
    public void setErrorListener(ErrorListener listener){
        this.listener=listener;
      }

    public void unregisterErrorListener(){
        this.listener=null;
      }
  }
