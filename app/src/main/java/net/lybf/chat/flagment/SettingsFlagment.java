package net.lybf.chat.flagment;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Message;
import java.util.Map;
import android.content.res.ObbInfo;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.app.Dialog;
import java.text.DecimalFormat;
import android.support.design.widget.Snackbar;
import android.widget.Toast;
import org.json.JSONException;
import net.lybf.chat.flagment.SettingsFlagment.ThemeChange;
import net.lybf.chat.MainApplication;
import net.lybf.chat.ui.SettingsActivity;

public class SettingsFlagment extends PreferenceFragment
  {

    //设置类/配置类
    private settings set;

    //夜间主题
    private CheckBoxPreference checkbox_darktheme;

    private SettingsFlagment.ThemeChange listener;

    private MainApplication app;
    @Override
    public void onCreate(Bundle savedInstanceState){
          app=new MainApplication();
        this.ctx=SettingsActivity.ctx;
        set=app.set;
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        init();
      }

    private Context ctx;
    public SettingsFlagment(){
        app=new MainApplication();
        this.ctx=SettingsActivity.ctx;
        set=app.set;
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
                  }catch(JSONException e){}
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
        if(g.length>=1)
          end=g[g.length-1];
        for(File h:g){
            copyFile(h,outPath);
          }
      }


    private File end;

    private void copyFile(File inpath,File outpath){   
        try{
            String strname=inpath.getName();
            File nowfile=new File(outpath.getPath()+"/"+strname);
            if(inpath.isFile()){                      //判断是否是文件     
                nowfile.createNewFile();
                InputStream in=new FileInputStream(inpath);
                OutputStream out=new FileOutputStream(nowfile);
                byte[] b=new byte[1024*1024*10];
                int j;
                while((j=in.read(b))!=-1)
                  out.write(b,0,j);
              }else if(inpath.isDirectory()){         //否则如果它是一个目录
                nowfile.mkdir();
                File files[] = inpath.listFiles();  //声明目录下所有的文件 files[];
                for(int i=0;i<files.length;i++){        //遍历目录下所有的文件
                    copyFile(files[i],nowfile);       //把每个文件 用这个方法进行迭代
                  } 
              } 
            if(inpath==end){
                Toast.makeText(ctx,"导入/出数据完成",Toast.LENGTH_SHORT)
                .show();
              }
          }catch(Exception e){
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
