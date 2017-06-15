package net.lybf.chat.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import java.util.HashMap;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.flagment.SettingsFlagment;
import net.lybf.chat.system.ActivityResultCode;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import org.json.JSONException;

public class SettingsActivity extends AppCompatActivity
  {

//    settings set;

    public static Context ctx;


    private MainApplication app;

    private settings set;

    private SettingsFlagment sf;

    private HashMap<String,Object> hash;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ctx=this;
        app=(MainApplication) getApplication();
        set=app.getSettings();
        // onRetainCustomNonConfigurationInstance();
        hash=(HashMap<String,Object>) getLastCustomNonConfigurationInstance();

        if(hash==null)
          hash=(HashMap<String, Object>) onRetainCustomNonConfigurationInstance();
        initView();

      }

    @Override
    protected void onPause(){
        super.onPause();
      }

    @Override
    protected void onDestroy(){
        if(hash.get("theme")!=set.isDark())
          this.setResult(ActivityResultCode.SETTINGS_CHANGE);
        super.onDestroy();
      }




    @Override
    public Object onRetainCustomNonConfigurationInstance(){
        super.onRetainCustomNonConfigurationInstance();
        HashMap<String,Object> hm=new HashMap<String,Object>();
        hm.put("theme",set.isDark());

        System.out.println("HashMap init");
        return hm;
      }





    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
      }


    public void initView(){
        //  set=app.getSettings();
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
                    try{
                        set.setDarkTheme(bool);
                        //   set.updateTheme();
                        set.save(new settings.SaveListener(){
                            @Override
                            public void done(Exception e){
                                if(e==null)
                                  print("Success");
                                else
                                  new Utils().print(this.getClass(),e);
                              }    
                          });

                      }catch(JSONException e){
                        new Utils().print(this.getClass(),e);
                      }
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
        if(hash.get("theme")!=set.isDark())
          setResult(ActivityResultCode.SETTINGS_CHANGE);
        this.finish();
        super.onBackPressed();
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
              if(hash.get("theme")!=set.isDark())
                this.setResult(ActivityResultCode.SETTINGS_CHANGE);
              finish();
              break;
          }
        return super.onOptionsItemSelected(item);
      }

    private void print(Object e){
        Utils.print(this.getClass(),e);
        //      new Utils().print(this.getClass(),e);
      }


  }
