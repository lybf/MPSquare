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
import net.lybf.chat.util.Logcat;
public class MPSActivity extends AppCompatActivity
  {
    public static final String TAG="MPSActivity";
    
    private static MainApplication application;

    private static settings settings;

    private static Logcat logcat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
      }

    private void init(){
        application=(MainApplication)getApplication();
        if(application==null){
            print(this.getClass(),"Application cast error");
            application=new MainApplication();
          }
        settings=application.getSettings();
        logcat=application.getLogcat();
      }


    @Override
    protected void onDestroy(){
       // settings.save();
        super.onDestroy();
      }



    public void hideActionBar(boolean bool){
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
