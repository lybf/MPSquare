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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.widget.SearchView.OnCloseListener;
import android.view.View.OnClickListener;
import android.view.View;

public class FeedbackActivity extends MPSActivity
  {
    //CreatedAt 2017/4/22 13:33
    private MyUser user;

    private MainApplication app;

    private settings set;

    private Context ctx;

    private static final String[] string={
      "崩溃/闪退",
      "无法登录",
      "无法刷新，刷新失败",
      "无法发消息",
      "无法使用部分功能",
      "数据错乱",
      "其他"
      };

    private Toolbar bar;

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
        setContentView(R.layout.content_feedback);
        ctx=this;
        init();
      }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
              finish();
              break;

            case R.id.feedback_send:
              Toast.makeText(this,"SEND",0).show();
              break;
          }
        return super.onOptionsItemSelected(item);
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_feedback,menu);
        final MenuItem item=menu.findItem(R.id.feedback_send);
        item.getActionView().setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                onOptionsItemSelected(item);
              }
          });
        return super.onCreateOptionsMenu(menu);
      }

    private void init(){
        bar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          }
        catch(Exception e){
            e.printStackTrace();
          }
        user=super.getMainApplication().getUser();
      }
  }
