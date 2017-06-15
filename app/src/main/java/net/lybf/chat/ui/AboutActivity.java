package net.lybf.chat.ui;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.UpdateLog;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.system.update;
import net.lybf.chat.util.Network;

public class AboutActivity extends AppCompatActivity
  {

    private Context ctx;
    private Toolbar bar;

    private TextView Name;

    private TextView Describe;

    private Network net;

    private PackageManager pm ;
    
    private PackageInfo pi ;

    private Button CheckUpdate;

    private Button Updatelog;

    private settings set;

    private MainApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        net=new Network(this);
        ctx=this;
        app=new MainApplication();
        set=app.getSettings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_about);
        
        pm=ctx.getPackageManager();
        try{
            pi=pm.getPackageInfo(ctx.getPackageName(),0);
          }catch(Exception e){
            print(e);
          }  

        bar=(Toolbar)findViewById(R.id.about_toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Name=(TextView)findViewById(R.id.about_name);
        Describe=(TextView)findViewById(R.id.about_describe);

        CheckUpdate=(Button)findViewById(R.id.about_checkupdate);
        CheckUpdate.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                update();
              }
          });

        Updatelog=(Button)findViewById(R.id.about_updatelog);
        Updatelog.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                startActivity(new Intent(ctx,UpdateLogActivity.class));
              }
          });

        try{
            Name.setText(Name.getText().toString()+pi.versionName.toString()+"("+pi.versionCode+")");
          }catch(Exception e){
            print(e);
          }
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


    private void update(){
        if(net.isConnectedOrConnecting()){
            print("\n检测更新中\n");
            BmobQuery<UpdateLog> q = new BmobQuery<UpdateLog>();
            q.order("-createdAt");
            //  q.setLimit(50);   
            q.findObjects(new FindListener<UpdateLog>() {
                @Override
                public void done(List<UpdateLog> object,BmobException er){
                    if(er==null){
                        print("\n检测更新成功\n");
                        //if(object.size()<
                        UpdateLog l=object.get(0);
                        if(l!=null){
                            update up=new update();
                            up.setApkUrl(l.getApkFile());
                            up.setContent(l.getMessage());
                            up.setCreatedAt(l.getCreatedAt());
                            up.setLevel(l.getLevel());
                            up.setShowType(l.getShowType().intValue());
                            up.setUpdatedAt(l.getUpdatedAt());
                            up.setVersionCode(l.getVersionCode());
                            up.setVersionName(l.getVersionName());
                            up.setTitle(l.getTile());

                            try{

                                int i=pi.versionCode;

                                String ii=pi.versionName;
                                if(i-up.getVersionCode().intValue()<0){
                                    showAppUpdateMessage(up);
                                  }
                              }catch(Exception e){
                                print(e);
                              }
                          }


                      }else{

                        print(new ErrorMessage().getMessage(er.getErrorCode()));
                      }
                  }
              }
            );
          }else{

          }
      }


    private void showAppUpdateMessage(update u){
        final String str=("版本号:"+u.getVersionCode()
        +"\n版本名:"+u.getVersionName()
        +"\n更新时间:"+u.getUpdatedAt()
        +"\n创建时间:"+u.getCreatedAt())+"\n更新内容:\n";

        final String content=u.getContent();
        final String download="\n\n下载地址:"+u.getApkUrl();

        TextView tv=new TextView(ctx);
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setSaveEnabled(true);
        tv.setSelected(true);
        if(u.getShowType().intValue()==update.TYPE_HTML.intValue()){
            tv.setText(Html.fromHtml(str.replaceAll("\n","<br></br>")+content+download.replaceAll("\n","<br></br>")));
          }else{
            tv.setText(downloadMessage=str+content+download);
          }
        new AlertDialog.Builder(ctx)
        .setTitle(u.getTitle())
        .setView(tv)
        .setPositiveButton("下载",null)
        .setNegativeButton("复制",new DownloadApp()
        )
        .setNeutralButton("关闭",null)
        .show();

      }

    private String downloadMessage;
    private class DownloadApp implements DialogInterface.OnClickListener
      {
        @Override
        public void onClick(DialogInterface p1,int p2){
            ClipboardManager clip=(ClipboardManager)getSystemService(ctx.CLIPBOARD_SERVICE);
            clip.setText(downloadMessage);
          }
      }


    private void print(Object p0){
        new Utils().print(this.getClass(),p0);
      }
  }
