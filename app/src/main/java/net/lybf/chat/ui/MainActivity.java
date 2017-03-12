package net.lybf.chat.ui;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import java.io.File;
import java.util.List;
import net.lybf.chat.adapter.MainTieAdapter;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.R;
import net.lybf.chat.bmob.UpdateLog;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.system.update;
import net.lybf.chat.ui.MainActivity;
import net.lybf.chat.ui.SettingsActivity;
import net.lybf.chat.util.BitMapTools;
import net.lybf.chat.util.CommonUtil;
import net.lybf.chat.util.Network;
import net.lybf.chat.widget.CircleImageView;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import net.lybf.chat.adapter.MainPagerAdaptet;
import net.lybf.chat.util.CommentCount;
import org.json.JSONObject;
import net.lybf.chat.system.ActivityResultCode;

public class MainActivity extends AppCompatActivity
  {


    //public static String APPID ="4eaad1f155b7ed751472ed23e05bf084";
	//Tabs
	private String[] tabs={
	  "主页",
	  "社区"};
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
    //图片工具
    //private BitMapTools bitmaptools;
    private int 加载信息条数=30;
    private Bitmap tu;
    private boolean first=true;

    private Network net;
    private settings set;

	//RecyclerView
	private RecyclerView mRecyclerview;

	private MainPagerAdaptet mViewPagerAdapter;

	private boolean FIRST_READ=true;;

	private boolean REFRESH_HINT=true;
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
		//getSupportActionBar().hide();
		ctx=this;
        set=new settings();
		if(set.isDark()){
			setTheme(R.style.DarkTheme);
		  }else{
			setTheme(R.style.LightTheme);
		  }
        setContentView(R.layout.activity_main);
        try{
            if(19>=Build.VERSION.SDK_INT){
				//      setStatusBarTint(R.color.accent);
              }
          }catch(Exception e){
            print(e);
          }
        initView();

      }


    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
        .setTitle("退出?")
        .setMessage("你想去外面的世界看看？")
        .setPositiveButton("是",new ExitApp())
        .setNegativeButton("不",null)
        .setCancelable(false)
        .show();
      }

    private class ExitApp implements DialogInterface.OnClickListener
      {
        @Override
        public void onClick(DialogInterface p1,int p2){
            MainActivity.this.finish();
          }

      }

