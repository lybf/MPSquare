package net.lybf.chat.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import net.lybf.chat.MainApplication;
import net.lybf.chat.maps.Robot;
import net.lybf.chat.maps.RobotList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Network
  {
	private Context ctx
    ;
	public Network(Context c){
		ctx=c;
	  }

    public Network(){
        ctx=new MainApplication().getContext();
      }


	public boolean isConnected(){
		ConnectivityManager cm=(ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		if(info!=null)
		  return info.isConnected();
		else
		  return false;
	  }


	public boolean isConnectedOrConnecting(){
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo!=null&&netInfo.isConnectedOrConnecting())
		  return true;
		else
		  return false;
	  }
      
    public interface TuLingRobotListener
      {
        void done(Robot robot);
      }
      
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
