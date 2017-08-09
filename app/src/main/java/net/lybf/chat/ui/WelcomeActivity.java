package net.lybf.chat.ui;
import android.content.Intent;
import android.os.Bundle;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.system.settings;
import android.os.Process;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.BmobUser;
import net.lybf.chat.MainApplication;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.exception.BmobException;

public class WelcomeActivity extends MPSActivity /*AppCompatActivity*/
  {

    private MainApplication app;

    // private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app=getMainApplication();
        MyUser user=app.getUser();
        BmobInstallation in=app.getDevice();
        if(user!=null){
            user.setDevice(in);
            user.update(new UpdateListener(){
                @Override
                public void done(BmobException p1){
                    if(p1==null)
                      getLogcat().println(this,"Update user info success.");
                    else
                      getLogcat().println(this,"Update user info failed. info:"+p1.getMessage());
                  }       
              });
          }
         
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        getLogcat().println("startApp-WelcomeActivity");
        this.finish();
        getLogcat().println("closeActivity-WelcomeActivity");
      }
  }