//右上角菜单  CteateMenu
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
              final String str=u.getDeviceInfo();
              new AlertDialog.Builder(this)
              .setTitle("设备信息")
              .setMessage(str)
              .setPositiveButton("复制",new DialogInterface.OnClickListener(){
				  @Override
				  public void onClick(DialogInterface p1,int p2){
					  Utils u=new Utils();
					  final String str=u.getDeviceInfo();
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
        read(true);
        if(requestCode==0&&resultCode==ActivityResultCode.USER_REFRESH){
			use=null;
            refreshUser();
          }
        super.onActivityResult(requestCode,resultCode,data);
      }










    private void 详情(){
        try{
            if(use!=null&&use.getUsername()!=null)
              startActivityForResult(new Intent(this,UserActivity.class),0);
            else
              startActivityForResult(new Intent(this,LoginActivity.class),0);
          }catch(Exception e){
            print(e);
          }
      }





    private void refreshUser(){
        use=BmobUser.getCurrentUser(MyUser.class);
        try{

            if(use!=null){
                try{
                    BmobFile icon=use.getIcon();
                    final String ic=icon.getFilename();
                    print("图片名:"+ic);
                    final File f=new File("/sdcard/lybf/MPSquare/.user/"+use.getObjectId()+"/"+ic);
					print("文件路径:"+f.getAbsolutePath());
                    if(!f.getParentFile().exists())
                      f.getParentFile().mkdirs();
                    if(f.exists()){
                        nv_header.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
                      }else{
                        icon.download(f.getAbsoluteFile(),new DownloadFileListener(){
                            @Override
                            public void done(String p1,BmobException p2){
                                if(p2==null){
                                    nv_header.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
                                  }else{
                                    System.out. println("下载失败:"+p2);
                                  }
                              }
                            @Override
                            public void onProgress(Integer p1,long p2){
								print("下载:"+ic+"到:"+f.getAbsolutePath()+"   进度:"+p1);
                              }   
                          });
                      }
                  }catch(Exception e){
                    print("错误。"+e);
                  }

                if(use.getUsername()!=null)
                  if(nv_name!=null)
                    nv_name.setText(""+use.getUsername());
                  else
                    print("控件空指针了");
                else
                  print("名字为空");
              }else{
                if(nv_name!=null){
					nv_name.setText("未登录，点击头像以登录");
					nv_header.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_user_header));
				  }
              }


          }catch(Exception e){
            print(e);
          }
      }



    private void update(){
        if(net.isNetWork()){
            print("\n检测更新中\n");
            BmobQuery<UpdateLog> q = new BmobQuery<UpdateLog>();
            q.order("-createdAt");
            q.setLimit(50);   
            q.findObjects(new FindListener<UpdateLog>() {
                @Override
                public void done(List<UpdateLog> object,BmobException er){
                    if(er==null){
                        print("\n检测更新成功\n");
                        UpdateLog u=object.get(object.size()-1);
                        if(u!=null){
                            Integer n=u.getVersionCode();
                            String name=u.getVersionName();
                            String title=u.getTile();
                            String msg=u.getMessage();
                            String ApkF=u.getApkFile();
                            try{
                                PackageManager pm = ctx.getPackageManager();  
                                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),0);  

                                int i=pi.versionCode;
                                String ii=pi.versionName;
                                if(i-n<0){
                                    final String m=u.getCreatedAt()
                                    +":\n当前版本号:"+i
                                    +"\n最新版本:"+n+
                                    "\n----------\n当前版本名:"+ii
                                    +"\n最新版本:"+name
                                    +"\n----------\n更新详情:"+msg
                                    +"\n----------\n下载地址:"+ApkF;
                                    showAppUpdateMessage(title,m);
                                  }
                              }catch(Exception e){
                                print(e);
                              }
                          }

                      }
                  }
              }
            );
          }else{

          }
      }


    private void showAppUpdateMessage(String title,String message){
        new AlertDialog.Builder(ctx)
        .setTitle(title)
        .setMessage((downloadMessage=message))
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




    private void updateApp(String title,String text,String ApkFile){
        Intent iii= new Intent();
        iii.setClass(ctx,MainActivity.class);
        iii.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent Pi=
        PendingIntent.getActivity(ctx,0,iii,0);

        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(this)
        .setTicker("应用更新")

        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(title)
        .setContentText(text)
		.setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
        .setContentIntent(Pi);
        // 发送通知
        NotificationManager nm = (NotificationManager) ctx.getSystemService(this.NOTIFICATION_SERVICE);
        Notification nms= mBuilder.build();
        nm.notify(0,nms);

      }



    private void initView(){
	    mViewPager=(ViewPager)findViewById(R.id.main_viewpage);
		mTabLayout=(TabLayout)findViewById(R.id.main_tabs);
		Post=LayoutInflater.from(this).inflate(R.layout.content_main_posts,null);
		MainPage=new View(this);
		mContent.add(Post);
		mContent.add(MainPage);
		mViewPagerAdapter=new MainPagerAdaptet(tabs,mContent);
		use=BmobUser.getCurrentUser(MyUser.class);
		net=new Network(this);
		try{
			mViewPager.setAdapter(mViewPagerAdapter);
			// 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
			mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
				@Override
				public void onPageScrolled(int p1,float p2,int p3){

				  }

				@Override
				public void onPageSelected(int p1){
					//mToolbar.setTitle(mTitles[position]);

				  }

				@Override
				public void onPageScrollStateChanged(int p1){
				  }

			  });


			//mTabLayout.setTabMode(MODE_SCROLLABLE);
			// 将TabLayout和ViewPager进行关联，让两者联动起来
			mTabLayout.setupWithViewPager(mViewPager);
			// 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
			mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
		  }catch(Exception e){
			print(e+"  initTabLayout&ViewPager");
		  }
        try{


			mRecyclerview=(RecyclerView)Post.findViewById(R.id.main_post_listview);
            mRecyclerview.setEnabled(true);
            mRecyclerview.setFocusable(true);


            LinearLayoutManager Manager = new LinearLayoutManager(this); 		
            Manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerview.setLayoutManager(Manager); 
            mRecyclerview.setAdapter((MTA=new MainTieAdapter(this)));
            mRecyclerview.setItemAnimator(new DefaultItemAnimator());


            mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
            mToolbar=(Toolbar) findViewById(R.id.toolbar);
            mNavigationView=(NavigationView) findViewById(R.id.navigationview);
            mNavigationView.inflateHeaderView(R.layout.activity_main_nv_menu);
            mNavigationView.inflateMenu(R.menu.menu_main_nav);
            NavigationViewListener(mNavigationView);
            setSupportActionBar(mToolbar);

            ActionBarDrawerToggle mActionbarDrawerToggle= new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
            mActionbarDrawerToggle.syncState();
            mDrawerLayout.setDrawerListener(mActionbarDrawerToggle);

            fab=(FloatingActionButton)Post.findViewById(R.id.main_fab_writePost);

            fab .setOnClickListener(new SendTie());


            refresh=(SwipeRefreshLayout)Post.findViewById(R.id.main_refresh);	
            refresh.setProgressViewOffset(false,0,new CommonUtil().dip2px(24f));
            refresh.setOnRefreshListener(new SwipeRefresh());
            //refresh.setProgressBackgronv_namedColor(0xFFFFFFFF);
            refresh.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue); 
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
                    详情();
                  }
              });
            refreshUser();
          }catch(Exception e){
            print("Error:"+e);
          }

      }



    private class SendTie implements OnClickListener
      {
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
                  }
                )
                .setNegativeButton("关闭",null)
                .setCancelable(false)
                .show();
              }
          }
      }


    private class setNetWork implements DialogInterface.OnClickListener
      {
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
            if(net.isNetWork()){
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
					.setNegativeButton("设置网络",new setNetWork())  .show();
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
            if(i==5&&refresh.isRefreshing()&&refresh!=null){
                refresh.setRefreshing(false);
                showError(null,"未知错误:刷新时间超过5秒，请检查您的网络连接").show();
              }		
            if(refresh.isRefreshing()&&i<=5)
              refreshing.postDelayed(this,1000);
          }
      };






    private void NavigationViewListener(NavigationView mNav){
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
		  {
			@Override
			public boolean onNavigationItemSelected(MenuItem p1){
				switch(p1.getItemId()){
					case R.id.检查更新:
					  update();
					  break;

					  //

					case R.id.关于:
					  new AlertDialog.Builder(ctx)
					  .setPositiveButton("加入群",new DialogInterface.OnClickListener(){
						  @Override
						  public void onClick(DialogInterface p1,int p2){
							  Uri uri = Uri.parse("http://jq.qq.com/?_wv=1027&k=41AHK2k");
							  Intent intent = new Intent(Intent.ACTION_VIEW,uri);
							  startActivity(intent);
							}
						}
					  )
					  .setNegativeButton("关闭",null)
					  .setView(R.layout.content_about)
					  .setCancelable(false)
					  .show();
					  break;

					  //

					case R.id.反馈:
					  break;

					  //

					case R.id.设置:
					  startActivity(new Intent(ctx,SettingsActivity.class));
					  break;
				  }
				return false;
			  }
		  }
		);
	  }










    private void read(boolean b){
        MTA.clearAll();
        MTA.notifyDataSetChanged();
        print("扫描…………");
        BmobQuery<Post> query = new BmobQuery<Post>();
		query.addWhereEqualTo("type","0");
        query.order("-createdAt");
        query.include("user");
		/* query.include("image");
		 query.include("image2");
		 query.include("image3");*/
        query.setLimit(加载信息条数);
		boolean isCache = query.hasCachedResult(Post.class);
		if(!b||!net.isNetWork()){//离线阅读
			if(isCache){
				//	query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
				query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
			  }else{
				query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
				showError(null,"无缓存").show();//提示无缓存，为何isCache为false？
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
                if(er==null){
					int le=obj.size();
                    print("查询成功............................共"+le+"条");
                    for(int i=0;i<le;i++){
                        final Post m=(Post)obj.get(i);

						new CommentCount().setPostID(m.getObjectId()).count(new CommentCount.CommentCountListener(){
							@Override
							public void done(int i,BmobException e){
								if(e==null){
									MTA.addPostCommentCount(m.getObjectId(),i);
									MTA.additem(m);
									print("增加数据:"+m.getObjectId());           
								  }else{
									MTA.additem(m);
									print("增加数据:"+m.getObjectId());           
									print("CountCommentE:"+e.getMessage());
								  }

							  }							  
						  });
                      }

                    if(refresh.isRefreshing()&&refresh!=null)
                      refresh.setRefreshing(false);
                  }else{
                    ErrorMessage msg=new ErrorMessage();
                    print("查询失败:"+er.getErrorCode()+"  "+msg.getMessage(er.getErrorCode())+"\n"+er.getMessage());
                  }
              }
          }
        );

      }


    private void print(Object o){
		new Utils().print(this.getClass(),o);
	  }






    public  void setStatusBarTint(int color){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(true);
		  }
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
	  }

    @TargetApi(19)
    private  void setTranslucentStatus(boolean on){
		Window win = this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if(on){
			winParams.flags|=bits;
		  }else{
			winParams.flags&=~bits;
		  }
		win.setAttributes(winParams);
	  }

  }

