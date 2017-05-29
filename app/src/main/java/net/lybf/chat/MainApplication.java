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
import net.lybf.chat.system.Utils;
import cn.bmob.v3.BmobACL;
public class MainApplication extends Application
  {
    private static Context mContext;

    private CrashHandler crash;

    public settings set=new settings(this);

    public boolean LastTheme;
    @Override
    public void onCreate(){
        try{
           System.out.println("init Bmob:"+(true==BmobInitialize(this)));
            String key=getkey();
            if(key!=null){
                 // Bmob.initialize(this,key);
              }else{
                print("Key获取失败");
                //Bmob.initialize(this,APPID);
              }
          }catch(Exception e){
            print(e);
          }
        mContext=this;
        super.onCreate();
        crash=new CrashHandler(this);
        crash.init();
        //    set=new settings(this);

      }


    public Context getContext(){
        return mContext;
      }

    public Application getApplication(){
        return this;
      }

    public settings getSettings(){
        if(set==null){
            print("Settings Is Null");
            set=new settings(this);
          }

        LastTheme=set.isDark();
        /*if(LastTheme!=set.isDark())
         LastTheme=set.isDark();
         */
        return this.set;
      }

    static{
        try{
            System.loadLibrary("system");
          }catch(Exception e){
            new Utils().print("MainApplication",e);
          }
      }

    public native String getkey();

    public native boolean BmobInitialize(Context ctx);

    private void print(Object... obj){
        StringBuilder build=new StringBuilder();
        for(Object o:obj){
            build.append(o.toString());
          }
        System.out.println("MainApplication.class:"+build.toString());
      }
  }
