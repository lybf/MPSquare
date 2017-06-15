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

            try{
                if(key!=null){
                    BmobInstallation install= BmobInstallation.getCurrentInstallation();
                    if(true){
                        install.save();
                        MyUser user=BmobUser.getCurrentUser(MyUser.class);
                        if(user!=null){
                            install.setValue("user",user);
                            install.update(install.getObjectId(),new UpdateListener(){
                                @Override
                                public void done(BmobException p1){
                                    if(p1!=null){
                                        print("BmobInstallation init Success");
                                        Log.w("MainApplication",p1.getMessage());
                                      }else{
                                        print("BmobInstalla init error  -->"+new ErrorMessage().getMessage(p1.getErrorCode()));
                                      }
                                  }         
                              });

                          }else{
                            install.save();
                          }
                      }
                  }else{
                    print("Key获取失败");
                  }

              }catch(Exception e){
                print(e);
              }
          }catch(Exception e){
            print(e);
          }
        mContext=this;
        super.onCreate();
        crash=new CrashHandler(this);
        crash.init();
        if(set==null)
          set=new settings(this);
        updateSettings();
      }


    public Context getContext(){
        return mContext;
      }

    public Application getApplication(){
        return this;
      }

    public settings getSettings(){
        updateSettings();
        if(set==null){
            print("Settings Is Null");
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
    static{
        System.loadLibrary("system");
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
