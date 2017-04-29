package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.widget.FrameLayout;
import android.preference.PreferenceFragment;
import net.lybf.chat.flagment.SettingsFlagment;
import android.view.MenuItem;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import org.json.JSONException;
import android.app.FragmentManager;
import net.lybf.chat.MainApplication;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.ActivityResultCode;
import android.content.Context;

public class SettingsActivity extends AppCompatActivity
  {

//    settings set;

    public static Context ctx;
    private Bundle bundle;

    private MainApplication app;

    private settings set;

    private SettingsFlagment sf;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(bundle=savedInstanceState);
        ctx=this;
        app=new MainApplication();
        initView();
      }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
      }


    public void initView(){
        set=app.getSettings();
		if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
	    setContentView(R.layout.activity_settings);
        init();
      }

    private Toolbar bar;
    private FrameLayout fm;
    public void init(){
        bar=(Toolbar)findViewById(R.id.toolbar_settings);
        setSupportActionBar(bar);
        ActionBar ab= getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        try{
            fm=(FrameLayout)findViewById(R.id.settings_framelayout);
            sf=new SettingsFlagment();
            //getFragmentManager().beginTransaction().hide(sf);
            getFragmentManager().beginTransaction().replace(R.id.settings_framelayout,sf).commit();
            sf.setContext(this);
            sf.setThemeChangeListener(new SettingsFlagment.ThemeChange(){
                public void change(boolean bool){
					recreate();
                    SettingsActivity.this.setResult(ActivityResultCode.SETTINGS_CHANGE);
                  }
              });
          }catch(Exception e){
            print(e);
          }
      }

    @Override
    public void onBackPressed(){
		if(app.LastTheme!=set.isDark()){
		  setResult(ActivityResultCode.SETTINGS_CHANGE);
		  app.LastTheme=set.isDark();
		  }
        this.finish();
        super.onBackPressed();
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
			  if(app.LastTheme!=set.isDark()){
				  this.setResult(ActivityResultCode.SETTINGS_CHANGE);
				  app.LastTheme=set.isDark();
				}  
			  finish();
              break;
          }
        return super.onOptionsItemSelected(item);
      }

    private void print(Object e){
        new Utils().print(this.getClass(),e);
      }
  }
