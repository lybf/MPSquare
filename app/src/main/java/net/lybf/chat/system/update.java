package net.lybf.chat.system;
import java.io.File;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class update
{
   private String rootPath="/sdcard/lybf/MPSquare/ApplicationUpdate.json";
   private File file;
   private JSONObject rootjson;
   public update(){
      file=new File(rootPath);
      if(!file.getParentFile().exists())
         file.getParentFile().mkdirs();
      if(file.exists()){
         try{
            InputStream in=new FileInputStream(file);
            }catch(Exception e){}
         }
   }
}
