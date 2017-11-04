package net.lybf.chat.activity;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.Logcat;

import static net.lybf.chat.system.Utils.print;
import android.graphics.Color;
public class MPSActivity extends SwipeBackActivity/* AppCompatActivity*/
  {
    //Created by lybf on 2017/7/23 13:34
    public static final String TAG="MPSActivity";

    //单例
    private static MainApplication application;

    //设置
    private static settings settings;

    //日志记录
    private static Logcat logcat;

    private static boolean autoDark=false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
      }

    private void init(){
        application=(MainApplication)getApplication();
        if(application==null){
            print(this.getClass(),"Application cast error");
            application=MainApplication.getInstance();
          }
        settings=application.getSettings();
        //   application.init();
        logcat=application.getLogcat();
      }

    protected void onStop(){
        application=null;
        settings=null;
        logcat=null;
        super.onStop();
      }

    //刷新数据
    public void refreshData(){
        init();
      }

    public void fitsSystemWindow(DrawerLayout view,boolean bool){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar
                view.setFitsSystemWindows(bool);        
                //将主页面顶部延伸至status bar;
                view.setClipToPadding(false);
              }
          }
      }
      
    public void fitsSystemWindow(DrawerLayout view,boolean isTranslucent,boolean bool){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();  
            if(isTranslucent)
              localLayoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|localLayoutParams.flags);
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){   
                //将侧边栏顶部延伸至status bar
                view.setFitsSystemWindows(bool);        
                //将主页面顶部延伸至status bar;
                view.setClipToPadding(false);
              } 
          }
      }

    public void fitsSystemWindow(DrawerLayout view,int color,boolean bool){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            setStatusBarColor(color);    
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){   
                //将侧边栏顶部延伸至status bar
                view.setFitsSystemWindows(bool);        
                //将主页面顶部延伸至status bar;
                view.setClipToPadding(false);
              }
          }
      }

    protected void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().setStatusBarColor(color);
          }
      }

    protected void autoDark(boolean bool){
        this.autoDark=bool;
        if(autoDark){
            if(settings.isDark()){
                setTheme(R.style.DarkTheme);
              }else{
                setTheme(R.style.LightTheme);
              }
          }
      }

    protected void hideActionBar(boolean bool){
        ActionBar bar=getActionBar();
        if(bar==null)
          return;
        if(bool)
          bar.hide();
        else
          bar.show();
      }

    protected MainApplication getMainApplication(){
        if(application==null){
            init();
          }
        return this.application;
      }

    protected Logcat getLogcat(){
        if(logcat==null){
            init();
          }
        return this.logcat;
      }

    protected settings getSettings(){
        if(settings==null){
            init();
          }
        return this.settings;
      }

  }
