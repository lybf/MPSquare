package net.lybf.chat.ui;
import net.lybf.chat.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;

public class ErrorActivity extends AppCompatActivity
  {

	//This is error message EditText
    private EditText dis;
	//错误消息 Error message
    private TextView message;
	//获取设备信息，重启应用
    private CheckBox get,reApp;
	//配置信息类
    private settings set;
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        print("接收到错误信息");
        super.onCreate(savedInstanceState);
        set=new settings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
			setTheme(R.style.LightTheme);
		  }

        setContentView(R.layout.activity_error);
        Toolbar t=(Toolbar)findViewById(R.id.toolbar_error);
        t.setTitle(R.string.Error);
        setSupportActionBar(t);
        message=(TextView)findViewById(R.id.error_message);
        Intent i=getIntent();
        Bundle b = null;
        try{
            b=i.getBundleExtra("Error");
          }catch(Exception e){
            print(e);
          }
        if(b!=null)
          if(b.getString("error")!=null){
			  print(b.getString("error").toString());
              //long date=System.currentTimeMillis();
              String str = null;
              try{
                  Date d=new Date();
                  SimpleDateFormat da=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
                  str=da.format(d);
                }catch(Exception e){
                  print(e);
                }
              Utils u=new Utils();
              message.setText("设备信息:\n"+u.getDeviceInfo()+"\n\n-------"+str+"-------\n\n"+b.getString("error"));
              print(u.getDeviceInfo());
            }
      }
	  
	  public void print(Object o){
		new Utils().print(this.getClass(),o);
	  }



  }
