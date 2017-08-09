package net.lybf.chat.system;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import net.lybf.chat.MainApplication;
import net.lybf.chat.utils.Logcat;
import net.lybf.chat.utils.StackTraceMessage;

public class settings
  {
    //根
    private JSONObject root;
    //Context
    private static Context ctx;
    //文件
    private static File file;
    //设置文件路径
    private static final String path=Paths.SETTINGS_PATH;
    //Assets-main.json
    private static final String res="settings/main.json";

    private static Logcat logcat;

    private static settings settings;;

    private settings(){
        if(logcat==null)
          logcat=MainApplication.getInstance().getLogcat();
      }

    public static settings getInstance(){
        if(settings==null)
          settings=new settings();
        return settings;
      }

    public void init(Context context){
        this.ctx=context;
        init();
      }

    private synchronized void init(){
        file=new File(path);
        if(!file.getParentFile().exists())
          file.getParentFile().mkdirs();
        if(!file.exists()){
            try{
                InputStream input=ctx.getAssets().open(res);
                FileOutputStream out=new FileOutputStream(file);
                byte[] by=new byte[input.available()];
                input.read(by);
                root=new JSONObject(new String(by));
                Date d=new Date(System.currentTimeMillis());
                SimpleDateFormat p=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);
                p.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                String date=p.format(d);
                root.put("updatedAt",date);
                root.put("createdAt",date);
                save();
                //  out.write(format(ar.toString()).getBytes());
              }catch(Exception e){
                print(e);
              }
          }
        importSettings(this.file);
        // importSettings(file);
      }

    public synchronized void importSettings(File file){
        if(file.exists()&&file.length()>0){
            try{
                InputStream in=new FileInputStream(file);
                byte[] b=new byte[in.available()];
                in.read(b);
                root=new JSONObject(new String(b));
                in.close();
              }catch(Exception e){
                print(e);
              }
          }else{
            init();
          }
      }



    public synchronized void setRandomBackground(boolean can)throws JSONException{
        set("RandomBackground",can);
        save();
      }

    public boolean getRandomBackground(){
        boolean b= get("RandomBackground");
        return b;
      }


    public boolean isDark(){
        boolean b=get("DarkTheme");
        return b;
      }

    public synchronized void setDarkTheme(boolean b)throws JSONException{
        set("DarkTheme",b);
        save();
      }

    public String getUpdatedAt(){
        return (String)get("updatedAt");
      }

    public String getCreatedAt(){   
        return (String)get("createdAt");
      }


    private synchronized void set(String key,Object object){
        try{
            root.put(key,object);
          }catch(JSONException e){
            e.printStackTrace();
            logcat.println(this,StackTraceMessage.getMessage(e).toString());
          }
      }

    private synchronized Object get(String key){
        Object result = null;
        try{
            result=root.opt(key);
          }catch(Exception e){
            e.printStackTrace();
            logcat.println(this,StackTraceMessage.getMessage(e).toString());
          }
        return result;
      }
      
      
    public void refresh(){
        init();
      }


    public String format(){
        String result=format(root.toString());
        return result;
      }

    public String format(String string){
        Date d=new Date(System.currentTimeMillis());
        SimpleDateFormat p=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);
        p.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String date=p.format(d);
        print("date:"+date);
        try{
            root.put("updatedAt",""+date);
          }catch(JSONException e){
            Utils.print(this.getClass(),e);
          }
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(""+string);
        String format= gson3.toJson(je);
        return format;
      }

    public synchronized void save(){
        try{
            FileOutputStream out=new FileOutputStream(file);
            out.write(format().getBytes());
          }catch(Exception e){
            print(e);
          }
      }

    public interface SaveListener
      {
        void done(Exception e);
      }

    private SaveListener savelistener;
    public synchronized void save(SaveListener listener){
        savelistener=listener;
        try{
            FileOutputStream out=new FileOutputStream(file);
            out.write(format().getBytes());
            if(savelistener!=null)
              savelistener.done(null);
          }catch(Exception e){
            print(e);
            savelistener.done(e);
          }
      }


    private void print(Object o){
        Utils.print(this.getClass(),o);
      }
  }
