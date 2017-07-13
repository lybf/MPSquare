package net.lybf.chat.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.system.settings;
import net.lybf.chat.MainApplication;
import org.json.JSONException;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.content.Intent;

public class MPSActivity extends AppCompatActivity
  {
    private MainApplication application;

    private settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        application=new MainApplication();
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
    
    public MainApplication  getMainApplication(){
        return this.application;
      }


    protected settings getSettings(){
        return this.settings;
      }

    protected void setDarkTheme(boolean bool){
        try{
            settings.setDarkTheme(bool);
          }catch(JSONException e){
            e.printStackTrace();
          }
      }


    protected void setRandomBackground(boolean bool){
        try{
            settings.setRandomBackground(bool);
          }catch(JSONException e){
            e.printStackTrace();
          }
      }

  }
