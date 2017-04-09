package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
import net.lybf.chat.MainApplication;

public class PhotosPickerActivity extends AppCompatActivity
  {

    private settings set;

    private MainApplication app;
      @Override
      protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
          app=new MainApplication();
          set=app.set;
          if(set.isDark()){
              setTheme(R.style.DarkTheme);
            }
          
         }
}
