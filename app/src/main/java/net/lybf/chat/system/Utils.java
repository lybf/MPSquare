package net.lybf.chat.system;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lybf.chat.BuildConfig;
import net.lybf.chat.MainApplication;
import net.lybf.chat.util.StackTraceMessage;

public class Utils
  {

    public boolean root = false;

    public Context ctx;

    private PackageManager pm;

    private PackageInfo info;

    public  String getDeviceInfo(){
        ctx=new MainApplication().getContext();
        pm=ctx.getPackageManager();
        try{
            info=pm.getPackageInfo(ctx.getPackageName(),0);
          }catch(Exception e){
            print(this.getClass(),e);
          }
        StringBuffer sb =new StringBuffer();
        if(info!=null){
            sb.append("\n-------软件信息-------");
            sb.append("".format("\n名称：%s","冒泡广场"));
            sb.append("".format("\n软件版本号：%s",info.versionCode));
            sb.append("".format("\n软件版本名：%s",info.versionName));
            sb.append("".format("\n软件包名：%s",info.packageName));
            sb.append("".format("\n上次更新安装时间：%s",Long2Date(info.lastUpdateTime)));
          }else{
            print(this.getClass(),info);
          }
        sb.append("\n\n-------设备信息-------");
        sb.append("\n版本名:"+Build.VERSION.RELEASE);
        sb.append("\n版本号:"+Build.VERSION.SDK_INT);
        sb.append("\n版本："+Build.MODEL);
        //sb.append("\n主板："+Build.BOARD);
        //sb.append("\n系统启动程序版本号："+Build.BOOTLOADER);
        sb.append("\n系统定制商："+Build.BRAND);
        sb.append("\ncpu指令集："+Build.CPU_ABI);
        sb.append("\ncpu指令集2："+Build.CPU_ABI2);
        sb.append("\n设置参数："+Build.DEVICE);
        sb.append("\n硬件制造商："+Build.MANUFACTURER);
        sb.append("\n硬件序列号："+Build.SERIAL);
        sb.append("\n编译时间:"+Long2Date(Build.TIME));
        sb.append("".format("\n安装空间：%s/%s",getInstallationAvailableSize(),getInstallationTotalSize()));
        sb.append("".format("\n内置存储：%s/%s",getRomAvailableSize(),getRomTotalSize()));
        sb.append("".format("\n外置存储：%s/%s",getSDAvailableSize(),getSDTotalSize()));
        return sb.toString();
      }


    public static String Long2Date(long l){
        Date d=new Date(l);
        SimpleDateFormat da=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        return da.format(d);
      }



    public static boolean isEmail(String email){
        Pattern p=Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher    m= p.matcher(email);
        if(m.find())
          return true;
        else
          return false;
      }


    public boolean isRoot(){
        try{
            if(!new File("su/bin/su").exists())
              root=false;
            else
              root=true;
          }catch(Exception e){
            print(e);
          }
        return root;
      }

    public static long getTotalSize(String path){ 
        StatFs fileStats = new StatFs(path); 
        fileStats.restat(path);
        long count= fileStats.getBlockCount();
        long size=fileStats.getBlockSize(); 
        long l=count*size;
        if(l<-1)
          l=~l;
        return l;
      }

    public static String getFileSize(Context mContext,File path){
        File file =path;
        String str;
        if(file.isDirectory()){
            long usableSpace = file.getUsableSpace();
            str=Formatter.formatFileSize(mContext,usableSpace);
          }else{
            long l=file.length();
            str=Formatter.formatFileSize(mContext,l);
          }
        return str;

      }

    /**
     * 获得SD卡总大小
     * 
     * @return
     */
    public String getSDTotalSize(){
        File path =new File("/storage/sdcard1");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(ctx,blockSize*totalBlocks);
      }

    /**
     * 获得sd卡剩余容量，即可用大小
     * 
     * @return
     */
    public String getSDAvailableSize(){
        File path =new File("/storage/sdcard1");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(ctx,blockSize*availableBlocks);
      }



    /**
     * 获得机身安装内存总大小(Android 6.0)
     * 
     * @return
     */
    public String getInstallationTotalSize(){
        File path = Environment.getDataDirectory()
        ;
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(ctx,blockSize*totalBlocks);
      }

    /**
     * 获得机身安装空间可用内存 (Android 6.0)
     * 
     * @return
     */
    public String getInstallationAvailableSize(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(ctx,blockSize*availableBlocks);
      }


    /**
     * 获得机身内存总大小
     * 
     * @return
     */
    public String getRomTotalSize(){
        File path = new File("/sdcard");
        ;
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(ctx,blockSize*totalBlocks);
      }

    /**
     * 获得机身可用内存
     * 
     * @return
     */
    public String getRomAvailableSize(){
        File path = new File("/sdcard");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(ctx,blockSize*availableBlocks);
      }


    public static void print(Object obj){
        System.out.println(obj);
      }

    public static void print(String classname,Object msg){
        String pkg=classname;
        System.out.println();
        System.out.println("\n\n-----------"+pkg+"---------");
        System.out.println(String.format("\n%s-->\n    %s\n",pkg,msg.toString()));
        System.out.println("\n-----------"+pkg+"---------");
        System.out.println();   
      }

    public static void print(java.lang.Class mclass,Object obj){
        String pkg=mclass.getName();
        System.out.println();
        System.out.println("\n\n-----------"+pkg+"---------");
        System.out.println(String.format("\n%s-->\n    %s\n",pkg,obj.toString()));
        System.out.println("\n-----------"+pkg+"---------");
        System.out.println();
        }


    public static void print(java.lang.Class mclass,Exception e){
        String pkg=mclass.getName();
        System.out.println();
        System.out.println("\n\n-----------"+pkg+"---------");
        System.out.println(String.format("\n%s-->\n    %s\n",pkg,new StackTraceMessage().init(e).getMessage().toString()));
        System.out.println("\n-----------"+pkg+"---------");
        System.out.println();
      }

    public class Log
      {
        private BuildConfig b;

        public Log(){
            b=new BuildConfig();
          }
        public  void e(String tag,String msg){
            if(b.DEBUG)
              android.util.Log.e(tag,msg);
          }
        public  void w(String tag,String msg){
            if(b.DEBUG)
              android.util.Log.w(tag,msg);
          }
      }
  }

      
