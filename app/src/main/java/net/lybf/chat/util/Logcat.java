package net.lybf.chat.util;
import java.io.*;
import net.lybf.chat.system.Paths;
public class Logcat
  {
    public String path;
    private File file;
    private FileOutputStream out;
    public Logcat(){
        String pa=Paths.LOGCHAT_CRASH+"/Log.logcat";
        File d=new File(pa);
        if(!d.getParentFile().exists())
          d.getParentFile().mkdirs();
        if(!d.exists()){
            path=pa;
            file=new File(pa);
          }else{
            d.delete();
            try{
                d.createNewFile();
              }catch(IOException e){
                e.printStackTrace();
              }
          }
        try{
            FileOutputStream out=new FileOutputStream(file,true);
          }catch(FileNotFoundException e){
            e.printStackTrace();
          }

      }


    public void write(String string) throws IOException {
        out.write((string+"\n").getBytes());
     //   out.close();
      }
      
     public void close() throws IOException{
       out.close();
     }
     
     public void newWrite() throws FileNotFoundException{
       out=new FileOutputStream(file);
     }
  }
