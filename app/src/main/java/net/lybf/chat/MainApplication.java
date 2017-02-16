package net.lybf.chat;
import android.app.Application;
import cn.bmob.v3.Bmob;
import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Intent;
import android.os.Bundle;
import net.lybf.chat.ui.ErrorActivity;
import android.os.Process;
import android.content.Context;
import android.app.Activity;
import net.lybf.chat.util.CrashHandler;
public class MainApplication extends Application
  {
    private static String APPID ="4eaad1f155b7ed751472ed23e05bf084";
	private static Context mContext;

	private CrashHandler crash;
	@Override
    public void onCreate(){
        Bmob.initialize(this,APPID);
		mContext=this;
        super.onCreate();
        crash=new CrashHandler(this);
		crash.init();
      }


	public Context getContext(){
		return mContext;
	  }

	public Application getApplication(){
		return this;
	  }


	private void print(Object... obj){
		StringBuilder build=new StringBuilder();
		for(Object o:obj){
			build.append(o.toString());
		  }
		System.out.println("MainApplication.class:"+build.toString());
	  }
  }
