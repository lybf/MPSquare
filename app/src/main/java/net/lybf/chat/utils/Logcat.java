package net.lybf.chat.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import net.lybf.chat.system.Paths;

import static net.lybf.chat.system.Utils.getClassName;
import static net.lybf.chat.system.Utils.print;
public class Logcat
  {
    //单例
    private static Logcat INSTANCE;

    //路径
    private static String path;

    //日志文件
    private static File file;

    private static FileOutputStream out;

    private Logcat(){
        //  init();
      }

    public static Logcat getInstance(){
        if(INSTANCE==null)
          INSTANCE=new Logcat();
        return INSTANCE;
      }

    public synchronized void init(){
        Date date=new Date(System.currentTimeMillis());
        String name=DateTools.format(date,"yyyy-MM-dd");
        path=Paths.LOGCHAT_CRASH+"/"+name+".logcat";
        File d=new File(path);
        if(!d.getParentFile().exists())
          d.getParentFile().mkdirs();
        file=d;
        try{
            d.createNewFile();
          }catch(IOException e){
            e.printStackTrace();
          }
        try{
            out=new FileOutputStream(file,true);
        //    out.write("############Start##########\n".getBytes());
          }catch(Exception e){
            e.printStackTrace();
          }

      }


    public synchronized void println(Object thisz,Object object){
        String str="@date="+getTime()+"|||||@class="+getClassName(thisz)+"|||||@info="+object.toString();
        write(str);
      }

    public synchronized void println(Object object){
        String str="@date="+getTime()+"|||||@info="+object.toString();
        print(str);
        write(str);
      }


    public synchronized void write(String string){
        try{
            if(out!=null)
              out.write((string+"\n").getBytes());
            else
              newWrite();
          }catch(Exception e){
            e.printStackTrace();
          }
      }

    public static String getTime(){
        String time=DateTools.format(System.currentTimeMillis(),"yyy/MM/dd HH:mm:ss:SSS");
        return time;
      }


    public synchronized void close() throws IOException{
        out.write("###########End##########\n\n\n".getBytes());
        out.close();
        out=null;
        System.gc();
      }

    public synchronized void newWrite() throws Exception{
        out=new FileOutputStream(file,true);
        out.write("############Start##########\n".getBytes());
      }
  }
