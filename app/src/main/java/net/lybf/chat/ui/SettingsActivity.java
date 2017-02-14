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

public class SettingsActivity extends AppCompatActivity
  {

    settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		set=new settings();
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
        fm=(FrameLayout)findViewById(R.id.settings_framelayout);
        ab. setDisplayHomeAsUpEnabled(true);
        SettingsFlagment sf=  new SettingsFlagment(this);
        getFragmentManager().beginTransaction().replace(R.id.settings_framelayout,sf).commit();
      }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
              finish();
              break;
          }
        return super.onOptionsItemSelected(item);
      }

  }
