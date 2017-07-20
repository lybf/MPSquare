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
import net.lybf.chat.util.CrashHandler;
import net.lybf.chat.util.DateTools;
import cn.bmob.v3.Bmob;
import net.lybf.chat.system.Utils;
import net.lybf.chat.util.Logcat;
public class MainApplication extends Application
  {

    private static Logcat logcat;

    static{
        System.loadLibrary("system");
      }

    public native String getkey();

    public native boolean BmobInitialize(Context ctx);


    private static Context mContext;

    private CrashHandler crash;

    public settings set=new settings(this);

    public boolean LastTheme;


    @Override
    public void onCreate(){
        logcat=Logcat.getInstance();
        logcat.init();
        logcat.println("Application init");
        super.onCreate();
        crash=CrashHandler.getInstance();
        crash.init(this);
        logcat.println("init Bmob:"+(true==BmobInitialize(this)));
        BmobInstallation in=BmobInstallation.getCurrentInstallation();
        in.save();
        mContext=this;
        if(set==null)
          set=new settings(this.getApplicationContext());
        updateSettings();
      }


    public Context getContext(){
        return mContext;
      }

    public Application getApplication(){
        return this;
      }

    public Logcat getLogcat(){
        return logcat;
      }

    public settings getSettings(){
        updateSettings();
        if(set==null){
            logcat.println("Settings Is Null");
            set=new settings(this);
          }
        return this.set;
      }

    private void updateSettings(){
        DateTools dt=new DateTools();
        settings sett=new settings(this);
        long l=dt.getLong(dt.getDate(sett.getUpdatedAt(),BmobUtils.BMOB_DATE_TYPE));
        long now=dt.getLong(dt.getDate(set.getUpdatedAt(),BmobUtils.BMOB_DATE_TYPE));
        if(l>now){
            set=sett;
          }
      }


  }
