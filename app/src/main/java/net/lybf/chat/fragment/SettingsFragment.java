package net.lybf.chat.fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.ui.SettingsActivity;
import org.json.JSONException;

public class SettingsFragment extends PreferenceFragment
  {

    //设置类/配置类
    private settings set;

    //夜间主题
    private CheckBoxPreference checkbox_darktheme;

    private SettingsFragment.ThemeChange listener;

    private MainApplication app;
    @Override
    public void onCreate(Bundle savedInstanceState){
        app=new MainApplication();
        this.ctx=SettingsActivity.ctx;
        set=app.getSettings();
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        init();
      }

    private Context ctx;
    public SettingsFragment(){
        app=new MainApplication();
        this.ctx=SettingsActivity.ctx;
        set=app.getSettings();
        //init();
      }

    public void setContext(Context ctx){
        this.ctx=ctx;
      }
    //初始化
    public void init(){
        PreferenceManager p=this.getPreferenceManager();

        //随机登录背景
        CheckBoxPreference RBG=getCheckBox("CheckBox_RandomBackground");
        if(RBG.isChecked()!=set.getRandomBackground()){
            RBG.setChecked(set.getRandomBackground());
          }
        RBG.setChecked(set.getRandomBackground());
        RBG.setOnPreferenceClickListener(
        new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference p1){
                try{
                    set.setRandomBackground(((CheckBoxPreference)p1).isChecked());
                  }catch(JSONException e){}   
                return false;
              }
          }
        );


        final EditTextPreference output=getEditText("EditText_outputpath"),
        input=(EditTextPreference)findPreference("EditText_inputpath");

        PreferenceScreen out=getScreen("outputdata"),
        in=(PreferenceScreen)findPreference("inputdata");

        out.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference p1){
                new AlertDialog.Builder(ctx)
                .setTitle("导出数据")
                .setMessage("确定要导出数据到:"+output.getText()+"?")
                .setPositiveButton("是的",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface p1,int p2){
                        File f=new File(output.getText());
                        if(!f.exists())
                          f.mkdirs();
                        copy(new File("/data/data/"+ctx.getPackageName()),f);
                      }

                  }
                )
                .setNegativeButton("不了",null)
                .show();
                return false;
              }


          }
        );
        in.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference p1){

                new AlertDialog.Builder(ctx)
                .setTitle("导入数据")
                .setMessage("确定要导入数据到:"+output.getText()+"?这可能会导致程序异常")
                .setPositiveButton("是的",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface p1,int p2){
                        File f=new File(input.getText());
                        copy(f,new File("/data/data/"+ctx.getPackageName()));
                      }
                  }
                )
                .setNegativeButton("不了",null)
                .show();
                return false;
              }
          }
        );


        checkbox_darktheme=getCheckBox("CheckBox_DarkTheme");

        checkbox_darktheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

            @Override
            public boolean onPreferenceClick(Preference p1){
                try{
                    set.setDarkTheme(checkbox_darktheme.isChecked());
                    if(listener!=null)
                      listener.change(set.isDark());
                  }catch(JSONException e){
                    new Utils().print(this.getClass(),e);
                  }
                return false;
              } 
          });

      }


    public void setThemeChangeListener(ThemeChange listener){
        this.listener=listener;
      }
      
      
    public interface ThemeChange
      {
        public void change(boolean isDark);
      }

    public CheckBoxPreference getCheckBox(String key){
        return (CheckBoxPreference)findPreference(key);
      }

    public EditTextPreference getEditText(String key){
        return (EditTextPreference)findPreference(key);
      }

    public PreferenceScreen getScreen(String key){
        return (PreferenceScreen)findPreference(key);
      }

    public CheckBoxPreference getCheckBoxDarkTheme(){
        return checkbox_darktheme;
      }


    public void copy(File inpath,File outPath){
        File[] g=inpath.listFiles();
        for(File h:g){
            copyFile(h,outPath);
          }
        Toast.makeText(ctx,"导入/出数据完成",Toast.LENGTH_SHORT)
        .show();

      }



    
    private void copyFile(File inpath,File outpath){   
        try{
            String strname=inpath.getName();
            File nowfile=new File(outpath.getPath()+"/"+strname);
            if(inpath.isFile()){                         
                nowfile.createNewFile();
                InputStream in=new FileInputStream(inpath);
                OutputStream out=new FileOutputStream(nowfile);
                byte[] b=new byte[1024*1024*10];
                int j;
                while((j=in.read(b))!=-1)
                  out.write(b,0,j);
              }else if(inpath.isDirectory()){   
                String[] skip={
                  "app_b_sta",
                  "app_hola_q",
                  "app_img_dat",
                  "app_textures",
                  "app_webview",
                  "code_cache",
                  "lib"
                  };
                for(int i=0;i<skip.length;i++){
                    //if(inpath.getName().equals(skip[i]))
                      //return;
                  }
                nowfile.mkdir();
                File files[] = inpath.listFiles(); 
                for(int i=0;i<files.length;i++){        
                    copyFile(files[i],nowfile);      
                  } 
              } 

          }catch(Exception e){
            new Utils().print(this.getClass(),e);
          }
      }


    public interface Copy
      {
        public void count(List<File> files);
      }
    private Thread copy=new Thread(){
        public ArrayList list=new ArrayList();
        @Override
        public void run(){
          }

      };


    private void print(Object o){
        System.out.println(o);
      }
  }
