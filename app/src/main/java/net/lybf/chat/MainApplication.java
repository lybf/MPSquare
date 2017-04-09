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
import net.lybf.chat.system.settings;
public class MainApplication extends Application
  {
    private static String APPID ="4eaad1f155b7ed751472ed23e05bf084";
	private static Context mContext;

	private CrashHandler crash;

	public settings set=new settings(this);
	@Override
    public void onCreate(){
        Bmob.initialize(this,APPID);
		mContext=this;
        super.onCreate();
        crash=new CrashHandler(this);
		crash.init();
		set=new settings(this);
		if(set.isDark()){
			setTheme(R.style.DarkTheme);
		  }else{
			setTheme(R.style.LightTheme);
		  }
      }


	public Context getContext(){
		return mContext;
	  }

	public Application getApplication(){
		return this;
	  }

	public settings getSettings(){
		if(set==null){
			print("SettingsIsNull");
			set=new settings(this);
		  }
		return this.set;
	  }


	private void print(Object... obj){
		StringBuilder build=new StringBuilder();
		for(Object o:obj){
			build.append(o.toString());
		  }
		System.out.println("MainApplication.class:"+build.toString());
	  }
  }
