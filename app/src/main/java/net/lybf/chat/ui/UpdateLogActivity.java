package net.lybf.chat.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.adapter.UpdateLogAdapter;
import net.lybf.chat.bmob.UpdateLog;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.system.update;
import net.lybf.chat.util.Network;

public class UpdateLogActivity extends AppCompatActivity
  {

    public static Context ctx;

    private Toolbar bar;
    
    private net.lybf.chat.util.UpdateLog log;

    private RecyclerView mListView;

    private UpdateLogAdapter adapter;

    private Network net;

    private settings set;

    private MainApplication app;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ctx=this;
        app=new MainApplication();
        set=app.getSettings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_updatelog);
        log=new net.lybf.chat.util.UpdateLog();
        net=new Network(this);

        bar=(Toolbar)findViewById(R.id.updatelog_toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
      }

    private void initData(){
        if(net.isConnectedOrConnecting()){
            BmobQuery<UpdateLog> query=new BmobQuery<UpdateLog>();
            query.order("-createdAt");
            query.setLimit(1000);
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
            query.findObjects(new FindListener<UpdateLog>(){
                @Override
                public void done(List<UpdateLog> p1,BmobException e){
                    if(e==null){
                        for(UpdateLog l:p1){
                            update up=new update();
                            up.setObjecId(l.getObjectId())
                            .setApkUrl(l.getApkFile())
                            .setContent(l.getMessage())
                            .setCreatedAt(l.getCreatedAt())
                            .setLevel(l.getLevel())
                            .setShowType(l.getShowType().intValue())
                            .setUpdatedAt(l.getUpdatedAt())
                            .setVersionCode(l.getVersionCode())
                            .setVersionName(l.getVersionName())
                            .setTitle(l.getTile());
                            log.addItem(up);
                            adapter.addItem(up);
                          }
                      }else{
                        print(e);
                        if(log.count()>0){
                            List<update> l=log.getAllUpdateLog();
                            for(int i=0;i<l.size();i++){
                                adapter.addItem(l.get(i));
                              }
                          }
                      }
                  }
              });

          }else{
            try{
                if(log.count()>0){
                    List<update> l=log.getAllUpdateLog();
                    for(int i=0;i<l.size();i++){
                        adapter.addItem(l.get(i));
                      }
                  }
              }catch(Exception e){
                print(e);
              }
          }


      }


    private void initView(){
        mListView=(RecyclerView)findViewById(R.id.updatelog_listview);
        mListView.setAdapter((adapter=new UpdateLogAdapter(this)));
        LinearLayoutManager Manager = new LinearLayoutManager(this);         
        Manager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(Manager); 
        mListView.setItemAnimator(new DefaultItemAnimator());

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
        new Utils().print(this.getClass(),e);
      }

  }
