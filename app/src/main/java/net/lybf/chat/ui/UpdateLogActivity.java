package net.lybf.chat.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.Network;

public class UpdateLogActivity extends MPSActivity
  {

    public static Context ctx;

    private Toolbar bar;

    private Network net;

    private settings set;

    private MainApplication app;

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ctx=this;
        app=getMainApplication();
        set=app.getSettings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_updatelog);
        net=new Network(this);

        bar=(Toolbar)findViewById(R.id.updatelog_toolbar);
        setSupportActionBar(bar);
        try{
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(Exception e){
          Utils.print(this.getClass(),e);
        }
        initView();
      }



    private void initView(){
        webview=(WebView)findViewById(R.id.updatelog_changelog);
        //webview.setBackgroundColor(Color.TRANSPARENT); 
        webview.loadUrl("file:///android_asset/changelog.html");
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
              }
          });
      }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
              this.finish();
              break;
          }
        return true;
      }
    private void print(Object e){
        Utils.print(this.getClass(),e);
      }

  }
