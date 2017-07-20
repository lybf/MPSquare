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
public class MPSActivity extends AppCompatActivity
  {
    private static MainApplication application;

    private static settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        application=(MainApplication)getApplication();
        if(application==null){
            print(this.getClass(),"Application cast error");
            application=new MainApplication();
          }
        settings=application.getSettings();
        super.onCreate(savedInstanceState);
      }


    @Override
    protected void onDestroy(){
        settings.save();
        super.onDestroy();
      }



    public void hideActionBar(boolean bool){
        if(bool)
          getActionBar().hide();
        else
          getActionBar().show();
      }

    public MainApplication getMainApplication(){
        return this.application;
      }


    protected settings getSettings(){
        return this.settings;
      }

  }
