package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;

public class PhotosPickerActivity extends AppCompatActivity
  {

    private settings set;
      @Override
      protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
          set=new settings();
          if(set.isDark()){
              setTheme(R.style.DarkTheme);
            }
          
         }
}
