package net.lybf.chat.system;
import android.os.Build;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import android.util.Log;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.lybf.chat.ui.MainActivity;
import android.system.ErrnoException;
import net.lybf.chat.util.StackTraceMessage;
import android.os.Environment;
import android.text.format.Formatter;
import android.content.Context;
import net.lybf.chat.MainApplication;
import android.os.StatFs;

public class Utils
  {

    public boolean root = false;

    public Context ctx;
    public String getDeviceInfo(){
        ctx=new MainApplication().getContext();
        StringBuffer sb =new StringBuffer();
        sb.append("版本名:"+Build.VERSION.RELEASE);
        sb.append("\n版本号:"+Build.VERSION.SDK_INT);
        sb.append("\n版本："+Build.MODEL);
        //sb.append("\n主板："+Build.BOARD);
        //sb.append("\n系统启动程序版本号："+Build.BOOTLOADER);
        sb.append("\n系统定制商："+Build.BRAND);
        sb.append("\ncpu指令集："+Build.CPU_ABI);
        sb.append("\ncpu指令集2："+Build.CPU_ABI2);
        sb.append("\n设置参数："+Build.DEVICE);
        //sb.append("\n显示屏参数："+Build.DISPLAY);
        //sb.append("\n无线电固件版本："+Build.getRadioVersion());
        //sb.append("\n硬件识别码："+Build.FINGERPRINT);
        //sb.append("\n硬件名称："+Build.HARDWARE);
        //sb.append("\nHOST:"+Build.HOST);
        //sb.append("\n修订版本列表："+Build.ID);
        sb.append("\n硬件制造商："+Build.MANUFACTURER);
        sb.append("\n硬件序列号："+Build.SERIAL);
        //sb.append("\n手机制造商："+Build.PRODUCT);
        //sb.append("\n描述Build的标签："+Build.TAGS);
        //sb.append("\nBuilder类型："+Build.TYPE);
        //sb.append("\nUSER:"+Build.USER);
        sb.append("\n编译时间:"+Long2Date(Build.TIME));
        sb.append("\n内置存储："
        +String.format("%s/%s",getRomAvailableSize(),getRomTotalSize()));

        sb.append("\n外置存储："
        +String.format("%s/%s",getSDAvailableSize(),getSDTotalSize()));

        //getFileSize(ctx,Environment.getExternalStorageDirectory())));
        //sb.append("\n当前开发者代号:"+Build.VERSION.CODENAME);
        //sb.append("\n源码控制版本号:"+Build.VERSION.INCREMENTAL);
        /*
         String[][] des={
         {"os.version","OS版本号"}
         ,{"os.name","OS名称"}
         ,{"os.arch","OS架构"}
         ,{"user.dir","dir属性"}
         ,{"path.separator","路径分隔符"}
         ,{"line.separator","行文分隔符"}
         ,{"file.separator","文件分隔符"}
         ,{"java.version","Java 版本"}
         };

         try{
         for(String[] s:des)
         sb.append("\n"+s[1]+":"+System.getProperty(s[0]));
         }catch(Exception e){
         print(e);
         }*/
        return sb.toString();
      }


    public String Long2Date(long l){
        Date d=new Date(l);
        SimpleDateFormat da=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        return da.format(d);
      }



    public boolean isEmail(String email){
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

    public long getTotalSize(String path){ 
        StatFs fileStats = new StatFs(path); 
        fileStats.restat(path);
        long count= fileStats.getBlockCount();
        long size=fileStats.getBlockSize(); 
        long l=count*size;
        if(l<-1)
          l=~l;
        return l;
      }

    public String getFileSize(Context mContext,File path){
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
        File path = Environment.getExternalStorageDirectory();
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
        File path = Environment.getExternalStorageDirectory();
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
        File path = Environment.getDataDirectory();
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
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(ctx,blockSize*availableBlocks);
      }

    public void print(Object obj){
        System.out.println(obj);
      }

    public void print(String classname,Object msg){
        System.out.println(String.format("\n%s-->:%s\n",classname,msg.toString()));
      }

    public void print(java.lang.Class mclass,Object obj){
        System.out.println(String.format("\n%s-->%s\n",mclass.getPackage(),obj));
      }

   
    public void print(java.lang.Class mclass,Exception e){
        System.out.println(String.format("\n%s-->%s\n",mclass.getPackage(),new StackTraceMessage().init(e).getMessage()));
      }
    public void logE(String tag,String msg){
        Log.e(tag,msg);
      }
  }

      
