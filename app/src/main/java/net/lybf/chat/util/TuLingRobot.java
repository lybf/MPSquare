package net.lybf.chat.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.HttpClientConnection;
import net.lybf.chat.maps.Robot;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import net.lybf.chat.maps.RobotList;
import java.util.ArrayList;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.BmobUser;
import android.os.Build;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import net.lybf.chat.MainApplication;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.provider.Telephony;
import android.telephony.CellLocation;     
import android.telephony.PhoneStateListener;  
public class TuLingRobot

  {
    //CreatedAt 2017/4/24 16:00
    private MyUser user;
    private String key="b6b3d000e00d4580a0b0eaf3a1236bd9";
    private MainApplication app;
    private Context ctx;
    public TuLingRobot(){
        user=BmobUser.getCurrentUser(MyUser.class);
        app=new MainApplication();
        ctx=app.getContext();
      }
    public Robot send(String message){
        String url="http://www.tuling123.com/openapi/api";
        JSONObject obj=new JSONObject();
        try{
            obj.put("key",key);
            obj.put("info",message);
            String id;
            if(user!=null)
            //设置用户id
              id=user.getObjectId();
            else
            //IMEI
              id=((TelephonyManager)ctx. getSystemService(ctx.TELEPHONY_SERVICE))
              .getDeviceId();
            obj.put("userid",id);
            System.out.println(Build.ID);
          }catch(JSONException e){
            e.printStackTrace();
          }
        return sendPost(obj.toString(),url);
      }

    //发送Post
    public static Robot sendPost(String param,String url){
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

        JSONObject json;
        Robot robot = null;
        try{
            if(!result.toString().equals(""))
              json=new JSONObject(result.toString());
            else{
                json=new JSONObject("{\"code\":404,\"text\":\"未知错误\"}");
              }
            robot=new Robot();
            robot.setFlag(robot.FLAG_ROBOT);
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

        return robot;
      }    

  }
