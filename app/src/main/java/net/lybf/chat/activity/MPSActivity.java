package net.lybf.chat.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.system.settings;
import net.lybf.chat.MainApplication;
import org.json.JSONException;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import static net.lybf.chat.system.Utils.*;
import net.lybf.chat.utils.Logcat;
import net.lybf.chat.R;
import android.app.Fragment;
public class MPSActivity extends AppCompatActivity
  {
    //Created by lybf on 2017/7/23 13:34
    public static final String TAG="MPSActivity";
    
    private static MainApplication application;

    private static settings settings;

    private static Logcat logcat;

    private static boolean autoDark=false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
      }

    private void init(){
        application=(MainApplication)getApplication();
        if(application==null){
            print(this.getClass(),"Application cast error");
            application=MainApplication.getInstance();
          }
        settings=application.getSettings();
     //   application.init();
        logcat=application.getLogcat();
      }


    @Override
    protected void onDestroy(){
       // settings.save();
        super.onDestroy();
      }

    @Override
    public void startActivity(Intent intent){
       // logcat.println(
        super.startActivity(intent);
      }

    @Override
    public void startActivity(Intent intent,Bundle options){
        // TODO: Implement this method
        super.startActivity(intent,options);
      }
      

    @Override
    public void startActivities(Intent[] intents){
        super.startActivities(intents);
      }

    @Override
    public void startActivityForResult(Intent intent,int requestCode,Bundle options){
        super.startActivityForResult(intent,requestCode,options);
      }


 
    public void RefreshData(){
      init();
    }
    
    
    protected void AutoDark(boolean bool){
      this.autoDark=bool;
      if(autoDark){
        if(settings.isDark()){
          setTheme(R.style.DarkTheme);
        }else{
          setTheme(R.style.LightTheme);
        }
      }
    }
    
    protected void hideActionBar(boolean bool){
        if(bool)
          getActionBar().hide();
        else
          getActionBar().show();
      }

    protected MainApplication getMainApplication(){
        if(application==null){
            init();
          }
        return this.application;
      }

    protected Logcat getLogcat(){
        if(logcat==null){
            init();
          }
        return this.logcat;
      }
      
    protected settings getSettings(){
        if(settings==null){
            init();
          }
        return this.settings;
      }

  }
