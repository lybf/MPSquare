package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.R;
import android.widget.ImageButton;
import java.util.List;
import android.view.View;
import java.util.ArrayList;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import com.gc.materialdesign.views.ButtonRectangle;
import android.view.View.OnClickListener;
import android.content.Intent;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import net.lybf.chat.system.settings;
public class GuideActivity extends AppCompatActivity
  {

    private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        set=new settings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }
        //	setContentView(R.layout.activity_first_guide);
      }


  }
