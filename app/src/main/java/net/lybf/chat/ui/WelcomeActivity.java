package net.lybf.chat.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import net.lybf.chat.system.settings;

public class WelcomeActivity extends AppCompatActivity
  {

    private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        /* set=new settings(this);
         if(set.isDark()){
         setTheme(R.style.AppDarkTheme);
         }
         */
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);
        this.finish();

      }
  }
