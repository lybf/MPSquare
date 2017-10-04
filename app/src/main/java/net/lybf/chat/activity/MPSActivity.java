package net.lybf.chat.activity;
import android.app.ActionBar;
import android.os.Bundle;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.Logcat;

import static net.lybf.chat.system.Utils.print;
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
