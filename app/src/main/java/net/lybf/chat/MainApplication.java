package net.lybf.chat;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.BmobUtils;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.CrashHandler;
import net.lybf.chat.utils.DateTools;
import cn.bmob.v3.Bmob;
import net.lybf.chat.system.Utils;
import net.lybf.chat.utils.Logcat;
import cn.bmob.v3.listener.SaveListener;
import net.lybf.chat.utils.chat;
import cn.bmob.push.BmobPush;

/*
 @author: lybf
 @Date : 2017/.....
 */
public class MainApplication extends Application
  {

    //单例
    private static MainApplication INSTANCE;

    //日志记录
    private static Logcat logcat;

    //Context
    private static Context mContext;

    //崩溃记录器
    private static CrashHandler crash;

    //应用配置
    private static settings set;

    //用户
    private static MyUser user;

    //
    private static BmobInstallation device;

    //JNI
    private static chat jni;
    
    @Override
    public void onCreate(){
        INSTANCE=this;
        init();
        logcat.println(this,"Application onCreate");
        super.onCreate();
      }

    @Override
    public void onLowMemory(){
        logcat.println(this,"onLowMemory");
        super.onLowMemory();
      }

    @Override
    public void onTerminate(){
        logcat.println(this,"kill application:MPSquare");
        super.onTerminate();
      }



    public void init(){
        jni=new chat();
        set=settings.getInstance();
        set.init(getApplicationContext());
        logcat=Logcat.getInstance();
        logcat.init();
        super.onCreate();
        crash=CrashHandler.getInstance();
        crash.init(this);
        BmobPush.startWork(this);
        logcat.println(this,"BmobPush startWork");
        boolean success=jni.BmobInitialize(getApplicationContext());
        logcat.println("Bmob init:"+success);
        mContext=this;
        initBmob();
      }

    public static MainApplication getInstance(){
        if(INSTANCE==null){
            synchronized(MainApplication.class){
                INSTANCE=new MainApplication();
                // INSTANCE.init();
              }
          }
        return INSTANCE;
      }

    private void initBmob(){
        user=BmobUser.getCurrentUser(MyUser.class);
        device=BmobInstallation.getCurrentInstallation();
        device.save(new SaveListener<String>(){
            @Override
            public void done(String p,BmobException e){
                Utils.print(this.getClass(),"Installation :"+p);
                if(e==null){
                    //  Utils.print(this.getClass(),"Installation save success");
                  }else{
                    //    Utils.print(this.getClass(),"Installation info save failed:"+e.getMessage());
                  }
              }    
          });
      }

    public BmobInstallation getDevice(){
        if(device==null)
          initBmob();
        return this.device;
      }

    public MyUser getUser(){
        if(user==null)
          initBmob();
        return this.user;
      }

    public Context getContext(){
        if(INSTANCE==null){
            INSTANCE=new MainApplication();
          }
        return INSTANCE;
      }

    public Application getApplication(){
        return this;
      }

    public Logcat getLogcat(){
        if(logcat==null){
            logcat=Logcat.getInstance();
            logcat.init();
          }
        return logcat;
      }

    public settings getSettings(){
        if(set==null){
            logcat.println("Settings is Null");
            set=settings.getInstance();
            set.init(getApplicationContext());
          }
        return this.set;
      }


  }
