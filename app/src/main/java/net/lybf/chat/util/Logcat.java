package net.lybf.chat.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.lybf.chat.system.Paths;
import static net.lybf.chat.system.Utils.*;
import java.util.Date;
public class Logcat
  {
    private static Logcat INSTANCE=new Logcat();

    public static String path;

    private static File file;

    private static FileOutputStream out;

    private Logcat(){

      }

    public static Logcat getInstance(){
        return INSTANCE;
      }

    public void init(){
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
            out.write("############Start##########\n".getBytes());
          }catch(Exception e){
            e.printStackTrace();
          }

      }


    public void println(Object thisz,Object object){
        String str=print(thisz.getClass(),object);
        write(str);
      }

    public void println(Object object){
        print(object);
        write(object.toString());
      }

    public void printWithTime(Object object){
        print(object);
        String time=DateTools.format(System.currentTimeMillis(),"yyy/MM/dd HH:mm:ss:SSS");
        write(time+" "+object.toString());
      }

    public void write(String string){
        try{
            String time=DateTools.format(System.currentTimeMillis(),"yyy/MM/dd HH:mm:ss:SSS");
            if(out!=null)
              out.write((time+"   "+string+"\n").getBytes());
            else
              newWrite();
          }catch(Exception e){
            e.printStackTrace();
          }
      }

    public void close() throws IOException{
        out.write("###########End##########\n\n\n".getBytes());
        out.close();
        out=null;
        System.gc();
      }

    public void newWrite() throws Exception{
        out=new FileOutputStream(file,true);
        out.write("############Start##########\n".getBytes());
      }
  }
