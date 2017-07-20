package net.lybf.chat.ui;
import android.os.Bundle;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
public class GuideActivity extends MPSActivity
  {

    private settings set;

    private MainApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app=getMainApplication();
        set=app.getSettings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }
        //    setContentView(R.layout.activity_first_guide);
      }


  }
