package net.lybf.chat.ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.gc.materialdesign.views.ButtonRectangle;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.bmob.CrashMessage;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.Logcat;

public class ErrorActivity extends MPSActivity
  {

    private EditText setps;
    //错误消息 Error message
    private TextView message;
    //获取设备信息，重启应用
    private CheckBox collectInfo;

    private CheckBox reStartApp;
    //配置信息类
    private settings set;

    private MainApplication app;

    private Logcat logcat;

    private ButtonRectangle send;

    private String crashMsg;

    private static boolean sent=false;

    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Utils.print("接收到错误信息");
        this.ctx=this;
        super.onCreate(savedInstanceState);
        app=getMainApplication();
        set=app.getSettings();
        logcat=getLogcat();
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
            if(b!=null)
              if((crashMsg=b.getString("error"))!=null){
                  logcat.println(this.getClass(),"接收到错误，错误信息:"+crashMsg);
                  String str = null;
                  Date d=new Date();
                  SimpleDateFormat da=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
                  str=da.format(d);
                  Utils u=new Utils();
                  message.setText("设备信息:\n"+u.getDeviceInfo(this)+"\n\n-------"+str+"-------\n\n"+b.getString("error"));
                  Utils.print(u.getDeviceInfo(this));
                }
          }catch(Exception e){
            Utils.print(e);
          }

        reStartApp=(CheckBox)findViewById(R.id.error_reStartApp);
        setps=(EditText)findViewById(R.id.error_setps);
        send=(ButtonRectangle)findViewById(R.id.error_send);
        send.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                CrashMessage crash=new CrashMessage();
                /*BmobInstallation install=BmobInstallation.
                 getCurrentInstallation();*/
                String id=new BmobInstallation().getInstallationId();
                MyUser user=BmobUser.getCurrentUser(MyUser.class);
                crash.setUser(user);
                crash.setDeviceId(null);//install.getObjectId());
                crash.setInstalltionId(id);
                crash.setDeviceInfo(new Utils().getDeviceInfo(ErrorActivity.this));
                crash.setCrashMessage(crashMsg);
                crash.setSetps(setps.getText().toString());
                if(sent){
                    Toast.makeText(ctx,"不可重复发送哦",Toast.LENGTH_LONG).show();
                    if(sent&&reStartApp.isChecked()){
                        Toast.makeText(ctx,"2秒后重启应用",Toast.LENGTH_SHORT).show();
                        new Thread(new Thread(){
                            public void run(){
                                try{
                                    this.sleep(2000);
                                  }catch(InterruptedException e){
                                    e.printStackTrace();
                                  }
                                Utils.forceKillAllProcessorServices(ErrorActivity.this);
                                Intent in=new Intent(ErrorActivity.this,WelcomeActivity.class);
                                in.addFlags(in.FLAG_ACTIVITY_NEW_TASK);
                                ErrorActivity.this.startActivity(in);
                              }
                          });
                      }
                  }else{
                    crash.save(new SaveListener<String>(){
                        @Override
                        public void done(String string,BmobException e){
                            if(e==null){
                                Toast.makeText(ctx,"发送成功",Toast.LENGTH_SHORT).show();       
                                sent=true;    
                                if(sent&&reStartApp.isChecked()){
                                    Toast.makeText(ctx,"2秒后重启应用",Toast.LENGTH_SHORT)
                                    .show();
                                    new Thread(new Thread(){
                                        public void run(){
                                            try{
                                                this.sleep(2000);
                                              }catch(InterruptedException e){
                                                e.printStackTrace();
                                              }
                                            Utils.forceKillAllProcessorServices(ErrorActivity.this);
                                            Intent in=new Intent(ErrorActivity.this,WelcomeActivity.class);
                                            in.addFlags(in.FLAG_ACTIVITY_NEW_TASK);
                                            ErrorActivity.this.startActivity(in);
                                          }
                                      });

                                  }
                              }else{
                                Utils.print(this.getClass(),e);
                                Toast.makeText(ctx,"发送错误信息失败，请重试",Toast.LENGTH_SHORT).show();
                              }
                          }       
                      });
                  }//if
              }
          });


      }




  }
