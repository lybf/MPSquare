package net.lybf.chat.ui;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.adapter.MainPagerAdaptet;
import net.lybf.chat.adapter.MainTieAdapter;
import net.lybf.chat.adapter.MainToolsAdapter;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.maps.MainTools;
import net.lybf.chat.system.ActivityResultCode;
import net.lybf.chat.system.Colors;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.ui.MainActivity;
import net.lybf.chat.ui.SettingsActivity;
import net.lybf.chat.utils.BitmapTools;
import net.lybf.chat.utils.CommentCount;
import net.lybf.chat.utils.CommonUtil;
import net.lybf.chat.utils.Logcat;
import net.lybf.chat.utils.Network;
import net.lybf.chat.utils.UserManager;
import net.lybf.chat.widget.CircleImageView;
import android.view.WindowManager;

public class MainActivity extends MPSActivity/*AppCompatActivity*/
  {


    //Tabs
    private String[] tabs=
      {
      "社区",
      "工具"
      };
    //界面
    private List<View> mContent=new ArrayList<View>();

    //社区
    private View Post;
    //主页
    private View MainPage;

    //页卡
    private ViewPager mViewPager;
    //选项卡
    private TabLayout mTabLayout;

    //Context
    private Context ctx;

    //适配器
    private MainTieAdapter MTA;

    //用户信息
    private MyUser use;

    //侧边栏头布局
    private View mHeaderLayout;

    //抽屉布局
    private DrawerLayout mDrawerLayout;

    //工具栏
    private Toolbar mToolbar;

    //侧边栏……
    private NavigationView mNavigationView;

    //下拉刷新(社区)
    private SwipeRefreshLayout refresh;

    //发帖(社区)
    private FloatingActionButton fab;

    //用户名称
    private TextView nv_name;

    //用户图片
    private CircleImageView nv_header;

    private int 加载信息条数=30;

    //网络类
    private Network net;

    //配置类
    private settings set;

    //RecyclerView
    private RecyclerView mRecyclerview;

    private MainPagerAdaptet mViewPagerAdapter;

    private static boolean FIRST_READ=true;;

    private static boolean REFRESH_HINT=true;

    private static Bundle bundle;

    private static MainApplication app;

    private static RecyclerView mTools;

    private static MainToolsAdapter ToolsAdapter;

    private static boolean themechange;

    private static Logcat logcat;

    private static UserManager userManager;
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        super.setSwipeBackEnable(false);

        Intent ip=new Intent();
        ip.setAction("net.lybf.chat.action.push");
        Bundle bun=new Bundle();
        bun.putString("title","测试");
        bun.putString("message","这是测试内容，点击后跳转到MainActivity");
        ip.putExtra("data",bun);
        //  sendBroadcast(ip);

        bundle=save;
        ctx=this;
        userManager=new UserManager(this);
        app=getMainApplication();
        set=getSettings();
        logcat=getLogcat();
        themechange=set.isDark();
        logcat.println(this,"启动MainActivity");
        logcat.println(this,"初始化控件");
        initViews();
      }


    @Override
    protected void onPause(){
        super.onPause();
      }


    @Override
    protected void onDestroy(){
        logcat.println(this,"退出应用");
        try{
            logcat.close();
          }catch(IOException e){
            e.printStackTrace();
          }
        super.onDestroy();
      }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
      }

    public void initViews(){
        if(set.isDark()){
            setTheme(R.style.DarkThemeMain);
          }else{
            setTheme(R.style.LightThemeMain);
          }
        setContentView(R.layout.activity_main);
        initView();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();    
            localLayoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|localLayoutParams.flags);
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);        
                //将主页面顶部延伸至status bar;
                mDrawerLayout.setClipToPadding(false);
              }
          }
        
      }


    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
        .setTitle("退出?")
        .setMessage("你想去外面的世界看看？")
        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1,int p2){
                MainActivity.this.finish();
              }
          })
        .setNegativeButton("不了",null)
        .setCancelable(false)
        .show();
      }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
      }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.feedback_MyDescribe:
              Utils u=new Utils();
              final String str=u.getDeviceInfo(this);
              new AlertDialog.Builder(this)
              .setTitle("设备信息")
              .setMessage(str)
              .setPositiveButton("复制",new DialogInterface.OnClickListener(){
                  @Override
                  public void onClick(DialogInterface p1,int p2){
                      Utils u=new Utils();
                      ClipboardManager clip=(ClipboardManager)getSystemService(ctx.CLIPBOARD_SERVICE);        
                      clip.setText(str);
                    }
                }
              )
              .setCancelable(false)
              .setNegativeButton("关闭",null)
              .show();            
              break;
          }
        return super.onOptionsItemSelected(item);
      }




    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // read(true);
        print("ActivityResult-requestCode:"+requestCode+"  resultCode:"+resultCode);
        if(requestCode==0){

            switch(resultCode){
                case ActivityResultCode.USER_REFRESH:
                  use=null;
                  refreshUser();
                  break;

                case ActivityResultCode.SETTINGS_CHANGE:
                  print("SettingsChange");
                  //  settings st=new settings(this);
                  recreate();
                  //initViews();
                  break;

                case ActivityResultCode.USER_LOGIN:
                  recreate();
                  break;

                case ActivityResultCode.USER_LOGOUT:
                  recreate();
                  break;

              }


          }

        super.onActivityResult(requestCode,resultCode,data);
      }












    private void refreshUser(){
        use=BmobUser.getCurrentUser(MyUser.class);
        try{
            if(use!=null){
                File file=userManager.getIconFile(use);
                if(file!=null)
                  Picasso.with(this).load(file).into(nv_header);       

                if(use.getUsername()!=null)
                  if(nv_name!=null)
                    nv_name.setText(""+use.getUsername());
              }else{
                if(nv_name!=null){
                    nv_name.setText("未登录，点击头像以登录");
                    Picasso.with(ctx).load(R.drawable.ic_account_circle).into(nv_header);
                  }    
              }
          }catch(Exception e){
            e.printStackTrace();
          }
      }









    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.main_viewpage);
        mTabLayout=(TabLayout)findViewById(R.id.main_tabs);
        Post=LayoutInflater.from(this).inflate(R.layout.content_main_posts,null);
        MainPage=LayoutInflater.from(this).inflate(R.layout.content_main_tools,null);
        mContent.add(Post);
        mContent.add(MainPage);
        mViewPagerAdapter=new MainPagerAdaptet(tabs,mContent);
        use=BmobUser.getCurrentUser(MyUser.class);
        net=new Network(this);

        try{
            mRecyclerview=(RecyclerView)Post.findViewById(R.id.main_post_listview);
            mRecyclerview.setEnabled(true);
            mRecyclerview.setFocusable(true);
            LinearLayoutManager Manager = new LinearLayoutManager(this);         
            Manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerview.setLayoutManager(Manager); 
            mRecyclerview.setAdapter((MTA=new MainTieAdapter(this)));
            mRecyclerview.setItemAnimator(new DefaultItemAnimator());

            try{
                MTA.setOnItemClickListener(new MainTieAdapter.OnItemClickListener(){
                    @Override
                    public void onClick(View view,int index){
                        Post m=MTA.getPost(index);
                        Intent i=new Intent(ctx,PostActivity.class);
                        Bundle bu=new Bundle();
                        bu.putString("帖子",m.getObjectId());
                        i.putExtra("Mydata",bu);
                        ctx.startActivity(i);
                      }
                  });
              }catch(Exception e){
                print(e);
              }

            try{
                mTools=(RecyclerView)MainPage.findViewById(R.id.main_tools_listview);
                mTools.setEnabled(true);
                mTools.setFocusable(true);
                GridLayoutManager manager=    new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
                //manager.setOrientation(LinearLayoutManager.VERTICAL);
                mTools.setLayoutManager(manager); 
                mTools.setAdapter(ToolsAdapter=new MainToolsAdapter(this));
                mTools.setItemAnimator(new DefaultItemAnimator());

                final Object[][] tools={
                  //Tag,Icon,name,Class
                    {"robot",R.drawable.ic_android_black,"聊天机器人",ChatRobotActivity.class},
                    {"flashlight",R.drawable.ic_flashlight,"手电筒",FlashLightActivity.class},
                    {"count",R.drawable.ic_contrast,"统计",null}
                  };
                for(int i=0;i<tools.length;i++){
                    MainTools mt=new MainTools();
                    Bitmap bm=BitmapFactory.decodeResource(getResources(),tools[i][1]);
                    if(set.isDark()){
                        int x=Colors.gray;
                        int y=Colors.white;
                        bm=new BitmapTools().setColor(x,y,bm);
                      }
                    mt.setBitmap(bm);
                    mt.setTAG(""+tools[i][0]);
                    mt.setDescribe(""+tools[i][2]);
                    ToolsAdapter.additem(mt);
                  }

                try{
                    ToolsAdapter.setOnItemClickListener(new MainToolsAdapter.OnItemClickListener(){
                        @Override
                        public void onClick(View view,int index){
                            MainTools mt=ToolsAdapter.getItemData(index);
                            Class obj=(Class)tools[index][3];
                            if(obj!=null){
                                //  Class css=obj.getClass();
                                Intent i=new Intent(ctx,obj);
                                try{
                                    ctx.startActivity(i);
                                  }catch(Exception e){
                                    e.printStackTrace();
                                  }
                              }else{
                                if(obj==null){
                                    Snackbar.make(mToolbar,"尚未添加，请耐心等待哦",Snackbar.LENGTH_SHORT)

                                    .show();
                                  }             
                              }
                          }
                      });
                  }catch(Exception e){
                    print(e);
                  }
              }catch(Exception e){
                print("工具初始化错误");
                print(e);
              }
            mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
            mToolbar=(Toolbar) findViewById(R.id.toolbar);
            mToolbar.setSubtitle("version:"+ctx.getPackageManager().getPackageInfo(getPackageName(),0).versionName);
            mNavigationView=(NavigationView) findViewById(R.id.navigationview);
            mNavigationView.inflateHeaderView(R.layout.activity_main_nv_menu);
            mNavigationView.inflateMenu(R.menu.menu_main_nav);
            NavigationViewListener(mNavigationView);
            setSupportActionBar(mToolbar);

            ActionBarDrawerToggle mActionbarDrawerToggle= new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
            mActionbarDrawerToggle.syncState();
            mDrawerLayout.setDrawerListener(mActionbarDrawerToggle);

            fab=(FloatingActionButton)findViewById(R.id.main_fab);
            fab .setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    if(use!=null&&use.getUsername()!=null){
                        startActivityForResult(new Intent(ctx,WritePostActivity.class),9483);
                      }else{
                        new AlertDialog.Builder(ctx)
                        .setTitle("失败")
                        .setMessage("你需要登录后才能发言")
                        .setPositiveButton("登录",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface p1,int p2){
                                startActivityForResult(new Intent(ctx,LoginActivity.class),0);
                              }
                          })
                        .setNegativeButton("关闭",null)
                        .setCancelable(false)
                        .show();
                      }
                  }       
              });


            refresh=(SwipeRefreshLayout)Post.findViewById(R.id.main_refresh);    
            refresh.setProgressViewOffset(false,0,new CommonUtil().dip2px(24f));
            refresh.setOnRefreshListener(new SwipeRefresh());
            //refresh.setProgressBackground(Color.parseColor("0xFFFFFFFF"));
            refresh.setColorSchemeResources(R.color.blue,
            R.color.orange,R.color.green,R.color.primary_dark_material_dark,R.color.primary_dark_material_light); 
            //refresh.setColorSchemeResources(android.R.colorholo_blue_ligh,android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light);
            //refresh.setRefreshing(true);
            refresh.setSize(SwipeRefreshLayout.DEFAULT);
            read(false);

          }catch(Exception e){
            print("\nError:"+e);
          }
        try{
            mHeaderLayout=mNavigationView.getHeaderView(0);
            nv_name=(TextView)mHeaderLayout.findViewById(R.id.main_nav_name);
            nv_header=(CircleImageView)mHeaderLayout.findViewById(R.id.main_nav_header);
            nv_header.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    try{
                        if(use!=null&&use.getUsername()!=null)
                          MainActivity.this.startActivityForResult(new Intent(ctx,UserActivity.class),0);
                        else
                          MainActivity.this.startActivityForResult(new Intent(ctx,LoginActivity.class),0);
                      }catch(Exception e){
                        print(e);
                      }
                  }
              });
            refreshUser();
          }catch(Exception e){
            print("Error:"+e);
          }
        try{
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                @Override
                public void onPageScrolled(int p1,float p2,int p3){}

                @Override
                public void onPageSelected(int p1){
                    //mToolbar.setTitle(mTitles[position]);
                    switch(p1){
                        case 0:
                          fab.setVisibility(View.VISIBLE);
                          fab.setOnClickListener(new OnClickListener(){
                              public void onClick(View v){
                                  if(use!=null&&use.getUsername()!=null){
                                      startActivityForResult(new Intent(ctx,WritePostActivity.class),9483);
                                    }else{
                                      new AlertDialog.Builder(ctx)
                                      .setTitle("失败")
                                      .setMessage("你需要登录后才能发言")
                                      .setPositiveButton("登录",new DialogInterface.OnClickListener(){
                                          @Override
                                          public void onClick(DialogInterface p1,int p2){
                                              startActivityForResult(new Intent(ctx,LoginActivity.class),0);
                                            }
                                        })
                                      .setNegativeButton("关闭",null)
                                      .setCancelable(false)
                                      .show();
                                    }
                                }
                            });
                          break;
                        case 1:
                          fab.setVisibility(View.GONE);
                          fab.setOnClickListener(null);
                          break;
                      }
                  }

                @Override
                public void onPageScrollStateChanged(int p1){}
              });


            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
          }catch(Exception e){
            print(e+"  initTabLayout&ViewPager");
          }

      }






    private AlertDialog.Builder showError(String title,String msg){
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        if(title!=null)
          build .setTitle(title);
        if(msg!=null)
          build.setMessage(msg);
        return build;
      }



    private class SwipeRefresh implements SwipeRefreshLayout.OnRefreshListener
      {
        @Override
        public void onRefresh(){
            logcat.println(this,"刷新贴子");
            if(net.isConnectedOrConnecting()){
                i=0;
                refreshing.postDelayed(run,1000);
                加载信息条数+=10;
                read(true);    
              }else{
                read(false);
                refresh.setRefreshing(false);
                if(REFRESH_HINT){
                    new AlertDialog.Builder(ctx)
                    .setTitle("没网了")
                    .setMessage("你必须连接网络才能刷新最新数据，无网将从缓存读取(不耗流量)")
                    .setPositiveButton("知道了",null)
                    .setNeutralButton("隐藏",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface p1,int p2){
                            REFRESH_HINT=false;
                          }              
                      })
                    .setNegativeButton("设置网络",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface p1,int p2){
                            Intent intent = null;
                            if(Build.VERSION.SDK_INT>10){  // 3.0以上
                                intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                              }else{
                                intent=new Intent();
                                intent.setClassName("com.android.settings","com.android.settings.WirelessSettings");
                              }
                            startActivity(intent);
                          }
                      }).show();
                  }else{
                    REFRESH_HINT=false;
                  }
              }
          }    
      }


    final Handler refreshing=new Handler();
    private int i=0;
    private Runnable run=new Runnable(){
        public void run(){
            i++;
            if(i==7&&refresh.isRefreshing()&&refresh!=null){
                refresh.setRefreshing(false);
                showError(null,"刷新超时(7秒)，请检查您的网络连接").show();
              }        
            if(refresh.isRefreshing()&&i<=7)
              refreshing.postDelayed(this,1000);
          }
      };






    private void NavigationViewListener(final NavigationView mNav){
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
          {
            @Override
            public boolean onNavigationItemSelected(MenuItem p1){
                switch(p1.getItemId()){
                    case R.id.关于:
                      startActivity(new Intent(ctx,AboutActivity.class));
                      break;
                    case R.id.反馈:
                      break;
                    case R.id.设置:
                      startActivityForResult(new Intent(ctx,SettingsActivity.class),0);
                      break;
                  }
                mDrawerLayout.closeDrawer(mNav);
                return true;
              }
          });
      }










    private void read(boolean b){
        synchronized(this){
            print("扫描…………");
            BmobQuery<Post> query = new BmobQuery<Post>();
            query.addWhereEqualTo("type","0");
            query.order("-createdAt");
            query.include("user,image,image2,image3");
            query.setLimit(加载信息条数);
            boolean isCache = query.hasCachedResult(Post.class);
            if(!b||!net.isConnectedOrConnecting()){//离线阅读
                if(isCache){
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
                  }else{
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                    print("无缓存");
                  }
              }else{
                if(FIRST_READ|!b){
                    FIRST_READ=false;
                    if(isCache){
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                      }else{
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                      }
                  }else{
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                  }
              }

            query.findObjects(new FindListener<Post>() {
                @Override
                public void done(List<Post> obj,BmobException er){
                    MTA.clearAll();
                    MTA.notifyDataSetChanged();
                    if(er==null){
                        int le=obj.size();
                        print("QuerySuccess,count:"+le);
                        for(int i=0;i<le;i++){
                            final Post m=obj.get(i);
                            new CommentCount().setPost(m).count(new CommentCount.CommentCountListener(){
                                @Override
                                public void done(int i,BmobException e){
                                    if(e==null){
                                        MTA.addPostCommentCount(m.getObjectId(),i);
                                        MTA.additem(m);
                                      }else{
                                        MTA.additem(m);
                                      }
                                  }                              
                              });
                          }

                        if(refresh.isRefreshing()&&refresh!=null)
                          refresh.setRefreshing(false);
                      }else{
                        ErrorMessage msg=new ErrorMessage();
                        print("QueryFailed:"+er.getErrorCode()+"  "+msg.getMessage(er.getErrorCode())+"\n"+er.getMessage());
                      }
                  }
              });

          }

      }


    private void print(Object o){
        Utils.print(this.getClass(),o);
      }
  }

