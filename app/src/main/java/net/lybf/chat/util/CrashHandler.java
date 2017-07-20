package net.lybf.chat.util;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.Paths;
import static net.lybf.chat.system.Utils.*;
public class CrashHandler implements UncaughtExceptionHandler
  {
    public static final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    private boolean logcat;

    private Logcat log;

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler(){
      }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance(){
        return INSTANCE;
      }

    public void init(Context context){
        mContext=context;
        mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
      }

    public void startLogcat(){
        log=Logcat.getInstance();
        log.init();
        logcat=true;
      }

    public Context getContext(){
        return this.mContext;
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



    @Override
    public void uncaughtException(Thread thread,Throwable ex){

        if(!handleException(ex,thread)&&mDefaultHandler!=null){
            mDefaultHandler.uncaughtException(thread,ex);
          }else{
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
                print(this.getClass(),ex);
                Intent i=new Intent(mContext,Class.forName("net.lybf.chat.ui.ErrorActivity"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b=new Bundle();

                b.putString("error",builder.toString());
                i.putExtra("Error",b);
                mContext.startActivity(i);
                collectDeviceInfo(mContext);
                saveCrashInfo2File(ex);
                if(logcat)
                  log.println(print(this.getClass(),builder.toString()));
                Process.killProcess(Process.myPid());
                System.exit(1);
              }catch(Exception e){
                try{
                    log.println(print(this.getClass(),e));
                  }catch(Exception p){
                    p.printStackTrace();
                  }
                print(this.getClass(),e);
                return false;
              }

            if(builder.toString().length()>1){
                return true;
              }else{
                return false;
              }
          }

      }
    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx){
        try{
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
            PackageManager.GET_ACTIVITIES);
            if(pi!=null){
                String versionName = pi.versionName==null? "null"
                :pi.versionName;
                String versionCode = pi.versionCode+"";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
              }
          }catch(PackageManager.NameNotFoundException e){
            Log.e(TAG,"an error occured when collect package info",e);
          }
        Field[] fields = Build.class.getDeclaredFields();
        for(Field field : fields){
            try{
                field.setAccessible(true);
                infos.put(field.getName(),field.get(null).toString());
                Log.d(TAG,field.getName()+" : "+field.get(null));
              }catch(Exception e){
                Log.e(TAG,"an error occured when collect crash info",e);
              }
          }
      }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex){

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> entry : infos.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key+"="+value+"\n");
          }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while(cause!=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
          }
        printWriter.close();
        String result = writer.toString();
        Log.d("########",result);
        sb.append(result);
        try{
            String fileName = "Crash.log";
            String path = Paths.LOGCHAT_CRASH+"/";
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdirs();
              }

            File file=new File(path+fileName);
            if(file.exists())
              if(file.length()>1024*1024)
                file.delete();
            FileOutputStream fos = new FileOutputStream(path+fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
            return fileName;
          }catch(Exception e){
            Log.e(TAG,"an error occured while writing file...",e);
          }
        return null;
      }

  }
