package net.lybf.chat.system;
import org.json.JSONObject;
import java.io.File;
import android.os.storage.StorageManager;
import android.content.Context;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.json.JSONException;
import net.lybf.chat.MainApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class settings
  {
    //根
    private JSONObject root;
    //Context
    private Context ctx;
    //文件
    private File file;
    //设置文件路径
    private String path=Paths.SETTINGS_PATH;
    //Assets-main.json
    private String res="settings/main.json";


    public settings(Context ctx){
        this.ctx=ctx;
        init();
      }


    private void init(){
        file=new File(path);
        if(!file.getParentFile().exists())
          file.getParentFile().mkdirs();
        if(!file.exists()){
            try{
                InputStream input=ctx.getAssets().open("settings/main.json");
                FileOutputStream out=new FileOutputStream(file);
                byte[] by=new byte[input.available()];
                input.read(by);
                out.write(by);
              }catch(Exception e){
                print(e);
              }
          }
        if(file.exists()){
            try{
                InputStream in=new FileInputStream(file);
                byte[] b=new byte[in.available()];
                in.read(b);
                root=new JSONObject(new String(b));
              }catch(Exception e){
                print(e);
              }
          }
      }



    public void setRandomBackground(boolean can) throws JSONException{
        root.put("RandomBackground",can);
        save();
      }

    public boolean getRandomBackground(){
        boolean b= root.opt("RandomBackground");
        return b;
      }


    public boolean isDark(){
        boolean b=root.opt("DarkTheme");
        return b;
      }

    public void setDarkTheme(boolean b) throws JSONException{
        root.put("DarkTheme",b);
        save();
      }


    public void refresh(){
        init();
      }


    public String format(){
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(""+root.toString());
        String format= gson3.toJson(je);
        return format;
      }
    public void save(){
        try{
            FileOutputStream out=new FileOutputStream(file);
            out.write(format().getBytes());
          }catch(Exception e){
            print(e);
             }
      }

    public interface SaveListener
      {
        public void done(Exception e);
      }

    SaveListener savelistener;
    public void save(SaveListener listener){
        savelistener=listener;
        try{
            FileOutputStream out=new FileOutputStream(file);
            out.write(format().getBytes());
            savelistener.done(null);
          }catch(Exception e){
            print(e);
            savelistener.done(e);
          }
      }
    private void print(Object o){
        new Utils().print(this.getClass(),o);
      }
  }
