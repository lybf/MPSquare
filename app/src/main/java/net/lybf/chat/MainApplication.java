package net.lybf.chat;
import android.app.Application;
import android.content.Context;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobUser;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.CrashHandler;
import net.lybf.chat.utils.Logcat;
import net.lybf.chat.utils.chat;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import java.util.List;

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

    //InstallationManager
    private static BmobInstallationManager device;

    private static BmobInstallation Installation;
    //JNI
    private static chat jni;

    @Override
    public void onCreate(){
        if(INSTANCE==null)
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
        device=BmobInstallationManager.getInstance();
        Installation=device.getCurrentInstallation();
        device.initialize(new InstallationListener<BmobInstallation>(){
            @Override
            public void done(BmobInstallation installation,BmobException e){
                if(e==null){
                    Installation=installation;
                    logcat.println(this,"Installation init success:"+installation.getInstallationId());
                  }else{
                    logcat.println(this,"Installation init failed:"+e.toString());
                  }
              }
          });
      }

    public BmobInstallation getDevice(){
        if(device==null)
          initBmob();
        return this.Installation;
      }

    public BmobInstallationManager getInstallationManager(){
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
