package net.lybf.chat.ui;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import cn.bmob.v3.BmobUser;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.settings;

public class FeedbackActivity extends MPSActivity
  {
    //CreatedAt 2017/4/22 13:33
    private MyUser user;

    private MainApplication app;

    private settings set;

    private Context ctx;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        app=getMainApplication();
        set=app.getSettings();

        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_writepost);
        ctx=this;
        init();
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

    private void init(){
      /*  bar=(Toolbar)findViewById(R.id.toolbar_sendtie);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        */
 
        user=BmobUser.getCurrentUser(MyUser.class);
      }
}
