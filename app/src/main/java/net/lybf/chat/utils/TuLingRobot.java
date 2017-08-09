package net.lybf.chat.utils;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import cn.bmob.v3.BmobUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import net.lybf.chat.MainApplication;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.maps.Robot;
import net.lybf.chat.maps.RobotList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.lybf.chat.system.Utils;  
public class TuLingRobot

  {
    //CreatedAt 2017/4/24 16:00
    private static MyUser user;
    private static final String key="b6b3d000e00d4580a0b0eaf3a1236bd9";

    private static MainApplication app;
    private static Context ctx;
    private static String id;

    private static TuLingRobot.TuLingRobotListener listener;

    private static final int success=100;

    private Network net;
    public TuLingRobot(){
        init();
        user=BmobUser.getCurrentUser(MyUser.class);
      }

    public TuLingRobot(MyUser user){
        init();
        this.user=user; 
      }

    private void init(){
        this.app=new MainApplication();
        this.ctx=app.getContext();
        this.net=new Network(ctx); 
      }

    public interface TuLingRobotListener
      {
        void done(Robot robot);
      }

    public void setUserID(String id){
        if(id!=null){
            this.id=id;
          }else{
            Random r=  new Random();
            String str="";
            for(int i=0;i<18;i++){
                str+=r.nextInt(999);
              }
            if(str.length()>15){
                str=str.substring(0,15);
              }
            this.id=str;
            Utils.print(this.getClass(),str);
          }
      }
      
    public void send(String message,TuLingRobotListener listener){
        this.listener=listener;
        String url="http://www.tuling123.com/openapi/api";
        JSONObject obj=new JSONObject();
        try{
            obj.put("key",key);
            obj.put("info",message);
            obj.put("userid",id);
          }catch(JSONException e){
            e.printStackTrace();
          }
        sendPost(obj.toString(),url);
        //  return sendPost(obj.toString(),url);
      }

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case success:
                  if(listener!=null){
                      listener.done((Robot)msg.obj);
                    }
                  break;
              }
          }

      };
    //发送Post
    public  void sendPost(final String param,final String url){
        new Thread(new Thread(){
            public void run(){
                OutputStreamWriter out = null;
                BufferedReader in = null;
                StringBuilder result = new StringBuilder();
                try{
                    URL realUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(50000);
                    conn.setReadTimeout(50000);
                    conn.setRequestProperty("Content-Type","application/json");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization","token");
                    conn.setRequestProperty("tag","htc_new");

                    conn.connect();

                    out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                    out.write(param);

                    out.flush();
                    out.close();
                    //
                    in=new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),"UTF-8"));
                    String line = "";
                    while((line=in.readLine())!=null){
                        result.append(line);
                      }
                  }catch(Exception e){
                    e.printStackTrace();
                  }finally{
                    try{
                        if(out!=null){
                            out.close();
                          }
                        if(in!=null){
                            in.close();
                          }
                      }catch(IOException ex){
                        ex.printStackTrace();
                      }
                  }

                JSONObject json = null;
                Robot robot = null;
                try{
                    if(!result.toString().equals("")){
                        json=new JSONObject(result.toString());
                        json.put("name","聊天机器人");
                      }else{
                        json=new JSONObject("{\"name\":\"系统\",\"code\":404,\"text\":\"未知错误\"}");
                        if(!net.isConnected()){
                            json.put("text","网络似乎出了点问题～请检查你的网络！");
                          }
                      }
                    robot=new Robot();
                    robot.setFlag(robot.FLAG_ROBOT);
                    robot.setName(""+json.opt("name"));
                    robot.setCode(json.opt("code"))
                    .setText(""+json.opt("text"))
                    .setUrl(""+json.opt("url"));

                    try{
                        JSONArray array=(JSONArray) json.opt("list");
                        if(array!=null){
                            ArrayList<RobotList> l=new ArrayList<RobotList>();
                            for(int i=0;i<array.length();i++){
                                JSONObject obj=(JSONObject) array.opt(i);
                                if(obj!=null){
                                    RobotList list=new RobotList();
                                    //new Gson().fromJson(obj.toString(),RobotList.class);
                                    list.setArticle(""+obj.opt("article"))
                                    .setDetailUrl(""+obj.opt("detailurl"))
                                    .setFlag(0)
                                    .setIcon(""+obj.opt("icon"))
                                    .setInfo(""+obj.opt("info"))
                                    .setName(""+obj.opt("name"))
                                    .setSource(""+obj.opt("source"));
                                    l.add(list);         
                                  }//end if
                              }//end for
                            if(l.size()>0)
                              robot.setList(l);
                          }//end if
                      }catch(Exception e){
                        e.printStackTrace();
                      }//end try

                  }catch(JSONException e){
                    e.printStackTrace();
                  }
                Message msg=handler.obtainMessage();
                msg.what=success;
                msg.obj=robot;
                handler.sendMessage(msg);
                /*   if(listener!=null){
                 listener.done(robot);
                 }*/

              }
          }
        ).start();
        //return robot;
      }    

  }
